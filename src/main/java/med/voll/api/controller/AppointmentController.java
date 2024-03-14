package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentCancelDTO;
import med.voll.api.domain.appointment.AppointmentDetailDTO;
import med.voll.api.domain.appointment.AppointmentService;
import med.voll.api.domain.appointment.AppointmentScheduleDTO;

@RestController
@RequestMapping("appointments")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @Transactional
    public ResponseEntity<AppointmentDetailDTO> schedule(@RequestBody @Valid AppointmentScheduleDTO data) {
        var detailDTO = appointmentService.schedule(data);
        return ResponseEntity.ok(detailDTO);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> cancel(@RequestBody @Valid AppointmentCancelDTO data) {
        appointmentService.cancel(data);
        return ResponseEntity.noContent().build();
    }

}
