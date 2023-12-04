package urbanjungletech.hardwareservice.model.credentials;

import jakarta.persistence.Entity;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;

public class CertificateCredentials extends Credentials {
    private Byte[] certificate;

    public Byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(Byte[] certificate) {
        this.certificate = certificate;
    }
}
