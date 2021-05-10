package com.cauadev.mercadopago.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusPayment {
	
	private String description;
	private String status;
	private String status_detail;
	private String payment_method_id;
	private String payment_type_id;
	private Float transaction_amount;
	private String date_approved;
	private String date_created;
	

}
