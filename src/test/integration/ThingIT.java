import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import org.junit.jupiter.api.Test;

public class ThingIT {

    @Test
    public void bla(){
        QueueClient client = new QueueClientBuilder()
                .endpoint("https://frentztesting.queue.core.windows.net")
                .queueName("frentz-testing-queue")
                .sasToken("?sv=2022-11-02&ss=q&srt=sco&sp=rwdlacup&se=2023-12-05T09:54:34Z&st=2023-12-05T01:54:34Z&sip=66.234.33.165&spr=https&sig=vE5Vu5Z4FPjJ5lzaK21o2yopy%2FHOD5k0i5xrOImOlEA%3D")
                .buildClient();
        client.sendMessage("Hello World");
    }
}
