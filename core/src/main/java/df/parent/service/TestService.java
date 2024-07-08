package df.parent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestService {

    private StateCheck stateCheck;

    @Autowired
    public TestService(StateCheck stateCheck) {
        this.stateCheck = stateCheck;
    }

}
