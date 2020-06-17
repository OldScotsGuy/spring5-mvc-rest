package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;

    // By default the property name is used in teh Json file - can be changed using the annotation below
    @JsonProperty("customer_url")
    private String customerUrl;
}
