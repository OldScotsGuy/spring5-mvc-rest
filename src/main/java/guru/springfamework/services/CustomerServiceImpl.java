package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.utilities.Constants;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerMapper customerMapper;
    CustomerRespository customerRespository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRespository customerRespository) {
        this.customerMapper = customerMapper;
        this.customerRespository = customerRespository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRespository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(Constants.BASE_CUSTOMER_URL + "/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRespository.findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(Constants.BASE_CUSTOMER_URL + "/" + customer.getId());
                    return customerDTO;
                })
                .orElseThrow(RuntimeException::new);    // todo make more elegant!
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

        return saveAndReturnDTO(customerMapper.customerDTOtoCustomer(customerDTO));
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerRespository.save(customer);
        CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        returnDTO.setCustomerUrl(Constants.BASE_CUSTOMER_URL + "/" + savedCustomer.getId());
        return returnDTO;
    }

    @Override
    public CustomerDTO saveCustomerDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOtoCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return  customerRespository.findById(id).map(customer -> {

            if (customerDTO.getFirstName() != null) {
                customer.setFirstName(customerDTO.getFirstName());
            }
            if (customerDTO.getLastName() != null) {
                customer.setLastName(customerDTO.getLastName());
            }

            CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(customerRespository.save(customer));
            returnDTO.setCustomerUrl(Constants.BASE_CUSTOMER_URL + "/" + id);
            return returnDTO;
        }).orElseThrow(RuntimeException::new);  // Todo implement better handling
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRespository.deleteById(id);
    }
}
