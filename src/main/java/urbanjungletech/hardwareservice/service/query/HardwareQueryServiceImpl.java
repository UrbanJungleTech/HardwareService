package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.model.Hardware;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HardwareQueryServiceImpl implements HardwareQueryService {

    private HardwareDAO hardwareDAO;
    private HardwareConverter hardwareConverter;

    public HardwareQueryServiceImpl(HardwareDAO hardwareDAO,
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

    @Override
    public Hardware getHardwareByDesiredState(long hardwareStateId) {
        HardwareEntity result = this.hardwareDAO.getHardwareByStateId(hardwareStateId);
        return this.hardwareConverter.toModel(result);
    }
}
