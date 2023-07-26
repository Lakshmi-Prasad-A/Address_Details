package com.insignia.serviceInterface;

import java.util.List;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.entity.AddressDetails;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;

public interface AddressServiceInterface {

	public AddressResponse saveAddress(AddressRequest addressReq)
			throws TokenExpiredException, InvalidInputParametersException;

	public AddressResponse updateAddress(AddressRequest addressReq)
			throws TokenExpiredException, InvalidInputParametersException;

	public void deleteByAddressId(Integer sequenceNumber, Long customer_sequence_number) throws TokenExpiredException;

	public void deleteAddressForCustomer(Long customer_sequence_number) throws TokenExpiredException;

	public List<AddressDetails> getAddressList(Long customer_sequence_number) throws TokenExpiredException;

}
