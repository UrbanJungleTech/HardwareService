package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink.service;

public interface TpLinkEncryptionService {
    byte[] encryptWithHeader(String payload);

    byte[] encrypt(String payload);
}
