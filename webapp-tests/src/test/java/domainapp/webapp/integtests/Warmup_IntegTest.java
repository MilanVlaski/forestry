package domainapp.webapp.integtests;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@AutoConfigureMockMvc
class Warmup_IntegTest extends WebAppIntegTestAbstract{

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testMyEndpoint() throws Exception {
        mockMvc.perform(get("/_ah/warmup"))
                .andExpect(status().isOk());
    }
}
