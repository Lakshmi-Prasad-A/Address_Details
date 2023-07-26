package com.insignia.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.AddressDaoInterface;
import com.insignia.entity.AddressDetails;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;
import com.insignia.serviceInterface.AddressServiceInterface;
import com.insignia.stringConstant.AddressValidatorConstants;

@Service
public class AddressServiceImpl implements AddressServiceInterface {

	@Autowired
	private AddressDaoInterface daoRepo;

	@Override
	public AddressResponse saveAddress(AddressRequest addressReq)
			throws TokenExpiredException, InvalidInputParametersException {
		if (isTokenNotValid(addressReq.getCustomer_sequence_number()))
			throw new TokenExpiredException(AddressValidatorConstants.validateTokenErrorCode,
					AddressValidatorConstants.validateToken);

		return daoRepo.saveAddress(addressReq);
	}

	@Transactional
	@Override
	public AddressResponse updateAddress(AddressRequest addressReq)
			throws TokenExpiredException, InvalidInputParametersException {
		if (isTokenNotValid(addressReq.getCustomer_sequence_number()))
			throw new TokenExpiredException(AddressValidatorConstants.validateTokenErrorCode,
					AddressValidatorConstants.validateToken);

		return daoRepo.updateAddress(addressReq);

	}

	@Override
	public void deleteByAddressId(Integer sequenceNumber, Long customer_sequence_number) throws TokenExpiredException {
		if (isTokenNotValid(customer_sequence_number))
			throw new TokenExpiredException(AddressValidatorConstants.validateTokenErrorCode,
					AddressValidatorConstants.validateToken);
		daoRepo.deleteByAddressId(sequenceNumber);

	}

	@Override
	public void deleteAddressForCustomer(Long customer_sequence_number) throws TokenExpiredException {
		if (isTokenNotValid(customer_sequence_number))
			throw new TokenExpiredException(AddressValidatorConstants.validateTokenErrorCode,
					AddressValidatorConstants.validateToken);

		daoRepo.deleteAddressForCustomer(customer_sequence_number);

	}

	@Override
	public List<AddressDetails> getAddressList(Long customer_sequence_number) throws TokenExpiredException {
		if (isTokenNotValid(customer_sequence_number))
			throw new TokenExpiredException(AddressValidatorConstants.validateTokenErrorCode,
					AddressValidatorConstants.validateToken);

		return daoRepo.getAddressList(customer_sequence_number);
	}

	private Boolean isTokenNotValid(Long customer_sequence_number) {
		return daoRepo.isTokenNotValid(customer_sequence_number);
	}
}
