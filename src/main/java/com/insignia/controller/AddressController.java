package com.insignia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.addressValidator.AddressValidator;
import com.insignia.constant.AddressStringConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;
import com.insignia.serviceInterface.AddressServiceInterface;
import com.insignia.stringConstant.AddressValidatorConstants;

@RestController
public class AddressController {

	@Autowired
	private AddressServiceInterface serviceRepo;

	@PostMapping("/saveAddress")
	public ResponseEntity<?> saveAddress(@RequestBody AddressRequest addressReq) {
		try {

			AddressValidator.ValidateAddressLine1(addressReq.getAddressLine1(),
					AddressStringConstant.addresLine1length);
			AddressValidator.ValidateCity(addressReq.getCity(), AddressStringConstant.city);
			AddressValidator.ValidateState(addressReq.getState(), AddressStringConstant.state);
			AddressValidator.ValidateCountry(addressReq.getCountry(), AddressStringConstant.country);
			AddressValidator.ValidateZipCode(addressReq.getZipCode(), AddressStringConstant.zipCode);

			return ResponseEntity.ok(serviceRepo.saveAddress(addressReq));
	
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(ex.getErrorCode(), ex.getStrMsg()));
			
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddressResponse(
					AddressValidatorConstants.validateTokenErrorCode, AddressValidatorConstants.validateToken));

		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(AddressValidatorConstants.validateUnexpectedErrorCode,
							AddressValidatorConstants.validateUnexpectedErrorMessage));
		}}


	
	@PutMapping("/updateAddress")
	public ResponseEntity<?> updateAddress(@RequestBody AddressRequest addressReq) {
		try {
			AddressValidator.ValidateAddressLine1(addressReq.getAddressLine1(),
					AddressStringConstant.addresLine1length);
			AddressValidator.ValidateCity(addressReq.getCity(), AddressStringConstant.city);
			AddressValidator.ValidateState(addressReq.getState(), AddressStringConstant.state);
			AddressValidator.ValidateCountry(addressReq.getCountry(), AddressStringConstant.country);
			AddressValidator.ValidateZipCode(addressReq.getZipCode(), AddressStringConstant.zipCode);
			serviceRepo.updateAddress(addressReq);
			return ResponseEntity.ok("Successfully");

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(ex.getErrorCode(), ex.getStrMsg()));
		
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddressResponse(
					AddressValidatorConstants.validateTokenErrorCode, AddressValidatorConstants.validateToken));
		}}
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new AddressResponse(AddressValidatorConstants.validateUnexpectedErrorCode,
//							AddressValidatorConstants.validateUnexpectedErrorMessage));
//		}}
	

	@DeleteMapping("/deleteAddress/{sequenceNumber}/{customer_sequence_number}")
	public ResponseEntity<?> deleteByAddressId(@PathVariable Integer sequenceNumber,
			@PathVariable Long customer_sequence_number) {
		try {

			serviceRepo.deleteByAddressId(sequenceNumber, customer_sequence_number);
			return ResponseEntity.ok("Record Successfully Deleted");

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddressResponse(
					AddressValidatorConstants.validateTokenErrorCode, AddressValidatorConstants.validateToken));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(AddressValidatorConstants.validateUnexpectedErrorCode,
							AddressValidatorConstants.validateUnexpectedErrorMessage));
		}
	}

	@DeleteMapping("/deleteCustomer/{customer_sequence_number}")
	public ResponseEntity<?> deleteAddressForCustomer(@PathVariable Long customer_sequence_number){
		try {

			serviceRepo.deleteAddressForCustomer(customer_sequence_number);
			return ResponseEntity.ok("Record Successfully Deleted");

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddressResponse(
					AddressValidatorConstants.validateTokenErrorCode, AddressValidatorConstants.validateToken));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(AddressValidatorConstants.validateUnexpectedErrorCode,
							AddressValidatorConstants.validateUnexpectedErrorMessage));
		}

	}

	@GetMapping("/getAddress/{customer_sequence_number}")
	public ResponseEntity<?> getAddressList(@PathVariable Long customer_sequence_number)  {
		
		try {
			return ResponseEntity.ok(serviceRepo.getAddressList(customer_sequence_number));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddressResponse(
					AddressValidatorConstants.validateTokenErrorCode, AddressValidatorConstants.validateToken));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(AddressValidatorConstants.validateUnexpectedErrorCode,
							AddressValidatorConstants.validateUnexpectedErrorMessage));
		}
	}

}
