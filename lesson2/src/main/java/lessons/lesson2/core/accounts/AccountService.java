package lessons.lesson2.core.accounts;

import lessons.lesson2.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class AccountService {

    private Map<Long, Account> accounts = new HashMap<>();
    private Long lastId = 0L;

    @Autowired
    public AccountService() {
        log.info("AccountService constructor");
        init();
    }

    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(accounts.get(id));
    }

    private void init() {
        Account account1 = new Account(1L,new BigDecimal(10.0),1L);
        Account account2 = new Account(2L,new BigDecimal(10.0),2L);
        Account account3 = new Account(3L,new BigDecimal(10.0),3L);
        accounts.put(generateId(), account1);
        accounts.put(generateId(), account2);
        accounts.put(generateId(), account3);
    }

    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    public boolean deleteById(Long id) {
        if(accounts.keySet().contains(id)) {
            accounts.remove(id);
            log.info("Deleting account");
            return true;
        }
        log.info("Account do not exists");
        return false;
    }

    public Account create(Account resource) {
        Long id = generateId();
        resource.setId(id);
        accounts.put(id, resource);
        log.info("Creating account");
        return resource;
    }

    private Long generateId() {
        return ++lastId;
    }

    public Optional<Account> update(Long id, Account account) {
        if (accounts.keySet().contains(id)) {
            Account toUpdate = accounts.get(id);
            toUpdate.setBalance(account.getBalance());
            toUpdate.setClientId(account.getClientId());
            return Optional.of(toUpdate);
        } else {
            return Optional.empty();
        }
    }

}
