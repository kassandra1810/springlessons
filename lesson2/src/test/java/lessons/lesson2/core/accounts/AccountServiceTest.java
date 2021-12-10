package lessons.lesson2.core.accounts;

import lessons.lesson2.model.Account;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    static AccountService accountService;

    @BeforeAll
     static void init () {
        accountService = new AccountService();
    }

    @Test
    void testAllOrders() {
        final Account account1 = new Account();
        account1.setClientId(1L);
        account1.setBalance(BigDecimal.valueOf(12345.0));

        final Account account2 = new Account();
        account2.setClientId(2L);
        account2.setBalance(BigDecimal.valueOf(54321.0));

        assertEquals(3, accountService.findAll().size());

        accountService.create(account1);
        accountService.create(account2);

        assertEquals(5, accountService.findAll().size());
    }

    @Test
    void findAccountByIdCorrectId() {
        final Account account1 = new Account();
        account1.setClientId(1L);
        account1.setBalance(BigDecimal.valueOf(12345.0));
        Account expectedAccount = accountService.create(account1);

        Optional<Account> actualAccount = accountService.findById(expectedAccount.getId());

        assertEquals(expectedAccount, actualAccount.get());
    }

    @Test
    void findAccountByIdIncorrectId() {
        Optional<Account> actualAccount = accountService.findById(-77777l);

        assertEquals(Optional.empty(), actualAccount);
    }

    @Test
    void deleteAccount() {
        final Account account1 = new Account();
        account1.setClientId(1L);
        account1.setBalance(BigDecimal.valueOf(12345.0));
        Account createdAccount = accountService.create(account1);

        accountService.deleteById(account1.getId());

        Optional<Account> actual = accountService.findById(account1.getId());

        assertEquals(Optional.empty(), actual);
        assertFalse(accountService.findAll().contains(createdAccount));
    }

    @Test
    void createAccount() {
        final Account account1 = new Account();
        account1.setClientId(1L);
        account1.setBalance(BigDecimal.valueOf(12345.0));
        Account createdAccount = accountService.create(account1);

        assertTrue(accountService.findAll().contains(createdAccount));
        assertNotNull(accountService.findById(createdAccount.getId()));
    }

    @Test
    void updateAccountCorrectId() {
        Account account1 = new Account();
        account1.setClientId(1L);
        account1.setBalance(BigDecimal.valueOf(12345.0));
        Account createdAccount = accountService.create(account1);

        Account toUpdate = new Account();
        toUpdate.setClientId(2L);
        toUpdate.setBalance(BigDecimal.valueOf(2222.0));

        Account actual = accountService.update(createdAccount.getId(), toUpdate).get();

        assertEquals(actual.getId(), createdAccount.getId());
        assertEquals(actual.getBalance(), toUpdate.getBalance());
        assertEquals(actual.getClientId(), toUpdate.getClientId());
    }

    @Test
    void updateAccountIncorrectId() {
        Account toUpdate = new Account();
        toUpdate.setClientId(2L);
        toUpdate.setBalance(BigDecimal.valueOf(2222.0));
        Optional<Account> actual = accountService.update(-111L, toUpdate);

        assertEquals(Optional.empty(), actual);
    }









}