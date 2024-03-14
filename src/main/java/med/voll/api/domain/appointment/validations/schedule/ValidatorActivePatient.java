package med.voll.api.domain.appointment.validations.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.appointment.AppointmentScheduleDTO;
import med.voll.api.domain.patient.PatientRepository;
import med.voll.api.infra.exception.ValidationException;

@Component
public class ValidatorActivePatient implements ValidatorAppointmentSchedule { 

    @Autowired
    private PatientRepository repository;

    public void validate(AppointmentScheduleDTO data) {
        var activePatient = repository.findActiveById(data.idPatient());
        if (! activePatient) {
            throw new ValidationException("It is not possible to schedule an appointment with an excluded patient");
        }
    }

}
