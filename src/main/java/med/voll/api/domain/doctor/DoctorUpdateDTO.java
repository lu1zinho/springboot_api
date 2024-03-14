package med.voll.api.domain.doctor;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.address.AddressDTO;

public record DoctorUpdateDTO(
        @NotNull Long id,
        String name,
        String telephone,
        AddressDTO address) {

}
