package lessons.lesson2.api;

import lessons.lesson2.core.accounts.AccountService;
import lessons.lesson2.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    AccountService accountService = Mockito.mock(AccountService.class);
    final AccountController accountController = new AccountController(accountService);

    @Test
    void findAccountByIdExistingId() {
        Mockito.when(accountService.findById(Mockito.any()))
                .thenReturn(Optional.of(new Account(1l, BigDecimal.valueOf(123.11), 1L)));

        final ResponseEntity<Account> responseEntity = accountController.findById(1L);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void findAccountByIdNotExistingId() {
        Mockito.when(accountService.findById(Mockito.any()))
                .thenReturn(Optional.empty());

        final ResponseEntity<Account> responseEntity = accountController.findById(444L);

        assertEquals(404, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());
    }

    @Test
    void findAllAccounts() {
        Account[] accounts = { new Account(1l, BigDecimal.valueOf(123), 1L),
                                new Account(2l, BigDecimal.valueOf(123), 2L)};
        Mockito.when(accountService.findAll()).thenReturn(new ArrayList<>(Arrays.asList(accounts)));

        final ResponseEntity<List<Account>> responseEntity = accountController.findAll();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(Arrays.asList(accounts), responseEntity.getBody());
    }

    @Test
    void findAllAccountsEmptyList() {
        Mockito.when(accountService.findAll()).thenReturn(new ArrayList<>());

        final ResponseEntity<List<Account>> responseEntity = accountController.findAll();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void createUser() {
        Mockito.when(accountService.create(Mockito.any())).thenReturn(new Account(1l, BigDecimal.valueOf(123), 1L));

        Account toCreate = new Account();
        toCreate.setBalance(BigDecimal.valueOf(123));
        toCreate.setClientId(1L);

        final ResponseEntity<Account> responseEntity = accountController.create(toCreate);

        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals(BigDecimal.valueOf(123), responseEntity.getBody().getBalance());
        assertEquals(1L, responseEntity.getBody().getClientId());
    }

    @Test
    void updateUserExistingId() {
        Account account = new Account(1l, BigDecimal.valueOf(123), 1l);
        Mockito.when(accountService.update(Mockito.any(), Mockito.any())).thenReturn(Optional.of(account));

        final ResponseEntity<Account> responseEntity = accountController.update(1l, account);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(account, responseEntity.getBody());
    }

    @Test
    void updateUserNotExistingId() {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(123));
        account.setClientId(1L);

        Mockito.when(accountService.update(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        final ResponseEntity<Account> responseEntity = accountController.update(-1l, account);

        assertEquals(404, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());
    }

    @Test
    void deleteUserExistingId() {
        Mockito.when(accountService.deleteById(Mockito.any())).thenReturn(true);

        final ResponseEntity responseEntity = accountController.delete(1l);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteUserNotExistingId() {
        Mockito.when(accountService.deleteById(Mockito.any())).thenReturn(false);

        final ResponseEntity responseEntity = accountController.delete(1l);

        assertEquals(404, responseEntity.getStatusCodeValue());
    }







}