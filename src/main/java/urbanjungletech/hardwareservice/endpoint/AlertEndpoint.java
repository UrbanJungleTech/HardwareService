package urbanjungletech.hardwareservice.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urbanjungletech.hardwareservice.addition.AlertActionAdditionService;
import urbanjungletech.hardwareservice.addition.AlertAdditionService;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.service.query.AlertQueryService;

import java.util.List;

@RestController
@RequestMapping("/alert")
public class AlertEndpoint {

    private AlertAdditionService alertAdditionService;
    private AlertQueryService alertQueryService;
    private AlertActionAdditionService alertActionAdditionService;
    public AlertEndpoint(AlertAdditionService alertAdditionService,
                         AlertQueryService alertQueryService,
                         AlertActionAdditionService alertActionAdditionService){
        this.alertAdditionService = alertAdditionService;
        this.alertQueryService = alertQueryService;
        this.alertActionAdditionService = alertActionAdditionService;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        this.alertAdditionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alert> update(@PathVariable("id") long id, @RequestBody Alert alert){
        Alert result = this.alertAdditionService.update(id, alert);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alert> getSensorReadingAlert(@PathVariable("id") long id){
        Alert result = this.alertQueryService.getSensorReadingAlert(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<List<Alert>> getAllSensorReadingAlerts(){
        List<Alert> result = this.alertQueryService.getAllSensorReadingAlerts();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/")
    public ResponseEntity<Alert> create(@RequestBody Alert alert){
        Alert response = this.alertAdditionService.create(alert);
        return ResponseEntity.ok(response);
    }

}
