package com.cauadev.mercadopago.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Identification {
	
	private String type;
	private String number;

}
