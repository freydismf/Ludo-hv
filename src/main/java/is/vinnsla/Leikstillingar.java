package is.vinnsla;
/******************************************************************************
 *  Nafn     : Freydís María og Hrefna Sóley
 *  Lýsing   : Klasi fyrir nafn og lit peðs
 *****************************************************************************/
public class Leikstillingar {
    private final String nafn;
    private final String litur;

    /**
     *Smiður fyrir leikstillingar
     * @param nafn leikmanns
     * @param litur peðs
     */
    public Leikstillingar(String nafn, String litur){
        this.nafn = nafn;
        this.litur = litur;
    }

    /**
     * Skilar nafni
     * @return nafn
     */
    public String getNafn(){
        return nafn;
    }

    /**
     * Skilar lit
     * @return litur
     */
    public String getLitur(){
        return litur;
    }

    /**
     * skilar streng sem lýsir hlutnum
     * @return strengur með innihaldi hlutarins
     */
    @Override
    public String toString() {
        return "Leikstillingar{" +
                "Nafn=" + nafn +
                ", Litur=" + litur +
                '}';
    }
    /**
     * Main fall
     * @param args args
     */
    static void main(String[] args){
        Leikstillingar l = new Leikstillingar("Freydís", "Gulur");
        System.out.println(l);
    }
}

