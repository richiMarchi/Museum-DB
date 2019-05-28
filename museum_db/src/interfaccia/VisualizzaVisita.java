package interfaccia;

import java.util.Iterator;
import java.util.List;

import entità.VisitaSingola;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tabelle.VisitaSingolaTab;

public class VisualizzaVisita {

    public static void getVisualizzaVisita(Stage primaryStage, int codMuseo) {
        
        Stage stage1 = new Stage();
        
        GridPane gp1 = new GridPane();
        //gp1.setGridLinesVisible(true);
        gp1.setPadding(new Insets(20,20,20,20));
        gp1.setHgap(50);
        gp1.setVgap(10);
        
        Text vs1 = new Text("Museo");
        vs1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs2 = new Text("Biglietto");
        vs2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs3 = new Text("Data Visita");
        vs3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs4 = new Text("Numero in Giornata");
        vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        
        gp1.add(vs1, 0, 0);
        gp1.add(vs2, 1, 0);
        gp1.add(vs3, 2, 0);
        gp1.add(vs4, 3, 0);
        
        List<VisitaSingola> elements = new VisitaSingolaTab().getAllTable(codMuseo);
        
        if (elements != null) {
            int i = 1;
            Iterator<VisitaSingola> itBig = (Iterator<VisitaSingola>) elements.iterator();
            while(itBig.hasNext()) {
                VisitaSingola big = itBig.next();
                gp1.add(new Text("" + big.getCodMuseo()), 0, i);
                gp1.add(new Text("" + big.getCodBiglietto()), 1, i);
                gp1.add(new Text("" + big.getDataUtilizzo()), 2, i);
                gp1.add(new Text("" + big.getNumeroProgressivo()), 3, i);
                i++;
            }
            Text vs5 = new Text("Totale Visite: " + (i-1));
            vs5.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(vs5, 1, i+1);
        } else {
            gp1.add(new Text("/"), 0, 1);
            gp1.add(new Text("/"), 1, 1);
            gp1.add(new Text("/"), 2, 1);
            gp1.add(new Text("/"), 3, 1);
        }
        
        BorderPane bp1 = new BorderPane();
        bp1.setPadding(new Insets(10,50,50,50));
        bp1.setTop(gp1);
        Button close1 = new Button("Chiudi");
        close1.setOnAction(closing -> {
            stage1.close();
        });
        bp1.setBottom(close1);
        
        Scene scene1 = new Scene(bp1);
        stage1.setScene(scene1);
        stage1.sizeToScene();
        stage1.setResizable(false);
        stage1.showAndWait();
    }
}
