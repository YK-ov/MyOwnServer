package pl.umcs.oop;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class ChatClientGUI extends StackPane {
    private Canvas canvas;
    private GraphicsContext gc;
    private TextArea chatArea;
    private TextField inputField;
    private Button sendButton;
    private VBox chatUI;

    public ChatClientGUI(double width, double height) {
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();

        chatArea = new TextArea();
        chatArea.setEditable(false);

        inputField = new TextField();

        sendButton = new Button("Send");

        sendButton.setOnAction(e -> sendMessage());
        inputField.setOnAction(e -> sendMessage()); //for enter

        chatUI = new VBox(10, chatArea, inputField, sendButton);
        this.getChildren().add(chatUI);
    }

    private void drawBackGround() {
        gc.setFill(Paint.valueOf("white"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            chatArea.appendText("Me: " + message + "\n");
            inputField.clear();
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public VBox getChatUI() {
        return chatUI;
    }
}