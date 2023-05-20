module cmps251 {
    requires javafx.controls;
    requires javafx.fxml;

    opens cmps251 to javafx.fxml;
    // opens cmps251.controllers to javafx.fxml;
    // opens cmps251.exceptions to javafx.fxml;
    // opens cmps251.main to javafx.fxml;
    opens cmps251.models to javafx.base;
    // opens cmps251.repos to javafx.fxml;
    // opens cmps251.utils to javafx.fxml;
    exports cmps251;
    // exports cmps251.controllers;
    // exports cmps251.exceptions;
    // exports cmps251.main;
    // exports cmps251.models;
    // exports cmps251.repos;
    // exports cmps251.utils;
}
