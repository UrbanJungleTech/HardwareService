package urbanjungletech.hardwareservice.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urbanjungletech.hardwareservice.addition.SensorReadingRouterAdditionService;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;
import urbanjungletech.hardwareservice.service.query.SensorReadingRouterQueryService;

@RestController
@RequestMapping("/sensorreadingrouter")
public class SensorReadingRouterEndpoint {
    private SensorReadingRouterAdditionService sensorReadingRouterAdditionService;
    private SensorReadingRouterQueryService sensorReadingRouterQueryService;

    public SensorReadingRouterEndpoint(SensorReadingRouterAdditionService sensorReadingRouterAdditionService,
                                       SensorReadingRouterQueryService sensorReadingRouterQueryService) {
        this.sensorReadingRouterAdditionService = sensorReadingRouterAdditionService;
        this.sensorReadingRouterQueryService = sensorReadingRouterQueryService;
    }
    @PostMapping("/")
    public ResponseEntity<SensorReadingRouter> createRouter(@RequestBody  SensorReadingRouter router) {
        SensorReadingRouter result = sensorReadingRouterAdditionService.create(router);
        return ResponseEntity.created(null).body(result);
    }

    @DeleteMapping("/{sensorReadingRouterId}")
    public ResponseEntity<SensorReadingRouter> deleteRouter(@PathVariable Long sensorReadingRouterId) {
        this.sensorReadingRouterAdditionService.delete(sensorReadingRouterId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sensorReadingRouterId}")
    public ResponseEntity<SensorReadingRouter> getRouter(@PathVariable Long sensorReadingRouterId) {
        SensorReadingRouter result = this.sensorReadingRouterQueryService.getSensorReadingRouter(sensorReadingRouterId);
        return ResponseEntity.ok(result);
    }
}
