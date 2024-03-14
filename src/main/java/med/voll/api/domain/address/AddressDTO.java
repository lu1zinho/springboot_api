package med.voll.api.domain.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressDTO(
        @NotBlank String street,
        @NotBlank String district,
        @NotBlank @Pattern(regexp = "\\d{5,8}") String zipCode,
        @NotBlank String city,
        @NotBlank String state,
        String complement,
        String number) {

}
