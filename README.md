# Integração MercadoPago

Repositório com intuito de ajudar na integração de sua aplicacão Spring com o MercadoPago 🙂

## Tecnologias

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Lombok](https://projectlombok.org/)
* [Maven](http://maven.apache.org/)
* [DevTools](https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-devtools.html)
* [SDK MercadoPago](https://github.com/mercadopago/sdk-java)

### Referências
https://www.mercadopago.com.br/developers/pt/guides/online-payments/web-tokenize-checkout/introduction

### Pastas
O repositório contém o frontend e backend. o front tem somente um index.html onde fica o button para testarmos a aplicação.
Já no back, temos o servidor onde irá fazer as requisições e o tratamento dos dados.

### Credencial
Para obter os tokens clique [aqui](https://www.mercadopago.com.br/developers/panel/credentials/)

## Frontend

Adicionando o botao que ao clicar ira abrir um modal com o formulario dos dados
```html
<form action="http://localhost:8081/process" method="POST"> //Endpoint do backend que sera enviado os dados
    <script
    src="https://www.mercadopago.com.br/integrations/v1/web-tokenize-checkout.js" //url do MP que ira carregar as funções do formulário
    data-public-key="ACCESS_TOKEN_ENV" //seu PUBLIC_TOKEN
    data-transaction-amount="30.00"> //Valor que ira aparecer no formulario     OBS:não é o valor que o backend irá receber.
    </script>
</form>
```

## Backend

primeiro vá na class [PaymentContoller](https://github.com/Cauadev/Integration-MercadoPago/blob/master/Back-end/src/main/java/com/cauadev/mercadopago/controller/PaymentController.java) e mude o valor da variável 
```java
private static String ACCESS_TOKEN_ENV = "ACCESS_TOKEN_ENV";
```
#### Endpoints

* ``http://localhost:8081/process``
URL onde sera executada a requisição do pagamento.
```java
//Recebendo valores enviados pelo frontend e armazenando em variáveis

String token = request.getParameter("token");
String payment_method_id = request.getParameter("payment_method_id");
int installments = Integer.valueOf(request.getParameter("installments"));
String issuer_id = request.getParameter("issuer_id");
```

Setando o token de acesso.
```java
MercadoPago.SDK.setAccessToken(ACCESS_TOKEN_ENV);
```

```java
//realizando a requisicao.
payment.save();

//obtendo o status da requisicao e carredando ela em uma variavel
Status status_payment = new Status(payment.getStatus().name(), payment.getStatusDetail());

return ResponseEntity.ok(status_payment);
```

* ``http://localhost:8081/status``
URL onde sera executada a requisição para verificar o status do pagamento.

Neste endpoint deve ser passado id do pedido como parametro.
ex: ``/status?id=23445...``

Para realizar este tipo de requisição devemos passar no header o TOKEN de acesso.
```java
headers.add("Authorization", "Bearer "+ACCESS_TOKEN_ENV);
```

E por fim, obtendo o status retornamos ele
```java
ResponseEntity<StatusPayment> res = restTemplate.exchange("https://api.mercadopago.com/v1/payments/"+id  //url que ira nos retornar o status
  , HttpMethod.GET
  , entity
  , StatusPayment.class);


return ResponseEntity.ok(res.getBody());
```


