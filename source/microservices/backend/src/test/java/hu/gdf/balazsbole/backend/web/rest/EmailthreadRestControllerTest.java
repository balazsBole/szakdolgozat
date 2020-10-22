package hu.gdf.balazsbole.backend.web.rest;

import hu.gdf.balazsbole.backend.service.EmailthreadService;
import hu.gdf.balazsbole.domain.dto.Emailthread;
import hu.gdf.balazsbole.domain.dto.User;
import hu.gdf.balazsbole.domain.enumeration.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmailthreadRestController.class)
class EmailthreadRestControllerTest {


    private static final String REST_URL = EmailthreadRestController.ROOT_PATH;

    @Autowired
    private EmailthreadRestController controller;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmailthreadService service;


    @Test
    void unassigned_should_return_result_from_service() throws Exception {
        Emailthread emailthread = new Emailthread();
        emailthread.setUser(new User());
        emailthread.getUser().setUsername("test");
        when(service.getUnassignedEmailThreads()).thenReturn(Collections.singletonList(emailthread));

        Assertions.assertDoesNotThrow(() ->
                mvc.perform(get(REST_URL + "/unassigned"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].user.username", is("test"))));
    }


    @Test
    void findAllByStatusAndUser_should_return_result_from_service() throws Exception {
        final var id = UUID.randomUUID();

        Emailthread emailthread = new Emailthread();
        emailthread.setUser(new User());
        emailthread.getUser().setUsername("test");
        when(service.findAllByStatusAndKeycloakUser(eq(id), eq(Status.OPEN))).thenReturn(Collections.singletonList(emailthread));

        Assertions.assertDoesNotThrow(() ->
                mvc.perform(get(REST_URL + "/{userid}}", id))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].user.username", is("test"))));
    }


}