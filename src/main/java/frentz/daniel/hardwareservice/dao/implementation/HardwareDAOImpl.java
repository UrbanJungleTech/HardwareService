package frentz.daniel.hardwareservice.dao.implementation;

import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.dao.HardwareDAO;
import frentz.daniel.hardwareservice.entity.HardwareControllerEntity;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import frentz.daniel.hardwareservice.repository.HardwareRepository;
import frentz.daniel.hardwareservice.service.ExceptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HardwareDAOImpl implements HardwareDAO {

    private HardwareRepository hardwareRepository;
    private HardwareConverter hardwareConverter;
    private ExceptionService exceptionService;
    private HardwareControllerRepository hardwareControllerRepository;

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
    public HardwareEntity updateHardware(HardwareEntity hardware) {
        return this.hardwareRepository.save(hardware);
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
    public HardwareEntity getHardware(String serialNumber, long port) {
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
}
