package is.vinnsla;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/******************************************************************************
 *  Nafn     : Freydís María og Hrefna Sóley
 *  Lýsing   : Klasi fyrir leikmann
 *****************************************************************************/
public class Leikmadur {
    private final SimpleStringProperty leikmadur = new SimpleStringProperty();
    private final SimpleIntegerProperty reiturProperty =
            new SimpleIntegerProperty(1);

    /**
     *Constructor fyrir leuikmnn
     * @param leikmadur leikmaður
     */
    public Leikmadur(String leikmadur) {
        this.leikmadur.setValue(leikmadur);
    }


    /**
     * Færir peð leikmanns um i sæti en þó aldrei fram yfir max
     * @param i sæti sem á að færa peðið fram um
     * @param max hæsta sæti
     */
    public void faera(int i, int max) {
        this.reiturProperty.set(Math.min(max, this.reiturProperty.get() + i));
    }

    /**
     * skilar reit leikmanns
     * @return reitur (heiltala)
     */
    public int getReitur() {
        return reiturProperty.get();
    }

    /**
     * get aðferð fyrir ReiturProperty
     * @return property sem inniheldur á hvaða reit leikmanns
     */
    public IntegerProperty getReiturProperty() {
        return reiturProperty;
    }

    /**
     * skilar nafni leikmanns
     * @return nafn (Strengur)
     */
    public String getNafn() {
        return leikmadur.get();
    }

    /**
     * Setur nafn leikmanns
     * @param nafn
     */
    public void setNafn(String nafn){
        leikmadur.set(nafn);
    }

    /**
     * Setter fyrir reit
     * @param reitur reitur
     */
    public void setReitur(int reitur) {
        reiturProperty.set(reitur);
    }

    /**
     * skilar streng sem lýsir hlutnum
     * @return strengur með innihaldi hlutarins
     */
    @Override
    public String toString() {
        return "Leikmaður{" +
                "leikmadur=" + leikmadur.get() +
                ", reiturProperty=" + reiturProperty.get() +
                '}';
    }

    /**
     * Main fall
     * @param args args
     */
    static void main(String[] args){
        Leikmadur l = new Leikmadur("Freydís");
        System.out.println(l);
    }
}
