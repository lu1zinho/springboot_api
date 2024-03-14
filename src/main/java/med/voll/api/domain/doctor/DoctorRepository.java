package med.voll.api.domain.doctor;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
            select d from Doctor d
            where
            d.specialty = :specialty
            and 
            d.id not in(
                select a.doctor.id from Appointment a
                where
                a.date = :date
                and
                a.cancelReason is null
            )
            order by rand()
            limit 1
            """)
    Doctor chooseRandomDoctorFreeOnDate(Specialty specialty, LocalDateTime date);
}
