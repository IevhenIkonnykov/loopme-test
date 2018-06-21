package integration;

import com.fullstack.test.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TestApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class UserControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getUserForAuthorizedUserSucceeds() throws Exception {
        mvc.perform(get("/api/user")
                .headers(ITUtils.getHeadersForPublisher()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.name").value("publisher1"))
                .andExpect(jsonPath("$.email").value("c@c.com"))
                .andExpect(jsonPath("$.authorities").value("PUBLISHER"));
    }

    @Test
    public void getUserForUnauthorizedUserThrowsException() throws Exception {
        mvc.perform(get("/api/user")
                .headers(ITUtils.getHeadersForUnauthenticatedUser()))
                .andExpect(status().is(401));
    }

    @Test
    public void getsPublishersForOperator() throws Exception {
        mvc.perform(get("/api/publisher")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void createsPublisherForOperator() throws Exception {
        mvc.perform(post("/api/publisher")
                .content("{\"id\": null, \"name\": \"pub3\", \"email\": \"d@d.com\", \"authorities\": [\"PUBLISHER\"]}")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk());

        mvc.perform(get("/api/publisher")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name").value("pub3"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updatesPublisherForOperator() throws Exception {
        mvc.perform(put("/api/publisher")
                .content("{\"id\": 3, \"name\": \"publisher1Update\", \"email\": \"c@c.com\", \"authorities\": [\"PUBLISHER\"]}")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk());

        mvc.perform(get("/api/publisher")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("publisher1Update"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deletesPublisherForOperator() throws Exception {
        mvc.perform(delete("/api/publisher/3")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk());

        mvc.perform(get("/api/publisher")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void operatorCannotDeleteOperator() throws Exception {
        mvc.perform(delete("/api/operator/2")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void publisherCannotDeletePublisher() throws Exception {
        mvc.perform(delete("/api/operator/2")
                .headers(ITUtils.getHeadersForPublisher()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminCannotDeleteAdmin() throws Exception {
        mvc.perform(delete("/api/operator/1")
                .headers(ITUtils.getHeadersForAdmin()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getsOperatorsForAdmin() throws Exception {
        mvc.perform(get("/api/operator")
                .headers(ITUtils.getHeadersForAdmin()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    // and more cases
}
