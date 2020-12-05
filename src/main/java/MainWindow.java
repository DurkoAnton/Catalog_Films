import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("StartWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        StartWindowController start = new StartWindowController(stage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
