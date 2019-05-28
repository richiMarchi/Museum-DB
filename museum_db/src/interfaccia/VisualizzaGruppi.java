package interfaccia;

import java.util.Iterator;
import java.util.List;

import entità.VisitaGruppo;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tabelle.VisitaGruppoTab;

public class VisualizzaGruppi {

    public static void getVisualizzaGruppi(Stage primaryStage, int codMuseo) {
Stage stage1 = new Stage();
        
        GridPane gp1 = new GridPane();
        //gp1.setGridLinesVisible(true);
        gp1.setPadding(new Insets(20,20,20,20));
        gp1.setHgap(50);
        gp1.setVgap(10);
        
        Text vs1 = new Text("Museo");
        vs1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs2 = new Text("Numero Gruppo");
        vs2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs3 = new Text("Guida");
        vs3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs4 = new Text("Data");
        vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs5 = new Text("Ora Inizio");
        vs5.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        
        gp1.add(vs1, 0, 0);
        gp1.add(vs2, 1, 0);
        gp1.add(vs3, 2, 0);
        gp1.add(vs4, 3, 0);
        gp1.add(vs5, 4, 0);
        
        List<VisitaGruppo> elements = new VisitaGruppoTab().getAllTable(codMuseo);
        
        if (elements != null) {
            int i = 1;
            Iterator<VisitaGruppo> itBig = (Iterator<VisitaGruppo>) elements.iterator();
            while(itBig.hasNext()) {
                VisitaGruppo big = itBig.next();
                gp1.add(new Text("" + big.getCodMuseo()), 0, i);
                gp1.add(new Text("" + big.getCodGruppo()), 1, i);
                gp1.add(new Text("" + big.getCodFiscale()), 2, i);
                gp1.add(new Text("" + big.getData()), 3, i);
                gp1.add(new Text("" + big.getOraInizio()), 4, i);
                i++;
            }
            Text vs11 = new Text("Totale Gruppi: " + (i-1));
            vs11.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(vs11, 2, i+1);
        } else {
            gp1.add(new Text("/"), 0, 1);
            gp1.add(new Text("/"), 1, 1);
            gp1.add(new Text("/"), 2, 1);
            gp1.add(new Text("/"), 3, 1);
            gp1.add(new Text("/"), 4, 1);
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
        stage1.setTitle("Visualizza Gruppi");
        stage1.setScene(scene1);
        stage1.sizeToScene();
        stage1.setResizable(false);
        stage1.showAndWait();
    }
}
