package frentz.daniel.controller.controller;

import frentz.daniel.controller.service.HardwareControllerService;
import frentz.daniel.controllerclient.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/hardwarecontroller", produces = "application/json")
public class HardwareControllerControl {

    private HardwareControllerService hardwareControllerService;

    public HardwareControllerControl(HardwareControllerService hardwareControllerService){
        this.hardwareControllerService = hardwareControllerService;
    }

    @PostMapping("/")
    public HardwareController create(@RequestBody HardwareController hardwareControl){
        return this.hardwareControllerService.createHardwareController(hardwareControl);
    }

    @GetMapping(value = "/")
    public List<HardwareController> getAll(){
        return this.hardwareControllerService.getAllHardware();
    }

    @GetMapping("/{hardwareControllerId}")
    public HardwareController getHardware(@PathVariable long hardwareControllerId){
        return this.hardwareControllerService.getHardwareController(hardwareControllerId);
    }

    @PostMapping("/{hardwareControllerId}/hardware")
    public Hardware createHardware(@PathVariable long hardwareControllerId, @RequestBody Hardware hardware){
        return this.hardwareControllerService.addHardware(hardwareControllerId, hardware);
    }

    @PostMapping("/{hardwareControllerId}/hardware/{port}/state")
    public HardwareController setState(@PathVariable long hardwareControllerId, @PathVariable long port, @RequestBody HardwareState hardwareState){
        return this.hardwareControllerService.setHardwareState(hardwareControllerId, port, hardwareState);
    }

}
