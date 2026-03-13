package is.vidmot;

import is.vinnsla.Reitur;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.beans.binding.Bindings;
import is.vinnsla.Ludo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/******************************************************************************
 *  Nafn    : Freydís María Friðriksdóttir
 *  T-póstur: fmf6@hi.is
 *  Lýsing  : Controller eða stýring fyrir notendaviðmótið
 *****************************************************************************/
public class LudoController {
    //fastar
    public static final String LEIK_LOKID_LEIKMADUR = "Leik lokið - leikmaður ";
    public static final String LEIKUR_I_GANGI_NAESTI_GERIR_ = "Leikur í gangi, næst gerir ";
    public static final String VANN = " vann ";
    public static final String TENINGUR = "Ýttu á tening til þess að kasta";
    public static final String NYR_LEIKUR = "Ýttu á \"Nýr leikur\" til þess að hefja nýjann leik";

    //Tilviksbreytur
    @FXML
    private GridPane fxBord;
    @FXML
    private Label fxLeikmadur;
    @FXML
    private Button fxNyrLeikur;
    @FXML
    private Label fxSkilabod;
    @FXML
    private Button fxTeningur;
    //vinnslan
    private final Ludo ludo = new Ludo();

    private final HashMap<Reitur, StackPane> vidmotLeid = new HashMap<>();

    /**
     * Handler fyrir "Nýr leikur" takkann
     * @param event e
     */
    @FXML
    void onNyrLeikur(ActionEvent event) {
        ludo.nyrLeikur();
    }
    /**
     * Handler fyrir teninginn
     * @param event e
     */
    @FXML
    void onTeinigur(ActionEvent event) {
        ludo.leikaLeik();
    }
   /**
    * Frumstilling á viðmótshlutum og byrjar leikinn
    */
    public void initialize() throws IOException {
      // búa til leiðina á lúdó borðinu (6)
        geraLeid();
        geracCute();
      // binda teningamyndirnar við teninginn (7)
        stillaTening();
      // bindur reitina á borðinu við reitinn sem leikmaður er á (8)
        bindaLeikmenn();
      // binda hnappana við ástandið á leiknum (9)
        bindaHnappa();
      // bindur skilaboðin um hver á að gera og hver er sigurvegari við gögn úr vinnslunni (10)
        bindaSkilabod();
    }

    /**
     * bindur teningamyndir við teninginn
     */
    private void stillaTening(){
        String[] teningaMyndir = {"one", "two", "three", "four", "five", "six"};
        fxTeningur.getStyleClass().add(teningaMyndir[5]);
        ludo.getTeningur().tala().addListener((observableValue, gamaltGildi, nyttGildi) -> {
            fxTeningur.getStyleClass().remove(teningaMyndir[gamaltGildi.intValue() -1]);
            fxTeningur.getStyleClass().add(teningaMyndir[nyttGildi.intValue() -1]);
        });
    }

    /**
     * Býr til stackpane
     * @return stackpane
     * @throws IOException e
     */
    private StackPane nySella() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("reitur-view.fxml"));
        return loader.load();
    }

    /**
     * Setur leið á borðið
     * @throws IOException e
     */
    private void geraLeid() throws IOException{
        ArrayList<Reitur> leid = ludo.getLeid();
        for(Reitur r: leid){
            StackPane s = nySella();
            fxBord.add(s ,r.getDalkur(), r.getRod());
            vidmotLeid.put(r,s);
        }
    }

    /**
     * bindur hnappa við stöðu leiks
     */
    private void bindaHnappa() {
        fxTeningur.disableProperty().bind(ludo.erLokid());
        fxNyrLeikur.disableProperty().bind(ludo.erIGangi());
    }

    /**
     * Setur myndir af leikmönnum  á réttan reit
     */
    private void bindaLeikmenn() {
        String[] leikmadurStill = {"tumi", "nero"};
        vidmotLeid.get(ludo.getLeid().getFirst()).getStyleClass().add(leikmadurStill[0]);
        vidmotLeid.get(ludo.getLeid().getFirst()).getStyleClass().add(leikmadurStill[1]);
        ludo.getLeikmadur(0).getReiturProperty().addListener((obs, gamaltGildi, nyttGildi) -> {

            Reitur gamliReitur = ludo.getLeid().get(gamaltGildi.intValue()-1);
            Reitur nyiReitur = ludo.getLeid().get(nyttGildi.intValue()-1);

            vidmotLeid.get(gamliReitur)
                    .getStyleClass()
                    .remove(leikmadurStill[0]);

            vidmotLeid.get(nyiReitur)
                    .getStyleClass()
                    .add(leikmadurStill[0]);
        });

        ludo.getLeikmadur(1).getReiturProperty().addListener((obs, gamaltGildi, nyttGildi) -> {

            Reitur gamliReitur = ludo.getLeid().get(gamaltGildi.intValue()-1);
            Reitur nyiReitur = ludo.getLeid().get(nyttGildi.intValue()-1);

            vidmotLeid.get(gamliReitur)
                    .getStyleClass()
                    .remove(leikmadurStill[1]);

            vidmotLeid.get(nyiReitur)
                    .getStyleClass()
                    .add(leikmadurStill[1]);
        });
    }

    /**
     * Setur skilaboð í label
     */
    private void bindaSkilabod(){
        fxLeikmadur.textProperty().bind(
                Bindings.when(ludo.erLokid())
                        .then(Bindings.concat
                                (LEIK_LOKID_LEIKMADUR, ludo.sigurvegariProperty(), VANN))
                        .otherwise(Bindings.concat
                                (LEIKUR_I_GANGI_NAESTI_GERIR_, ludo.naestiLeikmadurProperty())));
        fxSkilabod.textProperty().bind(
                Bindings.when(ludo.erLokid())
                        .then(NYR_LEIKUR)
                        .otherwise(TENINGUR));
    }

    /**
     * Setur liti á reitina
     */
    private void geracCute(){
        String[] litir = {"start", "end" ,"normal"};
        Reitur byrjun = ludo.getLeid().getFirst();
        vidmotLeid.get(byrjun).getStyleClass().add(litir[0]);
        for(int i = 1; i < 23; i++){
            Reitur reitur = ludo.getLeid().get(i);
            vidmotLeid.get(reitur).getStyleClass().add(litir[2]);
        }
        Reitur mark = ludo.getLeid().getLast();
        vidmotLeid.get(mark).getStyleClass().add(litir[1]);
    }

}

