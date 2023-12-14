package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.repository.HardwareRepository;

import java.util.List;

@Service
public class HardwareDAOImpl implements HardwareDAO {

    private final HardwareRepository hardwareRepository;
    private final HardwareConverter hardwareConverter;
    private final ExceptionService exceptionService;
    private final HardwareControllerRepository hardwareControllerRepository;

    public HardwareDAOImpl(HardwareRepository hardwareRepository,
                           HardwareConverter hardwareConverter,
                           ExceptionService exceptionService,
                           HardwareControllerRepository hardwareControllerRepository){
        this.hardwareConverter = hardwareConverter;
        this.hardwareRepository = hardwareRepository;
        this.exceptionService = exceptionService;
        this.hardwareControllerRepository = hardwareControllerRepository;
    }

    @Override
    public HardwareEntity updateHardware(Hardware hardware) {
        HardwareEntity hardwareEntity = this.hardwareRepository.findById(hardware.getId()).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(HardwareEntity.class, hardware.getId());
        });
        this.hardwareConverter.fillEntity(hardwareEntity, hardware);
        this.hardwareRepository.save(hardwareEntity);
        return hardwareEntity;
    }


    @Override
    public HardwareEntity createHardware(Hardware hardware) {
        HardwareEntity hardwareEntity = new HardwareEntity();
        this.hardwareConverter.fillEntity(hardwareEntity, hardware);
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository.findById(hardware.getHardwareControllerId()).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(HardwareControllerEntity.class, hardware.getHardwareControllerId());
        });
        hardwareEntity.setHardwareController(hardwareControllerEntity);
        hardwareEntity = this.hardwareRepository.save(hardwareEntity);
        hardwareControllerEntity.getHardware().add(hardwareEntity);
        this.hardwareControllerRepository.save(hardwareControllerEntity);
        return hardwareEntity;
    }

    @Override
    public HardwareEntity getHardware(long hardwareId) {
        HardwareEntity hardwareEntity = this.hardwareRepository.findById(hardwareId).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(HardwareEntity.class, hardwareId);
        });
        return hardwareEntity;
    }

    @Override
    public HardwareEntity getHardware(String serialNumber, String port) {
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository.findBySerialNumber(serialNumber);
        HardwareEntity hardwareEntity = this.hardwareRepository.findHardwareBySerialNumberAndPort(serialNumber, port).orElseThrow(() -> {
            throw this.exceptionService.createNotFoundException(HardwareEntity.class, port);
        });
        return hardwareEntity;
    }

    @Override
    public List<HardwareEntity> getAllHardware() {
        return this.hardwareRepository.findAll();
    }

    @Override
    public void delete(long hardwareId) {
        HardwareEntity hardwareEntity = hardwareRepository.findById(hardwareId).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(HardwareEntity.class, hardwareId);
        });
        hardwareEntity.getHardwareController().getHardware().remove(hardwareEntity);
        this.hardwareControllerRepository.save(hardwareEntity.getHardwareController());
        this.hardwareRepository.deleteById(hardwareId);
    }

    @Override
    public List<HardwareEntity> getHardwareByHardwareControllerId(long hardwareControllerId) {
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository.findById(hardwareControllerId).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(HardwareControllerEntity.class, hardwareControllerId);
        });
        return hardwareControllerEntity.getHardware();
    }

    @Override
    public HardwareEntity getHardwareByStateId(long hardwareStateId) {
        HardwareEntity result = this.hardwareRepository.findHardwareByHardwareStateId(hardwareStateId).orElseThrow(() -> {
            return this.exceptionService.createNotFoundException(HardwareEntity.class, hardwareStateId);
        });
        return result;
    }
}
