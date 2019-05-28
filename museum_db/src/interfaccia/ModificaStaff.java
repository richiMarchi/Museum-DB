package interfaccia;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import entità.Guida;
import entità.Restauratore;
import entità.Personale;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tabelle.GuidaTab;
import tabelle.RestauratoreTab;
import utilities.GenericUtilities;

public class ModificaStaff {

    public static void getModificaStaff(Stage primaryStage, int codMuseo) {
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
        
        Text text = new Text("Modifica Staff");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);
        
        hb.getChildren().add(text);
        
        //different start
        Button add = new Button("Assunzione");
        add.setOnAction(assumi -> {
            Stage biglietto1 = new Stage();
            BorderPane bp1 = new BorderPane();
            bp1.setPadding(new Insets(10,50,50,50));
        
            GridPane gridPane1 = new GridPane();
            gridPane1.setPadding(new Insets(20,20,20,20));
            gridPane1.setHgap(5);
            gridPane1.setVgap(5);
            
            DropShadow dropShadow1 = new DropShadow();
            dropShadow1.setOffsetX(5);
            dropShadow1.setOffsetY(5);
            
            HBox hb1 = new HBox();
            hb1.setPadding(new Insets(0,20,20,30));
            
            Text text1 = new Text("Assunzione");
            text1.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
            text1.setEffect(dropShadow);
            
            hb1.getChildren().add(text1);
            
            //different start
            Label mansione = new Label("Mansione");
            ToggleGroup strume = new ToggleGroup();
            RadioButton rad = new RadioButton("Guida");
            RadioButton uti = new RadioButton("Restauratore");
            rad.setSelected(true);
            rad.setToggleGroup(strume);
            uti.setToggleGroup(strume);
            gridPane1.add(mansione, 0, 0);
            gridPane1.add(rad, 1, 0);
            gridPane1.add(uti, 2, 0);
            Label lblNome = new Label("Nome");
            final TextField txtNome = new TextField();
            GenericUtilities.addTextLimiter(txtNome,20);
            Label lblCognome = new Label("Cognome");
            final TextField txtCognome = new TextField();
            GenericUtilities.addTextLimiter(txtCognome,20);
            Label lblCodFiscale = new Label("Codice Fiscale");
            final TextField txtCodFiscale = new TextField();
            GenericUtilities.addTextLimiter(txtCodFiscale,16);
            Label lblMail = new Label("Mail");
            final TextField txtMail = new TextField();
            GenericUtilities.addTextLimiter(txtMail, 40);
            Label lblTel = new Label("Telefono");
            final TextField txtTel = new TextField();
            GenericUtilities.addTextLimiter(txtTel,15);
            Label lblDataNascita = new Label("Data di nascita");
            LocalDate localDate = LocalDate.now();
            final DatePicker dp = new DatePicker(localDate);
            gridPane1.add(lblNome, 0, 1);
            gridPane1.add(txtNome, 1, 1);
            gridPane1.add(lblCognome, 0, 2);
            gridPane1.add(txtCognome, 1, 2);
            gridPane1.add(lblCodFiscale, 0, 3);
            gridPane1.add(txtCodFiscale, 1, 3);
            gridPane1.add(lblMail, 0, 4);
            gridPane1.add(txtMail, 1, 4);
            gridPane1.add(lblTel, 0, 5);
            gridPane1.add(txtTel, 1, 5);
            gridPane1.add(lblDataNascita, 0, 6);
            gridPane1.add(dp, 1, 6);
            //different end
            
            Button btnOk1 = new Button("Assumi");
            btnOk1.setOnAction(proviamo -> {
                if (txtNome.getText().toString().equals("") || txtCognome.getText().toString().equals("")
                        || txtCodFiscale.getText().toString().length()!=16 || !txtMail.getText().toString().contains("@")
                        || dp.getValue().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                        >=localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli() || txtTel.getText().toString().equals("")) {
                    new Alert(Alert.AlertType.ERROR, "Riempire tutti gli input con valori corretti!",ButtonType.CLOSE).showAndWait();
                } else {
                    try {
                        if (new GuidaTab().findByPrimaryKey(txtCodFiscale.getText().toUpperCase())==null
                                && new RestauratoreTab().findByPrimaryKey(txtCodFiscale.getText().toUpperCase())==null) {
                            if (rad.isSelected()) {
                                new GuidaTab().insertInDB(new Guida(codMuseo, txtCodFiscale.getText().toString().toUpperCase(), 
                                        txtNome.getText().toString(), txtCognome.getText().toString(), Date.valueOf(dp.getValue()),
                                        txtMail.getText().toString(), 0, txtTel.getText().toString(), Date.valueOf(LocalDate.now()), null));
                                new Alert(Alert.AlertType.INFORMATION, "Guida Inserita!",ButtonType.CLOSE).showAndWait();
                                biglietto1.close();
                            } else {
                                new RestauratoreTab().insertInDB(new Restauratore(codMuseo, txtCodFiscale.getText().toString().toUpperCase(),
                                        txtNome.getText().toString(),txtCognome.getText().toString(), Date.valueOf(dp.getValue()),
                                        txtMail.getText().toString(),0, txtTel.getText().toString(), Date.valueOf(LocalDate.now()), null));
                                new Alert(Alert.AlertType.INFORMATION, "Restauratore Inserito!",ButtonType.CLOSE).showAndWait();
                                biglietto1.close();
                            }
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Staff già presente!").showAndWait();
                        }
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, "Staff già presente!").showAndWait();
                    }
                }
            });
            
            Button btnClose1 = new Button("Close");
            btnClose1.setOnAction(close -> {
                biglietto1.close();
            });
            
            GridPane bottomBox1 = new GridPane();
            bottomBox1.setPadding(new Insets(20,20,20,30));
            bottomBox1.setHgap(30);
            bottomBox1.add(btnOk1, 0, 0);
            bottomBox1.add(btnClose1, 1, 0);

            bp1.setTop(hb1);
            bp1.setCenter(gridPane1); 
            bp1.setBottom(bottomBox1);
            
            Scene scene1 = new Scene(bp1);
            biglietto1.setScene(scene1);
            biglietto1.sizeToScene();
            biglietto1.setResizable(false);
            biglietto1.showAndWait();
            
        });
        Button remove = new Button("Licenziamento");
        remove.setOnAction(licenzia -> {
            Stage biglietto1 = new Stage();
            BorderPane bp1 = new BorderPane();
            bp1.setPadding(new Insets(10,50,50,50));
        
            GridPane gridPane1 = new GridPane();
            gridPane1.setPadding(new Insets(20,20,20,20));
            gridPane1.setHgap(5);
            gridPane1.setVgap(5);
            
            DropShadow dropShadow1 = new DropShadow();
            dropShadow1.setOffsetX(5);
            dropShadow1.setOffsetY(5);
            
            HBox hb1 = new HBox();
            hb1.setPadding(new Insets(0,20,20,30));
            
            Text text1 = new Text("Licenziamento");
            text1.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
            text1.setEffect(dropShadow);
            
            hb1.getChildren().add(text1);
            
            //different start
            Button btnOk1 = new Button("Licenzia");
            Label lbl = new Label("Seleziona il dipendente da licenziare");
            ComboBox<Integer> cb = new ComboBox<>();
            List<Personale> lic = GenericUtilities.getAllStaffs(1, codMuseo);
            if (lic!=null) {
                for (Personale st : lic) {
                    cb.getItems().add(st.getNumeroBadge());
                }
                cb.setValue(cb.getItems().get(0));
            } else {
                btnOk1.setDisable(true);
            }
            //different end
            btnOk1.setOnAction(licenziamo -> {
                GenericUtilities.setDataLic(codMuseo, cb.getValue());
                new Alert(Alert.AlertType.INFORMATION, "Licenziato correttamente!", ButtonType.OK).showAndWait();
                biglietto1.close();
            });
            
            Button btnClose1 = new Button("Close");
            btnClose1.setOnAction(close -> {
                biglietto1.close();
            });
            
            gridPane1.add(lbl, 0, 0);
            gridPane1.add(cb, 1, 0);
            
            GridPane bottomBox1 = new GridPane();
            bottomBox1.setPadding(new Insets(20,20,20,30));
            bottomBox1.setHgap(30);
            bottomBox1.add(btnOk1, 0, 0);
            bottomBox1.add(btnClose1, 1, 0);

            bp1.setTop(hb1);
            bp1.setCenter(gridPane1); 
            bp1.setBottom(bottomBox1);
            
            Scene scene1 = new Scene(bp1);
            biglietto1.setScene(scene1);
            biglietto1.sizeToScene();
            biglietto1.setResizable(false);
            biglietto1.showAndWait();
        });
        gridPane.add(add, 0, 0);
        gridPane.add(remove, 1, 0);
        //different end
        
        Button btnClose = new Button("Close");
        btnClose.setOnAction(close -> {
            biglietto.close();
        });
        
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
}
