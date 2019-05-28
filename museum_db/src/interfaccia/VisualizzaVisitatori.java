package interfaccia;

import java.util.Iterator;
import java.util.List;

import entità.Visitatore;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tabelle.VisitatoreTab;

public class VisualizzaVisitatori {

    public static void getVisualizzaVisitatori(Stage primaryStage, int codMuseo) {
        Stage stage1 = new Stage();
        
        GridPane gp1 = new GridPane();
        //gp1.setGridLinesVisible(true);
        gp1.setPadding(new Insets(20,20,20,20));
        gp1.setHgap(50);
        gp1.setVgap(10);
        
        Text v1 = new Text("Cod Fiscale");
        v1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text v2 = new Text("Nome");
        v2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text v3 = new Text("Cognome");
        v3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text v4 = new Text("Data Nascita");
        v4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text v5 = new Text("Mail");
        v5.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text v6 = new Text("Spesa Totale");
        v6.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        gp1.add(v1, 0, 0);
        gp1.add(v2, 1, 0);
        gp1.add(v3, 2, 0);
        gp1.add(v4, 3, 0);
        gp1.add(v5, 4, 0);
        gp1.add(v6, 5, 0);
        
        List<Visitatore> elements = new VisitatoreTab().getAllTable(codMuseo);
        
        if (elements != null) {
            int i = 1;
            Iterator<Visitatore> itBig = (Iterator<Visitatore>) elements.iterator();
            while(itBig.hasNext()) {
                Visitatore big = itBig.next();
                gp1.add(new Text("" + big.getCodFiscale()), 0, i);
                gp1.add(new Text("" + big.getNome()), 1, i);
                gp1.add(new Text("" + big.getCognome()), 2, i);
                gp1.add(new Text("" + big.getDataNascita()), 3, i);
                gp1.add(new Text("" + big.getMail()), 4, i);
                gp1.add(new Text("" + new VisitatoreTab().getSpesaTotale(big.getCodFiscale()) + " €"), 5, i);
                i++;
            }
            Text vs4 = new Text("Totale Visitatori: " + (i-1));
            vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(vs4, 2, i+1);
        } else {
            gp1.add(new Text("/"), 0, 1);
            gp1.add(new Text("/"), 1, 1);
            gp1.add(new Text("/"), 2, 1);
            gp1.add(new Text("/"), 3, 1);
            gp1.add(new Text("/"), 4, 1);
            gp1.add(new Text("/"), 5, 1);
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
