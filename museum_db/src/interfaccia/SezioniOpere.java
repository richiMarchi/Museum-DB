package interfaccia;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import entità.Autore;
import entità.Opera;
import entità.Permanenza;
import entità.Sezione;
import entità.Tipologia;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tabelle.AutoreTab;
import tabelle.OperaTab;
import tabelle.PermanenzaTab;
import tabelle.SezioneTab;
import tabelle.TipologiaTab;
import utilities.GenericUtilities;

public class SezioniOpere {

    public static void getSezioniOpere(Stage primaryStage, int codMuseo) {
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
        
        Text text = new Text("Sezionie e Opere");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);
        
        hb.getChildren().add(text);
        Button sez = new Button("Aggiungi Sezione");
        sez.setOnAction(seziona -> {
            Stage aggiungiMuseo = new Stage();
            
            GridPane gp = new GridPane();
            gp.setPadding(new Insets(20,20,20,20));
            gp.setHgap(5);
            gp.setVgap(5);
            
            Label lblNome = new Label("Nome");
            final TextField txtNome = new TextField();
            GenericUtilities.addTextLimiter(txtNome,20);
            Label lblCittà = new Label("Piano");
            final TextField txtCittà = new TextField();
            GenericUtilities.addTextLimiter(txtCittà,2);
            final Label message = new Label();
            
            Button insert = new Button("Aggiungi");
            insert.setOnAction(ev -> {
                if (!txtNome.getText().toString().equals("") && !txtCittà.getText().toString().equals("")) {
                    new SezioneTab().insertInDB(new Sezione(codMuseo, txtNome.getText().toString(), Integer.parseInt(txtCittà.getText())));
                    AllMuseums.getAllMuseums(primaryStage);
                    aggiungiMuseo.close();
                } else {
                    message.setText("Riempi tutti i campi!");
                }
            });
            BorderPane.setMargin(insert, new Insets(0,120,0,120));
            
            Button btnClose = new Button("Chiudi");
            btnClose.setOnAction(chiuda -> {
                aggiungiMuseo.close();
            });
            GridPane bottomBox = new GridPane();
            bottomBox.setPadding(new Insets(20,20,20,30));
            bottomBox.setHgap(5);
            bottomBox.setVgap(5);
            bottomBox.add(btnClose, 0, 0);
            bottomBox.add(insert, 1, 0);
            
            gp.add(lblNome, 0, 0);
            gp.add(txtNome, 1, 0);
            gp.add(lblCittà, 0, 1);
            gp.add(txtCittà, 1, 1);
            gp.add(message, 1, 2);
            
            BorderPane bordPane = new BorderPane();
            bordPane.setPadding(new Insets(10,50,50,50));
            bordPane.setTop(gp);
            bordPane.setBottom(bottomBox);
            
            Scene sceneAggMuseo = new Scene(bordPane);
            aggiungiMuseo.setScene(sceneAggMuseo);
            aggiungiMuseo.sizeToScene();
            aggiungiMuseo.setResizable(false);
            aggiungiMuseo.showAndWait();
        });
        Button btnClose = new Button("Chiudi");
        btnClose.setOnAction(close -> {
            biglietto.close();
        });
        
        Button autore = new Button("Aggiungi Autore");
        autore.setOnAction(resetta -> {
            Stage aggiungiMuseo = new Stage();
            
            GridPane gp = new GridPane();
            gp.setPadding(new Insets(20,20,20,20));
            gp.setHgap(5);
            gp.setVgap(5);
            
            Label lblNome = new Label("Nome Cognome");
            final TextField txtNome = new TextField();
            GenericUtilities.addTextLimiter(txtNome,20);
            Label lblPse = new Label("Presudonimo");
            final TextField txtPse = new TextField();
            GenericUtilities.addTextLimiter(txtPse,20);
            final Label message = new Label();
            
            Button insert = new Button("Aggiungi");
            insert.setOnAction(ev -> {
                if (!txtNome.getText().toString().equals("")) {
                    String s;
                    if (txtPse.getText().equals("")) {
                        s = null;
                    } else {
                        s = txtPse.getText();
                    }
                    new AutoreTab().insertInDB(new Autore(0, txtNome.getText(), s));
                    new Alert(Alert.AlertType.INFORMATION, "Autore aggiunto con successo!", ButtonType.OK).showAndWait();
                    aggiungiMuseo.close();
                } else {
                    message.setText("Riempi tutti i campi!");
                }
            });
            BorderPane.setMargin(insert, new Insets(0,120,0,120));
            
            gp.add(lblNome, 0, 0);
            gp.add(txtNome, 1, 0);
            gp.add(lblPse, 0, 1);
            gp.add(txtPse, 1, 1);
            gp.add(message, 1, 2);
            
            Button btnClose1 = new Button("Chiudi");
            btnClose1.setOnAction(chiuda -> {
                aggiungiMuseo.close();
            });
            GridPane bottomBox = new GridPane();
            bottomBox.setPadding(new Insets(20,20,20,30));
            bottomBox.setHgap(5);
            bottomBox.setVgap(5);
            bottomBox.add(btnClose1, 0, 0);
            bottomBox.add(insert, 1, 0);
            
            BorderPane bordPane = new BorderPane();
            bordPane.setPadding(new Insets(10,50,50,50));
            bordPane.setTop(gp);
            bordPane.setBottom(bottomBox);
            
            Scene sceneAggMuseo = new Scene(bordPane);
            aggiungiMuseo.setScene(sceneAggMuseo);
            aggiungiMuseo.sizeToScene();
            aggiungiMuseo.setResizable(false);
            aggiungiMuseo.showAndWait();
        });
        
