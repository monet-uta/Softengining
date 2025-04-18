package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.example.model.TransactionRecord;
import org.example.util.CsvParser;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import io.github.cdimascio.dotenv.Dotenv;


//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) {
        //TIP 当文本光标位于高亮显示的文本处时按 <shortcut actionId="ShowIntentionActions"/>
        // 查看 IntelliJ IDEA 建议如何修正。
        System.out.printf("Main start!\n");
        Dotenv dotenv = Dotenv.load();
        String deepseek_api_key = dotenv.get("DEEPSEEK_API_KEY");

        String huoshan_api_key = dotenv.get("HUOSHAN_API_KEY");
        System.out.println("huoshan_api_key: " + huoshan_api_key);



        String filepath = "src/main/resources/data/(20240101-20240229).csv";
        String filepatht = "src/main/resources/data/test.csv";

        List<TransactionRecord> records = CsvParser.parseCSV(filepath);

//        String apiKey = System.getenv("");




//        prompt.append("You are a classification engine. Determine which category each transaction belongs to based on the following transaction records (shopping, living, entertainment, catering).")
//                .append("Only return the JSON array in the following format. Do not add explanations or Markdown symbols.\n")
//                .append("Format: [{\"object\": \"object name\", \"amount\": amount, \"category\": \"category\"}]\n\n")
//                .append("The following is the transaction record:\n");


//
//        OpenAiChatModel model = OpenAiChatModel
//                .builder()
//                .baseUrl("https://api.deepseek.com")
//                .apiKey(deepseek_api_key)
//                .modelName("deepseek-chat")
//                .temperature(1.0)
//                .timeout(Duration.ofSeconds(600))
//                .build();

        OpenAiChatModel model_h = OpenAiChatModel
                .builder()
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3/")
                .apiKey(huoshan_api_key)
                .modelName("deepseek-v3-250324")
                .temperature(0.01)
                .timeout(Duration.ofSeconds(60))
                .build();


        List<List<TransactionRecord>> batches = splitIntoBatches(records, 10);

        for (List<TransactionRecord> batch : batches) {
            String prompt = buildPrompt(batch);
            String response = model_h.chat(prompt);
            fillCategories(batch, response);
        }


        // 输出最终结果
        System.out.println("\n result");
        records.forEach(System.out::println);

    }

    private static List<List<TransactionRecord>> splitIntoBatches(List<TransactionRecord> records, int batchSize) {
        List<List<TransactionRecord>> batches = new ArrayList<>();
        for (int i = 0; i < records.size(); i += batchSize) {
            batches.add(records.subList(i, Math.min(i + batchSize, records.size())));
        }
        return batches;
    }


    private static String buildPrompt(List<TransactionRecord> records) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("请对以下交易记录进行分类（shopping、living、entertainment、catering），返回格式为 JSON 数组：")
                .append("[{\"object\": \"对象\", \"amount\": 金额, \"category\": \"分类\"}]\n\n");

        for (TransactionRecord record : records) {
            prompt.append(record.getTransactionObject())
                    .append(" ")
                    .append(record.getAmount())
                    .append("\n");
        }

        return prompt.toString();
    }

    private static void fillCategories(List<TransactionRecord> batch, String response) {
        try {
            int start = response.indexOf('[');
            int end = response.lastIndexOf(']') + 1;

            if (start == -1 || end <= start) {
                System.err.println("no JSON ");
                return;
            }

            String jsonPart = response.substring(start, end).trim();
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> resultList = mapper.readValue(jsonPart, new TypeReference<>() {});

            for (Map<String, Object> item : resultList) {
                String object = (String) item.get("object");
                BigDecimal amount = new BigDecimal(item.get("amount").toString());
                String category = (String) item.get("category");

                for (TransactionRecord record : batch) {
                    if (record.getTransactionObject().equalsIgnoreCase(object)
                            && record.getAmount().compareTo(amount) == 0) {
                        record.setAiCategory(category);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("JSON  failed");
            System.err.println(response);
            e.printStackTrace();
        }
    }

}