package frentz.daniel.hardwareservice.addition.implementation;

import frentz.daniel.hardwareservice.addition.RegulatorAdditionService;
import frentz.daniel.hardwareservice.dao.RegulatorDAO;
import frentz.daniel.hardwareservice.event.regulator.RegulatorEventPublisher;
import frentz.daniel.hardwareservice.schedule.regulator.RegulatorScheduleService;
import frentz.daniel.hardwareservice.model.Regulator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegulatorAdditionServiceImpl implements RegulatorAdditionService {

    private RegulatorDAO regulatorDAO;
    private RegulatorEventPublisher regulatorEventPublisher;

    public RegulatorAdditionServiceImpl(RegulatorDAO regulatorDAO,
                                        RegulatorEventPublisher regulatorEventPublisher){
        this.regulatorDAO = regulatorDAO;
        this.regulatorEventPublisher = regulatorEventPublisher;
    }

    @Override
    @Transactional
    public Regulator create(Regulator regulator) {
        Regulator result = this.regulatorDAO.create(regulator);
        this.regulatorEventPublisher.publishCreateEvent(result.getId());
        return result;
    }

    @Override
    @Transactional
    public void delete(long regulatorId) {
        this.regulatorDAO.delete(regulatorId);
        this.regulatorEventPublisher.publishDeleteEvent(regulatorId);
    }

    @Override
    @Transactional
    public Regulator update(long regulatorId, Regulator regulator) {
        return null;
    }

    @Override
    @Transactional
    public List<Regulator> updateList(List<Regulator> regulators) {
        List<Regulator> result = regulators.stream().map(regulator -> {
            if(regulator.getId() != null) {
                return this.update(regulator.getId(), regulator);
            }
            else{
                return this.create(regulator);
            }
        }).collect(Collectors.toList());
        return result;
    }
}
