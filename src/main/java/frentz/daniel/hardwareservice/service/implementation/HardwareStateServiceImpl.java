package frentz.daniel.hardwareservice.service.implementation;

import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.dao.HardwareStateDAO;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.entity.HardwareStateEntity;
import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.service.HardwareStateService;
import org.springframework.stereotype.Service;

@Service
public class HardwareStateServiceImpl implements HardwareStateService {

    private HardwareStateDAO hardwareStateDAO;
    private HardwareStateConverter hardwareStateConverter;

    public HardwareStateServiceImpl(HardwareStateDAO hardwareStateDAO,
                                    HardwareStateConverter hardwareStateConverter) {
        this.hardwareStateDAO = hardwareStateDAO;
        this.hardwareStateConverter = hardwareStateConverter;
    }

    @Override
    public HardwareState getHardwareStateById(long hardwareStateId) {
        HardwareStateEntity hardwareStateEntity = this.hardwareStateDAO.findById(hardwareStateId);
        HardwareState result = this.hardwareStateConverter.toModel(hardwareStateEntity);
        return result;
    }
}
