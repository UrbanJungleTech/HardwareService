package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.dao.RegulatorDAO;
import frentz.daniel.hardwareservice.schedule.service.RegulatorScheduleService;
import frentz.daniel.hardwareservice.model.Regulator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegulatorAdditionServiceImpl implements RegulatorAdditionService{

    private RegulatorDAO regulatorDAO;
    private RegulatorScheduleService regulatorScheduleService;

    public RegulatorAdditionServiceImpl(RegulatorDAO regulatorDAO,
                                        RegulatorScheduleService regulatorScheduleService){
        this.regulatorDAO = regulatorDAO;
        this.regulatorScheduleService = regulatorScheduleService;
    }

    @Override
    public Regulator create(Regulator regulator) {
        Regulator result = this.regulatorDAO.create(regulator);
        this.regulatorScheduleService.start(result);
        return result;
    }

    @Override
    public void delete(long regulatorId) {
        this.regulatorDAO.delete(regulatorId);
    }

    @Override
    public Regulator update(long regulatorId, Regulator regulator) {
        return null;
    }

    @Override
    public List<Regulator> updateList(List<Regulator> models) {
        return null;
    }
}
