package org.example;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.time.Duration;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) {
        //TIP 当文本光标位于高亮显示的文本处时按 <shortcut actionId="ShowIntentionActions"/>
        // 查看 IntelliJ IDEA 建议如何修正。
        System.out.printf("Hello and welcome!\n");

        String apiKey = System.getenv("");
//        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
        OpenAiChatModel model = OpenAiChatModel
                .builder()
                .baseUrl("https://api.deepseek.com")
                .apiKey("sk-139414b0701b4855b0b25198a526b817")
                .modelName("deepseek-chat")
                .temperature(1.0)
                .timeout(Duration.ofSeconds(600))
                .build();

        OpenAiChatModel model_h = OpenAiChatModel
                .builder()
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3/")
                .apiKey("b2b7c86f-0123-4924-92e7-1ba4ae8c7964")
                .modelName("deepseek-r1-250120")
                .temperature(1.0)
                .timeout(Duration.ofSeconds(600))
                .build();

        String sentMessage = "Transaction time	Transaction object	Amount (RMB) 2/28/2024 22:47	DMALL	￥17.90 2/27/2024 22:41	DMALL	￥3.802/27/2024 22:36	Yiran	￥126.002/27/2024 19:58	Yiran	￥50.002/27/2024 16:48	Tencent	￥15.002/27/2024 7:53	DMALL	￥3.802/26/2024 18:50	DMALL	￥7.992/26/2024 18:23	BUPT	￥200.002/25/2024 19:54	Meituan	￥25.882/25/2024 9:31	Luodazhong	￥10.002/23/2024 19:20	Yiran	￥25.002/20/2024 21:02	Yitian	￥55.002/20/2024 14:03	Zhonghuang	￥2.002/19/2024 11:59	Yitian	￥18.002/17/2024 17:26	Rice Peel	￥8.002/15/2024 21:44	PDD	￥5.302/15/2024 20:02	Mingxin	￥37.75. Thought above transaction information, divide each transaction to four types which shopping living entertainment and catering, just give the most poosible output and response the type of  transaction one by one done anwser other sentence";

        String testMessage="2/15/2024 21:44	PDD	￥5.302/15/2024 20:02	Mingxin	￥37.75. Thought above transaction information, divide each transaction to four types which shopping living entertainment and catering";
        String answer = model.chat(testMessage);
        System.out.println(answer); // Hello World

//        for (int i = 1; i <= 5; i++) {
//            //TIP 按 <shortcut actionId="Debug"/> 开始调试代码。我们已经设置了一个 <icon src="AllIcons.Debugger.Db_set_breakpoint"/> 断点
//            // 但您始终可以通过按 <shortcut actionId="ToggleLineBreakpoint"/> 添加更多断点。
//            System.out.println("i = " + i);
//        }
    }
}