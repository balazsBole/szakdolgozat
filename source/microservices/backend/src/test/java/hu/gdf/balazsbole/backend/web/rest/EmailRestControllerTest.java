package hu.gdf.balazsbole.backend.web.rest;

import hu.gdf.balazsbole.backend.service.EmailService;
import hu.gdf.balazsbole.domain.dto.Email;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = EmailRestController.class)
class EmailRestControllerTest {
    private static final String REST_URL = EmailRestController.ROOT_PATH;

    @Autowired
    private EmailRestController controller;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmailService service;


    @Test
    void unassigned_should_return_result_from_service() throws Exception {
        final var id = UUID.randomUUID();
        Email value = new Email();
        value.setId(id);

        when(service.findById(any())).thenReturn(Optional.of(value));

        Assertions.assertDoesNotThrow(() ->
                mvc.perform(get(REST_URL + "/" + id))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", is(id.toString()))));
    }


    @Test
    void findAllByStatusAndUser_should_return_result_from_service() throws Exception {
        final var id = UUID.randomUUID();

        when(service.findById(any())).thenReturn(Optional.empty());

        mvc.perform(get(REST_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("404 NOT_FOUND \"resource not found\"", result.getResolvedException().getMessage()));

    }


}