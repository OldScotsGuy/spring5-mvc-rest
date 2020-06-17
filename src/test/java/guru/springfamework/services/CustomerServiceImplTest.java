package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRespository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    public static final String FIRST_NAME = "Joe";
    public static final String LAST_NAME = "Bloggs";
    public static final long ID = 1L;

    CustomerService customerService;

    @Mock
    CustomerRespository customerRespository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRespository);
    }

    @Test
    public void getAllNamesTest() {
        // given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());
        when(customerRespository.findAll()).thenReturn(customers);

        // when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        // then
        assertEquals(3, customerDTOS.size());
    }

    @Test
    public void getCustomerByIdTest() {
        // given
        Customer customer = new Customer();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        customer.setId(ID);
        when(customerRespository.findById(anyLong())).thenReturn(java.util.Optional.of(customer));

        // when
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        // then
        assertEquals(ID, customerDTO.getId().longValue());
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }
}