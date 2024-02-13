package urbanjungletech.hardwareservice.model.credentials;

public class CertificateCredentials extends Credentials {
    private Byte[] certificate;

    public Byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(Byte[] certificate) {
        this.certificate = certificate;
    }
}
