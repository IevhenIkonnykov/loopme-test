package integration;

import com.fullstack.test.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TestApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class LoginControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getUserForAuthorizedUserSucceeds() throws Exception {
        mvc.perform(get("/api/user")
                .headers(ITUtils.getHeadersForPublisher()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("publisher"))
                .andExpect(jsonPath("$.email").value("c@c.com"))
                .andExpect(jsonPath("$.authorities").value("PUBLISHER"));
    }

    @Test
    public void getUserForUnauthorizedUserThrowsException() throws Exception {
        mvc.perform(get("/api/user")
                .headers(ITUtils.getHeadersForUnauthenticatedUser()))
                .andExpect(status().is(401));
    }
}
