package com.example;
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
import javafx.stage.Stage;

public class FinancialDashboard extends Application {

    private SimpleDoubleProperty fontSizeFactor = new SimpleDoubleProperty(1.0);

    @Override
    public void start(Stage primaryStage) {
        // 设置舞台标题
        primaryStage.setTitle("Tally App");

        // 创建主布局
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // 设置顶部导航栏
        HBox topNav = new HBox(10);
        topNav.setPadding(new Insets(10));
        // topNav.setAlignment(Pos.CENTER);
        topNav.setStyle("-fx-background-color: #f5f0e6;");
        topNav.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // 创建 Label
        Label tallyAppLabel = new Label("Tally App");
        tallyAppLabel.setFont(new Font("Arial", 14));
        tallyAppLabel.setTextFill(Color.GRAY);

        // 使用 StackPane 来包裹 Label，并设置文本对齐为右对齐
        StackPane labelContainer = new StackPane(tallyAppLabel);
        StackPane.setAlignment(tallyAppLabel, Pos.CENTER_RIGHT); // 确保文本在右侧对齐

        // 设置 HBox 的扩展行为，让 Label 占据剩余空间
        HBox.setHgrow(labelContainer, Priority.ALWAYS);

        // 设置 Label 的右边距，避免与右边界太近
        HBox.setMargin(labelContainer, new Insets(0, 10, 0, 0));

        // 创建并设置返回按钮（previousBtn）
        Button previousBtn = new Button("←");
        previousBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #333333;");
        HBox.setMargin(previousBtn, new Insets(0, 0, 0, 10));

        topNav.getChildren().addAll(previousBtn, tallyAppLabel);
        root.setTop(topNav);

        // 设置时间筛选和保存目标按钮
        HBox filterAndGoal = new HBox(10);
        filterAndGoal.setPadding(new Insets(10));
        // filterAndGoal.setAlignment(Pos.CENTER);
        filterAndGoal.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // 时间筛选按钮
        Button monthBtn = new Button("Month");
        monthBtn.setStyle("-fx-background-color: #ffcc99; -fx-text-fill: #333333;");
        Button quarterBtn = new Button("Quarter");
        quarterBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #333333;");
        Button yearBtn = new Button("Year");
        yearBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #333333;");

        // Saving Goal 按钮
        Button savingGoalBtn = new Button("Saving Goal");
        savingGoalBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #333333;");
        HBox.setHgrow(savingGoalBtn, Priority.ALWAYS);

        filterAndGoal.getChildren().addAll(monthBtn, quarterBtn, yearBtn, savingGoalBtn);
        root.setTop(new VBox(topNav, filterAndGoal));

        // 设置财务概览卡片
        HBox overviewCards = new HBox(10);
        overviewCards.setPadding(new Insets(10));
        overviewCards.setAlignment(Pos.CENTER);
        overviewCards.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // 支出卡片
        VBox expenditureCard = createCard("Expenditure", "¥25,000", "¥20,000", Color.RED);
        // 收入卡片
        VBox incomeCard = createCard("Income", "¥40,000", "¥50,000", Color.GREEN);
        // 预算卡片
        VBox budgetCard = createCard("Budget", "¥20,000", "¥20,000", Color.BLACK);
        overviewCards.getChildren().addAll(expenditureCard, incomeCard, budgetCard);
        HBox.setHgrow(overviewCards, Priority.ALWAYS);
        root.setCenter(new VBox(overviewCards));

        // 设置图表区域
        HBox charts = new HBox(10);
        charts.setPadding(new Insets(10));
        charts.setAlignment(Pos.CENTER);
        charts.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // 支出趋势图
        LineChart<Number, Number> lineChart = createLineChart();
        // 支出分类饼图
        PieChart pieChart = createPieChart();
        charts.getChildren().addAll(lineChart, pieChart);
        HBox.setHgrow(lineChart, Priority.ALWAYS);
        HBox.setHgrow(pieChart, Priority.ALWAYS);
        root.setCenter(new VBox(overviewCards, charts));

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

        bottomSection.getChildren().addAll(rankList, financialPlanScrollPane);
        HBox.setHgrow(rankList, Priority.ALWAYS);
        HBox.setHgrow(financialPlanScrollPane, Priority.ALWAYS);
        root.setCenter(new VBox(overviewCards, charts, bottomSection));

        // 设置场景
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        // 添加窗口大小变化监听器
        scene.widthProperty().addListener((observable, oldValue, newValue) -> adjustFontSize(newValue.doubleValue()));
        scene.heightProperty().addListener((observable, oldValue, newValue) -> adjustFontSize(scene.getWidth()));
    }

