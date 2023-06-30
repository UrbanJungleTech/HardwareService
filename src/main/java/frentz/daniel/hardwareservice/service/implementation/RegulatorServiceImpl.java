package frentz.daniel.hardwareservice.service.implementation;

import frentz.daniel.hardwareservice.converter.RegulatorConverter;
import frentz.daniel.hardwareservice.dao.RegulatorDAO;
import frentz.daniel.hardwareservice.entity.RegulatorEntity;
import frentz.daniel.hardwareservice.model.Regulator;
import frentz.daniel.hardwareservice.service.RegulatorService;
import org.springframework.stereotype.Service;

@Service
public class RegulatorServiceImpl implements RegulatorService {

    private RegulatorDAO regulatorDAO;
    private RegulatorConverter regulatorConverter;

    public RegulatorServiceImpl(RegulatorDAO regulatorDAO, RegulatorConverter regulatorConverter) {
        this.regulatorDAO = regulatorDAO;
        this.regulatorConverter = regulatorConverter;
    }
    @Override
    public Regulator get(long regulatorId) {
        RegulatorEntity regulatorEntity = this.regulatorDAO.get(regulatorId);
        Regulator result = this.regulatorConverter.toModel(regulatorEntity);
        return result;
    }
}
