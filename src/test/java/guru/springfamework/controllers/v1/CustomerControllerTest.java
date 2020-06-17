package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.utilities.Constants;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    public static final String FIRST_NAME = "Michale";
    public static final String LAST_NAME = "Weston";
    public static final Long ID = 1L;

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void listCustomersTest() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");
        customer1.setCustomerUrl(Constants.BASE_CUSTOMER_URL + "/" + ID.toString());

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstName("Sam");
        customer2.setFirstName("Axe");
        customer2.setCustomerUrl(Constants.BASE_CUSTOMER_URL + "/" + ID.toString());

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get(Constants.BASE_CUSTOMER_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerDTOS", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {

        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);
        customerDTO.setCustomerUrl(Constants.BASE_CUSTOMER_URL + "/" + ID.toString());

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        // when
        mockMvc.perform(get(Constants.BASE_CUSTOMER_URL + "/" + ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));
    }

    @Test
    public void createCustomerTest() throws Exception {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);
        customerDTO.setCustomerUrl(Constants.BASE_CUSTOMER_URL + "/" + ID.toString());

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customerDTO.getFirstName());
        returnDTO.setLastName(customerDTO.getLastName());
        returnDTO.setCustomerUrl(Constants.BASE_CUSTOMER_URL + "/" + ID.toString());

        // when
        when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDTO);

        // then
        mockMvc.perform(post(Constants.BASE_CUSTOMER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
            .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
            .andExpect(jsonPath("$.customer_url",
                    equalTo(Constants.BASE_CUSTOMER_URL + "/" + ID.toString())));
    }

    @Test
    public void updateCustomerTest() throws Exception {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customerDTO.getFirstName());
        returnDTO.setLastName(customerDTO.getLastName());
        returnDTO.setCustomerUrl(Constants.BASE_CUSTOMER_URL + "/" + ID.toString());

        // when
        when(customerService.saveCustomerDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        // then
        mockMvc.perform(put(Constants.BASE_CUSTOMER_URL + "/" + ID.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url",
                        equalTo(Constants.BASE_CUSTOMER_URL + "/" + ID.toString())));
    }

    @Test
    public void patchControllerTest() throws Exception {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customerDTO.getFirstName());
        returnDTO.setLastName(LAST_NAME);
        returnDTO.setCustomerUrl(Constants.BASE_CUSTOMER_URL + "/" + ID.toString());

        // when
        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        // then
        mockMvc.perform(patch("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(Constants.BASE_CUSTOMER_URL + "/" + ID.toString())));
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        mockMvc.perform(delete(Constants.BASE_CUSTOMER_URL + "/" + ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}