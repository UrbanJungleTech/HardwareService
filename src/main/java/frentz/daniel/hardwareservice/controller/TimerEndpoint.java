package frentz.daniel.hardwareservice.controller;

import frentz.daniel.hardwareservice.addition.TimerAdditionService;
import frentz.daniel.hardwareservice.client.model.Timer;
import frentz.daniel.hardwareservice.service.TimerService;
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
    public ResponseEntity deleteTimer(@PathVariable("timerId") long timerId){
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
    public ResponseEntity<Timer> getTimer(@PathVariable("timerId") long timerId){
        Timer timer = this.timerService.getTimer(timerId);
        return ResponseEntity.ok(timer);
    }

    @GetMapping("/")
    public ResponseEntity<List<Timer>> getTimers(){
        List<Timer> result = this.timerService.getTimers();
        return ResponseEntity.ok(result);
    }
}
