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
public class AppControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getsAppsForPublisher() throws Exception {
        mvc.perform(get("/api/app")
                .headers(ITUtils.getHeadersForPublisher()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void getsAppsForOperator() throws Exception {
        mvc.perform(get("/api/app")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void createsAppForPublisher() throws Exception {
        mvc.perform(post("/api/app")
                .content("{\"id\": null, \"name\": \"app5\", \"type\": \"IOS\", \"contentTypes\": [\"VIDEO\"], \"userId\": 3}")
                .headers(ITUtils.getHeadersForPublisher()))
                .andExpect(status().isOk());

        mvc.perform(get("/api/app")
                .headers(ITUtils.getHeadersForPublisher()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[3].name").value("app5"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void createsAppForOperator() throws Exception {
        mvc.perform(post("/api/app")
                .content("{\"id\": null, \"name\": \"app5\", \"type\": \"IOS\", \"contentTypes\": [\"VIDEO\"], \"userId\": 3}")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk());

        mvc.perform(get("/api/app")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[4].name").value("app5"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updatesAppForPublisher() throws Exception {
        mvc.perform(put("/api/app")
                .content("{\"id\": 1, \"name\": \"app1Update\", \"type\": \"IOS\", \"contentTypes\": [\"VIDEO\"], \"userId\": 3}")
                .headers(ITUtils.getHeadersForPublisher()))
                .andExpect(status().isOk());

        mvc.perform(get("/api/app")
                .headers(ITUtils.getHeadersForPublisher()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value("app1Update"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updatesAppForOperator() throws Exception {
        mvc.perform(put("/api/app")
                .content("{\"id\": 1, \"name\": \"app1Update\", \"type\": \"IOS\", \"contentTypes\": [\"VIDEO\"], \"userId\": 3}")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk());

        mvc.perform(get("/api/app")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].name").value("app1Update"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deletesAppForPublisher() throws Exception {
        mvc.perform(delete("/api/app/1")
                .headers(ITUtils.getHeadersForPublisher()))
                .andExpect(status().isOk());

        mvc.perform(get("/api/app")
                .headers(ITUtils.getHeadersForPublisher()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void publisherCannotDeleteSomeonesApp() throws Exception {
        mvc.perform(delete("/api/app/4")
                .headers(ITUtils.getHeadersForPublisher()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deletesAnyAppForOperator() throws Exception {
        mvc.perform(delete("/api/app/4")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk());

        mvc.perform(get("/api/app")
                .headers(ITUtils.getHeadersForOperator()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void adminCannotDeleteApp() throws Exception {
        mvc.perform(delete("/api/app/1")
                .headers(ITUtils.getHeadersForAdmin()))
                .andExpect(status().isForbidden());
    }

    // and more cases
}
