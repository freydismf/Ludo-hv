package is.vidmot;

import is.vinnsla.Leikstillingar;
import is.vinnsla.Reitur;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.beans.binding.Bindings;
import is.vinnsla.Ludo;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;


/******************************************************************************
 *  Nafn    : Freydís María og Hrefna Sóley
 *  Lýsing  : Controller eða stýring fyrir notendaviðmótið
 *****************************************************************************/
public class LudoController implements GognInterface<Leikstillingar> {
    //fastar
    public static final String LEIK_LOKID= "Leik lokið";
    public static final String LEIKUR_I_GANGI= "Leikur í gangi";
    public static final String SIGURVEGARI_ER  = "Sigurvegari er ";
    public static final String TENINGUR = "Ýttu á tening til þess að kasta";
    public static final String NYR_LEIKUR = ". Ýttu á \"Nýr leikur\" til þess að hefja nýjann leik";
    public static final String A_LEIK = "Á leik";
    public static final String BIDUR = "Bíður";

    private static final int DICE_FLICKER_FRAMES = 14;
    private static final double DICE_FLICKER_MS = 75;
    private static final double STEP_PAUSE_MS = 130;
    private static final double COMPUTER_DELAY_MS = 800;
    private static final String[] DICE_FACES = {"one", "two",  "three", "four", "five", "six"};

    //Tilviksbreytur
    @FXML private GridPane fxBord;
    @FXML private Label fxLeikmadur;
    @FXML private Button fxNyrLeikur;
    @FXML private Label fxSkilabod;
    @FXML private Button fxTeningur;
    @FXML private ToggleGroup theme;
    @FXML private RadioMenuItem fxLjost;
    @FXML private RadioMenuItem fxDokkt;
    @FXML private RadioMenuItem fxPastel;
    @FXML private Label fxStadaTolvu;
    @FXML private Label fxNafnNotanda;
    @FXML private Label fxLiturNotanda;
    @FXML private Label fxStadaNotanda;

    //vinnslan
    //private final Ludo ludo = new Ludo();
    private Leikstillingar stillingar;
    private Ludo ludo;
    private final HashMap<Reitur, StackPane> vidmotLeid = new HashMap<>();
    private boolean animationRunning = false;
    private final Random random = new Random();



