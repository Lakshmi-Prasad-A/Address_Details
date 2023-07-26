package com.insignia.daoInterface;

import java.util.List;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.AddressDetails;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;

public interface AddressDaoInterface {

	public AddressResponse saveAddress(AddressRequest addressReq) throws InvalidInputParametersException;

	public AddressResponse updateAddress(AddressRequest addressReq) throws InvalidInputParametersException;

	public void deleteAddressForCustomer(Long customer_sequence_number);

	public void deleteByAddressId(Integer sequenceNumber);

	public List<AddressDetails> getAddressList(Long customerSequenceNumber);

	public Boolean isTokenNotValid(Long customer_sequence_number);

}
