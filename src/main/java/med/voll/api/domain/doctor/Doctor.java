package med.voll.api.domain.doctor;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Entity(name = "Doctor")
@Table(name = "doctors")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String telephone;
    private String code;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Embedded
    private Address address;

    public Doctor(DoctorCreationDTO data) {
        this.name = data.name();
        this.email = data.email();
        this.telephone = data.telephone();
        this.code = data.code();
        this.specialty = data.specialty();
        this.address = new Address(data.address());
    }

    public void updateInfos(@Valid DoctorUpdateDTO data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.telephone() != null) {
            this.telephone = data.telephone();
        }
        if (data.address() != null) {
            this.address.updateInfos(data.address());
        }
    }

}
