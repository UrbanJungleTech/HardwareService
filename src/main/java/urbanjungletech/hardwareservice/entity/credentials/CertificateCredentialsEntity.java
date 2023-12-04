package urbanjungletech.hardwareservice.entity.credentials;

import jakarta.persistence.Entity;

public class CertificateCredentialsEntity extends CredentialsEntity{
    private Byte[] certificate;

    public Byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(Byte[] certificate) {
        this.certificate = certificate;
    }
}
