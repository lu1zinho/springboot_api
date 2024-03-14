package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.appointment.AppointmentDetailDTO;
import med.voll.api.domain.appointment.AppointmentScheduleDTO;
import med.voll.api.domain.appointment.AppointmentService;
import med.voll.api.domain.doctor.Specialty;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AppointmentScheduleDTO> appointmentScheduleJson;

    @Autowired
    private JacksonTester<AppointmentDetailDTO> appointmentDetailJson;

    @MockBean
    private AppointmentService appointmentService;

    @Test
    @DisplayName("Should return status code 400 when data is invalid")
    @WithMockUser
    void test1Schedule() throws Exception {
        var response = mvc.perform(post("/appointments"))
                .andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return status code 200 when data is valid")
    @WithMockUser
    void test2Schedule() throws Exception {
        var date = LocalDateTime.now().plusHours(1);
        var specialty = Specialty.CARDIOLOGY;
        AppointmentDetailDTO appointmentDetail = new AppointmentDetailDTO(null, 2L, 5L, date);

        when(appointmentService.schedule(any())).thenReturn(appointmentDetail);

        var response = mvc
                .perform(
                        post("/appointments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(appointmentScheduleJson.write(
                                        new AppointmentScheduleDTO(2L, 5L, date, specialty)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = appointmentDetailJson.write(appointmentDetail).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}
