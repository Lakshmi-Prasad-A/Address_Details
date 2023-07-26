package com.insignia.daoImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.AddressDetails;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;
import com.insignia.repo.AddressRepository;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class TestAddressDaoImpl {


	@Mock
	private AddressRepository addressRepo;

	@InjectMocks
	private AddressDaoImpl addressDaoImpl;

	
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
		addressRequest.setZipCode("8765");
		addressRequest.setMobileNumber(9875689378L);
		addressRequest.setEmailId("lakshmisidharth678@gmail.com");
		addressRequest.setisBillingAddress(false);
		addressRequest.setIsDefaultAddress(false);
		addressRequest.setLandlineNumber(98765895L);

		addressRes.setAddressLine1("VinayakaTemple");
		addressRes.setAddressLine2("CinemaRoad");
		addressRes.setLandmark("Opp:ApolloHospital");
		addressRes.setCity("Kakinada");
		addressRes.setState("AndhraPradesh");
		addressRes.setCountry("India");
		addressRes.setZipCode("8765");
		addressRes.setMobileNumber(9875689378L);
		addressRes.setEmailId("lakshmisidharth678@gmail.com");
		addressRes.setBillingAddress(false);
		addressRes.setDefaultAddress(false);
		addressRes.setLandlineNumber(98765895L);


	}

	@Test
	public void testUpdateAddress() throws InvalidInputParametersException {

		EntityManager entityManager = mock(EntityManager.class);

		Query selectQuery = mock(Query.class);
		Query nativeQuery = mock(Query.class);

		addressDaoImpl = new AddressDaoImpl(addressRepo, entityManager);

		when(entityManager.createNativeQuery(anyString())).thenReturn(nativeQuery);

		addressRequest.setCustomer_sequence_number(1L);
		addressRequest.setSequenceNumber(2);
		addressRequest.setAddressLine1("RamaTemple");
		addressRequest.setAddressLine2("DMart");
		addressRequest.setLandmark("CinemaRoad");
		addressRequest.setCity("Kakinada");
		addressRequest.setState("AndhraPradesh");
		addressRequest.setCountry("India");
		addressRequest.setZipCode("8765");
		addressRequest.setMobileNumber(9875689378L);
		addressRequest.setEmailId("lakshmisidharth678@gmail.com");
		addressRequest.setisBillingAddress(false);
		addressRequest.setIsDefaultAddress(false);
		addressRequest.setLandlineNumber(98765895L);

		AddressDetails updatedAddress = new AddressDetails();
		updatedAddress.setAddressLine1("RamaTemple");
		updatedAddress.setAddressLine2("DMart");

		when(selectQuery.getSingleResult()).thenReturn(updatedAddress);
		String selectQueryString = "SELECT * FROM address_details WHERE sequence_number = :sequence_number";
		when(entityManager.createNativeQuery(selectQueryString, AddressDetails.class)).thenReturn(selectQuery);

		AddressResponse response = addressDaoImpl.updateAddress(addressRequest);

		assertNotNull(response);
		assertEquals("RamaTemple", response.getAddressLine1());
		assertEquals("DMart", response.getAddressLine2());

	}

	@Test
	public void testSaveAddress() throws InvalidInputParametersException {

		dataInitilization();
		doNothing().when(addressRepo).saveAddress(
		        any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
		    );
		    
		    addressDaoImpl.saveAddress(addressRequest);

		addressDaoImpl.saveAddress(addressRequest);

	}

	@Test
	public void testDeleteByAddressId() {

		Integer sequenceNumber = 1;

		addressDaoImpl.deleteByAddressId(sequenceNumber);
		verify(addressRepo, times(1)).deleteByAddressId(sequenceNumber);
	}

	@Test
	public void testDeleteAddressForCustomer() {

		Long customer_sequence_number = 8L;

		addressDaoImpl.deleteAddressForCustomer(customer_sequence_number);

		verify(addressRepo, times(1)).deleteAddressForCustomer(customer_sequence_number);
	}

	@Test
	public void testGetAddressList() {

		Long customerSequenceNumber = 8L;
		List<AddressDetails> expectedAddressList = new ArrayList<>();

		AddressDetails addressDetails = new AddressDetails();
		addressDetails.setSequenceNumber(8);
		addressDetails.setAddressLine1("VinayakaTemple");
		addressDetails.setAddressLine2("CinemaRoad");
		addressDetails.setLandmark("Opp:ApolloHospital");
		addressDetails.setCity("Kakinada");
		addressDetails.setState("AndhraPradesh");
		addressDetails.setCountry("India");
		addressDetails.setZipCode("8765");
		addressDetails.setMobileNumber(9875689378L);
		addressDetails.setEmailId("lakshmisidharth678@gmail.com");
		addressDetails.setDefaultAddress(false);
		addressDetails.setBillingAddress(false);

		expectedAddressList.add(addressDetails);

		when(addressRepo.getAddressList(customerSequenceNumber)).thenReturn(expectedAddressList);

		List<AddressDetails> actualAddressList = addressDaoImpl.getAddressList(customerSequenceNumber);

		verify(addressRepo, times(1)).getAddressList(customerSequenceNumber);

		assertEquals(expectedAddressList, actualAddressList);
	}

	@Test
	public void isTokenNotValid() {

		Long customer_sequence_number = 8L;

		addressDaoImpl.isTokenNotValid(customer_sequence_number);

		verify(addressRepo, times(1)).isTokenNotValid(customer_sequence_number);
	}
}
