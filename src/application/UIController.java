package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UIController {

	private Render render;

	private XYChart.Series<Number, Number> chartData;
	
	@FXML
	private ScatterChart<Number, Number> chart;
	
	@FXML
	private TextArea commandHistory;
	
	@FXML
	private TextField commandInput;

	public UIController() {
		chartData = new XYChart.Series<Number, Number>();
		render = new Render();
	}

	@FXML
	private void commandSubmit() {
		this.chartData.getData().add(new XYChart.Data<>(10, 10));
		 
		commandHistory.setText(commandHistory.getText() + "\n" + commandInput.getText());

		XYChart.Series<Number, Number> result = render.getShape(commandInput.getText());

		this.chart.getData().add(result);
	}
	
	public void drawDot(int x, int y) {
		this.chartData.getData().add(new XYChart.Data<>(x, y));

		this.chart.setData(FXCollections.observableArrayList(chartData));
	}
}
