package com.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Expense {
    private final StringProperty date;
    private final StringProperty location;
    private final StringProperty content;
    private final StringProperty amount;
    private final StringProperty type;

    public Expense(String date, String location, String content, String amount, String type) {
        this.date = new SimpleStringProperty(date);
        this.location = new SimpleStringProperty(location);
        this.content = new SimpleStringProperty(content);
        this.amount = new SimpleStringProperty(amount);
        this.type = new SimpleStringProperty(type);
    }

    public StringProperty dateProperty() { return date; }
    public StringProperty locationProperty() { return location; }
    public StringProperty contentProperty() { return content; }
    public StringProperty amountProperty() { return amount; }
    public StringProperty typeProperty() { return type; }

    public String getDate() { return date.get(); }
    public String getLocation() { return location.get(); }
    public String getContent() { return content.get(); }
    public String getAmount() { return amount.get(); }
    public String getType() { return type.get(); }
}
