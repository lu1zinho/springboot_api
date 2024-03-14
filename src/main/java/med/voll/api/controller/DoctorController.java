package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorCreationDTO;
import med.voll.api.domain.doctor.DoctorDetailDTO;
import med.voll.api.domain.doctor.DoctorListDTO;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.doctor.DoctorUpdateDTO;

@RestController
@RequestMapping("doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DoctorDetailDTO> add(@RequestBody @Valid DoctorCreationDTO data, UriComponentsBuilder uriBuilder) {
        var doctor = new Doctor(data);
        repository.save(doctor);

        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DoctorDetailDTO(doctor));
    }

    /**
     * This method returns a JSON representation of a Spring Page<DoctorDTO>.
     * Sort order, page size, and page number can be customized as parameters.
     */
    @GetMapping
    public ResponseEntity<Page<DoctorListDTO>> list(@PageableDefault(size = 10, sort = { "name" }) Pageable paginator) {
        var page = repository.findAll(paginator).map(DoctorListDTO::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DoctorDetailDTO> update(@RequestBody @Valid DoctorUpdateDTO data) {
        var doctor = repository.getReferenceById(data.id());
        doctor.updateInfos(data);
        return ResponseEntity.ok(new DoctorDetailDTO(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    //@Secured("ROLE_ADMIN") // second example of role authorization
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailDTO> get(@PathVariable Long id) {
        var doctor = repository.getReferenceById(id);
        return ResponseEntity.ok(new DoctorDetailDTO(doctor));
    }

}
