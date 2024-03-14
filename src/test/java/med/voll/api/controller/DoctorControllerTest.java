package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

import med.voll.api.domain.address.Address;
import med.voll.api.domain.doctor.DoctorCreationDTO;
import med.voll.api.domain.doctor.DoctorDetailDTO;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.doctor.DoctorRepositoryTest;
import med.voll.api.domain.doctor.Specialty;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DoctorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DoctorCreationDTO> doctorCreationJson;

    @Autowired
    private JacksonTester<DoctorDetailDTO> doctorDetailJson;

    @MockBean
    private DoctorRepository repository;

    @Test
    @DisplayName("Should return status code 400 when data is invalid")
    @WithMockUser
    void test1Add() throws Exception {
        var response = mvc.perform(post("/doctors"))
                .andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return status code 201 when data is valid")
    @WithMockUser
    void test2Add() throws Exception {
        var specialty = Specialty.CARDIOLOGY;
        DoctorCreationDTO doctorCreation = DoctorRepositoryTest.doctorData("doctor", "doctor@doctor.com", "123456", specialty);

        var response = mvc
                .perform(
                        post("/doctors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(doctorCreationJson.write(doctorCreation).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        DoctorDetailDTO doctorDetail = new DoctorDetailDTO(null, doctorCreation.name(), doctorCreation.email(), doctorCreation.code(), doctorCreation.telephone(), doctorCreation.specialty(), new Address(doctorCreation.address()));
        var expectedJson = doctorDetailJson.write(doctorDetail).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}
