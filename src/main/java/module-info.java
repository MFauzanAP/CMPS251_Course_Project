module cmps251 {
    requires javafx.controls;
    requires javafx.fxml;

    opens cmps251 to javafx.fxml;
    exports cmps251;
}
