package med.voll.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientCreationDTO;
import med.voll.api.domain.patient.PatientDTO;
import med.voll.api.domain.patient.PatientDetailDTO;
import med.voll.api.domain.patient.PatientRepository;
import med.voll.api.domain.patient.PatientUpdateDTO;

@RestController
@RequestMapping("patients")
@SecurityRequirement(name = "bearer-key")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<PatientDetailDTO> add(@RequestBody @Valid PatientCreationDTO data, UriComponentsBuilder uriBuilder) {
        Patient patient = new Patient(data);
        repository.save(patient);

        var uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(new PatientDetailDTO(patient));
    }

    /**
     * This method retrieves a list of all PatientDTO in JSON format.
     * Customization options are not available.
     */
    @GetMapping
    public ResponseEntity<List<PatientDTO>> listAll() {
        var list = repository.findAllByActiveTrue().stream().map(PatientDTO::new).toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PatientDetailDTO> update(@RequestBody @Valid PatientUpdateDTO data) {
        var patient = repository.getReferenceById(data.id());
        patient.updateInfos(data);
        return ResponseEntity.ok(new PatientDetailDTO(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    /**
     * Logical exclusion
     */
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        patient.delete();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDetailDTO> get(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        return ResponseEntity.ok(new PatientDetailDTO(patient));
    }

}
