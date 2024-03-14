package med.voll.api.domain.doctor;

public record DoctorListDTO(
        Long id,
        String name,
        String email,
        String code,
        Specialty specialty) {

    public DoctorListDTO(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCode(), doctor.getSpecialty());
    }

}
    