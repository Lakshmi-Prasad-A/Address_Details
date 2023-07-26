package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.AddressDaoInterface;
import com.insignia.entity.AddressDetails;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;

@ExtendWith(MockitoExtension.class)
public class TestAddressServiceImpl {

	@InjectMocks
	private AddressServiceImpl serviceImpl;

	@Mock
	private AddressDaoInterface daoRepo;

	AddressRequest addressRequest = new AddressRequest();
	AddressResponse addressRes = new AddressResponse();
	AddressDetails addressDetails = new AddressDetails();

	@BeforeEach
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
	void testTokenExpiredException() {
		Integer sequenceNumber = 1;
		Long customer_sequence_number = 8L;
		dataInitilization();

		when(daoRepo.isTokenNotValid(customer_sequence_number)).thenReturn(true);

		assertThrows(TokenExpiredException.class, () -> {
			serviceImpl.saveAddress(addressRequest);
		});

		assertThrows(TokenExpiredException.class, () -> {
			serviceImpl.updateAddress(addressRequest);
		});
		assertThrows(TokenExpiredException.class, () -> {
			serviceImpl.deleteByAddressId(sequenceNumber, customer_sequence_number);
		});

		assertThrows(TokenExpiredException.class, () -> {
			serviceImpl.deleteAddressForCustomer(customer_sequence_number);
		});

		assertThrows(TokenExpiredException.class, () -> {
			serviceImpl.getAddressList(customer_sequence_number);
		});
	}

	@Test
	void testSaveAddress() throws TokenExpiredException, InvalidInputParametersException {
		Long customer_sequence_number = 8L;
		dataInitilization();

		when(daoRepo.isTokenNotValid(customer_sequence_number)).thenReturn(false);
		daoRepo.isTokenNotValid(customer_sequence_number);

		serviceImpl.saveAddress(addressRequest);

	}

	@Test
	void testUpdateAddress() throws TokenExpiredException, InvalidInputParametersException {
		Long customer_sequence_number = 8L;
		dataInitilization();

		when(daoRepo.isTokenNotValid(customer_sequence_number)).thenReturn(false);
		daoRepo.isTokenNotValid(customer_sequence_number);

		serviceImpl.updateAddress(addressRequest);

	}

	@Test
	void testDeleteByAddressId() throws TokenExpiredException {
		Integer sequenceNumber = 1;
		Long customer_sequence_number = 8L;

		when(daoRepo.isTokenNotValid(customer_sequence_number)).thenReturn(false);
		daoRepo.isTokenNotValid(customer_sequence_number);

		serviceImpl.deleteByAddressId(sequenceNumber, customer_sequence_number);

	}

	@Test
	void testDeleteAddressForCustomer() throws TokenExpiredException {

		Long customer_sequence_number = 8L;

		dataInitilization();

		when(daoRepo.isTokenNotValid(customer_sequence_number)).thenReturn(false);
		daoRepo.isTokenNotValid(customer_sequence_number);

		serviceImpl.deleteAddressForCustomer(customer_sequence_number);

	}

	@Test
	void testGetAddressList() throws TokenExpiredException {

		Long customer_sequence_number = 8L;
		dataInitilization();

		when(daoRepo.isTokenNotValid(customer_sequence_number)).thenReturn(false);
		daoRepo.isTokenNotValid(customer_sequence_number);

		serviceImpl.getAddressList(customer_sequence_number);

	}

}
