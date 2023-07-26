package com.insignia.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_basic_details")
public class CustomerBasicDetailsEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "customer_sequence_number")
	private Long customerSequenceNumber;

	@Column(name = "application_id")
	private String applicationId;

	@Column(name = "tenant_id")
	private String tenantId;

	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "customer_password")
	private String customerPassword;

	@Column(name = "customer_email")
	private String customerEmail;

	private String userName;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_sequence_number")
	private List<AddressDetails> addressDetails = new ArrayList<>();

	public CustomerBasicDetailsEntity(String applicationId, String tenantId, String customerId, String customerPassword,
			String customerEmail, String userName, 
			List<AddressDetails> addressDetails) {
		super();
		this.applicationId = applicationId;
		this.tenantId = tenantId;
		this.customerId = customerId;
		this.customerPassword = customerPassword;
		this.customerEmail = customerEmail;
		this.userName = userName;
		this.addressDetails = addressDetails;
	}

}

