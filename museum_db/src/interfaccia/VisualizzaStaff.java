package interfaccia;

import java.util.Iterator;
import java.util.List;

import entità.Personale;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utilities.GenericUtilities;

public class VisualizzaStaff {

    public static void getVisualizzaStaff(Stage primaryStage, int codMuseo) {
        Stage stage1 = new Stage();
        
        GridPane gp1 = new GridPane();
        //gp1.setGridLinesVisible(true);
        gp1.setPadding(new Insets(20,20,20,20));
        gp1.setHgap(50);
        gp1.setVgap(10);
        
        Text vs1 = new Text("Museo");
        vs1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs2 = new Text("Numero Badge");
        vs2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs3 = new Text("Codice Fiscale");
        vs3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs4 = new Text("Nome");
        vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs5 = new Text("Cognome");
        vs5.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs6 = new Text("Data Nascita");
        vs6.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs7 = new Text("Mail");
        vs7.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs8 = new Text("Numero Telefono");
        vs8.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs9 = new Text("Inizio Lavoro");
        vs9.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text vs10 = new Text("Fine Lavoro");
        vs10.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        
        gp1.add(vs1, 0, 0);
        gp1.add(vs8, 7, 0);
        gp1.add(vs2, 1, 0);
        gp1.add(vs3, 2, 0);
        gp1.add(vs4, 3, 0);
        gp1.add(vs5, 4, 0);
        gp1.add(vs6, 5, 0);
        gp1.add(vs7, 6, 0);
        gp1.add(vs9, 8, 0);
        gp1.add(vs10, 9, 0);
        
        List<Personale> elements = GenericUtilities.getAllStaffs(codMuseo, 0);
        
        if (elements != null) {
            int i = 1;
            Iterator<Personale> itBig = (Iterator<Personale>) elements.iterator();
            while(itBig.hasNext()) {
                Personale big = itBig.next();
                gp1.add(new Text("" + big.getCodMuseo()), 0, i);
                gp1.add(new Text("" + big.getNumeroBadge()), 1, i);
                gp1.add(new Text("" + big.getCodFiscale()), 2, i);
                gp1.add(new Text("" + big.getNome()), 3, i);
                gp1.add(new Text("" + big.getCognome()), 4, i);
                gp1.add(new Text("" + big.getDataNascita()), 5, i);
                gp1.add(new Text("" + big.getMail()), 6, i);
                gp1.add(new Text("" + big.getNumeroTelefono()), 7, i);
                gp1.add(new Text("" + big.getInizioLavoro()), 8, i);
                if (big.getFineLavoro()!=null) {
                    gp1.add(new Text("" + big.getFineLavoro()), 9, i);
                } else {
                    gp1.add(new Text("N/A"), 9, i);
                }
                i++;
            }
            Text vs11 = new Text("Totale Staff: " + (i-1));
            vs11.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(vs11, 5, i+1);
        } else {
            gp1.add(new Text("/"), 0, 1);
            gp1.add(new Text("/"), 1, 1);
            gp1.add(new Text("/"), 2, 1);
            gp1.add(new Text("/"), 3, 1);
            gp1.add(new Text("/"), 4, 1);
            gp1.add(new Text("/"), 5, 1);
            gp1.add(new Text("/"), 6, 1);
            gp1.add(new Text("/"), 7, 1);
            gp1.add(new Text("/"), 8, 1);
            gp1.add(new Text("/"), 9, 1);
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
