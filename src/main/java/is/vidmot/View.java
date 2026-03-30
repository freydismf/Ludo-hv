package is.vidmot;

public enum View {
    LUDO("/is/vidmot/ludo-view.fxml"),
    LOGIN("/is/vidmot/login-view.fxml");


    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     * @return filename
     */
    public String getFileName() {
        return fileName;
    }
}
