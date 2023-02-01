package e2e;

import com.example.AudriusSadaunykas.DontBeInDebtly.DontBeInDebtlyApplication;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;

/**
 * Extend this class to test Endpoints. Some call this Integration Test, but I think EndpointTest
 * is actually a better name for what we're doing here.
 * <br><br>
 * This class spins up a static MySQL docker container for the duration of the test
 */
@Tag("e2e-test")
@AutoConfigureMockMvc
@SpringBootTest(classes = DontBeInDebtlyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public abstract class EndpointTest {
    @Autowired
    protected MockMvc mvc;

    static final MySQLContainer<?> MYSQL_CONTAINER;

    static {
        MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0.22")
                .withDatabaseName("debtly")
                .withUsername("alko_user")
                .withPassword("passwordux")
                .withReuse(true);
        MYSQL_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
    }
}
