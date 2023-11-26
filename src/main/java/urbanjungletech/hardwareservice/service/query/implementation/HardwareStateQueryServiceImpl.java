package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.dao.HardwareStateDAO;
import urbanjungletech.hardwareservice.entity.HardwareStateEntity;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.service.query.HardwareStateQueryService;

@Service
public class HardwareStateQueryServiceImpl implements HardwareStateQueryService {

    private final HardwareStateDAO hardwareStateDAO;
    private final HardwareStateConverter hardwareStateConverter;

    public HardwareStateQueryServiceImpl(HardwareStateDAO hardwareStateDAO,
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
