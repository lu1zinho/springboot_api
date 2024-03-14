package med.voll.api.domain.appointment;

import jakarta.validation.constraints.NotNull;

public record AppointmentCancelDTO(

    @NotNull
    Long idAppointment,
    
    @NotNull
    AppointmentCancelReason reason) {
    
}