        Button op = new Button("Aggiungi Opera");
        op.setOnAction(operare -> {
            Stage aggiungiMuseo = new Stage();
            
            GridPane gp = new GridPane();
            gp.setPadding(new Insets(20,20,20,20));
            gp.setHgap(5);
            gp.setVgap(5);
            
            Button insert = new Button("Aggiungi");
            
            Label lblAutore = new Label("Autore");
            final ComboBox<Integer> txtAutore = new ComboBox<>();
            List<Autore> aut = new AutoreTab().getAllTable();
            if (aut!=null) {
                for (Autore a : aut) {
                    txtAutore.getItems().add(a.getCodAutore());
                }
                txtAutore.setValue(txtAutore.getItems().get(0));
            } else {
                insert.setDisable(true);
            }
            Label lblNome = new Label("Nome Opera");
            final TextField txtNome = new TextField();
            GenericUtilities.addTextLimiter(txtNome,20);
            Label lblTipo = new Label("Nome Tipologia");
            final ComboBox<String> txtTipo = new ComboBox<>();
            List<Tipologia> tip = new TipologiaTab().getAllTable();
            if (tip!=null) {
                for (Tipologia a : tip) {
                    txtTipo.getItems().add(a.getNomeTipologia());
                }
                txtTipo.setValue(txtTipo.getItems().get(0));
            } else {
                insert.setDisable(true);
            }
            Label lblAnno = new Label("Anno");
            final TextField txtAnno = new TextField();
            GenericUtilities.addTextLimiter(txtAnno,20);
            Label lblSez = new Label("Sezione");
            ComboBox<String> sezz = new ComboBox<>();
            List<Sezione> sezioni = new SezioneTab().getAllTable(codMuseo);
            if (sezioni!=null) {
                for (Sezione a : sezioni) {
                    sezz.getItems().add(a.getNomeSezione());
                }
                sezz.setValue(sezz.getItems().get(0));
            } else {
                insert.setDisable(true);
            }
            final Label message = new Label();
            
            
            insert.setOnAction(ev -> {
                if (!txtNome.getText().toString().equals("") && !txtAnno.getText().toString().equals("")) {
                    new OperaTab().insertInDB(new Opera(txtAutore.getValue(), txtTipo.getValue(), txtNome.getText(),
                            Integer.parseInt(txtAnno.getText())));
                    new PermanenzaTab().insertInDB(new Permanenza(codMuseo, sezz.getValue(), txtAutore.getValue(), txtNome.getText(),
                            Date.valueOf(LocalDate.now()), null));
                    new Alert(Alert.AlertType.INFORMATION, "Opera aggiunta con successo!", ButtonType.OK).showAndWait();
                    aggiungiMuseo.close();
                } else {
                    message.setText("Riempi tutti i campi!");
                }
            });
            
            
            BorderPane.setMargin(insert, new Insets(0,120,0,120));
            
            Button btnClose1 = new Button("Chiudi");
            btnClose1.setOnAction(chiuda -> {
                aggiungiMuseo.close();
            });
            GridPane bottomBox = new GridPane();
            bottomBox.setPadding(new Insets(20,20,20,30));
            bottomBox.setHgap(5);
            bottomBox.setVgap(5);
            bottomBox.add(btnClose1, 0, 0);
            bottomBox.add(insert, 1, 0);
            
            gp.add(lblAutore, 0, 0);
            gp.add(txtAutore, 1, 0);
            gp.add(lblNome, 0, 1);
            gp.add(txtNome, 1, 1);
            gp.add(lblTipo, 0, 2);
            gp.add(txtTipo, 1, 2);
            gp.add(message, 1, 5);
            gp.add(txtAnno, 1, 3);
            gp.add(lblAnno, 0, 3);
            gp.add(lblSez, 0, 4);
            gp.add(sezz, 1, 4);
            
            BorderPane bordPane = new BorderPane();
            bordPane.setPadding(new Insets(10,50,50,50));
            bordPane.setTop(gp);
            bordPane.setBottom(bottomBox);
            
            Scene sceneAggMuseo = new Scene(bordPane);
            aggiungiMuseo.setScene(sceneAggMuseo);
            aggiungiMuseo.sizeToScene();
            aggiungiMuseo.setResizable(false);
            aggiungiMuseo.showAndWait();
        });
        
