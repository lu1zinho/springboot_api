package med.voll.api.domain.doctor;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.domain.address.AddressDTO;
import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientCreationDTO;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Should return null when the only inserted doctor isn't available on date")
    void test1ChooseRandomDoctorFreeOnDate() {
        // given or arrange
        var nextMondayAt10am = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var doctor = insertDoctor("name", "name@voll.med", "123456", Specialty.CARDIOLOGY);
        var patient = insertPatient("Patient", "patient@email.com", "00000000000");
        insertAppointment(doctor, patient, nextMondayAt10am);

        // when or act
        var randomDoctor = doctorRepository.chooseRandomDoctorFreeOnDate(Specialty.CARDIOLOGY, nextMondayAt10am);

        // then or assert
        assertThat(randomDoctor).isNull();
    }

    @Test
    @DisplayName("Should return doctor when he is available on date")
    void test2ChooseRandomDoctorFreeOnDate() {
        // given or arrange
        var nextMondayAt10am = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var doctor = insertDoctor("name", "name@voll.med", "123456", Specialty.CARDIOLOGY);

        // when or act
        var randomDoctor = doctorRepository.chooseRandomDoctorFreeOnDate(Specialty.CARDIOLOGY, nextMondayAt10am);

        // then or assert
        assertThat(randomDoctor).isEqualTo(doctor);
    }

    private void insertAppointment(Doctor doctor, Patient patient, LocalDateTime date) {
        em.persist(new Appointment(null, doctor, patient, date, null));
    }

    private Doctor insertDoctor(String name, String email, String code, Specialty specialty) {
        Doctor doctor = new Doctor(doctorData(name, email, code, specialty));
        em.persist(doctor);
        return doctor;
    }

    private Patient insertPatient(String name, String email, String cpf) {
        Patient patient = new Patient(patientData(name, email, cpf));
        em.persist(patient);
        return patient;
    }

    public static DoctorCreationDTO doctorData(String name, String email, String code, Specialty specialty) {
        return new DoctorCreationDTO(name, email, "48991230123", code, specialty, addressData());
    }

    private PatientCreationDTO patientData(String name, String email, String cpf) {
        return new PatientCreationDTO(name, email, "4832340123", cpf, addressData());
    }

    private static AddressDTO addressData() {
        return new AddressDTO("street", "district", "12345000", "Rio", "RJ", null, null);
    }
}
