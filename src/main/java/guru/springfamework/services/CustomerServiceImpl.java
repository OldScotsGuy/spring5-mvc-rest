package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
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
                    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRespository.findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .orElseThrow(RuntimeException::new);    // todo make more elegant!
    }
}
