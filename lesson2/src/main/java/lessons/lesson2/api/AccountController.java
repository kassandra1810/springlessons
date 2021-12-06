package lessons.lesson2.api;

import lessons.lesson2.core.accounts.AccountService;
import lessons.lesson2.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> findAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> findById(@PathVariable("id") Long id) {
        return accountService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody Account resource) {
        return accountService.create(resource);
    }


    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Account> update(@PathVariable( "id" ) Long id, @RequestBody Account toUpdate) {
        Optional<Account> account = accountService.update(id, toUpdate);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }



    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if (accountService.deleteById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
