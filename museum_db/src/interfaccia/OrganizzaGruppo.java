package interfaccia;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entità.Guida;
import entità.VisitaGruppo;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tabelle.GuidaTab;
import tabelle.SupportoGuidaTab;
import tabelle.VisitaGruppoTab;

public class OrganizzaGruppo {

    public static void getOrganizzaGruppo(Stage primaryStage, int codMuseo) {
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
        
        Text text = new Text("Gestione Gruppi");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);
        
        hb.getChildren().add(text);
        
        //different start
        
        List<Guida> ls = new GuidaTab().getAllTable(codMuseo);
        Map<Integer, String> map = new HashMap<>();
        if (ls!=null) {
            for(Guida g : ls) {
                map.put(g.getNumeroBadge(),g.getCodFiscale());
            }
        }
        Button execute = new Button("Organizza");
        execute.setDisable(true);
        Label il = new Label("Il");
        DatePicker dp = new DatePicker();
        LocalDate ld = LocalDate.now();
        dp.setOnAction(ecco -> {
            dp.setDisable(true);
            Label alle = new Label("Alle");
            ComboBox<Integer> hh = new ComboBox<>();
            gridPane.add(alle, 1, 0);
            gridPane.add(hh, 1, 1);
            hh.setOnAction(e -> {
                hh.setDisable(true);
                biglietto.sizeToScene();
                Label guida = new Label("Guida");
                ComboBox<Integer> guide = new ComboBox<>();
                guide.setOnAction(ssss -> {
                    biglietto.sizeToScene();
                });
                gridPane.add(guida, 2, 0);
                gridPane.add(guide, 2, 1);
                if (ls!=null) {
                    for (Guida g : ls) {
                        guide.getItems().add(g.getNumeroBadge());
                    }
                    execute.setDisable(false);
                    execute.setOnAction(organ -> {
                        if (dp.getValue().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                                > ld.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()) {
                            if (new SupportoGuidaTab().findByPrimaryKey(map.get(guide.getValue()), Date.valueOf(dp.getValue()), hh.getValue())==null) {
                                new VisitaGruppoTab().insertInDB(new VisitaGruppo(map.get(guide.getValue()), Date.valueOf(dp.getValue()),
                                        hh.getValue(), 0, codMuseo));
                                new Alert(Alert.AlertType.INFORMATION, "Gruppo creato!",ButtonType.OK).showAndWait();
                                biglietto.close();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Guida già impegnata in quell'orario!",ButtonType.OK).showAndWait();
                            }
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Scegli una data Futura!",ButtonType.OK).showAndWait();
                        }
                    });
                } else {
                    
                }
                
            });
            for(int i = 9; i<=16;i++) {
                hh.getItems().add(i);
            }
        });
        
        
        gridPane.add(il, 0, 0);
        gridPane.add(dp, 0, 1);
        
        //different end
        
        Button btnClose = new Button("Close");
        btnClose.setOnAction(close -> {
            biglietto.close();
        });
        
        Button btnReset = new Button("Reset");
        btnReset.setOnAction(resetta -> {
            OrganizzaGruppo.getOrganizzaGruppo(primaryStage, codMuseo);
        });
        
        GridPane bottomBox = new GridPane();
        bottomBox.setPadding(new Insets(20,20,20,30));
        bottomBox.setHgap(30);
        bottomBox.add(btnClose, 0, 0);
        bottomBox.add(btnReset, 1, 0);
        bottomBox.add(execute, 2, 0);

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
