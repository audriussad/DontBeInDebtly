package e2e;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class BudgetItemTest extends EndpointTest{

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql",  "/data/transactions/category.sql"})
    void createBudgetItem_ok() throws Exception {
        mvc.perform(post("/budget-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"date\": \"2023-02-02\",\n" +
                                "    \"plannedAmount\": \"100.00\",\n" +
                                "    \"categoryId\": \"2\"\n" +
                                "}"
                        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plannedAmount").value("100.0"))
//                .andExpect(jsonPath("$.categoryId").value(2))
                .andExpect(jsonPath("$.date").value("2023-02-02"));
        ;
    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql",
            "/data/budget-item/category.sql",
            "/data/budget-item/testBudgetItem.sql"})
    void readUserTransactions_ok() throws Exception {
        mvc.perform(get("/budget-item"))
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].plannedAmount").value(50.0))
                .andExpect(jsonPath("$[0].date").value("2023-02-02"))
                .andExpect(jsonPath("$[0].userId").value(1))
//                .andExpect(jsonPath("$[0].categoryId").value(2))
        ;
    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql",
            "/data/budget-item/category.sql",
            "/data/budget-item/testBudgetItem.sql"})
    void readSingleBudgetItem_ok() throws Exception {
        mvc.perform(get("/budget-item/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.plannedAmount").value(50.0))
                .andExpect(jsonPath("$.date").value("2023-02-02"))
                .andExpect(jsonPath("$.userId").value(1))
                ;
    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql",
            "/data/budget-item/category.sql",
            "/data/budget-item/testBudgetItem.sql"})
    void readSingleBudgetItem_fail() throws Exception {
        mvc.perform(get("/budget-item/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        ;
    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql",
            "/data/budget-item/category.sql",
            "/data/budget-item/testBudgetItem.sql"})
    void editBudgetItem_ok() throws Exception {
        mvc.perform(put("/budget-item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "    \"id\": \"1\",\n" +
                                "    \"date\": \"2023-02-03\",\n" +
                                "    \"plannedAmount\": \"420.69\",\n" +
                                "    \"categoryId\": \"2\"\n" +
                                "}"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2023-02-03"))
                .andExpect(jsonPath("$.plannedAmount").value("420.69"))
                ;
    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql",
            "/data/budget-item/category.sql",
            "/data/budget-item/testBudgetItem.sql"})
    void deleteBudgetItem_ok() throws Exception {
        mvc.perform(delete("/budget-item/1"))
                .andExpect(status().isOk())
                ;
        mvc.perform(get("/budget-item"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty())
                ;
    }
}
