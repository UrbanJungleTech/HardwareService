package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.HardwareControllerConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerDAO;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.HardwareControllerGroupEntity;
import urbanjungletech.hardwareservice.exception.exception.NotFoundException;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.repository.HardwareControllerGroupRepository;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;

import java.util.List;

@Service
public class HardwareControllerDAOImpl implements HardwareControllerDAO {

    private final HardwareControllerRepository hardwareControllerRepository;
    private final HardwareControllerConverter hardwareControllerConverter;
    private final ExceptionService exceptionService;
    private final HardwareControllerGroupRepository hardwareControllerGroupRepository;

    public HardwareControllerDAOImpl(HardwareControllerRepository hardwareControllerRepository,
                                     HardwareControllerConverter hardwareControllerConverter,
                                     ExceptionService exceptionService,
                                     HardwareControllerGroupRepository hardwareControllerGroupRepository){
        this.hardwareControllerConverter = hardwareControllerConverter;
        this.hardwareControllerRepository = hardwareControllerRepository;
        this.exceptionService = exceptionService;
        this.hardwareControllerGroupRepository = hardwareControllerGroupRepository;
    }

    @Override
    public HardwareControllerEntity createHardwareController(HardwareController hardwareController) {
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerConverter.createEntity(hardwareController);
        this.hardwareControllerConverter.fillEntity(hardwareControllerEntity, hardwareController);
        hardwareControllerEntity = this.hardwareControllerRepository.save(hardwareControllerEntity);
        return hardwareControllerEntity;
    }


    @Override
    public List<HardwareControllerEntity> getAllHardwareControllers() {
        return this.hardwareControllerRepository.findAll();
    }



    @Override
    public HardwareControllerEntity getHardwareController(long hardwareControllerId) {
        return this.hardwareControllerRepository
                .findById(hardwareControllerId).orElseThrow(() -> this.exceptionService.createNotFoundException(HardwareControllerEntity.class, hardwareControllerId));
    }

    @Override
    public HardwareControllerEntity getBySerialNumber(String serialNumber) {
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository
                .findBySerialNumber(serialNumber);
        if(hardwareControllerEntity == null){
            throw new NotFoundException(HardwareControllerEntity.class.getName(), serialNumber);
        }
        return hardwareControllerEntity;
    }

    @Override
    public boolean exists(String serialNumber) {
        return this.hardwareControllerRepository.existsBySerialNumber(serialNumber);
    }

    @Override
    public HardwareControllerEntity updateHardwareController(HardwareController hardwareController) {
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository.findById(hardwareController.getId()).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(HardwareControllerEntity.class, hardwareController.getId());
        });
        this.hardwareControllerConverter.fillEntity(hardwareControllerEntity, hardwareController);
        HardwareControllerEntity result = this.hardwareControllerRepository.save(hardwareControllerEntity);
        if(hardwareController.getHardwareControllerGroupId() != null){
            HardwareControllerGroupEntity hardwareControllerGroupEntity = this.hardwareControllerGroupRepository.findById(hardwareController.getHardwareControllerGroupId()).orElseThrow(() -> {
                return this.exceptionService.createNotFoundException(HardwareControllerGroupEntity.class, hardwareController.getHardwareControllerGroupId());
            });
            hardwareControllerEntity.setControllerGroup(hardwareControllerGroupEntity);
            if(hardwareControllerGroupEntity.getHardwareControllers().stream().noneMatch(hardwareControllerEntity1 -> hardwareControllerEntity1.getId() == hardwareController.getId())){
                hardwareControllerGroupEntity.getHardwareControllers().add(hardwareControllerEntity);
                this.hardwareControllerGroupRepository.save(hardwareControllerGroupEntity);
            }
            this.hardwareControllerRepository.save(hardwareControllerEntity);
        }

        return result;
    }

    @Override
    public String getHardwareControllerSerialNumber(long hardwareControllerId) {
        return this.hardwareControllerRepository.getSerialNumberById(hardwareControllerId);
    }

    @Override
    public void delete(Long hardwareControllerId) {
        if(this.hardwareControllerRepository.existsById(hardwareControllerId) == false){
            throw this.exceptionService.createNotFoundException(HardwareControllerEntity.class, hardwareControllerId);
        }
        this.hardwareControllerRepository.deleteById(hardwareControllerId);
    }

    @Override
    public String getSerialNumber(long hardwareControllerId) {
        return this.hardwareControllerRepository.getSerialNumberById(hardwareControllerId);
    }

    @Override
    public void deleteAll() {
        this.hardwareControllerRepository.deleteAll();
    }

    @Override
    public String getControllerType(String serialNumber) {
        return this.hardwareControllerRepository.getControllerType(serialNumber).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(HardwareControllerEntity.class, serialNumber);
        });
    }
}
