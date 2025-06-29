package pl.umcs.oop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Server server = new Server(5000);

    /*
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            server.shutdown();
        }));
*/

        server.start();

        ChatClientGUI GUI = new ChatClientGUI(1920, 1080);

        StackPane root = new StackPane();
        root.getChildren().add(GUI);

        Scene scene = new Scene(root, 500, 500);

        stage.setScene(scene);
        stage.setTitle("Chat Client");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }



}