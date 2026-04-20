package is.vidmot;
/******************************************************************************
 *  Nafn    : Freydís María og Hrefna Sóley
 *  Lýsing  : Enum klasi fyrir viewswitcher
 *****************************************************************************/
public enum View {
    LUDO("/is/vidmot/ludo-view.fxml"),
    LOGIN("/is/vidmot/login-view.fxml");

    private final String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter fyr nafn skráar
     * @return filename
     */
    public String getFileName() {
        return fileName;
    }
}
