package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink.service.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.exception.exception.DeviceNotFoundException;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardware.TpLinkHardware;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink.service.TpLinkCommandService;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink.service.TpLinkEncryptionService;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink.service.TpLinkQueryService;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink.service.TplinkActionService;

import java.io.OutputStream;
import java.net.Socket;

@Service
public class TplinkActionServiceImpl implements TplinkActionService {

    private final TpLinkEncryptionService tpLinkEncryptionService;
    private final TpLinkQueryService tpLinkQueryService;
    private final TpLinkCommandService tpLinkCommandService;

    public TplinkActionServiceImpl(TpLinkEncryptionService tpLinkEncryptionService,
                               TpLinkQueryService tpLinkQueryService,
                               TpLinkCommandService tpLinkCommandService) {
        this.tpLinkEncryptionService = tpLinkEncryptionService;
        this.tpLinkQueryService = tpLinkQueryService;
        this.tpLinkCommandService = tpLinkCommandService;
    }

    @Override
    public void setState(Hardware hardware) throws DeviceNotFoundException {
        TpLinkHardware tpLinkHardware = (TpLinkHardware) hardware;
        String macAddress = tpLinkHardware.getMacAddress();
        String ipAddress = this.tpLinkQueryService.getIpAddressFromMac(macAddress);
        if (ipAddress != null) {
            String payload = this.tpLinkCommandService.createStateCommand(hardware.getDesiredState().getState() == hardware.getOffState());
            byte[] encryptedPayload = this.tpLinkEncryptionService.encryptWithHeader(payload);
            try (Socket socket = new Socket(ipAddress, 9999)) {
                OutputStream os = socket.getOutputStream();
                os.write(encryptedPayload);
                os.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            throw new DeviceNotFoundException("Device with mac address " + macAddress + " not found");
        }
    }
}
