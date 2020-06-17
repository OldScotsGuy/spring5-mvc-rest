package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    public static final String FIRST_NAME = "Joe";
    public static final String LAST_NAME = "Bloggs";
    public static final long ID = 1L;

    @Test
    public void customerToCustomerDTO() {
        CustomerMapper customerMapper = CustomerMapper.INSTANCE;

        // given
        Customer customer = new Customer();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        customer.setId(ID);

        // when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        //then
        assertEquals(Long.valueOf(ID), customerDTO.getId());
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }
}