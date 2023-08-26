package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.dao.HardwareStateDAO;
import urbanjungletech.hardwareservice.entity.HardwareStateEntity;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.service.query.HardwareStateQueryService;
import org.springframework.stereotype.Service;

@Service
public class HardwareStateQueryServiceImpl implements HardwareStateQueryService {

    private HardwareStateDAO hardwareStateDAO;
    private HardwareStateConverter hardwareStateConverter;

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
