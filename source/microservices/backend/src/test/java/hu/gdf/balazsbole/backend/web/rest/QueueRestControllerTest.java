package hu.gdf.balazsbole.backend.web.rest;

import hu.gdf.balazsbole.backend.service.QueueService;
import hu.gdf.balazsbole.domain.dto.Queue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = QueueRestController.class)
class QueueRestControllerTest {

    private static final String REST_URL = QueueRestController.ROOT_PATH;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private QueueService service;

    @Test
    void all_should_return_result_from_service() throws Exception {
        Queue o = new Queue();
        o.setEmail("test");
        when(service.findAll()).thenReturn(Collections.singletonList(o));

        Assertions.assertDoesNotThrow(() ->
                mvc.perform(get(REST_URL + "/all"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].email", is("test"))));
    }

}