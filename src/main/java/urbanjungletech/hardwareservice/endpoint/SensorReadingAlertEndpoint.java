package urbanjungletech.hardwareservice.endpoint;

import urbanjungletech.hardwareservice.model.Action;
import urbanjungletech.hardwareservice.addition.ActionAdditionService;
import urbanjungletech.hardwareservice.addition.SensorReadingAlertAdditionService;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import urbanjungletech.hardwareservice.service.query.SensorReadingAlertQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensorreadingalert")
public class SensorReadingAlertEndpoint {

    private SensorReadingAlertAdditionService sensorReadingAlertAdditionService;
    private SensorReadingAlertQueryService sensorReadingAlertQueryService;
    private ActionAdditionService actionAdditionService;
    public SensorReadingAlertEndpoint(SensorReadingAlertAdditionService sensorReadingAlertAdditionService,
                                      SensorReadingAlertQueryService sensorReadingAlertQueryService,
                                      ActionAdditionService actionAdditionService){
        this.sensorReadingAlertAdditionService = sensorReadingAlertAdditionService;
        this.sensorReadingAlertQueryService = sensorReadingAlertQueryService;
        this.actionAdditionService = actionAdditionService;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        this.sensorReadingAlertAdditionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorReadingAlert> update(@PathVariable("id") long id, @RequestBody SensorReadingAlert sensorReadingAlert){
        SensorReadingAlert result = this.sensorReadingAlertAdditionService.update(id, sensorReadingAlert);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorReadingAlert> getSensorReadingAlert(@PathVariable("id") long id){
        SensorReadingAlert result = this.sensorReadingAlertQueryService.getSensorReadingAlert(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<SensorReadingAlert>> getAllSensorReadingAlerts(){
        Iterable<SensorReadingAlert> result = this.sensorReadingAlertQueryService.getAllSensorReadingAlerts();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{sensorReadingId}/actions")
    public ResponseEntity<Action> addAction(@RequestBody Action action,
                                            @PathVariable("sensorReadingId") long sensorReadingId){
        action.setSensorReadingAlertId(sensorReadingId);
        Action result = this.actionAdditionService.create(action);
        return ResponseEntity.ok(result);
    }

}
