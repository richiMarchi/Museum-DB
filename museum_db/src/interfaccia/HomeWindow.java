package interfaccia;

import java.util.LinkedList;
import java.util.List;

import entità.Museo;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tabelle.MuseoTab;

public class HomeWindow {

    public static void getHomeWindow(Stage primaryStage) {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,50,50,50));
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        
        Text text = new Text("Museum Manager");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);
        
        HBox hb = new HBox();
        hb.setPadding(new Insets(20,20,20,0));
        hb.getChildren().add(text);
        
        Label lblMuseo = new Label("Seleziona Museo");
        ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.setValue("---");
        comboBox.setOnAction(e -> {
            primaryStage.sizeToScene();
        });
        
        gridPane.add(lblMuseo, 0, 0);
        gridPane.add(comboBox, 1, 0);
        
        Button gestisciMuseo = new Button("Gestisci Museo");
        gestisciMuseo.setOnAction(staffEvent->{
            int codMuseo = Integer.parseInt(comboBox.getValue().substring(0, comboBox.getValue().indexOf(" ")));
            SingleMuseum.getSingleMuseum(primaryStage, codMuseo);
        });
        gridPane.add(gestisciMuseo, 2, 0);
        
        List<Museo> musei = new LinkedList<Museo>();
        musei = new MuseoTab().getAllTable();
        if(musei!=null) {
            for(Museo museo : musei) {
                comboBox.getItems().add(museo.getCodMuseo() + " - " + museo.getNomeMuseo());
            }
            comboBox.setValue(comboBox.getItems().get(0));
        } else {
            gestisciMuseo.setDisable(true);
        }
        
        comboBox.setOnAction(e -> {
            gestisciMuseo.setDisable(false);
        });
        
        Button tuttiMusei = new Button("Opera Su Tutti I Musei");
        tuttiMusei.setOnAction(e -> {
            AllMuseums.getAllMuseums(primaryStage);
        });
        Button chiudi = new Button("Chiudi Applicazione");
        chiudi.setOnAction(prego -> {
            primaryStage.close();
        });
        
        GridPane bottomBox = new GridPane();
        bottomBox.setPadding(new Insets(20,20,20,30));
        bottomBox.setHgap(30);
        
        bottomBox.add(chiudi, 0, 0);
        bottomBox.add(tuttiMusei, 1, 0);
        
        bp.setTop(hb);
        bp.setCenter(gridPane);
        bp.setBottom(bottomBox);
        
        Scene scene = new Scene(bp);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
