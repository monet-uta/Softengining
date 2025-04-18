package org.example.util;

import org.example.model.TransactionRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    public static List<TransactionRecord> parseCSV(String filePath) {
        List<TransactionRecord> records = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d HH:mm");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean reading = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Transaction time")) {
                    reading = true;
                    continue;
                }
                if (!reading || line.trim().isEmpty()) continue;

                String[] parts = line.split(",", -1);
                if (parts.length < 4) continue;

                try {
                    LocalDateTime time = LocalDateTime.parse(parts[0].trim(), formatter);
                    String object = parts[2].trim();
                    String amountStr = parts[3].replace("£¤", "").trim();
                    BigDecimal amount = new BigDecimal(amountStr);

                    records.add(new TransactionRecord(time, object, amount));
                } catch (Exception e) {
                    System.err.println("skip: " + line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }
}
