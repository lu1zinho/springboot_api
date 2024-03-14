package med.voll.api.domain.doctor;

import med.voll.api.domain.address.Address;

public record DoctorDetailDTO(
                Long id,
                String name,
                String email,
                String code,
                String telephone,
                Specialty specialty,
                Address address) {

        public DoctorDetailDTO(Doctor doctor) {
                this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCode(), doctor.getTelephone(),
                                doctor.getSpecialty(), doctor.getAddress());
        }

}
