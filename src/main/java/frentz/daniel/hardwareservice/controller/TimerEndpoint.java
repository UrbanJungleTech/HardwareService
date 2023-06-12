package frentz.daniel.hardwareservice.controller;

import frentz.daniel.hardwareservice.addition.TimerAdditionService;
import frentz.daniel.hardwareservice.client.model.Timer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timer")
public class TimerEndpoint {

    private final TimerAdditionService timerAdditionService;

    public TimerEndpoint(TimerAdditionService timerAdditionService){
        this.timerAdditionService = timerAdditionService;
    }

    @PostMapping("/")
    public ResponseEntity<Timer> addTimer(@RequestBody Timer timer, @PathVariable long hardwareId){
        Timer result = this.timerAdditionService.create(timer);
        return ResponseEntity.ok(result);
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
}
