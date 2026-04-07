package is.vinnsla;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/******************************************************************************
 *  Nafn    : Freydís María og Hrefna Sóley
 *  Lýsing  : Vinnsluklasi fyrir lúdó
 *****************************************************************************/
public class Ludo {

    private int naesti = 0;
    private int fyrri = 0;
    private final int MARK;
    private final int MARK2;
    enum Stada{
        I_GANGI,
        LOKID
    }

    //Teningur
    private final Teningur teningur = new Teningur();
    //Tveir leikmenn
    private final Leikmadur[] leikmenn =
            new Leikmadur[]{new Leikmadur("Þú"), new Leikmadur("Tölva")};
    //Heldur utan um reiti
    private final ArrayList<Reitur> leid = new ArrayList<>();
    private final ArrayList<Reitur> leid2 = new ArrayList<>();
    //Heldur utan um stöðu leiks
    private final SimpleObjectProperty<Stada> stada = new SimpleObjectProperty<>(Stada.I_GANGI);
    //Nær í sigurvegara
    private final SimpleStringProperty sigurvegariProperty = new SimpleStringProperty("");
    //Næsti leikmaður sem á að gera
    private final SimpleStringProperty naestiLeikmadurProperty =
            new SimpleStringProperty(leikmenn[0].getNafn());


    /**
     *
     */
    public Ludo(){

        //Upphafs reitur
        for (int i = 13; i >= 8; i--) {
            leid.add(new Reitur(i, 6));
        }

        for (int j =5; j >= 0; j--) {
            leid.add(new Reitur(8, j));
        }

        for (int i = 7; i >= 6; i--) {
            leid.add(new Reitur(i, 0));
        }

        for (int j = 1; j <= 6; j++) {
            leid.add(new Reitur(6, j));
        }

        for (int i = 5; i >= 0; i--) {
            leid.add(new Reitur(i, 6));
        }

        for (int j = 7; j <= 8; j++) {
            leid.add(new Reitur(0, j));
        }

        for (int i = 1; i <= 6; i++) {
            leid.add(new Reitur(i, 8));
        }

        for (int j =  9; j <= 14; j++) {
            leid.add(new Reitur(6, j));
        }

        for (int i = 7; i <= 8; i++) {
            leid.add(new Reitur(i, 14));
        }

        for (int j = 13; j >= 8; j--) {
            leid.add(new Reitur(8, j));
        }

        for (int i = 9; i <= 14; i++) {
            leid.add(new Reitur(i, 8));
        }

        for (int j = 7; j >= 6; j--) {
            leid.add(new Reitur(14, j));
        }

        for (int i = 13; i >= 8; i--) {
            leid.add(new Reitur(i, 7));
        }

        //Loka reitur
        Reitur mark = new Reitur(7,7);
        leid.add(mark);
        MARK = leid.size();

        ArrayList<Reitur> outerLoop = new ArrayList<>(leid.subList(0, 56));
        for (int i = 28; i < 56; i++) {
            leid2.add(outerLoop.get(i));
        }
        for (int i = 0; i < 27; i++) {
            leid2.add(outerLoop.get(i));
        }

        for (int i = 1; i <= 7; i++) {
            leid2.add(new Reitur(i, 7));
        }
        MARK2 = leid2.size();
    }

    /**
     *virkmi ludo leiksins
     * @return true ef leik er lokið
     */
    public boolean leikaLeik(){
        teningur.kasta();
        //færa leikmann eftir tening
        if(faeraleikmann()){
            stada.setValue(Stada.LOKID);
            //sigurvegari
            sigurvegariProperty.setValue(getLeikmadur().getNafn());
            return true;
        }
        if(samiReitur()){
            getFyrri().setReitur(1);
        }
        //næsti leikmaður gerir
        setNaesti();
        return false;
    }

    /**
     * færir leikmann eftir tening
     * @return hvort að leikmaður sé í marki
     */
    private boolean faeraleikmann(){
        int mark = (naesti == 1) ? MARK2 : MARK;
        getLeikmadur().faera(teningur.getTala(),  mark);
        return erImarki();
    }

