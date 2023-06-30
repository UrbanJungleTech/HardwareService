package frentz.daniel.hardwareservice.controller;

import frentz.daniel.hardwareservice.addition.RegulatorAdditionService;
import frentz.daniel.hardwareservice.model.Regulator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/regulator")
public class RegulatorEndpoint {

    private final RegulatorAdditionService regulatorAdditionService;

    public RegulatorEndpoint(RegulatorAdditionService regulatorAdditionService){
        this.regulatorAdditionService = regulatorAdditionService;
    }

    @PostMapping("/")
    public ResponseEntity<Regulator> createRegulator(@RequestBody Regulator regulator){
        Regulator result = this.regulatorAdditionService.create(regulator);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{regulatorId}")
    public ResponseEntity deleteRegulatorById(@PathVariable("regulatorId") long regulatorId){
        this.regulatorAdditionService.delete(regulatorId);
        return ResponseEntity.noContent().build();
    }
}
