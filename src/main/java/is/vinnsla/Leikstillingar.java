package is.vinnsla;
/******************************************************************************
 *  Nafn     : Freydís María og Hrefna Sóley
 *  Lýsing   : Klasi fyrir nafn og lit peðs
 *****************************************************************************/
public class Leikstillingar {
    private final String nafn;
    private final String litur;

    /**
     *
     * @param nafn
     * @param litur
     */
    public Leikstillingar(String nafn, String litur){
        this.nafn = nafn;
        this.litur = litur;
    }

    /**
     *
     * @return nafn
     */
    public String getNafn(){
        return nafn;
    }

    /**
     *
     * @return litur
     */
    public String getLitur(){
        return litur;
    }
}

