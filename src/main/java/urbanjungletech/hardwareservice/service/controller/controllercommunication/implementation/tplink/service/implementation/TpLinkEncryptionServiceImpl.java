package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink.service.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink.service.TpLinkEncryptionService;

@Service
public class TpLinkEncryptionServiceImpl implements TpLinkEncryptionService {
    @Override
    public byte[] encryptWithHeader(String payload) {
        byte[] buffer = new byte[payload.length() + 4];
        byte[] encryptBytes = encrypt(payload);
        buffer[3] = (byte) encryptBytes.length;
        System.arraycopy(encryptBytes, 0, buffer, 4, encryptBytes.length);
        return buffer;
    }

    @Override
    public byte[] encrypt(String payload) {
        byte[] bytes = payload.getBytes();
        byte key = (byte) 0xAB;
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] ^ key);
            key = bytes[i];
        }
        return bytes;
    }
}
