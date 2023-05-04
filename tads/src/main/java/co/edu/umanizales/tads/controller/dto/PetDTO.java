package co.edu.umanizales.tads.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PetDTO {
    private String identification;
    @Size(min = 1, max = 31, message = "Name must be less than 30 characters or equal")
    private String name;
    @Min(value = 1, message = "Age should not be less than 1")
    @Max(value = 14, message = "Age should not be greater than 15")
    private byte age;
    @Pattern(regexp = "^[FM]$", message = "Gender must be either 'F' or 'M'")
    private String gender;
    private String codeLocation;
}
