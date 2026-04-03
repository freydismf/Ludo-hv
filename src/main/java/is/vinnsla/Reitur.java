package is.vinnsla;

import javafx.beans.property.SimpleObjectProperty;

/******************************************************************************
 *  Nafn    : Freydís María og Hrefna Sóley
 *  Lýsing  : Klasi fyrir reiti í Lúdó
 *****************************************************************************/
public class Reitur {

    int rod;
    int dalkur;


    /**
     *
     * @param rod
     * @param dalkur
     */
    public Reitur(int rod, int dalkur){
        this.dalkur = dalkur;
        this.rod = rod;
    }

    /**
     *
     * @return rod
     */
    public int getRod(){
        return  rod;
    }

    /**
     *
     * @return dalkur
     */
    public int getDalkur(){
        return dalkur;
    }

}
