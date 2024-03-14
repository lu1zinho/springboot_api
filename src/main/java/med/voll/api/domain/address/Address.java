package med.voll.api.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String street;
    private String district;
    private String zipCode;
    private String number;
    private String complement;
    private String city;
    private String state;

    public Address(AddressDTO address) {
        this.street = address.street();
        this.district = address.district();
        this.zipCode = address.zipCode();
        this.number = address.number();
        this.complement = address.complement();
        this.city = address.city();
        this.state = address.state();
    }

    public void updateInfos(AddressDTO address) {
        if (address.street() != null) {
            this.street = address.street(); 
        }
        if (address.district() != null) {
            this.district = address.district(); 
        }
        if (address.zipCode() != null) {
            this.zipCode = address.zipCode(); 
        }
        if (address.number() != null) {
            this.number = address.number(); 
        }
        if (address.complement() != null) {
            this.complement = address.complement(); 
        }
        if (address.city() != null) {
            this.city = address.city(); 
        }
        if (address.state() != null) {
            this.state = address.state(); 
        }
    }

}
