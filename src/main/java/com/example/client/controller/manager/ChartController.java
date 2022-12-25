package com.example.client.controller.manager;

import com.example.client.DTO.MessageResponse;
import com.example.client.helper.ParserMessageFactory;
import com.example.client.model.Chart;
import com.example.client.type.RequestType;
import com.example.client.util.SocketService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ChartController implements Initializable {


    @FXML
    private AreaChart<?, ?> chart;

    @FXML
    private NumberAxis lowerBound;

    @FXML
    private NumberAxis upperBound;


    SocketService socketService = new SocketService();

    void initChart() {
        try {
            LocalDate date = LocalDate.now(); // получаем текущую дату
            int dayOfMonth = date.getDayOfMonth();
            upperBound.setUpperBound(dayOfMonth);
            XYChart.Series series = new XYChart.Series();
            Calendar cal = Calendar.getInstance();
            String month = new SimpleDateFormat("MMMM", new Locale("ru")).format(cal.getTime());
            series.setName("Статистика на " + month + " " + dayOfMonth);
            MessageResponse messageResponse = socketService.doGet(RequestType.ALL_ORDER_CHART);
            List<Chart> list = new ParserMessageFactory<Chart>().parsePayload(Chart.class, messageResponse);
            for (var i = 1; i <= upperBound.getUpperBound(); i++) {
                boolean flag = false;
                for (int j = 0; j < list.size(); j++) {
                    if (i == list.get(j).getDate().toLocalDate().getDayOfMonth()) {
                        flag = true;
                        series.getData().add(new XYChart.Data(i, list.get(j).getCount()));
                    }
                }
                if (!flag) {
                    series.getData().add(new XYChart.Data(i, 0));
                }
            }

            chart.getData().addAll(series);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void onLoadFile() throws URISyntaxException, IOException {
        File file = new File("src/main/resources/com/example/client/file/file.txt");

        if (!file.exists()) {
            file.createNewFile();
        }
        if (file.exists()) {
            try {
                System.out.println("exist");
                PrintWriter pw = new PrintWriter(file);
                var response = socketService.doGet(RequestType.ALL_ORDER_CHART);
                var list = new ParserMessageFactory<com.example.client.model.Chart>().parsePayload(Chart.class, response);
                System.out.println(list);
                for (int i = 0; i < list.size(); i++) {
                    String str = list.get(i).toString();
                    pw.println(str);
                }
                pw.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Файл");
                alert.setContentText("Вы успешно выгрузили результат в XML файл!");
                alert.showAndWait();
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Файл");
                alert.setContentText("Вы не успешно загрузили файл!");
                alert.showAndWait();
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initChart();
    }
}
