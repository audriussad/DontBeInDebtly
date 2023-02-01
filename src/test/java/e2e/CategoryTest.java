package e2e;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.accept;


public class CategoryTest extends EndpointTest{

    @Test
    @WithMockUser
//    @Sql()
    void createCategory_ok() throws Exception {
        mvc.perform(post("/category/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"testCat\"}"))
                .andExpect(status().isOk());
    }
}
