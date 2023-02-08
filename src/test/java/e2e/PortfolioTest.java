package e2e;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PortfolioTest extends  EndpointTest{

    @Test
    @WithMockUser
    @Sql({"/data/clearAll.sql", "/data/portfolio/portfolioCreate.sql"})
    void readPortfolio_ok() throws Exception {
        mvc.perform(get("/portfolio/2023:02"))
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].plannedAmount").value(90.0))
                .andExpect(jsonPath("$[1].plannedAmount").value(50.0))
                .andExpect(jsonPath("$[2].plannedAmount").value(40.0))
                .andExpect(jsonPath("$[3].plannedAmount").value(420.69))
                .andExpect(jsonPath("$[4].plannedAmount").value(420.69))

                .andExpect(jsonPath("$[0].actualAmount").value(35.0))
                .andExpect(jsonPath("$[1].actualAmount").value(30.0))
                .andExpect(jsonPath("$[2].actualAmount").value(5.00))
                .andExpect(jsonPath("$[3].actualAmount").value(420.69))
                .andExpect(jsonPath("$[4].actualAmount").value(420.69))
        ;
    }
}
