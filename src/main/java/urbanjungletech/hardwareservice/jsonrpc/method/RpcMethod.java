package urbanjungletech.hardwareservice.jsonrpc.method;

import java.util.Map;

public interface RpcMethod {
    void process(Map<String, Object> params);
}
