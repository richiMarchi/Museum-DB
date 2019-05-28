package interfaccia;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import entità.Opera;
import entità.Restauratore;
import entità.Restauro;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import tabelle.OperaTab;
import tabelle.RestauratoreTab;
import tabelle.RestauroTab;

public class GestisciRestauri {

    public static void getGestisciRestauri(Stage primaryStage, int codMuseo) {
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
        Button add = new Button("Inizia Restauro");
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
            
            Text text1 = new Text("Inizia Restauro");
            text1.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
            text1.setEffect(dropShadow);
            
            hb1.getChildren().add(text1);
            
            //different start
            
            Button btnOk1 = new Button("Inizia");
            Label combo1 = new Label("Opera");
            ComboBox<String> operes = new ComboBox<>();
            List<Opera> perms = new OperaTab().getAllTable(codMuseo);
            if (perms!=null) {
                for (Opera r : perms) {
                    operes.getItems().add("" + r.getCodAutore() + " " + r.getNomeOpera());
                }
                operes.setValue(operes.getItems().get(0));
            } else {
                btnOk1.setDisable(true);
            }
            Label combo2 = new Label("Restauratore");
            ComboBox<String> rester = new ComboBox<>();
            List<Restauratore> rests = new RestauratoreTab().getAllTable(codMuseo);
            if (rests!=null) {
                for (Restauratore r : rests) {
                    rester.getItems().add("" + r.getNumeroBadge());
                }
                rester.setValue(rester.getItems().get(0));
            } else {
                btnOk1.setDisable(true);
            }
            
            gridPane1.add(combo1, 0, 0);
            gridPane1.add(operes, 0, 1);
            gridPane1.add(combo2, 1, 0);
            gridPane1.add(rester, 1, 1);
            
            //different end
            
            
            btnOk1.setOnAction(proviamo -> {
                String[] splitted = operes.getValue().split(" ", 2);
                if (new RestauroTab().findAttivo(Integer.parseInt(splitted[0]), splitted[1])==null
                        && new RestauratoreTab().findLavora(codMuseo, Integer.parseInt(rester.getValue()))==null) {
                    Restauro resst = new Restauro(new RestauratoreTab().findByID(codMuseo, Integer.parseInt(rester.getValue())),
                            Integer.parseInt(splitted[0]), splitted[1], Date.valueOf(LocalDate.now()), null);
                    resst.setNumeroBadge(Integer.parseInt(rester.getValue()));
                    new RestauroTab().insertInDB(resst);
                    new Alert(Alert.AlertType.INFORMATION, "Restauro iniziato!", ButtonType.OK);
                    biglietto1.close();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Opera già in restauro o restauratore già impegnato!", ButtonType.CLOSE);
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
        Button remove = new Button("Termina Restauro");
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
            
            Text text1 = new Text("Termina Restauro");
            text1.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
            text1.setEffect(dropShadow);
            
            hb1.getChildren().add(text1);
            
            //different start
            Button btnOk1 = new Button("Termina");
            Label lbl = new Label("Seleziona il restauro da terminare");
            ComboBox<String> cb = new ComboBox<>();
            List<Restauro> lic = new RestauroTab().getRestauriAttivi(codMuseo);
            if (lic!=null) {
                for (Restauro st : lic) {
                    cb.getItems().add("" + st.getCodAutore() + " " + st.getNomeOpera());
                }
                cb.setValue(cb.getItems().get(0));
            } else {
                btnOk1.setDisable(true);
            }
            //different end
            btnOk1.setOnAction(licenziamo -> {
                String[] splitted = cb.getValue().split(" ", 2);
                new RestauroTab().terminaRest(Integer.parseInt(splitted[0]),splitted[1]);
                new Alert(Alert.AlertType.INFORMATION, "Restauro terminato correttamente!", ButtonType.OK).showAndWait();
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
