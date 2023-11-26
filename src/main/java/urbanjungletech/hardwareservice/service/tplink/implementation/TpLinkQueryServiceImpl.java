package urbanjungletech.hardwareservice.service.tplink.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.service.tplink.TpLinkQueryService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class TpLinkQueryServiceImpl implements TpLinkQueryService {

    @Override
    public String getIpAddressFromMac(String macAddress) {
        String ipAddress = null;
        try {
            Process process = Runtime.getRuntime().exec("arp -a");
            try (BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = buf.readLine()) != null) {
                    if (line.toLowerCase().contains(macAddress.toLowerCase())) {
                        String[] parts = line.split("\\s+");
                        if (parts.length > 1) {
                            ipAddress = parts[1];
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ipAddress;
    }
}
