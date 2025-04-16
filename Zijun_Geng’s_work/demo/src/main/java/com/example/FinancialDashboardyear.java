package com.example;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
public class FinancialDashboardyear extends Application {

    private SimpleDoubleProperty fontSizeFactor = new SimpleDoubleProperty(1.0);

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        
        root.setStyle("-fx-background-color: white;");

        Label lblTitle = new Label("Tally app");
        lblTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");

        // 创建按钮
        Button btnPrevious = new Button("< Previous");
        btnPrevious.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        Button btnMonth = new Button("Month");
        Button btnQuarter = new Button("Quarter");
        Button btnYear = new Button("Year");
        Button btnSavingGoal = new Button("Saving Goal");

        btnMonth.setPrefWidth(90);  // 设置按钮宽度为 150
        btnMonth.setPrefHeight(40);  // 设置按钮高度为 50
        btnQuarter.setPrefWidth(90);  // 设置按钮宽度为 150
        btnSavingGoal.setPrefHeight(40);  // 设置按钮高度为 50
        btnSavingGoal.setPrefWidth(90);  // 设置按钮宽度为 150
        btnQuarter.setPrefHeight(40);  // 设置按钮高度为 50
        btnYear.setPrefWidth(90);  // 设置按钮宽度为 150
        btnYear.setPrefHeight(40);  // 设置按钮高度为 50

        addScaleAnimation(btnMonth);
        addScaleAnimation(btnQuarter);
        addScaleAnimation(btnYear);
        addScaleAnimation(btnSavingGoal);

        btnYear.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white; -fx-background-radius: 10px;");
        btnQuarter.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-background-radius: 10px;");
        btnMonth.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-background-radius: 10px;");
        btnSavingGoal.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-background-radius: 10px;");


        // 创建顶部栏的布局（VBox），设置背景为淡橘色
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #F4E1D2; -fx-padding: 10px;");
        HBox.setHgrow(topBar, Priority.ALWAYS);
        // 创建左侧按钮（Previous）
        HBox leftBox = new HBox(10);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        leftBox.getChildren().addAll(btnPrevious);
        // HBox.setMargin(buttonRow, new Insets(0, 0, 0, 0));
        HBox.setHgrow(leftBox, Priority.ALWAYS);
        HBox RightBox = new HBox(10);
        RightBox.setAlignment(Pos.CENTER_RIGHT);
        RightBox.getChildren().addAll(lblTitle);
        HBox.setMargin(RightBox, new Insets(0, 20, 0, 0));
        HBox.setHgrow(RightBox, Priority.ALWAYS);
        // 创建顶部栏，包含左侧的按钮和标题
        topBar.getChildren().addAll(leftBox, RightBox);




        HBox BottemBar = new HBox(10);
        BottemBar.setAlignment(Pos.CENTER_LEFT);
        BottemBar.setStyle("-fx-background-color:rgb(255, 255, 255); -fx-padding: 10px;");
        HBox.setHgrow(BottemBar, Priority.ALWAYS);
        // 创建按钮的行（HBox）来排列 Month, Quarter, Year
        HBox buttonRow = new HBox(30);
        buttonRow.setAlignment(Pos.CENTER_LEFT);
        buttonRow.getChildren().addAll(btnMonth, btnQuarter, btnYear);
        HBox.setHgrow(buttonRow, Priority.ALWAYS);
        HBox.setMargin(buttonRow, new Insets(0, 0, 0, 30));
        // 创建底部按钮行（HBox），背景为白色
        HBox bottomRow = new HBox(10);
        bottomRow.setAlignment(Pos.CENTER_RIGHT);  // "Save Goal" 按钮右对齐
        bottomRow.setStyle("-fx-background-color: white; -fx-padding: 10px;");
        bottomRow.getChildren().addAll(btnSavingGoal);
        HBox.setMargin(bottomRow, new Insets(0, 30, 0, 0));
        HBox.setHgrow(bottomRow, Priority.ALWAYS);
        BottemBar.getChildren().addAll(buttonRow, bottomRow);
        
        VBox TopScreen = new VBox();
        TopScreen.getChildren().addAll(topBar, BottemBar);


        root.setTop(TopScreen);



        // 设置财务概览卡片
        HBox leftCards = new HBox(10);
        leftCards.setAlignment(Pos.CENTER_LEFT);
        HBox centerCards = new HBox(10);
        centerCards.setAlignment(Pos.CENTER);
        HBox rightCards = new HBox(10);
        rightCards.setAlignment(Pos.CENTER_RIGHT);

