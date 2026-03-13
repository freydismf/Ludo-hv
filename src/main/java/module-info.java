/******************************************************************************
 *  Nafn    :
 *  T-póstur:
 *  Lýsing  : er module skrá sem skilgreinir hvaða forritasöfn eru nauðsynleg og hver eru
 *  aðgengileg forritasöfnum  *
 *
 *****************************************************************************/
module ModuleNafn {
    requires javafx.fxml;
    requires javafx.controls;
    opens is.vidmot to javafx.fxml;

    exports is.vidmot;
    exports is.vinnsla;
}