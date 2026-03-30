package is.vidmot;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Interface sem leyfir að setja gögn
 *  Útfært af contollerum sem vilja hlaða inn gögnum í
 *  viðmótshluti
 *
 *
 *****************************************************************************/
public interface GognInterface<T> {
    /**
     *
     * @param data
     */
    public void setGogn(T data);
}
