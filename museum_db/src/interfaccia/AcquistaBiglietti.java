package interfaccia;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;

import entità.Biglietto;
import entità.Visitatore;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
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
import tabelle.BigliettoTab;
import tabelle.VisitatoreTab;
import utilities.GenericUtilities;

public class AcquistaBiglietti {

    public static void getAcquistaBiglietti(Stage primaryStage, int codMuseo) {
        Stage biglietto = new Stage();
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,50,50,50));
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
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
        Label lblDataNascita = new Label("Data di nascita");
        LocalDate localDate = LocalDate.now();
        Label prezzo = new Label("Prezzo");
        final TextField txtPrezzo = new TextField();
        final DatePicker dp = new DatePicker(localDate);
        final Label lblMessage = new Label();
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        
        HBox hb = new HBox();
        hb.setPadding(new Insets(0,20,20,30));
        
        Text text = new Text("Acquista Biglietto");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);
        
        hb.getChildren().add(text);
        
        gridPane.add(lblNome, 0, 0);
        gridPane.add(txtNome, 1, 0);
        gridPane.add(lblCognome, 0, 1);
        gridPane.add(txtCognome, 1, 1);
        gridPane.add(lblCodFiscale, 0, 2);
        gridPane.add(txtCodFiscale, 1, 2);
        gridPane.add(lblMail, 0, 3);
        gridPane.add(txtMail, 1, 3);
        gridPane.add(prezzo, 0, 4);
        gridPane.add(txtPrezzo, 1, 4);
        gridPane.add(lblDataNascita, 0, 5);
        gridPane.add(dp, 1, 5);
        gridPane.add(lblMessage, 1, 6);

        Button btnBiglietto = new Button("Acquista Biglietto");
        btnBiglietto.setOnAction(visitorEvent->{
            if (txtNome.getText().toString().equals("") || txtCognome.getText().toString().equals("") || txtCodFiscale.getText().toString().length()!=16
                    || !txtMail.getText().toString().contains("@") || dp.getValue().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli() >=
                    localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()) {
                lblMessage.setText("INPUT INCORRETTO!");
            } else {
                new VisitatoreTab().insertInDB(new Visitatore(txtCodFiscale.getText().toString().toUpperCase(), txtNome.getText().toString(),
                        txtCognome.getText().toString(), Date.valueOf(dp.getValue()), txtMail.getText().toString()));
                try {
                    new BigliettoTab().insertInDB(new Biglietto(codMuseo, txtCodFiscale.getText().toString().toUpperCase(), 0, 0,
                            Date.valueOf(LocalDate.now()), Integer.parseInt(txtPrezzo.getText())));
                    new Alert(Alert.AlertType.INFORMATION, "Biglietto Acquistato Con Successo!", ButtonType.OK).showAndWait();
                    biglietto.close();
                } catch (Exception e) {
                    lblMessage.setText("IL PREZZO DEVE ESSERE UN NUMERO!");
                }
            }
        });
        
        Button btnClose = new Button("Close");
        btnClose.setOnAction(close -> {
            biglietto.close();
        });
        
        GridPane bottomBox = new GridPane();
        bottomBox.setPadding(new Insets(20,20,20,30));
        bottomBox.setHgap(30);
        bottomBox.add(btnBiglietto, 1, 0);
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
