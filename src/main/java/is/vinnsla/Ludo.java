package is.vinnsla;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/******************************************************************************
 *  Nafn    : Freydís María Friðriksdóttir
 *  T-póstur: fmf6@hi.is
 *  Lýsing  :
 *****************************************************************************/
public class Ludo {

    private int naesti = 0;
    private int fyrri = 0;
    private final int MARK;
    enum Stada{
        I_GANGI,
        LOKID
    }

    //Teningur
    private final Teningur teningur = new Teningur();
    //Tveir leikmenn
    private final Leikmadur[] leikmenn =
            new Leikmadur[]{new Leikmadur("Tumi"), new Leikmadur("Neró")};
    //Heldur utan um reiti
    private final ArrayList<Reitur> leid = new ArrayList<>();
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
        Reitur byrjun = new Reitur(5,0);
        leid.add(byrjun);
        for(int i = 4; i >= 0 ; i--){
            Reitur nyrReitur = new Reitur(i, 0);
            leid.add(nyrReitur);
        }
        for(int i = 1; i <= 5 ; i++){
            Reitur nyrReitur = new Reitur(0, i);
            leid.add(nyrReitur);
        }
        for(int i = 1; i <= 5 ; i++){
            Reitur nyrReitur = new Reitur(i, 5);
            leid.add(nyrReitur);
        }
        for(int i = 4; i>=2;i--){
            Reitur nyrReitur = new Reitur(5, i);
            leid.add(nyrReitur);
        }
        for(int i = 4; i>=2;i--) {
            Reitur nyrReitur = new Reitur(i, 2);
            leid.add(nyrReitur);
        }
        Reitur mark = new Reitur(2,3);
        leid.add(mark);
        MARK = leid.size();
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
        getLeikmadur().faera(teningur.getTala(),MARK);
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

    // private hjálparaðferðir
    /**
     * @return segir til um hvort leikmaður er á lokareiti
     */
    private boolean erImarki(){
        return getLeikmadur().getReitur() == MARK;
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
