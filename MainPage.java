package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.collections.*;

public class MainPage extends Application {

    private TableView<Expense> table;
    private ObservableList<Expense> data;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TallyAPP");

        // --- 左侧菜单按钮 ---
        Label appTitle = new Label("TallyAPP");
        appTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        VBox.setMargin(appTitle, new Insets(10, 0, 20, 10));

        Button chartBtn = createIconButton("/images/chart.png", "Chart", 20, 160, 20);
        Button detailsBtn = createIconButton("/images/details.png", "Details", 20, 160, 20);

        chartBtn.setOnAction(e -> goToChartPage());     // TODO
        detailsBtn.setOnAction(e -> System.out.println("Already on details page"));

        VBox sideMenu = new VBox(10, appTitle, chartBtn, detailsBtn);
        sideMenu.setPadding(new Insets(10));
        sideMenu.setAlignment(Pos.TOP_CENTER);
        sideMenu.setStyle("-fx-background-color:rgb(248, 236, 220);");
        sideMenu.setPrefWidth(170);

        // --- 顶部搜索栏 ---
        TextField searchField = new TextField();
        searchField.setPromptText("Search transactions...");
        searchField.setPrefWidth(600);

        ComboBox<String> typeFilter = new ComboBox<>();
        typeFilter.getItems().addAll("All", "Shopping", "Catering", "Daily", "Entertainment");
        typeFilter.setValue("All");

        HBox searchBar = new HBox(10, searchField, typeFilter);
        searchBar.setPadding(new Insets(10, 0, 10, 0));
        searchBar.setAlignment(Pos.CENTER_LEFT);

        // --- Default Book 和按钮栏 ---
        Label defaultBook = new Label("Default Book");
        defaultBook.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Button historyBtn = createIconButton("/images/history.png", "history", 32, 10, 10);
        historyBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // 只显示图标
        Button exportBtn = createIconButton("/images/export.png", "export", 32,10,10);
        exportBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // 只显示图标
        Button importBtn = createIconButton("/images/import.png", "", 32,10,10);
        importBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // 只显示图标
        Button warningLabel = createIconButton("/images/warning.png", "", 32,10,10);
        warningLabel.setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // 只显示图标

        HBox topInfoBar = new HBox(
                new HBox(defaultBook),
                new HBox(historyBtn),
                new Region(),
                new HBox(5, exportBtn, warningLabel, importBtn)
        );
        HBox.setHgrow(topInfoBar.getChildren().get(1), Priority.ALWAYS);
        topInfoBar.setPadding(new Insets(5, 0, 5, 0));
        topInfoBar.setAlignment(Pos.CENTER_LEFT);

        // --- 表格区 ---
        table = new TableView<>();
        TableColumn<Expense, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(c -> c.getValue().dateProperty());
        TableColumn<Expense, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(c -> c.getValue().locationProperty());
        TableColumn<Expense, String> contentCol = new TableColumn<>("Content");
        contentCol.setCellValueFactory(c -> c.getValue().contentProperty());
        TableColumn<Expense, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(c -> c.getValue().amountProperty());
        TableColumn<Expense, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(c -> c.getValue().typeProperty());
        table.getColumns().addAll(dateCol, locationCol, contentCol, amountCol, typeCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 模拟数据
        data = FXCollections.observableArrayList(
                new Expense("2025-04-01", "Walmart", "Groceries", "120.00", "Daily"),
                new Expense("2025-04-02", "Cinema", "Movie", "50.00", "Entertainment")
        );
        table.setItems(data);

        // --- 主内容垂直堆叠 ---
        VBox centerContent = new VBox(10, searchBar, topInfoBar, table);
        centerContent.setStyle("-fx-background-color: white");
        centerContent.setPadding(new Insets(10));

        // --- 总布局为 BorderPane ---
        BorderPane root = new BorderPane();
        root.setLeft(sideMenu);
        root.setCenter(centerContent);

        // 事件监听（搜索过滤）
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterTable(newVal, typeFilter.getValue()));
        typeFilter.setOnAction(e -> filterTable(searchField.getText(), typeFilter.getValue()));

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createIconButton(String imagePath, String text, int imageSize, int btnWidth, int btnHeight) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        if (image.isError()) {
            System.err.println("❌ 图标加载失败: " + imagePath);
        }
        
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(imageSize);
        imageView.setFitHeight(imageSize);
    
        Button button = new Button(text, imageView); // 显示图标 + 文本
        button.setContentDisplay(ContentDisplay.LEFT); // 图标在左边，文本在右边
        button.setTooltip(new Tooltip(text));
        button.setPrefSize(btnWidth, btnHeight); // 长方形尺寸
        button.setStyle("-fx-background-color: #FFFFFF;" +
                "-fx-border-color: #FFFFFF;" +
                "-fx-border-radius: 5;" +
                "-fx-font-size: 13px;" +
                "-fx-alignment: CENTER_LEFT;" +
                "-fx-padding: 5 10 5 10;");
        return button;
    }
    
    

    private void filterTable(String keyword, String type) {
        ObservableList<Expense> filtered = FXCollections.observableArrayList();
        for (Expense e : data) {
            boolean matchKeyword = keyword.isEmpty() ||
                    e.getContent().toLowerCase().contains(keyword.toLowerCase()) ||
                    e.getType().toLowerCase().contains(keyword.toLowerCase());
            boolean matchType = type.equals("All") || e.getType().equalsIgnoreCase(type);
            if (matchKeyword && matchType) filtered.add(e);
        }
        table.setItems(filtered);
    }

    private void goToChartPage() {
        System.out.println("Navigating to Chart Page...");
        // TODO: 用新窗口或切换界面
    }

    public static void main(String[] args) {
        launch(args);
    }
}

