package frentz.daniel.hardwareservice.dao.implementation;

import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.dao.HardwareDAO;
import frentz.daniel.hardwareservice.dao.HardwareStateDAO;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.entity.HardwareStateEntity;
import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.model.HardwareStateType;
import frentz.daniel.hardwareservice.repository.HardwareRepository;
import frentz.daniel.hardwareservice.repository.HardwareStateRepository;
import frentz.daniel.hardwareservice.service.ExceptionService;
import org.springframework.stereotype.Service;

@Service
public class HardwareStateDAOImpl implements HardwareStateDAO {

    private HardwareRepository hardwareRepository;
    private HardwareStateRepository hardwareStateRepository;
    private HardwareStateConverter hardwareStateConverter;
    private ExceptionService exceptionService;

    public HardwareStateDAOImpl(HardwareRepository hardwareRepository,
                                HardwareStateRepository hardwareStateRepository,
                                ExceptionService exceptionService,
                                HardwareStateConverter hardwareStateConverter) {
        this.hardwareRepository = hardwareRepository;
        this.hardwareStateRepository = hardwareStateRepository;
        this.exceptionService = exceptionService;
        this.hardwareStateConverter = hardwareStateConverter;
    }

    @Override
    public HardwareStateEntity createHardwareState(HardwareState hardwareState, HardwareStateType hardwareStateType) {
        HardwareEntity hardwareEntity = this.hardwareRepository.findById(hardwareState.getHardwareId())
                .orElseThrow(() -> this.exceptionService.createNotFoundException(HardwareStateEntity.class, hardwareState.getHardwareId()));
        HardwareStateEntity hardwareStateEntity = this.hardwareStateConverter.toEntity(hardwareState);
        hardwareStateEntity.setHardware(hardwareEntity);
        hardwareStateEntity = this.hardwareStateRepository.save(hardwareStateEntity);
        if (hardwareStateType == HardwareStateType.DESIRED) {
            hardwareEntity.setDesiredState(hardwareStateEntity);
        } else {
            hardwareEntity.setCurrentState(hardwareStateEntity);
        }
        this.hardwareRepository.save(hardwareEntity);
        return hardwareStateEntity;
    }

    @Override
    public HardwareStateEntity updateHardwareState(HardwareState hardwareState) {
        HardwareStateEntity hardwareStateEntity = this.hardwareStateRepository.findById(hardwareState.getId())
                .orElseThrow(() -> this.exceptionService.createNotFoundException(HardwareStateEntity.class, hardwareState.getId()));
        this.hardwareStateConverter.fillEntity(hardwareStateEntity, hardwareState);
        hardwareStateEntity = this.hardwareStateRepository.save(hardwareStateEntity);
        return hardwareStateEntity;
    }

    @Override
    public HardwareStateEntity findById(long hardwareStateId) {
        return this.hardwareStateRepository.findById(hardwareStateId).orElseThrow(
                () -> this.exceptionService.createNotFoundException(HardwareStateEntity.class, hardwareStateId)
        );
    }
}
