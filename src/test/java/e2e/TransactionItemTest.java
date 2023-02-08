package e2e;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TransactionItemTest extends EndpointTest{

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql",  "/data/transactions/category.sql"})
    void createTransactionItem_ok() throws Exception {
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"amount\": \"20.00\",\n" +
                        "    \"categoryId\": \"2\",\n" +
                        "    \"date\": \"2023-02-02\"\n" +
                        "}"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value("20.0"))
//                .andExpect(jsonPath("$.categoryId").value(2))
                .andExpect(jsonPath("$.date").value("2023-02-02"));
        ;
    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql",
            "/data/transactions/category.sql",
            "/data/transactions/testTransaction.sql"})
    void readUserTransactions_ok() throws Exception {
        mvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].amount").value(20.0))
                .andExpect(jsonPath("$[0].createdAt").value("2023-02-02T12:44:08.590432Z"))
                .andExpect(jsonPath("$[0].date").value("2023-02-02"))
                .andExpect(jsonPath("$[0].userId").value(1))
//                .andExpect(jsonPath("$[0].category_category_id").value(2))
        ;
    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql",
            "/data/transactions/category.sql",
            "/data/transactions/testTransaction.sql"})
    void readSingleTransaction_ok() throws Exception {
        mvc.perform(get("/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(20.0))
                .andExpect(jsonPath("$.createdAt").value("2023-02-02T12:44:08.590432Z"))
                .andExpect(jsonPath("$.date").value("2023-02-02"))
                .andExpect(jsonPath("$.userId").value(1))
                ;
    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql",
            "/data/transactions/category.sql",
            "/data/transactions/testTransaction.sql"})
    void readSingleTransaction_fail() throws Exception {
        mvc.perform(get("/transactions/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
        ;
    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql",
            "/data/transactions/category.sql",
            "/data/transactions/testTransaction.sql"})
    void editTransaction_ok() throws Exception {
        mvc.perform(put("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": \"1\",\n" +
                        "    \"amount\": \"69.00\",\n" +
                        "    \"categoryId\": \"2\",\n" +
                        "    \"date\": \"2023-02-02\"\n" +
                        "}"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value("69.0"))
                .andExpect(jsonPath("$.date").value("2023-02-02"));
        ;

    }

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql",
            "/data/transactions/category.sql",
            "/data/transactions/testTransaction.sql"})
    void deleteTransaction_ok () throws Exception{
        mvc.perform(delete("/transactions/1"))
                .andExpect(status().isOk())
                ;
        mvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty())
        ;
    }
}
