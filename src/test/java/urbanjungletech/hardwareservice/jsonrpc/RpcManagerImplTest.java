package urbanjungletech.hardwareservice.jsonrpc;

import urbanjungletech.hardwareservice.exception.exception.RpcMethodNotFoundException;
import urbanjungletech.hardwareservice.jsonrpc.method.RpcMethod;
import urbanjungletech.hardwareservice.jsonrpc.model.JsonRpcMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RpcManagerImplTest {

    @Mock
    private Map<String, RpcMethod> methods;
    @Mock
    private RpcResponseProcessor rpcResponseProcessor;
    @Mock
    RpcMethod rpcMethod;
    @InjectMocks
    private RpcManagerImpl rpcManager;

    /**
     * Given an JsonRpcMessage with an id of 0
     * When rpcManager.process is called
     * Then the get method should be called on the methods map with the method field from the JsonRpcMessage
     * And the process method should be called on the RpcMethod returned from the methods map
     */
    @Test
    public void process_whenZeroIdIsGiven_andRpcMethodExists_shouldExecuteRpcMessageTest(){
        JsonRpcMessage jsonRpcMessage = new JsonRpcMessage();
        jsonRpcMessage.setId(0);
        String method = "testMethod";
        jsonRpcMessage.setMethod(method);

        when(methods.get(method)).thenReturn(rpcMethod);

        rpcManager.process(jsonRpcMessage);

        verify(methods, times(1)).get(method);
        verify(rpcMethod, times(1)).process(jsonRpcMessage.getParams());
    }

    /**
     * Given an JsonRpcMessage with a non zero id
     * When rpcManager.process is called
     * Then the addResponse method should be called on the rpcResponseProcessor
     * And the JsonRpcMessage should be passed to the addResponse method
     */
    @Test
    public void process_whenNonZeroIdIsGiven_shouldRegisterResponseTest(){
        JsonRpcMessage jsonRpcMessage = new JsonRpcMessage();
        jsonRpcMessage.setId(1);

        rpcManager.process(jsonRpcMessage);

        verify(rpcResponseProcessor, times(1)).addResponse(jsonRpcMessage);
    }

    /**
     * Given an JsonRpcMessage with a non zero id
     * Given the method in the RpcMessage is not in the methods map
     * When rpcManager.process is called
     * Then a RpcMethodNotFoundException should be thrown
     */
    @Test
    public void process_whenMethodIsNotFound_shouldThrowRpcMethodNotFoundExceptionTest(){
        JsonRpcMessage jsonRpcMessage = new JsonRpcMessage();
        jsonRpcMessage.setId(0);
        String method = "testMethod";
        jsonRpcMessage.setMethod(method);

        when(methods.get(method)).thenReturn(null);

        assertThrows(RpcMethodNotFoundException.class, () -> rpcManager.process(jsonRpcMessage));
    }
}