        // 支出卡片
        VBox expenditureCard = createCard("Expenditure", "¥ 25,000", "¥ 20,000", Color.RED);
        // 收入卡片
        VBox incomeCard = createCard("Income", "¥ 40,000", "¥ 50,000", Color.GREEN);
        // 预算卡片
        VBox budgetCard = createCard("Budget", "¥ 20,000", "¥ 20,000", Color.BLACK);

        leftCards.getChildren().addAll(expenditureCard);
        HBox.setHgrow(leftCards, Priority.ALWAYS);
        HBox.setMargin(leftCards, new Insets(0, 0, 0, 60));
        leftCards.setStyle("-fx-background-color: transparent;");

        centerCards.getChildren().addAll(incomeCard);
        HBox.setHgrow(centerCards, Priority.ALWAYS);
        HBox.setMargin(centerCards, new Insets(0, 0, 0, 0));
        centerCards.setStyle("-fx-background-color: transparent;");

        rightCards.getChildren().addAll(budgetCard);
        HBox.setHgrow(rightCards, Priority.ALWAYS);
        HBox.setMargin(rightCards, new Insets(0, 60, 0, 0));
        rightCards.setStyle("-fx-background-color: transparent;");

        HBox Card = new HBox(10);
        Card.getChildren().addAll(leftCards,centerCards,rightCards);


        HBox charts = new HBox(10);
        charts.setPadding(new Insets(10));
        charts.setAlignment(Pos.CENTER);
        charts.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // 支出趋势图
        LineChart<Number, Number> lineChart = createLineChart();
        // 支出分类饼图
        PieChart pieChart = createPieChart();
        HBox.setHgrow(lineChart, Priority.ALWAYS);
        HBox.setHgrow(pieChart, Priority.ALWAYS);
        charts.getChildren().addAll(lineChart, pieChart);


        // root.setCenter(Center);


        // 设置排名列表和财务优化计划
        HBox bottomSection = new HBox(10);
        bottomSection.setPadding(new Insets(10));
        bottomSection.setAlignment(Pos.CENTER);
        bottomSection.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // 排名列表
        VBox rankList = createRankList();
        // 财务优化计划
        ScrollPane financialPlanScrollPane = new ScrollPane(createFinancialPlan());
        financialPlanScrollPane.setFitToWidth(true);
        financialPlanScrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(rankList, Priority.ALWAYS);
        HBox.setHgrow(financialPlanScrollPane, Priority.ALWAYS);
        bottomSection.getChildren().addAll(rankList, financialPlanScrollPane);


        VBox Center = new VBox(10);
        Center.getChildren().addAll(Card,charts,bottomSection);
        root.setCenter(Center);
        // 设置场景
        Scene scene = new Scene(root, 1200, 1000);

        // 设置舞台
        primaryStage.setTitle("Tally App");
        primaryStage.setScene(scene);


