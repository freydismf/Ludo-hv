package is.vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/******************************************************************************
 *  Nafn    : Freydís María og Hrefna
 *  Lýsing  : Application klasi
 *****************************************************************************/
public class LudoApp extends javafx.application.Application {
    /**
     * Ræsir appið
     * @param stage glugginn
     * @throws Exception undnantekning sem verður ef villla
     */
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Pane());
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.LOGIN, true);
        stage.setTitle("Lúdó");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Aðalforritið sem ræsir appið
     * @param args ónotað
     */
    public static void main(String[] args) {
        // Ræsa forritið
        launch();
    }
}
