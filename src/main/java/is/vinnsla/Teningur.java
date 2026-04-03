package is.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.Random;
/******************************************************************************
 *  Nafn    : Freydís María og Hrefna Sóley
 *  Lýsing  : Teningaklasi fyrir lúdó
 *****************************************************************************/
public class Teningur {
    private static final int MAX = 6;
    private final IntegerProperty tala = new SimpleIntegerProperty(MAX);
    private final Random random = new Random();

    /**s
     * Kastar tening þannig að fundinn sé tala af handahófi á bilinu 1 til MAX+1
     */
    public void kasta() {
        tala.set(random.nextInt(1 , MAX + 1));
    }

    /**
     * get aðferð fyrir tala
     * @return property sem inniheldur teningshliðina eftir síðasta kast
     */
    public IntegerProperty tala(){
        return  tala;
    }

    /**
     * skilar teningshliðinni eftir síðasta kast
     * @return teningshliðin (heiltala)
     */
    public int getTala() {
        return tala.get();
    }

    /**
     * skilar streng sem lýsir hlutnum
     * @return strengur með innihaldi hlutarins
     */
    @Override
    public String toString(){
        return "Teningur {" +"tala = " + tala + '}';
    }
    /**
     * Test forrit fyrir klasann
     * @param args a
     */
    public static void main (String [] args) {
        Teningur t = new Teningur();
        System.out.println (t);
    }
}


