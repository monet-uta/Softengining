package org.example.model;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRecord {
    private LocalDateTime transactionTime;
    private String transactionObject;
    private BigDecimal amount;
    private String aiCategory;

    public TransactionRecord(LocalDateTime transactionTime, String transactionObject, BigDecimal amount) {
        this.transactionTime = transactionTime;
        this.transactionObject = transactionObject;
        this.amount = amount;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public String getTransactionObject() {
        return transactionObject;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getAiCategory() {
        return aiCategory;
    }

    public void setAiCategory(String aiCategory) {
        this.aiCategory = aiCategory;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "transactionTime=" + transactionTime +
                ", transactionObject='" + transactionObject + '\'' +
                ", amount=" + amount +
                ", aiCategory='" + aiCategory + '\'' +
                '}';
    }

    public static Map<String, Map<String, BigDecimal>> calculateTimeBasedCategoryTotals(List<TransactionRecord> records) {
        Map<String, Map<String, BigDecimal>> result = new HashMap<>();

        // 找到最新日期
        LocalDateTime latest = records.stream()
                .map(TransactionRecord::getTransactionTime)
                .max(LocalDateTime::compareTo)
                .orElseThrow(() -> new RuntimeException("No records found"));

        int latestYear = latest.getYear();
        int latestMonth = latest.getMonthValue();
        int latestQuarter = (latestMonth - 1) / 3 + 1;

        // 初始化返回结构
        Map<String, BigDecimal> monthlyTotals = new HashMap<>();
        Map<String, BigDecimal> quarterlyTotals = new HashMap<>();
        Map<String, BigDecimal> yearlyTotals = new HashMap<>();

        for (TransactionRecord record : records) {
            LocalDateTime time = record.getTransactionTime();
            String category = record.getAiCategory();
            if (category == null || category.isBlank()) continue;

            int year = time.getYear();
            int month = time.getMonthValue();
            int quarter = (month - 1) / 3 + 1;

            BigDecimal amount = record.getAmount();

            // 年统计
            if (year == latestYear) {
                yearlyTotals.merge(category, amount, BigDecimal::add);
            }

            // 季度统计
            if (year == latestYear && quarter == latestQuarter) {
                quarterlyTotals.merge(category, amount, BigDecimal::add);
            }

            // 月统计
            if (year == latestYear && month == latestMonth) {
                monthlyTotals.merge(category, amount, BigDecimal::add);
            }
        }

        result.put("monthly", monthlyTotals);
        result.put("quarterly", quarterlyTotals);
        result.put("yearly", yearlyTotals);

        return result;
    }

}
