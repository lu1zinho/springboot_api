package med.voll.api.domain.appointment.validations.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentScheduleDTO;
import med.voll.api.infra.exception.ValidationException;

@Component
public class ValidatorPatientWithoutAnotherAppointmentOnTheDay implements ValidatorAppointmentSchedule {

    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentScheduleDTO data) {
        var firsTime = data.date().withHour(7);
        var lasTime = data.date().withHour(18);
        var patientHasAnotherAppointmentOnTheDay = repository.existsByPatientIdAndDateBetweenAndCancelReasonIsNull(data.idPatient(), firsTime, lasTime);
        if (patientHasAnotherAppointmentOnTheDay) {
            throw new ValidationException("Patient already has an appointment scheduled on this day");
        }
    }

}
