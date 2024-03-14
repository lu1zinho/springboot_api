package med.voll.api.domain.appointment;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByPatientIdAndDateBetweenAndCancelReasonIsNull(Long idPatient, LocalDateTime firsTime, LocalDateTime lasTime);

    boolean existsByDoctorIdAndDateAndCancelReasonIsNull(Long idDoctor, LocalDateTime date);
}
