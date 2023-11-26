package urbanjungletech.hardwareservice.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urbanjungletech.hardwareservice.addition.TimerAdditionService;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.service.query.TimerQueryService;

import java.util.List;

@RestController
@RequestMapping("/timer")
public class TimerEndpoint {

    private final TimerAdditionService timerAdditionService;
    private final TimerQueryService timerQueryService;

    public TimerEndpoint(TimerAdditionService timerAdditionService,
                         TimerQueryService timerQueryService){
        this.timerAdditionService = timerAdditionService;
        this.timerQueryService = timerQueryService;
    }

    @DeleteMapping("/{timerId}")
    public ResponseEntity deleteTimerById(@PathVariable("timerId") long timerId){
        this.timerAdditionService.delete(timerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{timerId}")
    public ResponseEntity<Timer> updateTimer(@PathVariable("timerId") long timerId,
                                             @RequestBody Timer timer){
        timer = this.timerAdditionService.update(timerId, timer);
        return ResponseEntity.ok(timer);
    }

    @GetMapping("/{timerId}")
    public ResponseEntity<Timer> getTimerById(@PathVariable("timerId") long timerId){
        Timer timer = this.timerQueryService.getTimer(timerId);
        return ResponseEntity.ok(timer);
    }

    @GetMapping("/")
    public ResponseEntity<List<Timer>> getAllTimers(){
        List<Timer> result = this.timerQueryService.getTimers();
        return ResponseEntity.ok(result);
    }
}
