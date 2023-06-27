package frentz.daniel.hardwareservice.dao.implementation;

import frentz.daniel.hardwareservice.converter.RegulatorConverter;
import frentz.daniel.hardwareservice.dao.RegulatorDAO;
import frentz.daniel.hardwareservice.entity.RegulatorEntity;
import frentz.daniel.hardwareservice.repository.RegulatorRepository;
import frentz.daniel.hardwareservice.model.Regulator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegulatorDAOImpl implements RegulatorDAO {

    private RegulatorRepository regulatorRepository;
    private RegulatorConverter regulatorConverter;

    public RegulatorDAOImpl(RegulatorRepository regulatorRepository,
                            RegulatorConverter regulatorConverter){
        this.regulatorRepository = regulatorRepository;
        this.regulatorConverter = regulatorConverter;
    }

    @Override
    public Regulator create(Regulator regulator) {
        RegulatorEntity regulatorEntity = new RegulatorEntity();
        this.regulatorConverter.fillEntity(regulatorEntity, regulator);
        regulatorEntity = this.regulatorRepository.save(regulatorEntity);
        Regulator result = this.regulatorConverter.toModel(regulatorEntity);
        return result;
    }

    @Override
    public RegulatorEntity save(RegulatorEntity regulator) {
        return this.regulatorRepository.save(regulator);
    }

    @Override
    public List<Regulator> getAll() {
        List<RegulatorEntity> regulatorEntities = this.regulatorRepository.findAll();
        return this.regulatorConverter.toModels(regulatorEntities);
    }

    @Override
    public void delete(long regulatorId) {
        this.regulatorRepository.deleteById(regulatorId);
    }
}
