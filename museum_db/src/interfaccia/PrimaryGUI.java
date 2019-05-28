package interfaccia;

import javafx.application.Application;
import javafx.stage.Stage;
import utilities.GenericUtilities;


public class PrimaryGUI extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        GenericUtilities.createTables();
        primaryStage.setTitle("Museum Database");
        HomeWindow.getHomeWindow(primaryStage);
    }
}
