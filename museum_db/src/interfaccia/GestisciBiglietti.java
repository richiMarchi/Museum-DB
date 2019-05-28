package interfaccia;

import java.util.Iterator;
import java.util.List;

import entità.Biglietto;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tabelle.BigliettoTab;

public class GestisciBiglietti {

    public static void getGestisciBiglietti(Stage primaryStage, int codMuseo) {
        Stage stage1 = new Stage();
        
        GridPane gp1 = new GridPane();
        //gp1.setGridLinesVisible(true);
        gp1.setPadding(new Insets(20,20,20,20));
        gp1.setHgap(50);
        gp1.setVgap(10);
        
        Text b1 = new Text("Museo");
        b1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text b2 = new Text("Visitatore");
        b2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text b3 = new Text("Biglietto");
        b3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text b4 = new Text("Gruppo");
        b4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text b5 = new Text("Data Acquisto");
        b5.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text b6 = new Text("Prezzo");
        b6.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        gp1.add(b1, 0, 0);
        gp1.add(b2, 1, 0);
        gp1.add(b3, 2, 0);
        gp1.add(b4, 3, 0);
        gp1.add(b5, 4, 0);
        gp1.add(b6, 5, 0);
        
        List<Biglietto> elements = new BigliettoTab().getAllTable(codMuseo);
        
        if (elements != null) {
            int i = 1;
            Iterator<Biglietto> itBig = (Iterator<Biglietto>) elements.iterator();
            while(itBig.hasNext()) {
                Biglietto big = itBig.next();
                gp1.add(new Text("" + big.getCodMuseo()), 0, i);
                gp1.add(new Text("" + big.getCodFiscale()), 1, i);
                gp1.add(new Text("" + big.getCodBiglietto()), 2, i);
                if (big.getCodGruppo() != 0) {
                    gp1.add(new Text("" + big.getCodGruppo()), 3, i);
                } else {
                    gp1.add(new Text("N/A"), 3, i);
                }
                gp1.add(new Text("" + big.getData()), 4, i);
                gp1.add(new Text("" + big.getPrezzo() + " €"), 5, i);
                i++;
            }
            Text vs4 = new Text("Totale Biglietti: " + (i-1));
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
