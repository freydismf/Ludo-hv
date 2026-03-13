package is.vinnsla;

import javafx.beans.property.SimpleObjectProperty;

/******************************************************************************
 *  Nafn    : Freydís María Friðriksdóttir
 *  T-póstur: fmf6@hi.is
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
