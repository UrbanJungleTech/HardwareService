package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.entity.*;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import frentz.daniel.hardwareservice.repository.HardwareRepository;
import frentz.daniel.hardwareservice.service.ExceptionService;
import frentz.daniel.hardwareservice.client.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    @Transactional
    public void removeHardware(String hardwareControllerSerialNumber, long port) {
        HardwareEntity hardwareEntity = this.hardwareRepository.findHardwareBySerialNumberAndPort(hardwareControllerSerialNumber, port);
        hardwareEntity.getHardwareController().getHardware().remove(hardwareEntity);
        this.hardwareRepository.delete(hardwareEntity);
    }



    @Override
    public HardwareEntity getHardware(String serialNumber, long port) {
        HardwareControllerEntity hardwareControllerEntity = this.hardwareControllerRepository.findBySerialNumber(serialNumber);
        Optional<HardwareEntity> result = hardwareControllerEntity.getHardware().stream().filter((hardwareEntity -> {
            if(hardwareEntity.getPort() == port){
                return true;
            }
            return false;
        })).findFirst();
        if(result.isEmpty() == false){
            return result.get();
        }
        throw this.exceptionService.createNotFoundException(HardwareEntity.class, port);
    }

    @Override
    public List<HardwareEntity> getAllHardware() {
        return this.hardwareRepository.findAll();
    }

    @Override
    public void delete(long hardwareId) {
        HardwareEntity entity = hardwareRepository.findById(hardwareId).get();
        entity.getHardwareController().getHardware().removeIf((HardwareEntity hardwareEntity) -> {
            return hardwareEntity.getId() == hardwareId;
        });
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