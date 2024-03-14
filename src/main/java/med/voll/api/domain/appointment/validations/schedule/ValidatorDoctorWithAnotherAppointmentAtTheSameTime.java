package med.voll.api.domain.appointment.validations.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentScheduleDTO;
import med.voll.api.infra.exception.ValidationException;

@Component
public class ValidatorDoctorWithAnotherAppointmentAtTheSameTime implements ValidatorAppointmentSchedule {

    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentScheduleDTO data) {
        var doctorWithAnotherAppointmentAtTheSameTime = repository.existsByDoctorIdAndDateAndCancelReasonIsNull(data.idDoctor(), data.date());
        if (doctorWithAnotherAppointmentAtTheSameTime) {
            throw new ValidationException("Doctor already has another appointment scheduled at the same time");
        }
    }

}
