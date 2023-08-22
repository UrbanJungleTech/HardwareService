package urbanjungletech.hardwareservice.controller;

import urbanjungletech.hardwareservice.addition.TimerAdditionService;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.service.TimerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timer")
public class TimerEndpoint {

    private final TimerAdditionService timerAdditionService;
    private final TimerService timerService;

    public TimerEndpoint(TimerAdditionService timerAdditionService,
                         TimerService timerService){
        this.timerAdditionService = timerAdditionService;
        this.timerService = timerService;
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
        Timer timer = this.timerService.getTimer(timerId);
        return ResponseEntity.ok(timer);
    }

    @GetMapping("/")
    public ResponseEntity<List<Timer>> getAllTimers(){
        List<Timer> result = this.timerService.getTimers();
        return ResponseEntity.ok(result);
    }
}
