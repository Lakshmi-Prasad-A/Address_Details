package com.insignia.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.AddressDaoInterface;
import com.insignia.entity.AddressDetails;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;
import com.insignia.repo.AddressRepository;
import com.insignia.stringConstant.AddressValidatorConstants;

@Repository
public class AddressDaoImpl implements AddressDaoInterface {

	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private EntityManager entityManager;

	public AddressDaoImpl(AddressRepository addressRepo, EntityManager entityManager) {
		super();
		this.addressRepo = addressRepo;
		this.entityManager = entityManager;

	}

	@Override
	public AddressResponse saveAddress(AddressRequest addressReq) throws InvalidInputParametersException {
try {
		addressRepo.saveAddress(Long.valueOf(addressReq.getCustomer_sequence_number()), addressReq.getAddressLine1(),
				addressReq.getAddressLine2(), addressReq.getLandmark(), addressReq.getCity(), addressReq.getState(),
				addressReq.getCountry(), addressReq.getZipCode(), Long.valueOf(addressReq.getMobileNumber()),
				addressReq.getEmailId(), addressReq.getIsDefaultAddress(), addressReq.getIsBillingAddress(),Long.valueOf(addressReq.getLandlineNumber()));
		
} catch (DataIntegrityViolationException e) {
	throw new InvalidInputParametersException(AddressValidatorConstants.validateAddressErrorCode,
			AddressValidatorConstants.validateAddressMessage);
}
		AddressResponse addressRes = new AddressResponse();
		BeanUtils.copyProperties(addressReq, addressRes);

		return addressRes;
	}

	@Override
	public AddressResponse updateAddress(AddressRequest addressReq) throws InvalidInputParametersException {
try {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("UPDATE address_details SET ");

		if (addressReq.isAddressLine1Updated()) {
			queryBuilder.append("address_line_1= '" + addressReq.getAddressLine1()).append("'");

		}
		if (addressReq.isAddressLine2Updated()) {
			queryBuilder.append(",address_line_2= '" + addressReq.getAddressLine2()).append("'");

		}

		if (addressReq.isLandMarkUpdated()) {
			queryBuilder.append(",landmark= '" + addressReq.getLandmark()).append("'");

		}

		if (addressReq.isCityUpdated()) {
			queryBuilder.append(",city= '" + addressReq.getCity()).append("'");

		}
		if (addressReq.isStateUpdated()) {
			queryBuilder.append(",state= '" + addressReq.getState()).append("'");

		}
		if (addressReq.isCountryUpdated()) {
			queryBuilder.append(",country= '" + addressReq.getCountry()).append("'");

		}
		if (addressReq.isZipCodeUpdated()) {
			queryBuilder.append(",Zip_code= '" + addressReq.getZipCode()).append("'");

		}
		if (addressReq.isMobileNumberUpdated()) {
			queryBuilder.append(",mobile_number= '" + addressReq.getMobileNumber()).append("'");

		}
		if (addressReq.isLandLineNumberUpdated()) {
			queryBuilder.append(",landline_number= '" + addressReq.getLandlineNumber()).append("'");

		}
		if (addressReq.isEmailAddressUpdated()) {
			queryBuilder.append(",email_id= '" + addressReq.getEmailId()).append("'");

		}
		if (addressReq.isDefaultAddressUpdated()) {
			queryBuilder.append(",is_default_address= '" + addressReq.getIsDefaultAddress()).append("'");

		}
		if (addressReq.isBillingAddressUpdated()) {
			queryBuilder.append(",is_billing_address= '" + addressReq.getIsBillingAddress()).append("'");

		}

		queryBuilder.append(" WHERE customer_sequence_number = :customer_sequence_number");

		String queryString = queryBuilder.toString();

		Query query = entityManager.createNativeQuery(queryString);

		query.setParameter("customer_sequence_number", addressReq.getCustomer_sequence_number());

		query.executeUpdate();

} catch (DataIntegrityViolationException e) {
	throw new InvalidInputParametersException(AddressValidatorConstants.validateAddressErrorCode,
			AddressValidatorConstants.validateAddressMessage);
}

		String selectQueryString = "SELECT * FROM address_details WHERE sequence_number = :sequence_number";

		Query selectQuery = entityManager.createNativeQuery(selectQueryString, AddressDetails.class);

		selectQuery.setParameter("sequence_number", addressReq.getSequenceNumber());

		AddressDetails updatedAddress = (AddressDetails) selectQuery.getSingleResult();

		AddressResponse response = new AddressResponse();

		response.setAddressLine1(updatedAddress.getAddressLine1());
		response.setAddressLine2(updatedAddress.getAddressLine2());
		response.setLandmark(updatedAddress.getLandmark());
		response.setCity(updatedAddress.getCity());
		response.setState(updatedAddress.getState());
		response.setCountry(updatedAddress.getCountry());
		response.setZipCode(updatedAddress.getZipCode());
		response.setMobileNumber(updatedAddress.getMobileNumber());
		response.setLandlineNumber(updatedAddress.getLandlineNumber());
		response.setEmailId(updatedAddress.getEmailId());
		response.setDefaultAddress(updatedAddress.isDefaultAddress());
		response.setBillingAddress(updatedAddress.isBillingAddress());

		return response;
}

		
	

	@Override
	public void deleteByAddressId(Integer sequenceNumber) {
		addressRepo.deleteByAddressId(sequenceNumber);

	}

	@Override
	public void deleteAddressForCustomer(Long customer_sequence_number) {
		addressRepo.deleteAddressForCustomer(customer_sequence_number);

	}

	@Override
	public List<AddressDetails> getAddressList(Long customer_sequence_number) {
		return addressRepo.getAddressList(customer_sequence_number);
	}

	public Boolean isTokenNotValid(Long customer_sequence_number) {
		return addressRepo.isTokenNotValid(customer_sequence_number).isEmpty();
	}

}
