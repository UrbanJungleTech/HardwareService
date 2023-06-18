package frentz.daniel.hardwareservice.service.implementation;

import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.dao.HardwareDAO;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.service.HardwareService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HardwareServiceImpl implements HardwareService {

    private HardwareDAO hardwareDAO;
    private HardwareConverter hardwareConverter;

    public HardwareServiceImpl(HardwareDAO hardwareDAO,
                               HardwareConverter hardwareConverter){
        this.hardwareDAO = hardwareDAO;
        this.hardwareConverter = hardwareConverter;
    }

    @Override
    public Hardware getHardware(long hardwareId) {
        HardwareEntity hardwareEntity = this.hardwareDAO.getHardware(hardwareId);
        return this.hardwareConverter.toModel(hardwareEntity);
    }

    @Override
    public List<Hardware> getAllHardware() {
        List<HardwareEntity> hardwareEntities = this.hardwareDAO.getAllHardware();
        return this.hardwareConverter.toModels(hardwareEntities);
    }

    @Override
    public Hardware getHardware(String serialNumber, long hardwarePort) {
        HardwareEntity hardwareEntity = this.hardwareDAO.getHardware(serialNumber, hardwarePort);
        return this.hardwareConverter.toModel(hardwareEntity);
    }
}
