package urbanjungletech.hardwareservice.jsonrpc.method;

import java.util.HashMap;

public interface RpcCallback {
    Object process(HashMap<String, Object> params);
}
