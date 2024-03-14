package med.voll.api.domain.appointment.validations.cancel;

import med.voll.api.domain.appointment.AppointmentCancelDTO;

public interface ValidatorAppointmentCancel {

    void validate(AppointmentCancelDTO data);

}
