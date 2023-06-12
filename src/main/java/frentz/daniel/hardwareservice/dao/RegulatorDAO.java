package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.entity.RegulatorEntity;
import frentz.daniel.hardwareservice.client.model.Regulator;

import java.util.List;

public interface RegulatorDAO {
    Regulator create(Regulator regulator);
    RegulatorEntity save(RegulatorEntity regulator);
    List<Regulator> getAll();
    void delete(long regulatorId);
}
