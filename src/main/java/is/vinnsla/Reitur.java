package is.vinnsla;
/******************************************************************************
 *  Nafn    : Freydís María og Hrefna Sóley
 *  Lýsing  : Klasi fyrir reiti í Lúdó
 *****************************************************************************/
public class Reitur {

    //tilviksbreytur
    int rod;
    int dalkur;


    /**
     * Smiður fyri reit
     * @param rod Röð Rúðugrindar
     * @param dalkur Dálkur Rúðugrindar
     */
    public Reitur(int rod, int dalkur){
        this.dalkur = dalkur;
        this.rod = rod;
    }

    /**
     * Override fyrir equals
     * @param o   the reference object with which to compare.
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Reitur)) {
            return false;
        }
        Reitur r = (Reitur) o;
        return rod == r.rod && dalkur == r.dalkur;
    }

    /**
     * Oerride fyrir hashCode
     * @return hash code
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(rod, dalkur);
    }

    /**
     *Skilar röð
     * @return rod
     */
    public int getRod(){
        return  rod;
    }

    /**
     * Skilar dálki
     * @return dalkur
     */
    public int getDalkur(){
        return dalkur;
    }

}
