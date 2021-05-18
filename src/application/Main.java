package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("UI.fxml"));
			
			Scene scene = new Scene(root);
			
	        XYChart.Series<Number, Number>series = new XYChart.Series<>();
	        
	        series.getData (). add (new XYChart.Data<>(3, 3));
	        series.getData (). add (new XYChart.Data<>(2.75, 3));
	        series.getData (). add (new XYChart.Data<>(2.5, 3));
	        series.getData (). add (new XYChart.Data<>(2.25, 3));
	        series.getData (). add (new XYChart.Data<>(2, 3));
	        series.getData (). add (new XYChart.Data<>(1.75, 3));
	        series.getData (). add (new XYChart.Data<>(1.5, 3));
	        series.getData (). add (new XYChart.Data<>(1.25, 3));
	        series.getData (). add (new XYChart.Data<>(1, 3));

	        series.getData (). add (new XYChart.Data<>(1, 2.75));
	        series.getData (). add (new XYChart.Data<>(1, 2.5));
	        series.getData (). add (new XYChart.Data<>(1, 2.25));
	        series.getData (). add (new XYChart.Data<>(1, 2));
	        series.getData (). add (new XYChart.Data<>(1, 1.75));
	        series.getData (). add (new XYChart.Data<>(1, 1.5));
	        series.getData (). add (new XYChart.Data<>(1, 1.25));

	        series.getData (). add (new XYChart.Data<>(1, 1));
	        series.getData (). add (new XYChart.Data<>(1.25, 1));
	        series.getData (). add (new XYChart.Data<>(1.5, 1));
	        series.getData (). add (new XYChart.Data<>(1.75, 1));
	        series.getData (). add (new XYChart.Data<>(2, 1));
	        series.getData (). add (new XYChart.Data<>(2.25, 1));
	        series.getData (). add (new XYChart.Data<>(2.5, 1));
	        series.getData (). add (new XYChart.Data<>(2.75, 1));
	        series.getData (). add (new XYChart.Data<>(3, 1));

	        series.getData (). add (new XYChart.Data<>(3, 1.25));
	        series.getData (). add (new XYChart.Data<>(3, 1.5));
	        series.getData (). add (new XYChart.Data<>(3, 1.75));
	        series.getData (). add (new XYChart.Data<>(3, 2));
	        series.getData (). add (new XYChart.Data<>(3, 2.25));
	        series.getData (). add (new XYChart.Data<>(3, 2.5));
	        series.getData (). add (new XYChart.Data<>(3, 2.75));
	        series.getData (). add (new XYChart.Data<>(3, 3));

	        series.getData (). add (new XYChart.Data<>(-5, -5));

	        primaryStage.setScene(scene);
	        primaryStage.show();

	        // Node plotArea = scatter.lookup (".chart-plot-background");
	        // double centerXOnPlotArea = 5;
	        // double centerYOnPlotArea = 2;
	        // double radiusXOnPlotArea = 2;
	        // double radiusYOnPlotArea = 6;
	        // double cx1 = axisX.getDisplayPosition (centerXOnPlotArea);
	        // double cy1 = axisY.getDisplayPosition (centerYOnPlotArea);
	        // double cx2 = axisX.getDisplayPosition (centerXOnPlotArea + radiusXOnPlotArea);
	        // double cy2 = axisY.getDisplayPosition (centerYOnPlotArea + radiusYOnPlotArea);
	        // Bounds boundsOnPlotArea = new BoundingBox (
	        //   cx1, cy1, 0, Math.abs (cx2-cx1), Math.abs (cy2-cy1), 0);
	        // Bounds boundsOnPane = pane.sceneToLocal (plotArea.localToScene (boundsOnPlotArea));
	        // Ellipse ellipse = new Ellipse (
	        //   boundsOnPane.getMinX(), boundsOnPane.getMinY (),
	        //   boundsOnPane.getWidth (), boundsOnPane.getHeight ());
	        // ellipse.setFill (Color.BLUE);
	        // // I tried to make it semi-transparent because the ellipse makes the chart axes and auxiliary lines invisible
	        // ellipse.setOpacity (0.5);
	        // pane.getChildren ().add(ellipse);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		UIController controller = new UIController();
		
		launch(args);
		
	}
}
