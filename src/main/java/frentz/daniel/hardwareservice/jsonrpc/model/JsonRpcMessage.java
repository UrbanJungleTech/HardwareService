package frentz.daniel.hardwareservice.jsonrpc.model;

import java.util.HashMap;
import java.util.Map;

public class JsonRpcMessage {
    private String method;
    private Map<String, Object> params;
    private Map<String, Object> result;
    private String jsonrpc = "2.0";
    private long id;

    public JsonRpcMessage(){
        this.params = new HashMap<>();
    }

    public JsonRpcMessage(String method){
        this.method = method;
        this.params = new HashMap<>();
    }

    public JsonRpcMessage(String method, Map<String, Object> params){
        this.method = method;
        this.params = params;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
