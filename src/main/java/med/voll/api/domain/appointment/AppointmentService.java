package med.voll.api.domain.appointment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.appointment.validations.cancel.ValidatorAppointmentCancel;
import med.voll.api.domain.appointment.validations.schedule.ValidatorAppointmentSchedule;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;
import med.voll.api.infra.exception.ValidationException;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Spring @Autowired of List of Interface injects all the implementations
    @Autowired
    private List<ValidatorAppointmentSchedule> validators;

    @Autowired
    private List<ValidatorAppointmentCancel> validatorsCancel;

    public AppointmentDetailDTO schedule(AppointmentScheduleDTO data) {
        if (! patientRepository.existsById(data.idPatient())) {
            throw new ValidationException("Patient Id doesn't exist");
        }
        if (data.idDoctor() != null && ! doctorRepository.existsById(data.idDoctor())) {
            throw new ValidationException("Doctor Id doesn't exist");
        }

        validators.forEach(v -> v.validate(data));

        var patient = patientRepository.getReferenceById(data.idPatient());
        var doctor = chooseDoctor(data);
        if (doctor == null) {
            throw new ValidationException("There are no doctors available on this date");
        }

        var appointment = new Appointment(null, doctor, patient, data.date(), null);
        appointmentRepository.save(appointment);

        return new AppointmentDetailDTO(appointment);
    }

    public void cancel(AppointmentCancelDTO data) {
        if (! appointmentRepository.existsById(data.idAppointment())) {
            throw new ValidationException("Appointment Id doesn't exist");
        }

        validatorsCancel.forEach(v -> v.validate(data));

        var appointment = appointmentRepository.getReferenceById(data.idAppointment());
        appointment.cancel(data.reason());
    }

    private Doctor chooseDoctor(AppointmentScheduleDTO data) {
        if (data.idDoctor() != null) {
            return doctorRepository.getReferenceById(data.idDoctor());
        }

        if (data.specialty() == null) {
            throw new ValidationException("Specialty is mandatory when a doctor is not chosen");
        }

        return doctorRepository.chooseRandomDoctorFreeOnDate(data.specialty(), data.date());
    }
}