    /**
     * Frumstilling á viðmótshlutum og byrjar leikinn
     */
    @Override
    public void setGogn(Leikstillingar stillingar) {
        this.stillingar = stillingar;
        ludo = new Ludo();
        ludo.setNafnLeikmanns(stillingar.getNafn());
        try {
            geraLeid();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        geracCute();
        stillaTening();
        bindaLeikmenn();
        bindaHnappa();
        bindaSkilabod();
        bindaStoduLeikmanna();
        toggleTheme();
        setjaNafnOgLit();

    }

    /**
     * Handler fyrir "Nýr leikur" takkann
     * @param event e
     */
    @FXML
    public void onNyrLeikur(ActionEvent event) {
        animationRunning = false;
        ludo.nyrLeikur();
        fxTeningur.setDisable(false);
    }

    /**
     * Handler fyrir teninginn
     * @param event e
     */
    @FXML
    public void onTeinigur(ActionEvent event) {
        if (animationRunning || ludo.erLokid().get()) {
            return;
        }
        animationRunning = true;
        fxTeningur.disableProperty().unbind();
        fxTeningur.setDisable(true);

        ludo.kastaTening();
        int steps = ludo.getTeningsTala();

        startDiceAnimation(steps);
    }

    /**
     * Spilar hreyfimynd: flettir í gegn um allar tölur tenings að
     * handahófi áður en lent er á loka tölu
     * @param finalValue raungildi tenings kastað
     */
    private void startDiceAnimation(int finalValue) {
        fxTeningur.getStyleClass().removeAll(DICE_FACES);

        Timeline flicker = new Timeline();

        for (int i = 0; i < DICE_FLICKER_FRAMES; i++) {
            boolean isLastFrame = (i == DICE_FLICKER_FRAMES - 1);
            int face = isLastFrame ? (finalValue - 1) : random.nextInt(6);
            KeyFrame kf = new KeyFrame(
                    Duration.millis(DICE_FLICKER_MS * (i + 1)),
                    e -> {
                        fxTeningur.getStyleClass().removeAll(DICE_FACES);
                        fxTeningur.getStyleClass().add(DICE_FACES[face]);
                    }
            );
            flicker.getKeyFrames().add(kf);
        }
        flicker.setOnFinished(e -> startPieceAnimation(finalValue));
        flicker.play();
    }

    /**
     * Hreyfir peðin skref fyrir skref um reiti
     * @param steps fjöldi skrefa eftir teningsgildi
     */
    private void startPieceAnimation(int steps) {
        SequentialTransition seq = new SequentialTransition();
        for (int i = 0; i < steps; i++) {
            PauseTransition pause = new PauseTransition(Duration.millis(STEP_PAUSE_MS));
            pause.setOnFinished(e -> ludo.faeraLeikmann());
            seq.getChildren().add(pause);
        }
        seq.setOnFinished(e -> onAnimationDone());
        seq.play();
    }

    /**
     * Skoðar hvort talva á að gera, kemur pása milli leikmanna
     * Hreyfimynd lýkur annars
     */
    private void onAnimationDone() {
        boolean lokid = ludo.leikaLeik();
        animationRunning = false;

        if (lokid) {
            return;
        }
        if (ludo.erNaestiTolva()) {
            PauseTransition computerDelay = new PauseTransition(Duration.millis(COMPUTER_DELAY_MS));
            computerDelay.setOnFinished(e -> doComputerTurn());
            computerDelay.play();
        }
        else {
            fxTeningur.setDisable(false);
        }
    }

    /**
     * Sjálfkrafa ferð tölvu
     * Kallar og keyrir sömu hreyfimyndir og leikmaður
     */
    private void doComputerTurn() {
        if (ludo.erLokid().get()) {
            return;
        }
        animationRunning = true;
        ludo.kastaTening();
        int steps = ludo.getTeningsTala();
        startDiceAnimation(steps);
    }

    /**
     * bindur teningamyndir við teninginn
     */
    private void stillaTening() {
        fxTeningur.getStyleClass().add(DICE_FACES[5]);
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
    private void geraLeid() throws IOException {
        ArrayList<Reitur> leid = ludo.getLeid();
        for (Reitur r : leid) {
            StackPane s = nySella();
            fxBord.add(s, r.getDalkur(), r.getRod());
            vidmotLeid.put(r, s);
        }

        ArrayList<Reitur> leid2 = ludo.getLeid2();
        for (Reitur r : leid2) {
            if (!vidmotLeid.containsKey(r)) {
                StackPane s = nySella();
                fxBord.add(s, r.getDalkur(), r.getRod());
                vidmotLeid.put(r, s);
            }
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
        String litur = stillingar.getLitur();
        int i = veljaPed(litur);
        String[] leikmadurStill = {"blattPed", "rauttPed", "graentPed", "gultPed", "svartPed"};
        vidmotLeid.get(ludo.getLeid().getFirst()).getStyleClass().add(leikmadurStill[i]);
        vidmotLeid.get(ludo.getLeid2().getFirst()).getStyleClass().add(leikmadurStill[4]);
        ludo.getLeikmadur(0).getReiturProperty().addListener((obs, gamaltGildi, nyttGildi) -> {
            Reitur gamliReitur = ludo.getLeid().get(gamaltGildi.intValue() - 1);
            Reitur nyiReitur = ludo.getLeid().get(nyttGildi.intValue() - 1);
            vidmotLeid.get(gamliReitur)
                    .getStyleClass()
                    .remove(leikmadurStill[i]);
            vidmotLeid.get(nyiReitur)
                    .getStyleClass()
                    .add(leikmadurStill[i]);
        });

        ludo.getLeikmadur(1).getReiturProperty().addListener((obs, gamaltGildi, nyttGildi) -> {
            Reitur gamliReitur = ludo.getLeid2().get(gamaltGildi.intValue() - 1);
            Reitur nyiReitur = ludo.getLeid2().get(nyttGildi.intValue() - 1);
            vidmotLeid.get(gamliReitur)
                    .getStyleClass()
                    .remove(leikmadurStill[4]);
            vidmotLeid.get(nyiReitur)
                    .getStyleClass()
                    .add(leikmadurStill[4]);
        });
    }

    /**
     * Setur skilaboð í label
     */
    private void bindaSkilabod() {
        fxLeikmadur.textProperty().bind(
                Bindings.when(ludo.erLokid())
                        .then(LEIK_LOKID)
                        .otherwise(LEIKUR_I_GANGI));
        fxSkilabod.textProperty().bind(
                Bindings.when(ludo.erLokid())
                        .then(Bindings.concat(
                                SIGURVEGARI_ER, ludo.sigurvegariProperty(),NYR_LEIKUR))
                        .otherwise(TENINGUR));
    }

    /**
     * Bindur skilaboð um hvort leikmaður eigi að gera eða bíða
     */
    private void bindaStoduLeikmanna(){
        BooleanBinding egALeik = ludo.naestiLeikmadurProperty().isEqualTo(stillingar.getNafn());

        fxStadaNotanda.textProperty().bind(
                Bindings.when(egALeik).then(A_LEIK).otherwise(BIDUR)
        );
        fxStadaTolvu.textProperty().bind(
                Bindings.when(egALeik.not()).then(A_LEIK).otherwise(BIDUR)
        );
    }

    private void setjaNafnOgLit(){
        fxNafnNotanda.setText(stillingar.getNafn());
        fxLiturNotanda.setText(stillingar.getLitur());
    }

    /**
     * Setur liti á reitina
     */
    private void geracCute() {
        String[] litir = {"start", "end", "normal", "goal"};
        Reitur byrjun = ludo.getLeid().getFirst();
        Reitur byrjun2 = ludo.getLeid2().getFirst();
        vidmotLeid.get(byrjun).getStyleClass().add(litir[0]);
        vidmotLeid.get(byrjun2).getStyleClass().add(litir[0]);
        for (int i = 1; i < 56; i++) {
            Reitur reitur = ludo.getLeid().get(i);
            vidmotLeid.get(reitur).getStyleClass().add(litir[2]);
        }
        for (int i = 1; i < 28; i++) {
            Reitur reitur = ludo.getLeid2().get(i);
            vidmotLeid.get(reitur).getStyleClass().add(litir[2]);
        }

        for (int i = 55; i < ludo.getLeid().size() - 1; i++) {
            Reitur reitur = ludo.getLeid().get(i);
            vidmotLeid.get(reitur).getStyleClass().add(litir[1]);
        }
        for (int i = 55; i < ludo.getLeid2().size() - 1; i++) {
            Reitur reitur = ludo.getLeid2().get(i);
            vidmotLeid.get(reitur).getStyleClass().add(litir[1]);
        }
        Reitur mark = ludo.getLeid().getLast();
        vidmotLeid.get(mark).getStyleClass().add(litir[3]);
        vidmotLeid.get(byrjun2).getStyleClass().remove(litir[2]);
        vidmotLeid.get(byrjun2).getStyleClass().add(litir[0]);
    }

    /**
     * Hjálparaðferð fyrir val á peði
     * @param litur peðs
     * @return tala
     */
    private int veljaPed(String litur) {
        return switch (litur) {
            case "Blár" -> 0;
            case "Rauður" -> 1;
            case "Grænn" -> 2;
            case "Gulur" -> 3;
            default -> 0;
        };
    }

    /**
     * Litstener fyrir breytingu á þema
     */
    private void toggleTheme() {
        theme.selectedToggleProperty().addListener((obs, gamalt, nytt) -> setjaTheme());
        fxBord.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                setjaTheme();
            }
        });
    }

    /**
     * Hjálparfall sem breytir þema
     */
    private void setjaTheme(){
        Scene scene = fxBord.getScene();
        if (scene == null){
            return;
        }
        scene.getStylesheets().clear();

        if(fxLjost.isSelected()){
            scene.getStylesheets().add(Objects.requireNonNull(getClass()
                    .getResource("CSS/Light.css")).toExternalForm());
        }
        else if (fxDokkt.isSelected()){
            scene.getStylesheets().add(Objects.requireNonNull(getClass()
                    .getResource("CSS/Dark.css")).toExternalForm());
        }
        else if (fxPastel.isSelected()){
            scene.getStylesheets().add(Objects.requireNonNull(getClass()
                    .getResource("CSS/Pastel.css")).toExternalForm());
        }

    }

}

