package com.example.AudriusSadaunykas.DontBeInDebtly.services;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUser;
import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUserRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUserRole;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.TransactionItemRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateTransactionItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TransactionItemService.class})
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TransactionItemServiceTest {

    @MockBean
    private ApplicationUserRepository applicationUserRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionItemService transactionItemService;

    @Autowired
    private TransactionItemService underTest;

    @Mock
    private TransactionItemRepository transactionItemRepository;

//    @BeforeEach
//    void setUp() {
//        underTest = new TransactionItemService(transactionItemRepository, categoryRepository, applicationUserRepository);
//    }

    @Test
    @DisplayName("Checks if method gets all the transactions of a user")
    @Disabled
    void canGetTransactions() {
        // given
        Long userId = Long.valueOf(1);

        TransactionItemEntity item1 = new TransactionItemEntity();
        item1.setId(1L);
        item1.setAmount(BigDecimal.valueOf(1));
        item1.setYear(2022);
        item1.setUserId(userId);

        TransactionItemEntity item2 = new TransactionItemEntity();
        item2.setId(2L);
        item2.setAmount(BigDecimal.valueOf(2));
        item2.setYear(2022);
        item2.setUserId(userId);

        TransactionItemEntity item3 = new TransactionItemEntity();
        item3.setId(3L);
        item3.setAmount(BigDecimal.valueOf(3));
        item3.setYear(2022);
        item3.setUserId(2L);

        List<TransactionItemEntity> list = Stream.of(item1, item2, item3).toList();
        //when
        when(transactionItemRepository.findByUserId(userId)).thenReturn(list);

        // then
        assertEquals(2, underTest.getTransactions(userId).size());
    }

    /**
     * Method under test: {@link TransactionItemService#getTransactions(Long)}
     */
    @Test
    void testGetTransactions() {
        ArrayList<TransactionItemEntity> transactionItemEntityList = new ArrayList<>();
        when(transactionItemRepository.findByUserId((Long) any())).thenReturn(transactionItemEntityList);

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setApplicationUserRole(ApplicationUserRole.USER);
        applicationUser.setEmail("jane.doe@example.org");
        applicationUser.setEnabled(true);
        applicationUser.setFirstName("Jane");
        applicationUser.setId(123L);
        applicationUser.setLastName("Doe");
        applicationUser.setLocked(true);
        applicationUser.setPassword("iloveyou");
        Optional<ApplicationUser> ofResult = Optional.of(applicationUser);
        when(applicationUserRepository.findById((Long) any())).thenReturn(ofResult);
        List<TransactionItemEntity> actualTransactions = transactionItemService.getTransactions(123L);
        assertSame(transactionItemEntityList, actualTransactions);
        assertTrue(actualTransactions.isEmpty());
        verify(transactionItemRepository).findByUserId((Long) any());
        verify(applicationUserRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link TransactionItemService#getTransactions(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTransactions2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.orElseThrow(Optional.java:377)
        //       at com.example.AudriusSadaunykas.DontBeInDebtly.services.TransactionItemService.getTransactions(TransactionItemService.java:36)
        //   See https://diff.blue/R013 to resolve this issue.

        when(transactionItemRepository.findByUserId((Long) any())).thenReturn(new ArrayList<>());
        when(applicationUserRepository.findById((Long) any())).thenReturn(Optional.empty());
        transactionItemService.getTransactions(123L);
    }

    @Test
    @Disabled
    void saveTransaction() {
    }

    @Test
    @Disabled
    void editTransaction() {
    }

    @Test
    @Disabled
    void getTransaction() {
    }

    @Test
    @Disabled
    void deleteTransaction() {
    }

    @Test
    @Disabled
    void sumOfMonthlyTransactions() {
    }

    @Test
    @Disabled
    void isAuthorized() {
    }
}