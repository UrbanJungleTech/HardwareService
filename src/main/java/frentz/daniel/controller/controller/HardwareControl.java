package frentz.daniel.controller.controller;

import frentz.daniel.controller.service.HardwareService;
import frentz.daniel.controllerclient.model.Hardware;
import frentz.daniel.controllerclient.model.Timer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hardware")
public class HardwareControl {

    private HardwareService hardwareService;

    public HardwareControl(HardwareService hardwareService){
        this.hardwareService = hardwareService;
    }

    @GetMapping("/{hardwareId}")
    public Hardware getHardware(@PathVariable long hardwareId){
        return this.hardwareService.getHardware(hardwareId);
    }

    @PostMapping("/{hardwareId}/timer")
    public Hardware addTimer(@RequestBody Timer timer, @PathVariable long hardwareId){
        try {
            return this.hardwareService.addTimer(hardwareId, timer);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}
