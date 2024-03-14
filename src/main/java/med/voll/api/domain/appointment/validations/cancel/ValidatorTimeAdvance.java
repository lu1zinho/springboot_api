package med.voll.api.domain.appointment.validations.cancel;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.appointment.AppointmentCancelDTO;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.infra.exception.ValidationException;

@Component
public class ValidatorTimeAdvance implements ValidatorAppointmentCancel {

    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentCancelDTO data) {
        var appointment = repository.getReferenceById(data.idAppointment());
        var now = LocalDateTime.now();
        var differenceInHours = Duration.between(now, appointment.getDate()).toHours();

        if (differenceInHours < 24) {
            throw new ValidationException("Appointments must be canceled at least 24 hours in advance");
        }
    }

}
