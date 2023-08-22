package urbanjungletech.hardwareservice.dao.implementation;

import urbanjungletech.hardwareservice.converter.HardwareControllerGroupConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerGroupDAO;
import urbanjungletech.hardwareservice.entity.HardwareControllerGroupEntity;
import urbanjungletech.hardwareservice.model.HardwareControllerGroup;
import urbanjungletech.hardwareservice.repository.HardwareControllerGroupRepository;
import urbanjungletech.hardwareservice.service.exception.ExceptionService;
import org.springframework.stereotype.Service;

@Service
public class HardwareControllerGroupDAOImpl implements HardwareControllerGroupDAO {
    private HardwareControllerGroupRepository hardwareControllerGroupRepository;
    private HardwareControllerGroupConverter hardwareControllerGroupConverter;
    private ExceptionService exceptionService;

    public HardwareControllerGroupDAOImpl(HardwareControllerGroupRepository hardwareControllerGroupRepository,
                                          HardwareControllerGroupConverter hardwareControllerGroupConverter,
                                          ExceptionService exceptionService) {
        this.hardwareControllerGroupRepository = hardwareControllerGroupRepository;
        this.hardwareControllerGroupConverter = hardwareControllerGroupConverter;
        this.exceptionService = exceptionService;
    }
    @Override
    public HardwareControllerGroupEntity createHardwareControllerGroup(HardwareControllerGroup hardwareControllerGroup) {
        HardwareControllerGroupEntity hardwareControllerGroupEntity = new HardwareControllerGroupEntity();
        this.hardwareControllerGroupConverter.fillEntity(hardwareControllerGroupEntity, hardwareControllerGroup);
        hardwareControllerGroupEntity = this.hardwareControllerGroupRepository.save(hardwareControllerGroupEntity);
        return hardwareControllerGroupEntity;
    }

    @Override
    public HardwareControllerGroupEntity updateHardwareControllerGroup(HardwareControllerGroup hardwareControllerGroup) {
        HardwareControllerGroupEntity hardwareControllerGroupEntity = this.hardwareControllerGroupRepository
                .findById(hardwareControllerGroup.getId()).orElseThrow(() -> this.exceptionService.createNotFoundException(HardwareControllerGroupEntity.class, hardwareControllerGroup.getId()));
        this.hardwareControllerGroupConverter.fillEntity(hardwareControllerGroupEntity, hardwareControllerGroup);
        hardwareControllerGroupEntity = this.hardwareControllerGroupRepository.save(hardwareControllerGroupEntity);
        return hardwareControllerGroupEntity;
    }

    @Override
    public HardwareControllerGroupEntity findById(long hardwareControllerGroupId) {
        HardwareControllerGroupEntity result = this.hardwareControllerGroupRepository.findById(hardwareControllerGroupId)
                .orElseThrow(() -> this.exceptionService.createNotFoundException(HardwareControllerGroupEntity.class, hardwareControllerGroupId));
        return result;
    }

    @Override
    public void delete(long hardwareControllerGroupId) {
        if(!this.hardwareControllerGroupRepository.existsById(hardwareControllerGroupId)){
            throw this.exceptionService.createNotFoundException(HardwareControllerGroupEntity.class, hardwareControllerGroupId);
        }
        this.hardwareControllerGroupRepository.deleteById(hardwareControllerGroupId);
    }
}
