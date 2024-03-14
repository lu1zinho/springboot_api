package med.voll.api.domain.patient;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Patient")
@Table(name = "patients")
public class Patient {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String cpf;
    private String telephone;

    @Embedded
    private Address address;

    private Boolean active;

    public Patient(PatientCreationDTO data) {
        this.active = true;
        this.name = data.name();
        this.email = data.email();
        this.telephone = data.telephone();
        this.cpf = data.cpf();
        this.address = new Address(data.address());
    }

    public void delete() {
        this.active = false;
    }

    public void updateInfos(PatientUpdateDTO data) {
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