        Button tipo = new Button("Aggiungi Tipologia");
        tipo.setOnAction(tipi -> {
            Stage aggiungiMuseo = new Stage();
            
            GridPane gp = new GridPane();
            gp.setPadding(new Insets(20,20,20,20));
            gp.setHgap(5);
            gp.setVgap(5);
            
            Button insert = new Button("Aggiungi");
            
            Label lblNome = new Label("Nome Tipologia");
            final TextField txtNome = new TextField();
            GenericUtilities.addTextLimiter(txtNome,20);
            Label message = new Label();
            
            
            insert.setOnAction(ev -> {
                if (!txtNome.getText().toString().equals("")) {
                    if (new TipologiaTab().findByPrimaryKey(txtNome.getText())==null) {
                        new TipologiaTab().insertInDB(new Tipologia(txtNome.getText()));
                        new Alert(Alert.AlertType.INFORMATION, "Opera aggiunta con successo!", ButtonType.OK).showAndWait();
                        aggiungiMuseo.close();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Tipologia già presente!", ButtonType.OK).showAndWait();
                    }
                } else {
                    message.setText("Riempi tutti i campi!");
                }
            });
            BorderPane.setMargin(insert, new Insets(0,120,0,120));
            
            Button btnClose1 = new Button("Chiudi");
            btnClose1.setOnAction(chiuda -> {
                aggiungiMuseo.close();
            });
            GridPane bottomBox = new GridPane();
            bottomBox.setPadding(new Insets(20,20,20,30));
            bottomBox.setHgap(5);
            bottomBox.setVgap(5);
            bottomBox.add(btnClose1, 0, 0);
            bottomBox.add(insert, 1, 0);
            
            
            gp.add(lblNome, 0, 0);
            gp.add(txtNome, 1, 0);
            gp.add(message, 1, 1);
            
            BorderPane bordPane = new BorderPane();
            bordPane.setPadding(new Insets(10,50,50,50));
            bordPane.setTop(gp);
            bordPane.setBottom(bottomBox);
            
            Scene sceneAggMuseo = new Scene(bordPane);
            aggiungiMuseo.setScene(sceneAggMuseo);
            aggiungiMuseo.sizeToScene();
            aggiungiMuseo.setResizable(false);
            aggiungiMuseo.showAndWait();
        });
        
