module Sliver_Moon {
	requires javafx.controls;
	requires java.desktop;
	requires javafx.swing;
	requires javafx.graphics;
	requires com.google.gson;
	
	opens application to javafx.graphics, javafx.fxml;
	opens gameEngine to com.google.gson;
}
