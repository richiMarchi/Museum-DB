package interfaccia;

import java.util.Iterator;
import java.util.List;

import entità.SupportoMultimediale;
import entità.UtilizzoStrumento;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tabelle.SupportoMultimedialeTab;
import tabelle.UtilizzoStrumentoTab;

public class VisualizzaStrumenti {

    public static void getVisualizzaStrumenti(Stage primaryStage, int codMuseo) {
        Stage stage1 = new Stage();
        
        GridPane gp1 = new GridPane();
        //gp1.setGridLinesVisible(true);
        gp1.setPadding(new Insets(20,20,20,20));
        gp1.setHgap(50);
        gp1.setVgap(10);
        Text us1 = new Text("Museo");
        us1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text us2 = new Text("Strumento");
        us2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text us3 = new Text("Data");
        us3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text us4 = new Text("Affidato alle");
        us4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        gp1.add(us1, 0, 0);
        gp1.add(us2, 1, 0);
        gp1.add(us3, 2, 0);
        gp1.add(us4, 3, 0);
        
        List<UtilizzoStrumento> elements = new UtilizzoStrumentoTab().getAllTable(codMuseo);
        
        if (elements != null) {
            int i = 1;
            Iterator<UtilizzoStrumento> itBig = (Iterator<UtilizzoStrumento>) elements.iterator();
            while(itBig.hasNext()) {
                UtilizzoStrumento big = itBig.next();
                gp1.add(new Text("" + big.getCodMuseo()), 0, i);
                gp1.add(new Text("" + big.getCodStrumento()), 1, i);
                gp1.add(new Text("" + big.getDataUtilizzo()), 2, i);
                gp1.add(new Text("" + big.getOraInizio()), 3, i);
                i++;
            }
            Text vs4 = new Text("Totale Strumenti Utilizzati: " + (i-1));
            vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(vs4, 1, i+1);
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

    public static void getQuantiStrumenti(Stage primaryStage, int codMuseo) {
        Stage stage1 = new Stage();
        
        GridPane gp1 = new GridPane();
        //gp1.setGridLinesVisible(true);
        gp1.setPadding(new Insets(20,20,20,20));
        gp1.setHgap(50);
        gp1.setVgap(10);
        Text us1 = new Text("Museo");
        us1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text us2 = new Text("Strumento");
        us2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        gp1.add(us1, 0, 0);
        gp1.add(us2, 1, 0);
        
        List<SupportoMultimediale> elements = new SupportoMultimedialeTab().getAllTable(codMuseo);
        if (elements != null) {
            int i = 1;
            Iterator<SupportoMultimediale> itBig = (Iterator<SupportoMultimediale>) elements.iterator();
            while(itBig.hasNext()) {
                SupportoMultimediale big = itBig.next();
                gp1.add(new Text("" + big.getCodMuseo()), 0, i);
                gp1.add(new Text("" + big.getCodStrumento()), 1, i);
                i++;
            }
            Text vs4 = new Text("Totale Strumenti: " + (i-1));
            vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(vs4, 1, i+1);
        } else {
            gp1.add(new Text("/"), 0, 1);
            gp1.add(new Text("/"), 1, 1);
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
