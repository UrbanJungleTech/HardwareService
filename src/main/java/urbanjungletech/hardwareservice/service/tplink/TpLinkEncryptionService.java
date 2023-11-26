package urbanjungletech.hardwareservice.service.tplink;

public interface TpLinkEncryptionService {
    byte[] encryptWithHeader(String payload);

    byte[] encrypt(String payload);
}
