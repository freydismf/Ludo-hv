package is.vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/******************************************************************************
 *  Nafn    :
 *  T-póstur:
 *  Lýsing  : Beinagreind að Application klasa
 *
 *
 *****************************************************************************/
public class LudoApp extends javafx.application.Application {
    /**
     * Ræsir appið
     *
     * @param stage glugginn
     * @throws Exception undnantekning sem verður ef villla
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Smíða loader fyrir notendaviðmótið sem er geymt í skránni ludo-view.fxml
        // Gætið þess að .fxml skráin sé undir resources/is/vidmot
        FXMLLoader fxmlLoader = new FXMLLoader(LudoApp.class.getResource("ludo-view.fxml"));
        // Smíða senuna með notendaviðmótinu sem er núna lesið inn af resources
        Scene scene = new Scene(fxmlLoader.load());
        // Setja titilinn á gluggann
        stage.setTitle("Lúdó");
        // Tengja senuna við glugggann
        stage.setScene(scene);
        stage.setResizable(false);
        // Birta gluggann
        stage.show();
    }

    /**
     * Aðalforritið sem ræsir appið
     *
     * @param args ónotað
     */
    public static void main(String[] args) {
        // Ræsa forritið
        launch();
    }
}