        Button rimuovi = new Button("Rimuovi Opera");
        rimuovi.setOnAction(removee -> {
            Stage removeOpera = new Stage();
            
            GridPane gp1 = new GridPane();
            gp1.setPadding(new Insets(20,20,20,20));
            gp1.setHgap(5);
            gp1.setVgap(5);
            
            Button remove = new Button("Rimuovi");
            
            ComboBox<String> cbRem = new ComboBox<>();
            List<Permanenza> ops = new PermanenzaTab().getRemovableOpere(codMuseo);
            if (ops!=null) {
                for (Permanenza a : ops) {
                    cbRem.getItems().add("" + a.getCodAutore() + " " + a.getNomeOpera());
                }
                cbRem.setValue(cbRem.getItems().get(0));
            } else {
                remove.setDisable(true);
            }
            
            
            remove.setOnAction(ev -> {
                String[] splitted = cbRem.getValue().split(" ", 2);
                new PermanenzaTab().removeOpera(codMuseo, Integer.parseInt(splitted[0]), splitted[1]);
                new Alert(Alert.AlertType.INFORMATION, "Opera rimossa con successo!", ButtonType.OK).showAndWait();
                removeOpera.close();
            });
            BorderPane.setMargin(remove, new Insets(0,120,0,120));
            
            gp1.add(cbRem, 0, 0);
            
            Button btnClose1 = new Button("Chiudi");
            btnClose1.setOnAction(chiuda -> {
                removeOpera.close();
            });
            GridPane bottomBox = new GridPane();
            bottomBox.setPadding(new Insets(20,20,20,30));
            bottomBox.setHgap(5);
            bottomBox.setVgap(5);
            bottomBox.add(btnClose1, 0, 0);
            bottomBox.add(remove, 1, 0);
            
            BorderPane bordPane1 = new BorderPane();
            bordPane1.setPadding(new Insets(10,50,50,50));
            bordPane1.setTop(gp1);
            bordPane1.setBottom(bottomBox);
            
            Scene sceneAggMuseo1 = new Scene(bordPane1);
            removeOpera.setTitle("Rimuovi Opera");
            removeOpera.setScene(sceneAggMuseo1);
            removeOpera.sizeToScene();
            removeOpera.setResizable(false);
            removeOpera.showAndWait();
        });
        
        gridPane.add(sez, 0, 0);
        gridPane.add(autore, 1, 0);
        gridPane.add(op, 3, 0);
        gridPane.add(tipo, 2, 0);
        gridPane.add(rimuovi, 4, 0);
        
        GridPane bottomBox = new GridPane();
        bottomBox.setPadding(new Insets(20,20,20,30));
        bottomBox.setHgap(30);
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
    
    public static void getOpere(int codMuseo) {
        Stage stage1 = new Stage();
        GridPane gp1 = new GridPane();
        //gp1.setGridLinesVisible(true);
        gp1.setPadding(new Insets(20,20,20,20));
        gp1.setHgap(50);
        gp1.setVgap(10);
        
        Text o1 = new Text("Museo");
        o1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text o2 = new Text("Sezione");
        o2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text o3 = new Text("Autore");
        o3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text o4 = new Text("Nome");
        o4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text o5 = new Text("Tipologia");
        o5.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text o6 = new Text("Anno");
        o6.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text o7 = new Text("Data Inserimento");
        o7.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        Text o8 = new Text("Data Rimozione");
        o8.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        gp1.add(o1, 0, 0);
        gp1.add(o2, 1, 0);
        gp1.add(o3, 2, 0);
        gp1.add(o4, 3, 0);
        gp1.add(o5, 4, 0);
        gp1.add(o6, 5, 0);
        gp1.add(o7, 6, 0);
        gp1.add(o8, 7, 0);
        
        List<Permanenza> elements = new PermanenzaTab().getAllTableByMuseo(codMuseo);
        
        if (elements != null) {
            int i = 1;
            Iterator<Permanenza> itBig = (Iterator<Permanenza>) elements.iterator();
            while(itBig.hasNext()) {
                Permanenza big = itBig.next();
                gp1.add(new Text("" + big.getCodMuseo()), 0, i);
                gp1.add(new Text("" + big.getNomeSezione()), 1, i);
                gp1.add(new Text("" + big.getCodAutore()), 2, i);
                gp1.add(new Text("" + big.getNomeOpera()), 3, i);
                gp1.add(new Text("" + big.getTipologia()), 4, i);
                gp1.add(new Text("" + big.getAnno()), 5, i);
                gp1.add(new Text("" + big.getDataInserimento()), 6, i);
                if (big.getDataRimozione()!=null) {
                    gp1.add(new Text("" + big.getDataRimozione()), 7, i);
                } else {
                    gp1.add(new Text("N/A"), 7, i);
                }
                i++;
            }
            Text vs4 = new Text("Totale Opere: " + (i-1));
            vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(vs4, 1, i+1);
        } else {
            gp1.add(new Text("/"), 0, 1);
            gp1.add(new Text("/"), 1, 1);
            gp1.add(new Text("/"), 2, 1);
            gp1.add(new Text("/"), 3, 1);
            gp1.add(new Text("/"), 4, 1);
            gp1.add(new Text("/"), 5, 1);
            gp1.add(new Text("/"), 6, 1);
            gp1.add(new Text("/"), 7, 1);
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
        stage1.setTitle("Aggiungi Museo");
        stage1.setScene(scene1);
        stage1.sizeToScene();
        stage1.setResizable(false);
        stage1.showAndWait();
    }
}
