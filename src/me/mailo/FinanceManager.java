package me.mailo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FinanceManager extends Application {
    private static final List<String> months = List.of("Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre");
    private static final BorderPane root = new BorderPane();
    private static final GridPane input = new GridPane(10, 10);
    private static final Label month = new Label("Month");
    private static final TextField monthField = new TextField();
    private static final Label amount = new Label("Amount");
    private static final TextField amountField = new TextField();
    private static final RadioButton debt = new RadioButton("Debt");
    private static final RadioButton credit = new RadioButton("Credit");
    private static final Button confirm = new Button("Confirm");
    private static final CategoryAxis xAxis = new CategoryAxis();
    private static final NumberAxis yAxis = new NumberAxis();
    private static final BarChart<String, Number> graph = new BarChart<>(xAxis, yAxis);
    private static final XYChart.Series debtSeries = new XYChart.Series();
    private static final XYChart.Series creditSeries = new XYChart.Series();

    @Override
    public void start(Stage stage) throws Exception {
        input.addColumn(0, month, amount);
        input.addColumn(1, monthField, amountField);
        input.addRow(2, debt, credit);
        input.addRow(3, confirm);
        input.setAlignment(Pos.CENTER);
        input.setMaxWidth(stage.getMaxWidth());
        input.setMinWidth(stage.getMinWidth());
        input.setPrefWidth(stage.getWidth());

        debtSeries.setName("Addebiti");
        creditSeries.setName("Crediti");
        months.forEach((month) -> {
            debtSeries.getData().add(new XYChart.Data(month, 0));
            creditSeries.getData().add(new XYChart.Data(month, 0));
        });


        confirm.setOnAction(event -> {
            if (debt.isSelected()) {
                debtSeries.getData().stream().toList().forEach((element) -> {
                    if (((XYChart.Data<String, Number>) element).getXValue().equals(monthField.getText())) {
                        System.out.println("[Debug] Y Before change: " + ((XYChart.Data<?, ?>) element).getYValue().toString());
                        ((XYChart.Data<String, Number>) element).setYValue(Integer.parseInt(((XYChart.Data<?, ?>) element).getYValue().toString()) + Integer.parseInt(amountField.getText()));
                        System.out.println("[Debug] Y After change: " + ((XYChart.Data<?, ?>) element).getYValue().toString());
                    }

                });
            } else if (credit.isSelected()) {
                creditSeries.getData().stream().toList().forEach((element) -> {
                    if (((XYChart.Data<String, Number>) element).getXValue().equals(monthField.getText())) {
                        System.out.println("[Debug] Y Before change: " + ((XYChart.Data<?, ?>) element).getYValue().toString());
                        ((XYChart.Data<String, Number>) element).setYValue(Integer.parseInt(((XYChart.Data<?, ?>) element).getYValue().toString()) + Integer.parseInt(amountField.getText()));
                        System.out.println("[Debug] Y After change: " + ((XYChart.Data<?, ?>) element).getYValue().toString());
                    }

                });
            }
        });

        debt.setOnAction(event -> {
            if (credit.isSelected()) {
                credit.selectedProperty().set(false);
            }
        });

        credit.setOnAction(event -> {
            if (debt.isSelected()) {
                debt.selectedProperty().set(false);
            }
        });

        root.setTop(input);
        root.setBottom(graph);
        Scene scene = new Scene(root, 1280, 720);
        graph.getData().addAll(debtSeries, creditSeries);
        stage.setScene(scene);
        stage.setTitle("Willy's Finance Manager");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
