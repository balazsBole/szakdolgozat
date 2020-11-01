package hu.gdf.balazsbole.backend.web.rest;

import hu.gdf.balazsbole.backend.service.EmailThreadService;
import hu.gdf.balazsbole.domain.dto.EmailThread;
import hu.gdf.balazsbole.domain.dto.User;
import hu.gdf.balazsbole.domain.enumeration.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
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
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = EmailThreadRestController.class)
class EmailThreadRestControllerTest {


    private static final String REST_URL = EmailThreadRestController.ROOT_PATH;

    @Autowired
    private EmailThreadRestController controller;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmailThreadService service;


    @Test
    void unassigned_should_return_result_from_service() throws Exception {
        EmailThread emailThread = new EmailThread();
        emailThread.setUser(new User());
        emailThread.getUser().setUsername("test");
        when(service.getUnassignedEmailThreads()).thenReturn(Collections.singletonList(emailThread));

        Assertions.assertDoesNotThrow(() ->
                mvc.perform(get(REST_URL + "/unassigned"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].user.username", is("test"))));
    }


    @Test
    @WithMockUser(username = "bf92cc37-fbc9-4774-ae9b-d818fcd66676")
    void findAllByStatusAndUser_should_return_result_from_service() throws Exception {
        UUID uuid = UUID.fromString("bf92cc37-fbc9-4774-ae9b-d818fcd66676");

        EmailThread emailThread = new EmailThread();
        emailThread.setUser(new User());
        emailThread.getUser().setUsername("test");
        when(service.findAllByStatusAndKeycloakUser(eq(uuid), eq(Status.OPEN))).thenReturn(Collections.singletonList(emailThread));

        Assertions.assertDoesNotThrow(() ->
                mvc.perform(get(REST_URL + "/assigned-to-me"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].user.username", is("test"))));
    }


}