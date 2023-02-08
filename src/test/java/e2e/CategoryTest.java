package e2e;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoryTest extends EndpointTest{

    private static final Logger log = LoggerFactory.getLogger(CategoryTest.class);

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql"})
    void createCategory_ok() throws Exception {
        mvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"testCat\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql"})
    void createCategory_fail() throws Exception {
        mvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content("")).andExpect(status()
                .is4xxClientError());
    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql"})
    void createdCategoryName_correct() throws Exception {
        mvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"testCat\"}"))
                .andExpect(jsonPath("$.name").value("testCat"));
    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql", "/data/category/testCat.sql"})
    void readUserCategories_correct() throws Exception {
        mvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("testCat")));
    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql", "/data/category/testCat.sql"})
    void deletesCategory_ok() throws Exception {
        mvc.perform(delete("/category/1"))
                .andExpect(status().isOk())
                ;
        mvc.perform(get("/category"))
                .andExpect(jsonPath("$").isEmpty())
                ;
    }

}
