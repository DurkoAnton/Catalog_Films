import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartWindow.fxml"));
        Scene scene = new Scene(loader.load());
        StartWindowController st = (StartWindowController) loader.getController();
        stage.setScene(scene);
        stage.show();
        st.setStage(stage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
