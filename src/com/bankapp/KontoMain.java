package com.bankapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class KontoMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			Load dashboard.fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
//			BorderPane root = loader.load();
			StackPane stackRootPane = loader.load();

//			Create scene with mobile-like dimensions
			Scene scene = new Scene(stackRootPane, 500, 950);

			primaryStage.setTitle("Bank Konto - Dashboard");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
