package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.dao.HardwareStateDAO;
import urbanjungletech.hardwareservice.entity.HardwareStateEntity;
import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.HardwareStateType;
import urbanjungletech.hardwareservice.repository.HardwareRepository;
import urbanjungletech.hardwareservice.repository.HardwareStateRepository;

@Service
public class HardwareStateDAOImpl implements HardwareStateDAO {

    private final HardwareRepository hardwareRepository;
    private final HardwareStateRepository hardwareStateRepository;
    private final HardwareStateConverter hardwareStateConverter;
    private final ExceptionService exceptionService;

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
