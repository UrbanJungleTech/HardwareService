package urbanjungletech.hardwareservice.helpers.mock.action;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.service.alert.action.SpecificActionExecutionService;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class MockActionService implements SpecificActionExecutionService<MockAction> {

    private AtomicLong counter = new AtomicLong();

    public Long getCounter() {
        return counter.get();
    }

    @Override
    public void execute(MockAction action) {

        this.counter.incrementAndGet();
    }
}
