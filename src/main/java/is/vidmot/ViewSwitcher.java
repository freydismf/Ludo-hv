package is.vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 * <p>
 * EÞH - Breytt til að gera cache og controllers og að setja gögn eftir
 * að búið er að hlaða inn view
 */
public class ViewSwitcher {

    private static final Map<View, Parent> cache = new HashMap<>();
    // viðbót fyrir controllers
    private static final Map<View, Object> controllerCache = new HashMap<>();

    private static Scene scene;

    /**
     * setur scenuna scene
     *
     * @param scene
     */
    public static void setScene(Scene scene) {
        ViewSwitcher.scene = scene;
    }


    /**
     * Skiptir yfir viðmótstré í senunni
     *
     * @param view    rót viðmótstrésins
     */
    public static void  switchTo(View view) {
        switchTo(view, false, null);
    }


    /**
     * Skiptir yfir viðmótstré í senunni
     *
     * @param view    rót viðmótstrésins
     * @param isCache á að nota skyndiminni
     */
    public static void  switchTo(View view, boolean isCache) {
        switchTo(view, isCache, null);
    }

    /**
     * Skiptir yfir viðmótstré í senunni
     *
     * @param view    rót viðmótstrésins
     * @param isCache á að nota skyndiminni
     * @param hlutur gögnin sem á að setja inn í controllerinn
     */
    public static void switchTo(View view, boolean isCache, Object hlutur) {
        if (scene == null) {
            System.out.println("No scene was set");
            return;
        }
        try {
            Parent root;
            Object controller;
            if (cache.containsKey(view) && isCache) {
                System.out.println("Loading from cache");
                root = cache.get(view);
                controller = controllerCache.get(view);
            } else {
                System.out.println("Loading from FXML " + view.getFileName());
                FXMLLoader loader = new FXMLLoader(ViewSwitcher
                        .class.getResource(view.getFileName()));
                root = loader.load();
                controller = loader.getController();
                if (isCache) {
                    cache.put(view, root);
                    controllerCache.put(view, loader.getController());
                }
            }
//            if (controller instanceof GognInterface) {
//                ((GognInterface) controller).setGogn(hlutur);
//            }
            scene.setRoot(root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}