    private void adjustFontSize(double width) {
        double baseWidth = 1000;
        double factor = width / baseWidth;
        fontSizeFactor.set(factor);
    }

    private VBox createCard(String title, String from, String to, Color color) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        card.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Label titleLabel = new Label(title);
        titleLabel.setFont(new Font("Arial", 14 * fontSizeFactor.get()));
        titleLabel.setTextFill(Color.GRAY);

        Label fromLabel = new Label(from);
        fromLabel.setFont(new Font("Arial", 20 * fontSizeFactor.get()));
        fromLabel.setTextFill(color);

        Label arrowLabel = new Label("→");
        arrowLabel.setFont(new Font("Arial", 14 * fontSizeFactor.get()));
        arrowLabel.setTextFill(Color.GRAY);

        Label toLabel = new Label(to);
        toLabel.setFont(new Font("Arial", 20 * fontSizeFactor.get()));
        toLabel.setTextFill(color);

        HBox content = new HBox(5, fromLabel, arrowLabel, toLabel);
        card.getChildren().addAll(titleLabel, content);
        return card;
    }

    private LineChart<Number, Number> createLineChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Date");
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
        series.getData().add(new XYChart.Data<>(13, 3100));
        series.getData().add(new XYChart.Data<>(14, 3150));
        series.getData().add(new XYChart.Data<>(15, 3200));
        series.getData().add(new XYChart.Data<>(16, 3250));
        series.getData().add(new XYChart.Data<>(17, 3300));
        series.getData().add(new XYChart.Data<>(18, 3350));
        series.getData().add(new XYChart.Data<>(19, 3400));
        series.getData().add(new XYChart.Data<>(20, 3450));
        series.getData().add(new XYChart.Data<>(21, 3500));
        series.getData().add(new XYChart.Data<>(22, 3550));
        series.getData().add(new XYChart.Data<>(23, 3600));
        series.getData().add(new XYChart.Data<>(24, 3650));
        series.getData().add(new XYChart.Data<>(25, 3700));
        series.getData().add(new XYChart.Data<>(26, 3750));
        series.getData().add(new XYChart.Data<>(27, 3800));
        series.getData().add(new XYChart.Data<>(28, 3850));
        series.getData().add(new XYChart.Data<>(29, 3900));
        series.getData().add(new XYChart.Data<>(30, 4000));

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
        VBox rankList = new VBox(10);
        rankList.setPadding(new Insets(15));
        rankList.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        rankList.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Label titleLabel = new Label("Rank");
        titleLabel.setFont(new Font("Arial", 14 * fontSizeFactor.get()));
        titleLabel.setTextFill(Color.GRAY);
        rankList.getChildren().add(titleLabel);

        // 添加排名项
        String[] types = {"Catering", "Entertainment", "Transaction", "Daily expenses", "Social contact"};
        String[] fromAmounts = {"¥6,750", "¥5,000", "¥3,750", "¥2,300", "¥2,500"};
        String[] toAmounts = {"¥7,500", "¥7,500", "¥3,750", "¥3,750", "¥1,250"};
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PURPLE};

        for (int i = 0; i < types.length; i++) {
            HBox rankItem = new HBox(10);
            Circle icon = new Circle(10);
            icon.setFill(colors[i]);

            Label typeLabel = new Label("type: " + types[i]);
            typeLabel.setFont(new Font("Arial", 12 * fontSizeFactor.get()));
            typeLabel.setTextFill(Color.GRAY);

            Label fromLabel = new Label(fromAmounts[i]);
            fromLabel.setFont(new Font("Arial", 12 * fontSizeFactor.get()));
            fromLabel.setTextFill(colors[i]);

            Label arrowLabel = new Label("→");
            arrowLabel.setFont(new Font("Arial", 12 * fontSizeFactor.get()));
            arrowLabel.setTextFill(Color.GRAY);

            Label toLabel = new Label(toAmounts[i]);
            toLabel.setFont(new Font("Arial", 12 * fontSizeFactor.get()));
            toLabel.setTextFill(colors[i]);

            HBox amountBox = new HBox(5, fromLabel, arrowLabel, toLabel);
            HBox.setHgrow(amountBox, Priority.ALWAYS);

            rankItem.getChildren().addAll(icon, typeLabel, amountBox);
            rankList.getChildren().add(rankItem);
        }

        return rankList;
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