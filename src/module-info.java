module Sliver_Moon {
	requires javafx.controls;
	requires java.desktop;
	requires javafx.swing;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
}
