package interfaccia;

import java.util.Iterator;
import java.util.List;

import entità.Restauro;
import entità.SupportoMultimediale;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tabelle.RestauroTab;
import tabelle.SupportoMultimedialeTab;

public class SingleMuseum {

    public static void getSingleMuseum(Stage primaryStage, int codMuseo) {
        
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,50,50,50));
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        
        Text text = new Text("Gestione Museo " + codMuseo);
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);
        
        HBox hb = new HBox();
        hb.setPadding(new Insets(20,20,20,30));
        hb.getChildren().add(text);
        
        Label bigg = new Label("Biglietti");
        Button acquistoBiglietto = new Button("Acquisto Biglietto");
        acquistoBiglietto.setOnAction(e -> {
            AcquistaBiglietti.getAcquistaBiglietti(primaryStage, codMuseo);
        });
        Button btnBiglietti = new Button("Visualizza Biglietti");
        btnBiglietti.setOnAction(visbig -> {
            GestisciBiglietti.getGestisciBiglietti(primaryStage, codMuseo);
        });
        Button btnVisitatori = new Button("Visualizza Visitatori");
        btnVisitatori.setOnAction(visitators -> {
            VisualizzaVisitatori.getVisualizzaVisitatori(primaryStage, codMuseo);
        });
        
        Label viss = new Label("Visite");
        Button segnalaVisita = new Button("Segnala Visita");
        segnalaVisita.setOnAction(e -> {
            SegnalaVisita.getSegnalaVisita(primaryStage, codMuseo);
        });
        Button btnVisite = new Button("Visualizza Visite");
        btnVisite.setOnAction(visvis -> {
            VisualizzaVisita.getVisualizzaVisita(primaryStage, codMuseo);
        });
        
        Label staff = new Label("Staff");
        Button modificaStaff = new Button("Modifica Staff");
        modificaStaff.setOnAction(e -> {
            ModificaStaff.getModificaStaff(primaryStage, codMuseo);
        });
        Button btnGuide = new Button("Visualizza Staff");
        btnGuide.setOnAction(guidd -> {
            VisualizzaStaff.getVisualizzaStaff(primaryStage, codMuseo);
        });
        
        Label gruppi = new Label("Gruppi");
        Button organizzaGruppo = new Button("Organizza Gruppo");
        organizzaGruppo.setOnAction(e -> {
            OrganizzaGruppo.getOrganizzaGruppo(primaryStage, codMuseo);
        });
        Button btnGruppi = new Button("Visualizza Gruppi");
        btnGruppi.setOnAction(grups -> {
            VisualizzaGruppi.getVisualizzaGruppi(primaryStage, codMuseo);
        });
        
        Label strum = new Label("Strumenti");
        Button gestisciStrumenti = new Button("Aggiungi Strumento");
        gestisciStrumenti.setOnAction(e -> {
            new SupportoMultimedialeTab().insertInDB(new SupportoMultimediale(0, codMuseo));
            new Alert(Alert.AlertType.CONFIRMATION, "Strumento aggiunto con successo!", ButtonType.OK).showAndWait();
        });
        Button btnStrum = new Button("Visualizza Utilizzi");
        btnStrum.setOnAction(vistrum -> {
            VisualizzaStrumenti.getVisualizzaStrumenti(primaryStage, codMuseo);
        });
        Button btnQuanti = new Button("Visualizza Strumenti");
        btnQuanti.setOnAction(qnt -> {
            VisualizzaStrumenti.getQuantiStrumenti(primaryStage, codMuseo);
        });
        
        Label restauri = new Label("Restauri");
        Button gestisciRestauri = new Button("Gestisci Restauri");
        gestisciRestauri.setOnAction(e -> {
            GestisciRestauri.getGestisciRestauri(primaryStage, codMuseo);
        });
        Button btnRestauri = new Button("Visualizza Restauri");
        btnRestauri.setOnAction(resti -> {
            Stage nuovino = new Stage();
            
            BorderPane bp1 = new BorderPane();
            bp1.setPadding(new Insets(10,50,50,50));
        
            GridPane gp1 = new GridPane();
            gp1.setPadding(new Insets(20,20,20,20));
            gp1.setHgap(5);
            gp1.setVgap(5);
            gp1.setHgap(10);
            gp1.setVgap(10);
            
            Text rr1 = new Text("Museo");
            rr1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text rr2 = new Text("Restauratore");
            rr2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text rr3 = new Text("Autore");
            rr3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text rr4 = new Text("Opera");
            rr4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text rr5 = new Text("Data Inizio");
            rr5.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text rr6 = new Text("Data Fine");
            rr6.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(rr1, 0, 0);
            gp1.add(rr2, 1, 0);
            gp1.add(rr3, 2, 0);
            gp1.add(rr4, 3, 0);
            gp1.add(rr5, 4, 0);
            gp1.add(rr6, 5, 0);
            
            List<Restauro> resty = new RestauroTab().getAllTable(codMuseo);
            
            if (resty != null) {
                int i = 1;
                Iterator<Restauro> itBig = (Iterator<Restauro>) resty.iterator();
                while(itBig.hasNext()) {
                    Restauro big = itBig.next();
                    gp1.add(new Text("" + big.getCodFiscale()), 0, i);
                    gp1.add(new Text("" + big.getNumeroBadge()), 1, i);
                    gp1.add(new Text("" + big.getCodAutore()), 2, i);
                    gp1.add(new Text("" + big.getNomeOpera()), 3, i);
                    gp1.add(new Text("" + big.getDataInizio()), 4, i);
                    if (big.getDataFine()!=null) {
                        gp1.add(new Text("" + big.getDataFine()), 5, i);
                    } else {
                        gp1.add(new Text("N/A"), 5, i);
                    }
                    i++;
                }
                Text vs4 = new Text("Totale Restauri: " + (i-1));
                vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
                gp1.add(vs4, 1, i+1);
            } else {
                gp1.add(new Text("/"), 0, 1);
                gp1.add(new Text("/"), 1, 1);
                gp1.add(new Text("/"), 2, 1);
                gp1.add(new Text("/"), 3, 1);
                gp1.add(new Text("/"), 4, 1);
                gp1.add(new Text("/"), 5, 1);
            }
            
            Button btnBack = new Button("Chiudi");
            btnBack.setOnAction(e -> {
                nuovino.close();
            });
            
            
            GridPane bottomBox1 = new GridPane();
            bottomBox1.setPadding(new Insets(20,20,20,30));
            bottomBox1.setHgap(5);
            bottomBox1.setVgap(5);
            
            bottomBox1.add(btnBack, 0, 0);
            
            bp1.setCenter(gp1);
            bp1.setBottom(bottomBox1);
            
            Scene scene1 = new Scene(bp1);
            nuovino.setScene(scene1);
            nuovino.sizeToScene();
            nuovino.setResizable(false);
            nuovino.showAndWait();
        });
        
        Label op = new Label("Sezioni e Opere");
        Button gestisciSezioni = new Button("Gestisci Sez. e Op.");
        gestisciSezioni.setOnAction(e -> {
            SezioniOpere.getSezioniOpere(primaryStage, codMuseo);
        });
        Button btnOpere = new Button("Visualizza Opere");
        btnOpere.setOnAction(operes -> {
            SezioniOpere.getOpere(codMuseo);
        });
        
        gridPane.add(bigg, 0, 0);
        gridPane.add(acquistoBiglietto, 1, 0);
        gridPane.add(btnBiglietti, 2, 0);
        gridPane.add(btnVisitatori, 3, 0);
        gridPane.add(viss, 0, 1);
        gridPane.add(segnalaVisita, 1, 1);
        gridPane.add(btnVisite, 2, 1);
        gridPane.add(staff, 0, 2);
        gridPane.add(modificaStaff, 1, 2);
        gridPane.add(btnGuide, 2, 2);
        gridPane.add(gruppi, 0, 3);
        gridPane.add(organizzaGruppo, 1, 3);
        gridPane.add(btnGruppi, 2, 3);
        gridPane.add(strum, 0, 4);
        gridPane.add(gestisciStrumenti, 1, 4);
        gridPane.add(btnStrum, 2, 4);
        gridPane.add(btnQuanti, 3, 4);
        gridPane.add(restauri, 0, 5);
        gridPane.add(gestisciRestauri, 1, 5);
        gridPane.add(btnRestauri, 2, 5);
        gridPane.add(op, 0, 6);
        gridPane.add(gestisciSezioni, 1, 6);
        gridPane.add(btnOpere, 2, 6);
        
        Button btnBack = new Button("<- Back");
        btnBack.setOnAction(e -> {
            HomeWindow.getHomeWindow(primaryStage);
        });
        
        GridPane bottomBox = new GridPane();
        bottomBox.setPadding(new Insets(20,20,20,30));
        bottomBox.setHgap(30);
        bottomBox.add(btnBack, 0, 0);
        
        bp.setTop(text);
        bp.setCenter(gridPane);
        bp.setBottom(bottomBox);
        
        Scene scene = new Scene(bp);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
