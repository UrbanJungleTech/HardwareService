package frentz.daniel.hardwareservice.converter.implementation;

import frentz.daniel.hardwareservice.client.model.Regulator;
import frentz.daniel.hardwareservice.converter.RegulatorConverter;
import frentz.daniel.hardwareservice.entity.RegulatorEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegulatorConverterImpl implements RegulatorConverter {
    @Override
    public Regulator toModel(RegulatorEntity regulatorEntity) {
        Regulator result = new Regulator();
        result.setId(regulatorEntity.getId());
        result.setCheckInterval(regulatorEntity.getCheckInterval());
        result.setCorrectionInterval(regulatorEntity.getCorrectionInterval());
        result.setMaximumCorrectionHardwareId(regulatorEntity.getMaximumCorrectionHardwareId());
        result.setMinimumCorrectionHardwareId(regulatorEntity.getMinimumCorrectionHardwareId());
        result.setSensorId(regulatorEntity.getSensorId());
        result.setMaximumReading(regulatorEntity.getMaximumReading());
        result.setMinimumReading(regulatorEntity.getMinimumReading());
        return result;
    }

    @Override
    public List<Regulator> toModels(List<RegulatorEntity> regulatorEntities) {
        return regulatorEntities.stream().map((RegulatorEntity regulator) -> {
            return this.toModel(regulator);
        }).collect(Collectors.toList());
    }

    @Override
    public void fillEntity(RegulatorEntity regulatorEntity, Regulator regulator) {
        regulatorEntity.setCheckInterval(regulator.getCheckInterval());
        regulatorEntity.setMaximumCorrectionHardwareId(regulator.getMaximumCorrectionHardwareId());
        regulatorEntity.setMinimumCorrectionHardwareId(regulator.getMinimumCorrectionHardwareId());
        regulatorEntity.setMaximumReading(regulator.getMaximumReading());
        regulatorEntity.setMinimumReading(regulator.getMinimumReading());
        regulatorEntity.setSensorId(regulator.getSensorId());
        regulatorEntity.setCorrectionInterval(regulator.getCorrectionInterval());
    }
}
