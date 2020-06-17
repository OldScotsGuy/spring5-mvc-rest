package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRespository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRespository customerRespository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRespository customerRespository) {
        this.categoryRepository = categoryRepository;
        this.customerRespository = customerRespository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadCategories();
        loadCustomers();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Data Loaded = " + categoryRepository.count());
    }

    private void loadCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1l);
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");
        customerRespository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2l);
        customer2.setFirstName("Sam");
        customer2.setFirstName("Axe");

        customerRespository.save(customer2);

        System.out.println("Customers Loaded: " + customerRespository.count());
    }
}
