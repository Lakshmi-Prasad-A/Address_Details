package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.addressValidator.AddressValidator;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.AddressDaoInterface;
import com.insignia.entity.AddressDetails;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;
import com.insignia.serviceInterface.AddressServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestAddressController {

	@InjectMocks
	private AddressController addressController;

	@Mock
	private AddressDaoInterface addressDao;

	@Mock
	private AddressServiceInterface serviceRepo;
	
	@Mock
	private TokenExpiredException tokenExpire;
	
	@InjectMocks
    private AddressValidator addressValidator;

	AddressRequest addressRequest = new AddressRequest();
	AddressResponse addressRes = new AddressResponse();
	AddressDetails addressDetails = new AddressDetails();
	
	public void dataInitilization() {
		addressRequest.setCustomer_sequence_number(8L);
		addressRequest.setAddressLine1("VinayakaTemple");
		addressRequest.setAddressLine2("CinemaRoad");
		addressRequest.setLandmark("Opp:ApolloHospital");
		addressRequest.setCity("Kakinada");
		addressRequest.setState("AndhraPradesh");
		addressRequest.setCountry("India");
		addressRequest.setEmailId("lakshmisidharth678@gmail.com");
		addressRequest.setZipCode("8765");
		addressRequest.setMobileNumber(9875689378L);
		addressRequest.setLandlineNumber(98765895L);
		addressRequest.setisBillingAddress(false);
		addressRequest.setIsDefaultAddress(false);

		addressRes.setAddressLine1("VinayakaTemple");
		addressRes.setAddressLine2("CinemaRoad");
		addressRes.setLandmark("Opp:ApolloHospital");
		addressRes.setCity("Kakinada");
		addressRes.setState("AndhraPradesh");
		addressRes.setCountry("India");
		addressRes.setEmailId("lakshmisidharth678@gmail.com");
		addressRes.setZipCode("8765");
		addressRes.setMobileNumber(9875689378L);
		addressRes.setLandlineNumber(98765895L);
		addressRes.setBillingAddress(false);
		addressRes.setDefaultAddress(false);

		
	}
	
	 @Test
	    public void testSaveAddress() {
				dataInitilization();
				addressRequest.setAddressLine1(null);
				addressRequest.setCity(null);
				addressRequest.setState(null);
				addressRequest.setCountry(null);
				addressRequest.setZipCode(null);
				
				ResponseEntity<?> saveAddress = addressController.saveAddress(addressRequest);
				assertEquals(HttpStatus.BAD_REQUEST,saveAddress.getStatusCode());
				
				ResponseEntity<?> updateAddress = addressController.updateAddress(addressRequest);
				assertEquals(HttpStatus.BAD_REQUEST,updateAddress.getStatusCode());
				
			}	 
	 
	 @Test
	    public void testForTokenExpired() throws TokenExpiredException {
				dataInitilization();
				Integer sequenceNumber = 1;
				Long customer_sequence_number = 8L;
				
				when(addressController.saveAddress(addressRequest)).thenThrow(new TokenExpiredException(""));	
				ResponseEntity<?> saveAddress = addressController.saveAddress(addressRequest);
				
				when(addressController.updateAddress(addressRequest)).thenThrow(new TokenExpiredException(""));	
				addressController.updateAddress(addressRequest);
				
				when(addressController.deleteByAddressId(sequenceNumber, customer_sequence_number)).thenThrow(new TokenExpiredException(""));	
				addressController.deleteByAddressId(sequenceNumber, customer_sequence_number);
				
				when(addressController.deleteAddressForCustomer(customer_sequence_number)).thenThrow(new TokenExpiredException(""));	
				addressController.deleteAddressForCustomer(customer_sequence_number);
				
				when(addressController.getAddressList(customer_sequence_number)).thenThrow(new TokenExpiredException(""));	
				addressController.getAddressList(customer_sequence_number);
				
				assertEquals(HttpStatus.BAD_REQUEST,saveAddress.getStatusCode());
	 }
	        
	 @Test
	    public void testForDataIntegrityViolationException() {
				dataInitilization();
				
				when(addressController.saveAddress(addressRequest)).thenThrow(new DataIntegrityViolationException(""));	
				ResponseEntity<?> saveAddress = addressController.saveAddress(addressRequest);
				
				when(addressController.updateAddress(addressRequest)).thenThrow(new DataIntegrityViolationException(""));	
				addressController.updateAddress(addressRequest);
				
				assertEquals(HttpStatus.BAD_REQUEST,saveAddress.getStatusCode());
				
	 }
	 
	 @Test
	    public void testForException() {
				dataInitilization();
				Integer sequenceNumber = 1;
				Long customer_sequence_number = 8L;
				
				when(addressController.saveAddress(addressRequest)).thenThrow(new NullPointerException(""));	
				ResponseEntity<?> saveAddress = addressController.saveAddress(addressRequest);
				
				when(addressController.updateAddress(addressRequest)).thenThrow(new NullPointerException(""));	
				addressController.updateAddress(addressRequest);
				
				when(addressController.deleteByAddressId(sequenceNumber, customer_sequence_number)).thenThrow(new NullPointerException(""));	
				addressController.deleteByAddressId(sequenceNumber, customer_sequence_number);
				
				when(addressController.deleteAddressForCustomer(customer_sequence_number)).thenThrow(new NullPointerException(""));	
				addressController.deleteAddressForCustomer(customer_sequence_number);
				
				when(addressController.getAddressList(customer_sequence_number)).thenThrow(new NullPointerException(""));	
				addressController.getAddressList(customer_sequence_number);
				
				assertEquals(HttpStatus.BAD_REQUEST,saveAddress.getStatusCode());
	 }

	 

}
