package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.RegulatorEntity;
import frentz.daniel.hardwareservice.model.Regulator;

import java.util.List;

public interface RegulatorConverter {
    Regulator toModel(RegulatorEntity regulatorEntity);
    List<Regulator> toModels(List<RegulatorEntity> regulatorEntities);
    void fillEntity(RegulatorEntity regulatorEntity, Regulator regulator);
}
