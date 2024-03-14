package med.voll.api.domain.doctor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.address.AddressDTO;

public record DoctorCreationDTO(
        
        //Internationalization message file: ValidationMessages_xx.properties
        @NotBlank(message = "{name.notblank}") String name,
        @NotBlank @Email String email,
        @NotBlank String telephone,
        @NotBlank @Pattern(regexp = "\\d{4,6}") String code,
        @NotNull Specialty specialty,
        @NotNull @Valid AddressDTO address) {

}
