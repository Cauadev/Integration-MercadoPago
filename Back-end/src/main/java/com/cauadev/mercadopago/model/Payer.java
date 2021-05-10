package com.cauadev.mercadopago.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payer {
	
	private String email;
	private Identification identification;

}
