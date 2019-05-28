package interfaccia;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tabelle.AutoreTab;
import tabelle.BigliettoTab;
import tabelle.GuidaTab;
import tabelle.PermanenzaTab;
import tabelle.RestauroTab;
import tabelle.SezioneTab;
import tabelle.SupportoGuidaTab;
import tabelle.SupportoMultimedialeTab;
import tabelle.UtilizzoStrumentoTab;
import tabelle.VisitaSingolaTab;
import tabelle.VisitatoreTab;
import entità.Autore;
import entità.Biglietto;
import entità.Visitatore;
import entità.VisitaSingola;
import entità.SupportoMultimediale;
import entità.UtilizzoStrumento;
import entità.SupportoGuida;
import entità.Restauro;
import entità.Sezione;
import entità.Permanenza;

public class ResearchStage {

    public static void getResearchStage(Stage primaryStage) {
        
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,50,50,50));
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        
        Text text = new Text("Ricerca in tutti i Musei");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);
        
        HBox hb = new HBox();
        hb.setPadding(new Insets(0,20,20,30));
        hb.getChildren().add(text);
        
        GridPane bottomBox = new GridPane();
        bottomBox.setPadding(new Insets(20,20,20,30));
        bottomBox.setHgap(5);
        bottomBox.setVgap(5);
        
        Button execute = new Button("Esegui Ricerca");
        execute.setOnAction(execution -> {
            
        });
        execute.setDisable(true);
        
        Label lblMuseo = new Label("Tabella");
        ComboBox<String> comboBox1 = new ComboBox<String>();
        comboBox1.getItems().addAll("Visitatore", "Biglietti", "Staff", "Visite", "Strumenti", "Gruppi", "Restauri", "Opere");
        comboBox1.setValue("---");
        comboBox1.setOnAction(combo1 -> {
            primaryStage.sizeToScene();
            execute.setDisable(false);
            switch(comboBox1.getValue()) {
                case "Visitatore" :
                    execute.setOnAction(visiting -> {
                        List<Visitatore> visitatori = new VisitatoreTab().getAllTable();
                        ShowingStage.showStage(primaryStage, visitatori, ComingTable.VISITATORE);
                    });
                    break;
                case "Biglietti" :
                    comboBox1.setDisable(true);
                    Label box2 = new Label("Ricerca Per");
                    ComboBox<String> comboBox2 = new ComboBox<>();
                    gridPane.add(box2, 1, 0);
                    gridPane.add(comboBox2, 1, 1);
                    primaryStage.sizeToScene();
                    comboBox2.getItems().addAll("Data","Visitatore");
                    comboBox2.setValue("---");
                    execute.setOnAction(execution -> {
                        List<Biglietto> biglietti = new BigliettoTab().getAllTable();
                        ShowingStage.showStage(primaryStage, biglietti, ComingTable.BIGLIETTO);
                    });
                    comboBox2.setOnAction(combo2 -> {
                        comboBox2.setDisable(true);
                        primaryStage.sizeToScene();
                        switch (comboBox2.getValue()) {
                        case "Data" :
                            DatePicker dp = new DatePicker(LocalDate.now());
                            Label date = new Label("Dal");
                            Label date2 = new Label("Al");
                            DatePicker dp2 = new DatePicker(LocalDate.now());
                            gridPane.add(date, 2, 0);
                            gridPane.add(dp, 2, 1);
                            gridPane.add(date2, 3, 0);
                            gridPane.add(dp2, 3, 1);
                            primaryStage.sizeToScene();
                            execute.setOnAction(execution -> {
                                if (Date.valueOf(dp.getValue()).getTime() > Date.valueOf(dp2.getValue()).getTime()) {
                                    new Alert(AlertType.WARNING, "Intervallo di tempo inesistente!", ButtonType.CLOSE).showAndWait();
                                } else {
                                    List<Biglietto> biglietti = new BigliettoTab().bigliettiPerData(Date.valueOf(dp.getValue()),
                                            Date.valueOf(dp2.getValue()));
                                    ShowingStage.showStage(primaryStage, biglietti, ComingTable.BIGLIETTO);
                                }
                            });
                            break;
                        case "Visitatore" :
                            execute.setDisable(true);
                            List<Visitatore> visiteur = new LinkedList<>();
                            visiteur = new VisitatoreTab().getAllTable();
                            Label vis = new Label("Seleziona Visitatore");
                            ComboBox<String> visitor = new ComboBox<>();
                            if (visiteur != null) {
                                for(Visitatore visitatore : visiteur) {
                                    visitor.getItems().add(visitatore.getCodFiscale());
                                }
                                visitor.setValue("---");
                            } else {
                                visitor.setValue("None");
                            }
                            visitor.setOnAction(trimma -> {
                                execute.setDisable(false);
                                primaryStage.sizeToScene();
                            });
                            execute.setOnAction(execution -> {
                                List<Biglietto> biglietti = new BigliettoTab().bigliettiPerVisitatore(visitor.getValue());
                                ShowingStage.showStage(primaryStage, biglietti, ComingTable.BIGLIETTO);
                            });
                            gridPane.add(vis, 2, 0);
                            gridPane.add(visitor, 2, 1);
                            break;
                        }
                    });
                    break;
                case "Staff" :
                    comboBox1.setDisable(true);
                    Label box3 = new Label("Mansione");
                    ComboBox<String> comboBox3 = new ComboBox<>();
                    gridPane.add(box3, 1, 0);
                    gridPane.add(comboBox3, 1, 1);
                    comboBox3.getItems().addAll("Tutte","Guida", "Restauratore");
                    comboBox3.setValue("---");
                    primaryStage.sizeToScene();
                    execute.setDisable(true);
                    comboBox3.setOnAction(event3 -> {
                        primaryStage.sizeToScene();
                        comboBox3.setDisable(true);
                        execute.setDisable(false);
                        Label box4 = new Label("Status");
                        CheckBox chAss = new CheckBox("Assunto");
                        CheckBox chLic = new CheckBox("Licenziato");
                        gridPane.add(box4, 2, 0);
                        gridPane.add(chAss, 2, 1);
                        gridPane.add(chLic, 3, 1);
                        primaryStage.sizeToScene();
                        execute.setOnAction(lavoro -> {
                            if (chAss.isSelected() && chLic.isSelected()) {
                                switch (comboBox3.getValue()) {
                                case "Tutte":
                                    List<Integer> dummy = new LinkedList<>();
                                    dummy.add(0);
                                    ShowingStage.showStage(primaryStage, dummy, ComingTable.STAFF);
                                    break;
                                case "Guida":
                                    List<Integer> dummy2 = new LinkedList<>();
                                    dummy2.add(0);
                                    ShowingStage.showStage(primaryStage, dummy2, ComingTable.GUIDA);
                                    break;
                                case "Restauratore":
                                    List<Integer> dummy3 = new LinkedList<>();
                                    dummy3.add(0);
                                    ShowingStage.showStage(primaryStage, dummy3, ComingTable.RESTAURATORE);
                                    break;
                                }
                            } else if (chAss.isSelected() && !chLic.isSelected()) {
                                switch (comboBox3.getValue()) {
                                case "Tutte":
                                    List<Integer> dummy = new LinkedList<>();
                                    dummy.add(1);
                                    ShowingStage.showStage(primaryStage, dummy, ComingTable.STAFF);
                                    break;
                                case "Guida":
                                    List<Integer> dummy2 = new LinkedList<>();
                                    dummy2.add(1);
                                    ShowingStage.showStage(primaryStage, dummy2, ComingTable.GUIDA);
                                    break;
                                case "Restauratore":
                                    List<Integer> dummy3 = new LinkedList<>();
                                    dummy3.add(1);
                                    ShowingStage.showStage(primaryStage, dummy3, ComingTable.RESTAURATORE);
                                    break;
                                }
                            } else if (!chAss.isSelected() && chLic.isSelected()) {
                                switch (comboBox3.getValue()) {
                                case "Tutte":
                                    List<Integer> dummy = new LinkedList<>();
                                    dummy.add(2);
                                    ShowingStage.showStage(primaryStage, dummy, ComingTable.STAFF);
                                    break;
                                case "Guida":
                                    List<Integer> dummy2 = new LinkedList<>();
                                    dummy2.add(2);
                                    ShowingStage.showStage(primaryStage, dummy2, ComingTable.GUIDA);
                                    break;
                                case "Restauratore":
                                    List<Integer> dummy3 = new LinkedList<>();
                                    dummy3.add(2);
                                    ShowingStage.showStage(primaryStage, dummy3, ComingTable.RESTAURATORE);
                                    break;
                                }
                            } else {
                                new Alert(AlertType.WARNING, "Seleziona almeno uno dei due status lavorativi!", ButtonType.CLOSE).showAndWait();
                            }
                        });
                    });
                    break;
                case "Visite" :
                    comboBox1.setDisable(true);
                    Label box5 = new Label("Ricerca Per");
                    ComboBox<String> comboBox5 = new ComboBox<>();
                    gridPane.add(box5, 1, 0);
                    gridPane.add(comboBox5, 1, 1);
                    comboBox5.getItems().addAll("Tutti", "Data");
                    comboBox5.setValue("---");
                    execute.setDisable(true);
                    comboBox5.setOnAction(event5 -> {
                        comboBox5.setDisable(true);
                        switch(comboBox5.getValue()) {
                        case "Tutti" :
                            execute.setDisable(false);
                            execute.setOnAction(azionati -> {
                                List<VisitaSingola> visite = new VisitaSingolaTab().getAllTable();
                                ShowingStage.showStage(primaryStage, visite, ComingTable.VISITA_SINGOLA);
                            });
                            break;
                        case "Data" :
                            execute.setDisable(false);
                            DatePicker dp = new DatePicker(LocalDate.now());
                            Label date = new Label("Dal");
                            Label date2 = new Label("Al");
                            DatePicker dp2 = new DatePicker(LocalDate.now());
                            gridPane.add(date, 2, 0);
                            gridPane.add(dp, 2, 1);
                            gridPane.add(date2, 3, 0);
                            gridPane.add(dp2, 3, 1);
                            primaryStage.sizeToScene();
                            execute.setOnAction(azionati -> {
                                if (Date.valueOf(dp.getValue()).getTime() > Date.valueOf(dp2.getValue()).getTime()) {
                                    new Alert(AlertType.WARNING, "Intervallo di tempo inesistente!", ButtonType.CLOSE).showAndWait();
                                } else {
                                    List<VisitaSingola> visite = new VisitaSingolaTab().visiteSingolePerData(Date.valueOf(dp.getValue()), 
                                            Date.valueOf(dp2.getValue()));
                                    ShowingStage.showStage(primaryStage, visite, ComingTable.VISITA_SINGOLA);
                                }
                            });
                            break;
                        }
                    });
                    break;
                case "Strumenti" :
                    ToggleGroup strume = new ToggleGroup();
                    RadioButton rad = new RadioButton("Dispositivi");
                    RadioButton uti = new RadioButton("Utilizzo");
                    rad.setSelected(true);
                    rad.setToggleGroup(strume);
                    uti.setToggleGroup(strume);
                    gridPane.add(rad, 1, 1);
                    gridPane.add(uti, 2, 1);
                    execute.setOnAction(strumentazione -> {
                        List<SupportoMultimediale> supMul = new SupportoMultimedialeTab().getAllTable();
                        ShowingStage.showStage(primaryStage, supMul, ComingTable.SUPPORTO_MULTIMEDIALE);
                    });
                    uti.setOnAction(utii -> {
                        rad.setDisable(true);
                        uti.setDisable(true);
                        DatePicker dp = new DatePicker(LocalDate.now());
                        Label date = new Label("Dal");
                        Label date2 = new Label("Al");
                        DatePicker dp2 = new DatePicker(LocalDate.now());
                        gridPane.add(date, 3, 0);
                        gridPane.add(date2, 4, 0);
                        gridPane.add(dp, 3, 1);
                        gridPane.add(dp2, 4, 1);
                        primaryStage.sizeToScene();
                        execute.setOnAction(strumentazione -> {
                            if (Date.valueOf(dp.getValue()).getTime() > Date.valueOf(dp2.getValue()).getTime()) {
                                new Alert(AlertType.WARNING, "Intervallo di tempo inesistente!", ButtonType.CLOSE).showAndWait();
                            } else {
                                List<UtilizzoStrumento> supMul = new UtilizzoStrumentoTab().getAllTable(Date.valueOf(dp.getValue()),
                                        Date.valueOf(dp2.getValue()));
                                ShowingStage.showStage(primaryStage, supMul, ComingTable.UTILIZZO_STRUMENTO);
                            }
                        });
                    });
                    break;
                case "Gruppi" :
                    comboBox1.setDisable(true);
                    Label box6 = new Label("Ricerca Per    ");
                    CheckBox chAss = new CheckBox("Guida");
                    CheckBox chLic = new CheckBox("Data");
                    ComboBox<String> cmb = new ComboBox<>();
                    DatePicker dp = new DatePicker(LocalDate.now());
                    DatePicker dp2 = new DatePicker(LocalDate.now());
                    List<String> rest = new GuidaTab().getGuideConSupporti();
                    if (rest!=null) {
                        cmb.getItems().addAll(rest);
                        cmb.setValue(cmb.getItems().get(0));
                    } else {
                        cmb.setValue("---");
                        chAss.setDisable(true);
                        System.out.println("AUZ");
                    }
                    gridPane.add(box6, 1, 0);
                    gridPane.add(chAss, 2, 0);
                    gridPane.add(chLic, 3, 0);
                    gridPane.add(cmb, 2, 1);
                    gridPane.add(dp, 3, 1);
                    gridPane.add(dp2, 3, 2);
                    primaryStage.sizeToScene();
                    execute.setOnAction(grupp -> {
                        List<SupportoGuida> sup;
                        if (chAss.isSelected() && chLic.isSelected()) {
                            if (Date.valueOf(dp.getValue()).getTime() > Date.valueOf(dp2.getValue()).getTime()) {
                                new Alert(AlertType.WARNING, "Intervallo di tempo inesistente!", ButtonType.CLOSE).showAndWait();
                            } else {
                                String[] splitted = cmb.getValue().split("\\s+");
                                sup = new SupportoGuidaTab().getTabPerGruppi(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]),
                                        Date.valueOf(dp.getValue()), Date.valueOf(dp2.getValue()));
                                ShowingStage.showStage(primaryStage, sup, ComingTable.SUPPORTO_GUIDA);
                            }
                        } else if (chAss.isSelected() && !chLic.isSelected()) {
                            String[] splitted = cmb.getValue().split("\\s+");
                            sup = new SupportoGuidaTab().getTabPerGruppi(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
                            ShowingStage.showStage(primaryStage, sup, ComingTable.SUPPORTO_GUIDA);
                        } else if (!chAss.isSelected() && chLic.isSelected()) {
                            if (Date.valueOf(dp.getValue()).getTime() > Date.valueOf(dp2.getValue()).getTime()) {
                                new Alert(AlertType.WARNING, "Intervallo di tempo inesistente!", ButtonType.CLOSE).showAndWait();
                            } else {
                                sup = new SupportoGuidaTab().getTabPerGruppi(Date.valueOf(dp.getValue()), Date.valueOf(dp2.getValue()));
                                ShowingStage.showStage(primaryStage, sup, ComingTable.SUPPORTO_GUIDA);
                            }
                        } else {
                            sup = new SupportoGuidaTab().getTabPerGruppi();
                            ShowingStage.showStage(primaryStage, sup, ComingTable.SUPPORTO_GUIDA);
                        }
                    });
                    break;
                case "Restauri" :
                    comboBox1.setDisable(true);
                    Label box9 = new Label("Mansione");
                    ComboBox<String> comboBox9 = new ComboBox<>();
                    gridPane.add(box9, 1, 0);
                    gridPane.add(comboBox9, 1, 1);
                    comboBox9.getItems().addAll("Tutte","In Corso", "Effettuate");
                    comboBox9.setValue("---");
                    primaryStage.sizeToScene();
                    execute.setDisable(true);
                    comboBox9.setOnAction(actione -> {
                        execute.setDisable(false);
                        execute.setOnAction(execui -> {
                            switch(comboBox9.getValue()) {
                            case "Tutte" :
                                List<Restauro> resty = new RestauroTab().getAllTable(0);
                                ShowingStage.showStage(primaryStage, resty, ComingTable.RESTAURO);
                                break;
                            case "In Corso" :
                                List<Restauro> resty0 = new RestauroTab().getAllTable(1);
                                ShowingStage.showStage(primaryStage, resty0, ComingTable.RESTAURO);
                                break;
                            case "Effettuate" :
                                List<Restauro> resty1 = new RestauroTab().getAllTable(2);
                                ShowingStage.showStage(primaryStage, resty1, ComingTable.RESTAURO);
                                break;
                            }
                        });
                    });
                    break;
                case "Opere" :
                    comboBox1.setDisable(true);
                    Label box8 = new Label("Ricerca Per");
                    ComboBox<String> comboBox8 = new ComboBox<>();
                    gridPane.add(box8, 1, 0);
                    gridPane.add(comboBox8, 1, 1);
                    comboBox8.getItems().addAll("Tutte","Sezione","Autore");
                    comboBox8.setValue("---");
                    primaryStage.sizeToScene();
                    execute.setDisable(true);
                    comboBox8.setOnAction(actione -> {
                        comboBox8.setDisable(true);
                        switch(comboBox8.getValue()) {
                        case "Tutte" :
                            execute.setDisable(false);
                            execute.setOnAction(esegua -> {
                                List<Permanenza> resty = new PermanenzaTab().getAllTable(0);
                                ShowingStage.showStage(primaryStage, resty, ComingTable.OPERA);
                            });
                            break;
                        case "Sezione" :
                            Label see = new Label("Sezione");
                            ComboBox<String> cb = new ComboBox<>();
                            gridPane.add(see, 2, 0);
                            gridPane.add(cb, 2, 1);
                            List<Sezione> sez = new SezioneTab().getAllTable();
                            if (sez!=null) {
                                for (Sezione sezione : sez) {
                                    cb.getItems().add("" + sezione.getNomeSezione());
                                }
                                cb.setValue(cb.getItems().get(0));
                                execute.setDisable(false);
                            } else {
                                cb.setValue("---");
                            }
                            execute.setOnAction(esegua -> {
                                List<Permanenza> resty0 = new PermanenzaTab().getAllTableBySez(cb.getValue());
                                ShowingStage.showStage(primaryStage, resty0, ComingTable.OPERA);
                            });
                            break;
                        case "Autore" :
                            Label auu = new Label("Autore");
                            ComboBox<String> cba = new ComboBox<>();
                            gridPane.add(auu, 2, 0);
                            gridPane.add(cba, 2, 1);
                            List<Autore> aut = new AutoreTab().getAllTable();
                            if (aut!=null) {
                                for(Autore autore : aut) {
                                    cba.getItems().add("" + autore.getCodAutore());
                                }
                                cba.setValue(cba.getItems().get(0));
                                execute.setDisable(false);
                            } else {
                                cba.setValue("---");
                            }
                            execute.setOnAction(esegua ->{
                                List<Permanenza> resty1 = new PermanenzaTab().getAllTable(Integer.parseInt(cba.getValue()));
                                ShowingStage.showStage(primaryStage, resty1, ComingTable.OPERA);});
                            break;
                        }
                    });
                    break;
            }
        });
        
        gridPane.add(lblMuseo, 0, 0);
        gridPane.add(comboBox1, 0, 1);
        
        Button btnBack = new Button("<- Back");
        btnBack.setOnAction(e -> {
            AllMuseums.getAllMuseums(primaryStage);
        });
        
        Button reset = new Button("Resetta Campi");
        reset.setOnAction(resetting -> {
            ResearchStage.getResearchStage(primaryStage);
        });
        bottomBox.add(btnBack, 0, 0);
        bottomBox.add(reset, 1, 0);
        bottomBox.add(execute, 2, 0);
        
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
