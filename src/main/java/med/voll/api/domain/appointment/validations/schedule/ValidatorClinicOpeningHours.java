package med.voll.api.domain.appointment.validations.schedule;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import med.voll.api.domain.appointment.AppointmentScheduleDTO;
import med.voll.api.infra.exception.ValidationException;

@Component
public class ValidatorClinicOpeningHours implements ValidatorAppointmentSchedule {

    public void validate(AppointmentScheduleDTO data) {
        var appointmentDate = data.date();

        var sunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeClinicOpening = appointmentDate.getHour() < 7;
        var afterClinicClosing = appointmentDate.getHour() > 18;
        if (sunday || beforeClinicOpening || afterClinicClosing) {
            throw new ValidationException("Appointment outside clinic opening hours");
        }
    }

}