    /**
     * Skoðar hvort leikmenn séu á sama reit
     * @return true ef leikmenn eru á sama reit, annars false
     */
    private boolean samiReitur(){
        return getLeikmadur(0).getReitur() == getLeikmadur(1).getReitur();
    }

    /**
     * Hefur nýjan leik. Leikmenn settir á fyrsta reit
     */
    public void nyrLeikur(){
        stada.setValue(Stada.I_GANGI);
        leikmenn[0].setReitur(1);
        leikmenn[1].setReitur(1);
    }

    //get og set aðferðir
    /**
     * Setter aðferð fyrir nafn leikmanns og tölvu
     * @param nafn Nafn Leikmanns
     */
    public void setNafnLeikmanns(String nafn){
        leikmenn[0].setNafn(nafn);
        naestiLeikmadurProperty.set(leikmenn[naesti].getNafn());
    }
    /**
     * get aðferð fyrir leikmann númer i
     * @param i 0 eða 1
     * @return leikmaður
     */
    public Leikmadur getLeikmadur(int i){
        return leikmenn[i];
    }
    /**
     * Skilar næsta leikmanni
     * @return leikmaður
     */
    public Leikmadur getLeikmadur(){
        return leikmenn[naesti];
    }

    /**
     * Skilar þeim leikmanni sem er ekki að gera
     * @return leikmaður
     */
    public Leikmadur getFyrri(){
        return leikmenn[fyrri];
    }
    /**
     * Skilar sigurvegara property
     * @return sigurvegara
     */
    public SimpleStringProperty sigurvegariProperty() {
        return sigurvegariProperty;
    }

    /**
     *Skilar næsta leikmanni
     * @return næsta leikmanni
     */
    public SimpleStringProperty naestiLeikmadurProperty() {
        return naestiLeikmadurProperty;
    }

    /**
     * Skilar teningnum
     * @return teningurinn
     */
    public Teningur getTeningur() {
        return teningur;
    }

    /**
     * Skilar lista með leiðinni
     * @return leið
     */
    public ArrayList<Reitur> getLeid(){
        return leid;
    }
    /**
     * Skilar lista með leiðinni
     * @return leið2
     */
    public ArrayList<Reitur> getLeid2(){
        return leid2;
    }

    // private hjálparaðferðir
    /**
     * @return segir til um hvort leikmaður er á lokareiti
     */
    private boolean erImarki(){
        int mark = (naesti == 1) ? MARK2 : MARK;
        return getLeikmadur().getReitur() == mark;
    }

    /**
     *Skilar true ef leikur er í gangi
     * @return staða leiksins
     */
    public BooleanBinding erIGangi() {
        return stada.isEqualTo(Stada.I_GANGI);
    }

    /**
     *skilar true ef leik er lokið
     * @return staða leiksins
     */
    public BooleanBinding erLokid() {
        return stada.isEqualTo(Stada.LOKID);
    }


    /**
     * setur þann leikmann sem á að gera næst
     */
    private void setNaesti(){
        naesti = (naesti + 1) % leikmenn.length;
        naestiLeikmadurProperty.set(leikmenn[naesti].getNafn());
        fyrri = (naesti + 1) % leikmenn.length;
    }


    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Ludo ludo = new Ludo();
        ludo.nyrLeikur();
        System.out.println(ludo.getLeikmadur(0));
        System.out.println(ludo.getLeikmadur(1));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.print("Á næsti leikmaður að gera? ");
        String svar = scanner.next();
        while ("j".equalsIgnoreCase(svar)) {
            System.out.println();
            System.out.println("leikmaður á að gera " + ludo.getLeikmadur());
            if (ludo.leikaLeik()) {
                System.out.println(ludo.getLeikmadur() + "kominn í mark");
                System.out.println(ludo.getTeningur());
                return;
            }

            System.out.println(ludo.getTeningur());
            System.out.println(ludo.getLeikmadur(0));
            System.out.println(ludo.getLeikmadur(1));

            System.out.print("Á næsti leikmaður að gera?");
            svar = scanner.next();
        }
    }

}
