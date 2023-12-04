package urbanjungletech.hardwareservice.converter.credentials.implementation;

import urbanjungletech.hardwareservice.converter.credentials.SpecificCredentialsConverter;
import urbanjungletech.hardwareservice.entity.credentials.CertificateCredentialsEntity;
import urbanjungletech.hardwareservice.model.credentials.CertificateCredentials;

public class CertificateCredentialsConverter implements SpecificCredentialsConverter<CertificateCredentials, CertificateCredentialsEntity> {
    @Override
    public CertificateCredentials toModel(CertificateCredentialsEntity entity) {
        CertificateCredentials result = new CertificateCredentials();
        result.setCertificate(entity.getCertificate());
        return result;
    }

    @Override
    public CertificateCredentialsEntity createEntity(CertificateCredentials certificateCredentials) {
        CertificateCredentialsEntity result = new CertificateCredentialsEntity();
        this.fillEntity(result, certificateCredentials);
        return result;
    }

    @Override
    public void fillEntity(CertificateCredentialsEntity entity, CertificateCredentials certificateCredentials) {
        entity.setCertificate(certificateCredentials.getCertificate());
    }
}
