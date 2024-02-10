package urbanjungletech.hardwareservice.converter.hardware.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardware.SpecificHardwareConverter;
import urbanjungletech.hardwareservice.entity.hardware.TpLinkHardwareEntity;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardware.TpLinkHardware;

@Service
public class TpLinkHardwareConverter implements SpecificHardwareConverter<TpLinkHardware, TpLinkHardwareEntity> {
    @Override
    public TpLinkHardware toModel(TpLinkHardwareEntity hardware) {
        TpLinkHardware result = new TpLinkHardware();
        return result;
    }

    @Override
    public TpLinkHardwareEntity createEntity(TpLinkHardware hardware) {
        TpLinkHardwareEntity result = new TpLinkHardwareEntity();
        return result;
    }

    @Override
    public void fillEntity(TpLinkHardwareEntity hardwareEntity, TpLinkHardware hardware) {

    }
}
