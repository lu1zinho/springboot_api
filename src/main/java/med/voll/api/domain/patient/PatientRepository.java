package med.voll.api.domain.patient;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Spring framework has a feature that generates SQL queries automatically based
     * on the method name. Specifically, select all "where active = true".
     */
    Collection<Patient> findAllByActiveTrue();

    @Query("""
            select p.active
            from Patient p
            where
            p.id = :id
            """)
    Boolean findActiveById(Long id);
}
