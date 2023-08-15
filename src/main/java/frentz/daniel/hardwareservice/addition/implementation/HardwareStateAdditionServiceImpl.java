package frentz.daniel.hardwareservice.addition.implementation;

import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.addition.HardwareStateAdditionService;
import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.dao.HardwareStateDAO;
import frentz.daniel.hardwareservice.entity.HardwareStateEntity;
import frentz.daniel.hardwareservice.event.hardwarestate.HardwareStateEventPublisher;
import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.model.HardwareStateType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HardwareStateAdditionServiceImpl implements HardwareStateAdditionService {
    private HardwareStateDAO hardwareStateDAO;
    private HardwareStateConverter hardwareStateConverter;
    private HardwareStateEventPublisher hardwareStateEventPublisher;

    public HardwareStateAdditionServiceImpl(HardwareStateDAO hardwareStateDAO,
                                            HardwareStateConverter hardwareStateConverter,
                                            HardwareStateEventPublisher hardwareStateEventPublisher) {
        this.hardwareStateDAO = hardwareStateDAO;
        this.hardwareStateConverter = hardwareStateConverter;
        this.hardwareStateEventPublisher = hardwareStateEventPublisher;
    }

    @Transactional
    @Override
    public HardwareState create(HardwareState hardwareState, HardwareStateType hardwareStateType) {
        HardwareStateEntity hardwareStateEntity = this.hardwareStateDAO.createHardwareState(hardwareState, hardwareStateType);
        return this.hardwareStateConverter.toModel(hardwareStateEntity);
    }

    @Transactional
    @Override
    public HardwareState update(long hardwareStateId, HardwareState hardwareState) {
        hardwareState.setId(hardwareStateId);
        HardwareStateEntity currentHardwareState = this.hardwareStateDAO.findById(hardwareStateId);
        boolean updateState = currentHardwareState.getState() != hardwareState.getState() || currentHardwareState.getLevel() != hardwareState.getLevel();
        HardwareStateEntity hardwareStateEntity = this.hardwareStateDAO.updateHardwareState(hardwareState);
        HardwareState result = this.hardwareStateConverter.toModel(hardwareStateEntity);
        if(hardwareStateEntity.getHardware().getDesiredState().getId() == hardwareStateEntity.getId()
    && updateState){
            this.hardwareStateEventPublisher.publishHardwareStateUpdateEvent(hardwareStateEntity.getId());
        }
        return result;
    }
}
