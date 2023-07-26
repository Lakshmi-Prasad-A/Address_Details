package com.insignia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="address_details")
public class AddressDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sequence_number")
	private Integer sequenceNumber;
	
	
	@Column(name = "customer_sequence_number")
	private Long customer_sequence_number;
	
	
	@Column(name="address_line_1")
    private String addressLine1;

	@Column(name="address_line_2")
    private String addressLine2;
	
	@Column(name="landmark")
    private String landmark;

	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;

	@Column(name="country")
	private String country;	
	
	@Column(name="Zip_code",length=6,nullable=false)
    private String zipCode;
	
	@Column(name="mobile_number",length=10)
    private Long mobileNumber;

	@Column(name="landline_number",length=16)
    private Long landlineNumber;

    @Column(name="email_id")
    private String emailId;
    
    @Column(name="is_default_address")
    private boolean isDefaultAddress;
    
    @Column(name="is_billing_address")
    private boolean isBillingAddress;
    
}
