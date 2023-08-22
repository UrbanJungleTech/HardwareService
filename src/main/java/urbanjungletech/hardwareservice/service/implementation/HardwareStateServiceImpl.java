package urbanjungletech.hardwareservice.service.implementation;

import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.dao.HardwareStateDAO;
import urbanjungletech.hardwareservice.entity.HardwareStateEntity;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.service.HardwareStateService;
import org.springframework.stereotype.Service;

@Service
public class HardwareStateServiceImpl implements HardwareStateService {

    private HardwareStateDAO hardwareStateDAO;
    private HardwareStateConverter hardwareStateConverter;

    public HardwareStateServiceImpl(HardwareStateDAO hardwareStateDAO,
                                    HardwareStateConverter hardwareStateConverter) {
        this.hardwareStateDAO = hardwareStateDAO;
        this.hardwareStateConverter = hardwareStateConverter;
    }

    @Override
    public HardwareState getHardwareStateById(long hardwareStateId) {
        HardwareStateEntity hardwareStateEntity = this.hardwareStateDAO.findById(hardwareStateId);
        HardwareState result = this.hardwareStateConverter.toModel(hardwareStateEntity);
        return result;
    }
}
