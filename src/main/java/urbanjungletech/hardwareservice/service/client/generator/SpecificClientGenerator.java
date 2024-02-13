package urbanjungletech.hardwareservice.service.client.generator;

import urbanjungletech.hardwareservice.model.connectiondetails.ConnectionDetails;

public interface SpecificClientGenerator<ClientType, ConnectionDetailsType extends ConnectionDetails> {
    ClientType generateClient(ConnectionDetailsType connectionDetails);
}
