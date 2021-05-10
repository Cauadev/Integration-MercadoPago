package com.cauadev.mercadopago.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cauadev.mercadopago.model.Status;
import com.cauadev.mercadopago.model.StatusPayment;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;

@RestController
@CrossOrigin("*")
public class PaymentController {
	
	private static String ACCESS_TOKEN_ENV = "ACCESS_TOKEN_ENV";
	
	@PostMapping("/process")
	public ResponseEntity<?> executePayment(HttpServletRequest request) throws MPException{
		
		String token = request.getParameter("token");
		String payment_method_id = request.getParameter("payment_method_id");
		int installments = Integer.valueOf(request.getParameter("installments"));
		String issuer_id = request.getParameter("issuer_id");

		MercadoPago.SDK.setAccessToken(ACCESS_TOKEN_ENV);
		Payment payment = new Payment();
		payment.setTransactionAmount(100f)
		       .setToken(token)
		       .setDescription("Blue shirt")
		       .setInstallments(installments)
		       .setPaymentMethodId(payment_method_id)
		       .setIssuerId(issuer_id)
		       .setPayer(new com.mercadopago.resources.datastructures.payment.Payer()
		         .setEmail("john@yourdomain.com"));
		// Armazena e envia o pagamento
		payment.save();
		// Imprime o status do pagamento
		System.out.println(payment.getId());
		
		Status status_payment = new Status(payment.getStatus().name(), payment.getStatusDetail());
		
		return ResponseEntity.ok(status_payment);
	}
	
    
    
	@PostMapping("/status")
	public ResponseEntity<?> getPaymentStatus(@RequestParam("id") String id){
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer "+ACCESS_TOKEN_ENV);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		 ResponseEntity<StatusPayment> res = restTemplate.exchange("https://api.mercadopago.com/v1/payments/"+id
				, HttpMethod.GET
				, entity
				, StatusPayment.class);
		 
		 
		return ResponseEntity.ok(res.getBody());
	}

}
