package med.voll.api.domain.appointment.validations.schedule;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.domain.appointment.AppointmentScheduleDTO;
import med.voll.api.infra.exception.ValidationException;

//Spring Beans with the same class name cause a ConflictingBeanDefinitionException
//One solution is to declare different names in @Component
@Component("ValidatorTimeAdvanceCancel")
public class ValidatorTimeAdvance implements ValidatorAppointmentSchedule {

    public void validate(AppointmentScheduleDTO data) {
        var appointmentDate = data.date();
        var now = LocalDateTime.now();
        var differenceInMinutes = Duration.between(now, appointmentDate).toMinutes();

        if (differenceInMinutes < 30) {
            throw new ValidationException("Appointments must be scheduled at least 30 minutes in advance");
        }
    }

}
