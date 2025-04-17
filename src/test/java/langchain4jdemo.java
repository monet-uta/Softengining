import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;

public class langchain4jdemo {

    @Test
    void test01() {
        OpenAiChatModel model = OpenAiChatModel
                .builder()
                .baseUrl("https://api.deepseek.com/")
                .apiKey("sk-ba04b8518a584f1bacd87f783f655c28")
                .modelName("deepseek-chat")
                .build();


        String answer = model.chat("Say 'Hello World'");
        System.out.println(answer); // Hello World
    }
}
