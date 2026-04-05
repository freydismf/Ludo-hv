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
     * @param rod Röð Rúðugrindar
     * @param dalkur Dálkur Rúðugrindar
     */
    public Reitur(int rod, int dalkur){
        this.dalkur = dalkur;
        this.rod = rod;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Reitur)) return false;
        Reitur r = (Reitur) o;
        return rod == r.rod && dalkur == r.dalkur;
    }
    @Override
    public int hashCode() {
        return java.util.Objects.hash(rod, dalkur);
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
