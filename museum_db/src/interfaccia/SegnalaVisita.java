package interfaccia;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import entità.Biglietto;
import entità.SupportoMultimediale;
import entità.UtilizzoStrumento;
import entità.VisitaSingola;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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
import tabelle.BigliettoTab;
import tabelle.SupportoMultimedialeTab;
import tabelle.UtilizzoStrumentoTab;
import tabelle.VisitaSingolaTab;
import utilities.GenericUtilities;

public class SegnalaVisita {

    public static void getSegnalaVisita(Stage primaryStage, int codMuseo) {
        Stage biglietto = new Stage();
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,50,50,50));
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        
        HBox hb = new HBox();
        hb.setPadding(new Insets(0,20,20,30));
        
        Text text = new Text("Segnala Visita");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);
        
        hb.getChildren().add(text);
        
        //different start
        Label lb = new Label("Seleziona il biglietto");
        ComboBox<Integer> cb = new ComboBox<>();
        List<Biglietto> big = new BigliettoTab().getAllTable(codMuseo);
        CheckBox check = new CheckBox("Strumento");
        ComboBox<Integer> strum = new ComboBox<>();
        ComboBox<Integer> hh = new ComboBox<>();
        for(int i = 9; i<=16;i++) {
            hh.getItems().add(i);
        }
        hh.setValue(hh.getItems().get(0));
        List<SupportoMultimediale> sup = new SupportoMultimedialeTab().getAllTable(codMuseo);
        if (sup == null) {
            check.setDisable(true);
        } else {
            for (SupportoMultimediale s : sup) {
                strum.getItems().add(s.getCodStrumento());
            }
            strum.setValue(strum.getItems().get(0));
        }
        Button btnOk = new Button("Segnala");
        btnOk.setOnAction(ok -> {
            if (check.isSelected()) {
                if (new UtilizzoStrumentoTab().findByPrimaryKey(codMuseo, strum.getValue(), Date.valueOf(LocalDate.now()), hh.getValue())!=null) {
                    new Alert(Alert.AlertType.INFORMATION, "Strumento già assegnato!", ButtonType.CLOSE).showAndWait();
                } else {
                    new UtilizzoStrumentoTab().insertInDB(new UtilizzoStrumento(Date.valueOf(LocalDate.now()), 0, strum.getValue(), codMuseo,
                            hh.getValue()), cb.getValue());
                    new Alert(Alert.AlertType.INFORMATION, "Visita aggiunta con successo", ButtonType.CLOSE).showAndWait();
                    biglietto.close();
                }
            } else {
                new VisitaSingolaTab().insertInDB(new VisitaSingola(codMuseo, cb.getValue(),Date.valueOf(LocalDate.now()),0));
                new Alert(Alert.AlertType.INFORMATION, "Visita aggiunta con successo", ButtonType.CLOSE).showAndWait();
                biglietto.close();
            }
        });
        if (big!=null) {
            int i = 0;
            for (Biglietto b : big) {
                if (!GenericUtilities.hasVisited(b.getCodMuseo(), b.getCodBiglietto())) {
                    cb.getItems().add(b.getCodBiglietto());
                    i = 1;
                }
            }
            if (i == 1) {
                cb.setValue(cb.getItems().get(0));
            } else {
                btnOk.setDisable(true);
            }
        } else {
            btnOk.setDisable(true);
        }
        gridPane.add(lb, 0, 1);
        gridPane.add(cb, 1, 1);
        gridPane.add(check, 2, 0);
        gridPane.add(strum, 2, 1);
        gridPane.add(hh, 3, 1);
        //different end
        
        Button btnClose = new Button("Close");
        btnClose.setOnAction(close -> {
            biglietto.close();
        });
        
        GridPane bottomBox = new GridPane();
        bottomBox.setPadding(new Insets(20,20,20,30));
        bottomBox.setHgap(30);
        bottomBox.add(btnOk, 1, 0);
        bottomBox.add(btnClose, 0, 0);

        bp.setTop(hb);
        bp.setCenter(gridPane); 
        bp.setBottom(bottomBox);
        
        Scene scene = new Scene(bp);
        biglietto.setScene(scene);
        biglietto.sizeToScene();
        biglietto.setResizable(false);
        biglietto.showAndWait();
    }
}
