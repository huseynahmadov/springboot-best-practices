package az.company.bestpractices.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreateProductRequest {

    @NotBlank(message = "Product name cannot be null or empty")
    private String name;
    private String description;

    @NotBlank(message = "Product type cannot be null or empty")
    private String productType;

    @Min(value = 1,message = "Quantity is not defined !")
    private Integer quantity;

    @Min(value = 200, message = "Product price can't be less than 200")
    @Max(value = 50000, message = "Product price can't be greater than 50000")
    private Double price;
    private String supplierName;

    @NotBlank(message = "supplier code shouldn't be NULL OR EMPTY")
    private String supplierCode;

}
