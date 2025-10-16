package com.bankapp.controllers;

import com.bankapp.models.Transaction;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class DashboardController {

	@FXML
	private Label userLabel, balanceLabel, ibanLabel, holderLabel; // homepage labels
	@FXML
	private AnchorPane transactionDetailsPane;
	@FXML
	private BorderPane dashBoardPane;

	@FXML
	private Label transactionMerchant, transactionRef, detailsAmount;
	@FXML
	private Label postingDate, valueDate, ibanLabel1, bicLabel, categoryLabel, statusLabel;

	@FXML
	private boolean detailsVisible = false;
	@FXML
	private ImageView profileImage;

	// list style for the transactions
	@FXML
	private ListView<Transaction> transactionsList;

	@FXML
	private Button closeButton;

	@FXML
	private Region notifDot;

	@FXML
	private void initialize() {
		// Initialize defaults
		userLabel.setText("Jion Doe L.");
		balanceLabel.setText("€ 0.01");
		ibanLabel.setText("IBAN: DE12 3456 7890 1234 5678 90");
		holderLabel.setText("Jion Doe L.");

		// get profile image (e in resources/img/)
		try {
			Image img = new Image(getClass().getResourceAsStream("/img/trex.jpg"));
			profileImage.setImage(img);

			// Clip to circle - when image bounds are availbale
			profileImage.boundsInLocalProperty().addListener((obs, oldB, newB) -> {
				double radius = Math.min(newB.getWidth(), newB.getHeight() / 2.0);
				Circle clip = new Circle(newB.getWidth() / 2.0, newB.getHeight() / 2.0, radius);
				profileImage.setClip(clip);
			});

		} catch (Exception e) {
			System.out.println("Profile image not found.");
		}

		// Sample transactions
		transactionsList.getItems().addAll(new Transaction("Ganymed Berlin GmbH", "2025-10-09", "885,69  €"),
				new Transaction("KAUFLAND", "2025-10-06", "-19,69 €"),
				new Transaction("Payment to Amazon", "2025-10-4", "-50.00 €"),
				new Transaction("ALDI", "2025-09-25", "-22.81 €"));

		// custom cell layout
		transactionsList.setCellFactory(list -> new ListCell<Transaction>() {
			@Override
			protected void updateItem(Transaction tx, boolean empty) {
				super.updateItem(tx, empty);
				if (empty || tx == null) {
					setText(null);
					setGraphic(null);
				} else {
					VBox infoBox = new VBox(2);
					Label title = new Label(tx.getTitle());
					title.getStyleClass().add("tx-title");
					Label date = new Label(tx.getDate());
					date.getStyleClass().add("tx-date");
					infoBox.getChildren().addAll(title, date);

					Label amount = new Label(tx.getAmount());
					amount.getStyleClass().add("tx-amount");

					// Spacer - keep amount to the right
					Region spacer = new Region();
					HBox.setHgrow(spacer, Priority.ALWAYS);

					HBox container = new HBox(10, infoBox, spacer, amount);
					container.setPadding(new Insets(10));
					container.setAlignment(Pos.CENTER_LEFT);
					setGraphic(container);
				}
			}
		});

		// show transaction detail pane on Click
		transactionsList.setOnMouseClicked(e -> {
			Transaction selected = transactionsList.getSelectionModel().getSelectedItem();
			if (selected != null) {
				System.out.println("Clicked: " + selected.getTitle());
				showTransactionDetails(selected);
			}
		});

		// start hidden off-screen -animation
		transactionDetailsPane.setTranslateY(800);
		transactionDetailsPane.setVisible(false);
		transactionDetailsPane.setMouseTransparent(false);
	}

	// method to display transaction details
	public void showTransactionDetails(Transaction tr) {
		if (tr == null)
			return;

		// populate - some hard coded data, later change to dynamic
		transactionMerchant.setText(tr.getTitle());
		detailsAmount.setText(tr.getAmount());
		transactionRef.setText("Subscription Payment");
		postingDate.setText("2025-10-09");
		valueDate.setText("2025-10-10");
		ibanLabel1.setText("DE12 3456 7890 1234 5678 90");
		bicLabel.setText("BYLADEM1001");
		categoryLabel.setText("Entertainment");
		statusLabel.setText("Completed");

		// hide dashboard
		dashBoardPane.setMouseTransparent(true);
		dashBoardPane.setOpacity(0);

		// animate
		if (!detailsVisible) {
			transactionDetailsPane.setVisible(true);
			transactionDetailsPane.toFront();

			TranslateTransition slideUp = new TranslateTransition(Duration.millis(350), transactionDetailsPane);
			slideUp.setFromY(800);
			slideUp.setToY(0);
			slideUp.play();
			
			detailsVisible = true;
		}
	}

	// hide details pane
	@FXML
	private void closeDetails() {
		if (detailsVisible) {
			TranslateTransition slideDown = new TranslateTransition(Duration.millis(300), transactionDetailsPane);
			slideDown.setFromY(0);
			slideDown.setToY(800);
			slideDown.setOnFinished(e -> {
				transactionDetailsPane.setVisible(false);
				// restore dashboardpane
				dashBoardPane.setMouseTransparent(false);
				dashBoardPane.setOpacity(1.0);
			});
			slideDown.play();
			
			detailsVisible = false;
		}
	}

	// toggle notification red dot
	public void setNotifications(boolean hasUnread) {
		notifDot.setVisible(hasUnread);
	}

}