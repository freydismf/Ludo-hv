package is.vidmot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import is.vinnsla.Leikstillingar;
/******************************************************************************
 *  Nafn    : Freydís María og Hrefna Sóley
 *  Lýsing  : Controller eða stýring fyrir upphafsskjá
 *****************************************************************************/
public class LoginController {

    @FXML
    private ComboBox<String> fxLitaval;

    @FXML
    private TextField fxNafnLeikmans;

    private static final String[] litir = {"Gulur", "Rauður", "Grænn", "Blár"};
    private final int Hamark = 15;

    /**
     * Upphafstilla combobox og textfield
     */
    public void initialize(){
        ObservableList<String> litaval = FXCollections.observableArrayList(litir);
        fxLitaval.setItems(litaval);

        fxNafnLeikmans.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= Hamark) {
                return change;
            }
            return null;
        }));
    }

    /**
     * Handler fyrir Hefja leik takkann, skiptir um senu
     * @param event
     */
    @FXML
    void onHefjaLeik(ActionEvent event) {
        Leikstillingar stillingar = stilla();
        ViewSwitcher.switchTo(View.LUDO, false, stillingar);
    }

    /**
     * Hjálparaðferð sem skilar leikstillinaghlut
     * @return stillingar
     */
    private Leikstillingar stilla(){
        String nafn = fxNafnLeikmans.getText().trim();
        String litur = fxLitaval.getValue();

        if(nafn.isEmpty()){
            nafn = "Þú";
        }
        if(litur == null){
            litur = litir[3];
        }
        return new Leikstillingar(nafn, litur);
    }
}
