package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.insignia.entity.AddressDetails;
import com.insignia.model.AddressResponse;

@Repository
public interface AddressRepository extends JpaRepository<AddressDetails, Serializable> {

	public final static String saveAddress = "Insert into address_details(customer_sequence_number,address_line_1,address_line_2,landmark,city,state,country,Zip_code,mobile_number,email_id,is_default_address,is_billing_address,landline_number)values(:customer_sequence_number,:address_line_1,:address_line_2,:landmark,:city,:state,:country,:Zip_code,:mobile_number,:email_id,:is_default_address,:is_billing_address,:landline_number)";
	public final static String deleteByAddressId = "Delete from address_details where sequence_number = :sequence_number";
	public final static String deleteAddressForCustomer = "DELETE FROM address_details WHERE customer_sequence_number = :customer_sequence_number";
	public final static String getAddress = "select * from address_details WHERE customer_sequence_number = :customer_sequence_number";
	public final static String isTokenValid = "SELECT token_expires_at FROM tokens_table WHERE customer_sequence_number = :customer_sequence_number AND token_type = 'JWT' AND token_expires_at > CURRENT_TIMESTAMP";

	@Transactional
	@Modifying
	@Query(value = saveAddress, nativeQuery = true)
	public void saveAddress(@Param("customer_sequence_number") Long customer_sequence_number,
			@Param("address_line_1") String addressLine1, @Param("address_line_2") String addressLine2,
			@Param("landmark") String landmark, @Param("city") String city, @Param("state") String state,
			@Param("country") String country, @Param("Zip_code") String zipCode,
			@Param("mobile_number") Long mobileNumber, @Param("email_id") String emailAddress,
			@Param("is_default_address") Boolean isDefaultAddress,
			@Param("is_billing_address") Boolean isBillingAddress, @Param("landline_number") Long landlineNumber);

	@Transactional
	@Modifying
	@Query(value = deleteByAddressId, nativeQuery = true)
	public void deleteByAddressId(@Param("sequence_number") Integer sequenceNumber);

	@Transactional
	@Modifying
	@Query(value = deleteAddressForCustomer, nativeQuery = true)
	public void deleteAddressForCustomer(@Param("customer_sequence_number") Long customer_sequence_number);

	@Query(value = getAddress, nativeQuery = true)
	public List<AddressDetails> getAddressList(@Param("customer_sequence_number") Long customer_sequence_number);

	@Query(value = isTokenValid, nativeQuery = true)
	public List<Object[]> isTokenNotValid(Long customer_sequence_number);

}