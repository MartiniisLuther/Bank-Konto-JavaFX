package com.bankapp.controllers;

import com.bankapp.models.Transaction;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class DashboardController {

	@FXML
	private Label userLabel;
	@FXML
	private Label balanceLabel;
	@FXML
	private Label ibanLabel;
	@FXML
	private Label holderLabel;
	@FXML
	private ImageView profileImage;

	// list style for the transactions
	@FXML
	private ListView<Transaction> transactionsList;

	@FXML
	private Button addMoneyButton;
	@FXML
	private Button transferButton;

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
		transactionsList.getItems().addAll(
				new Transaction("Ganymed Berlin GmbH", "2025-10-09", "885,69  €"),
				new Transaction("KAUFLAND", "2025-10-06", "-19,69 €"),
				new Transaction("Payment to Amazon", "2025-10-4", "-50.00 €"),
				new Transaction("ALDI", "2025-09-25", "-22.81 €"));

		// rendering
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
	}

	// toggle notification red dot
	public void setNotifications(boolean hasUnread) {
		notifDot.setVisible(hasUnread);
	}

	@FXML
	private void handleTransfer() {
		System.out.println("Transfer button clicked!");
	}

	@FXML
	private void handleAddMoney() {
		System.out.println("Add Money button clicked!");
	}
}