        // 显示舞台
        primaryStage.show();
    }
    private void addScaleAnimation(Button button) {
        // 创建按钮的缩放动画
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setFromX(1);  // 初始大小
        scaleTransition.setFromY(1);
        scaleTransition.setToX(1.2);  // 按钮放大至 1.2 倍
        scaleTransition.setToY(1.2);

        // 当鼠标悬停时，执行放大动画
        button.setOnMouseEntered(e -> scaleTransition.play());

        // 当鼠标离开时，执行缩小动画（没有反向动画，而是重新播放缩小动画）
        button.setOnMouseExited(e -> {
            ScaleTransition shrinkTransition = new ScaleTransition(Duration.millis(200), button);
            shrinkTransition.setFromX(1.2);  // 从当前大小（1.2）开始
            shrinkTransition.setFromY(1.2);
            shrinkTransition.setToX(1);  // 恢复到原始大小
            shrinkTransition.setToY(1);
            shrinkTransition.play();  // 播放缩小动画
        });
    }
    private void adjustFontSize(double width) {
        double baseWidth = 1000;
        double factor = width / baseWidth;
        fontSizeFactor.set(factor);
    }

    private VBox createCard(String title, String from, String to, Color color) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15,40,30,40));
        card.setStyle("-fx-background-color: white; " +
              "-fx-border-color: #e0e0e0; " +
              "-fx-border-width: 0px; " +
              "-fx-background-radius: 15px; " +  // 设置圆角
              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 5);"); 
        card.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Label titleLabel = new Label(title);
        titleLabel.setFont(new Font("Arial", 14));
        titleLabel.setTextFill(Color.GRAY);

        Label fromLabel = new Label(from);
        fromLabel.setFont(new Font("Arial", 25));
        fromLabel.setTextFill(color);

        Label arrowLabel = new Label("→");
        arrowLabel.setFont(new Font("Arial", 25));
        arrowLabel.setTextFill(Color.BLACK);
        HBox.setMargin(arrowLabel, new Insets(0, 0, 5, 0));

        Label toLabel = new Label(to);
        toLabel.setFont(new Font("Arial", 25));
        toLabel.setTextFill(color);

        HBox content = new HBox(10, fromLabel, arrowLabel, toLabel);
        card.getChildren().addAll(titleLabel, content);
        return card;
    }

    private LineChart<Number, Number> createLineChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Year");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount (¥)");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Expenditure");
        lineChart.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        lineChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Expenditure");
        series.getData().add(new XYChart.Data<>(1, 2500));
        series.getData().add(new XYChart.Data<>(2, 2550));
        series.getData().add(new XYChart.Data<>(3, 2600));
        series.getData().add(new XYChart.Data<>(4, 2650));
        series.getData().add(new XYChart.Data<>(5, 2700));
        series.getData().add(new XYChart.Data<>(6, 2750));
        series.getData().add(new XYChart.Data<>(7, 2800));
        series.getData().add(new XYChart.Data<>(8, 2850));
        series.getData().add(new XYChart.Data<>(9, 2900));
        series.getData().add(new XYChart.Data<>(10, 2950));
        series.getData().add(new XYChart.Data<>(11, 3000));
        series.getData().add(new XYChart.Data<>(12, 3050));


        lineChart.getData().add(series);
        lineChart.setLegendVisible(false);
        lineChart.setCreateSymbols(false);
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setAlternativeColumnFillVisible(false);
        return lineChart;
    }

    private PieChart createPieChart() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Frame");
        pieChart.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        pieChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        PieChart.Data catering = new PieChart.Data("Catering", 34.78);
        PieChart.Data entertainment = new PieChart.Data("Entertainment", 23.64);
        PieChart.Data transaction = new PieChart.Data("Transaction", 13.69);
        PieChart.Data dailyExpenses = new PieChart.Data("Daily expenses", 16.1);
        PieChart.Data socialContact = new PieChart.Data("Social contact", 9.76);
        PieChart.Data others = new PieChart.Data("Others", 11.42);

        pieChart.getData().addAll(catering, entertainment, transaction, dailyExpenses, socialContact, others);
        pieChart.setLabelsVisible(true);
        pieChart.setLegendVisible(true);
        return pieChart;
    }

    private VBox createRankList() {
        VBox rankList = new VBox(20);
        rankList.setPadding(new Insets(15));
        rankList.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        rankList.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Label titleLabel = new Label("Rank");
        titleLabel.setFont(new Font("Arial", 14));
        titleLabel.setTextFill(Color.GRAY);
        rankList.getChildren().add(titleLabel);

        // 添加排名项
        String[] types = {"Catering", "Entertainment", "Transaction", "Daily expenses", "Social contact"};
        String[] fromAmounts = {"¥6,750", "¥5,000", "¥3,750", "¥2,300", "¥2,500"};
        String[] toAmounts = {"¥7,500", "¥7,500", "¥3,750", "¥3,750", "¥1,250"};
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PURPLE};

        for (int i = 0; i < types.length; i++) {
            HBox rankItem = new HBox(20);
            Circle icon = new Circle(10);
            icon.setFill(colors[i]);

            Label typeLabel = new Label("type: " + types[i]);
            typeLabel.setFont(new Font("Arial", 12));
            typeLabel.setTextFill(Color.GRAY);

            Label fromLabel = new Label(fromAmounts[i]);
            fromLabel.setFont(new Font("Arial", 12));
            fromLabel.setTextFill(colors[i]);

            Label arrowLabel = new Label("→");
            arrowLabel.setFont(new Font("Arial", 12));
            arrowLabel.setTextFill(Color.GRAY);

            Label toLabel = new Label(toAmounts[i]);
            toLabel.setFont(new Font("Arial", 12));
            toLabel.setTextFill(colors[i]);

            HBox amountBox = new HBox(5, fromLabel, arrowLabel, toLabel);
            HBox.setHgrow(amountBox, Priority.ALWAYS);

            rankItem.getChildren().addAll(icon, typeLabel, amountBox);
            rankList.getChildren().add(rankItem);

            // 动态监听 HBox 大小，调整字体大小
            rankItem.widthProperty().addListener((observable, oldValue, newValue) -> {
                double width = newValue.doubleValue();
                adjustFontSize(typeLabel, width);
                adjustFontSize(fromLabel, width);
                adjustFontSize(arrowLabel, width);
                adjustFontSize(toLabel, width);
            });
        }

        return rankList;
    }

    // 根据宽度调整字体大小
    private void adjustFontSize(Label label, double width) {
        double fontSize = Math.max(20, width / 30);  // 根据宽度调整字体大小，最小为10
        label.setFont(new Font("Arial", fontSize));
    }



    private VBox createFinancialPlan() {
        VBox financialPlan = new VBox(10);
        financialPlan.setPadding(new Insets(15));
        financialPlan.setStyle("-fx-background-color: #f5f0e6; -fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        financialPlan.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Label titleLabel = new Label("Financial Optimization Plan (Given by Fairy)");
        titleLabel.setFont(new Font("Arial", 14 * fontSizeFactor.get()));
        titleLabel.setTextFill(Color.GRAY);
        financialPlan.getChildren().add(titleLabel);

        // 添加计划内容
        Label overallStrategyTitle = new Label("1. Overall Strategy");
        overallStrategyTitle.setFont(new Font("Arial", 12 * fontSizeFactor.get()));
        overallStrategyTitle.setTextFill(Color.GRAY);
        financialPlan.getChildren().add(overallStrategyTitle);

        Label overallStrategyContent = new Label(
                "Aim to boost income by 12.3% (#40k→#45k) and reduce expenses by 20% (#25k→#20k) while maintaining a 20% savings rate.\n" +
                "- Budget: Focus on a 50-30-20 allocation\n" +
                "- Debt: Pay off credit card debt\n" +
                "- Savings: 20% savings/investments (#4k)\n" +
                "Create a \"Growth Fund\" (#500/month) for skill development to support long-term income growth."
        );
        overallStrategyContent.setFont(new Font("Arial", 11 * fontSizeFactor.get()));
        overallStrategyContent.setTextFill(Color.GRAY);
        financialPlan.getChildren().add(overallStrategyContent);

        Label keyAdjustmentsTitle = new Label("2. Key Adjustments");
        keyAdjustmentsTitle.setFont(new Font("Arial", 12 * fontSizeFactor.get()));
        keyAdjustmentsTitle.setTextFill(Color.GRAY);
        financialPlan.getChildren().add(keyAdjustmentsTitle);

        Label expenseOptimizationTitle = new Label("1) Expense Optimization");
        expenseOptimizationTitle.setFont(new Font("Arial", 11 * fontSizeFactor.get()));
        expenseOptimizationTitle.setTextFill(Color.GRAY);
        financialPlan.getChildren().add(expenseOptimizationTitle);

        Label expenseOptimizationContent = new Label(
                "- Dining: ¥750 → ¥600 (save ¥150+)\n" +
                "- Entertainment: ¥5,000 → ¥3,500\n" +
                "- Transportation: ¥3,750 → ¥2,500\n" +
                "- Daily expenses: ¥3,000 → ¥2,750\n" +
                "- Social contact: ¥2,500 → ¥1,250\n" +
                "- Utilities: Invest in smart home devices to cut energy costs.\n" +
                "- Set aside ¥1,000/month for emergencies."
        );
        expenseOptimizationContent.setFont(new Font("Arial", 10 * fontSizeFactor.get()));
        expenseOptimizationContent.setTextFill(Color.GRAY);
        financialPlan.getChildren().add(expenseOptimizationContent);

        Label incomeGrowthTitle = new Label("2) Income Growth");
        incomeGrowthTitle.setFont(new Font("Arial", 11 * fontSizeFactor.get()));
        incomeGrowthTitle.setTextFill(Color.GRAY);
        financialPlan.getChildren().add(incomeGrowthTitle);

        Label incomeGrowthContent = new Label(
                "- Career: Pursue certifications (#150/month) for a 15% salary increase.\n" +
                "- Side Hustle: Earn ¥3k+/month via freelance work (e.g., design, transcription).\n" +
                "- Investments: Allocate 50% of savings to modern ETF portfolios (5-7% returns)."
        );
        incomeGrowthContent.setFont(new Font("Arial", 10 * fontSizeFactor.get()));
        incomeGrowthContent.setTextFill(Color.GRAY);
        financialPlan.getChildren().add(incomeGrowthContent);

        return financialPlan;
    }

    public static void main(String[] args) {
        launch(args);
    }
}