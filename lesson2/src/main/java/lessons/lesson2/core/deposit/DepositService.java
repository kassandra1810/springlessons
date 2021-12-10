package lessons.lesson2.core.deposit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepositService {

    @Autowired
    public DepositService() {
        log.info("DepositService constructor");
    }

}
