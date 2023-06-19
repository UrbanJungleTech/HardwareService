package frentz.daniel.hardwareservice.dao.implementation;

import frentz.daniel.hardwareservice.converter.HardwareControllerConverter;
import frentz.daniel.hardwareservice.dao.HardwareControllerDAO;
import frentz.daniel.hardwareservice.entity.*;
import frentz.daniel.hardwareservice.exception.StandardNotFoundException;
import frentz.daniel.hardwareservice.repository.HardwareControllerRepository;
import frentz.daniel.hardwareservice.service.ExceptionService;
import frentz.daniel.hardwareservice.client.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HardwareControllerDAOImpl implements HardwareControllerDAO {

    private HardwareControllerRepository hardwareControllerRepository;
    private HardwareControllerConverter hardwareControllerConverter;
    private ExceptionService exceptionService;

    public HardwareControllerDAOImpl(HardwareControllerRepository hardwareControllerRepository,
                                     HardwareControllerConverter hardwareControllerConverter,
                                     ExceptionService exceptionService){
        this.hardwareControllerConverter = hardwareControllerConverter;
        this.hardwareControllerRepository = hardwareControllerRepository;
        this.exceptionService = exceptionService;
    }

    @Override
    public HardwareControllerEntity createHardwareController(HardwareController hardwareController) {
        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
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
            throw new StandardNotFoundException(HardwareControllerEntity.class.getName(), serialNumber);
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
}
