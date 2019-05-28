package interfaccia;

import java.util.LinkedList;
import java.util.List;

import entità.Museo;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import tabelle.MuseoTab;
import utilities.GenericUtilities;

public class AllMuseums {

    public static void getAllMuseums(Stage primaryStage) {
        
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,50,50,50));
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        //gridPane.setGridLinesVisible(true);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        
        Text text = new Text("Tutti I Musei");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);
        
        HBox hb = new HBox();
        hb.setPadding(new Insets(0,20,20,30));
        hb.getChildren().add(text);
        
        GridPane bottomBox = new GridPane();
        bottomBox.setPadding(new Insets(20,20,20,30));
        bottomBox.setHgap(5);
        bottomBox.setVgap(5);
        
        Text t1 = new Text("Codice");
        GridPane.setMargin(t1, new Insets(5, 10, 5, 10));
        t1.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        Text t2 = new Text("Nome");
        GridPane.setMargin(t2, new Insets(5, 10, 5, 10));
        t2.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        Text t3 = new Text("Città");
        GridPane.setMargin(t3, new Insets(5, 10, 5, 10));
        t3.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        Text t4 = new Text("Via/Piazza");
        GridPane.setMargin(t4, new Insets(5, 10, 5, 10));
        t4.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        
        gridPane.add(t1, 0, 0);
        gridPane.add(t2, 1, 0);
        gridPane.add(t3, 2, 0);
        gridPane.add(t4, 3, 0);
        
        List<Museo> musei = new LinkedList<Museo>();
        musei = new MuseoTab().getAllTable();
        if(musei!=null) {
            int index = 1;
            for(Museo museo : musei) {
                
                Text txt1 = new Text("" + museo.getCodMuseo());
                GridPane.setMargin(txt1, new Insets(5, 10, 5, 10));
                Text txt2 = new Text(museo.getNomeMuseo());
                GridPane.setMargin(txt2, new Insets(5, 10, 5, 10));
                Text txt3 = new Text(museo.getCittà());
                GridPane.setMargin(txt3, new Insets(5, 10, 5, 10));
                Text txt4 = new Text(museo.getVia_piazza());
                GridPane.setMargin(txt4, new Insets(5, 10, 5, 10));

                gridPane.add(txt1, 0, index);
                gridPane.add(txt2, 1, index);
                gridPane.add(txt3, 2, index);
                gridPane.add(txt4, 3, index);
                index++;
            }
        }
        
        Button btnBack = new Button("<- Back");
        btnBack.setOnAction(e -> {
            HomeWindow.getHomeWindow(primaryStage);
        });
        
        Button addMuseum = new Button("Aggiungi Museo");
        addMuseum.setOnAction(e -> {
            Stage aggiungiMuseo = new Stage();
            
            GridPane gp = new GridPane();
            gp.setPadding(new Insets(20,20,20,20));
            gp.setHgap(5);
            gp.setVgap(5);
            
            Label lblNome = new Label("Nome");
            final TextField txtNome = new TextField();
            GenericUtilities.addTextLimiter(txtNome,20);
            Label lblCittà = new Label("Città");
            final TextField txtCittà = new TextField();
            GenericUtilities.addTextLimiter(txtCittà,20);
            Label lblViaPiazza = new Label("Via/Piazza");
            final TextField txtViaPiazza = new TextField();
            GenericUtilities.addTextLimiter(txtViaPiazza, 20);
            final Label message = new Label();
            
            Button insert = new Button("Aggiungi");
            insert.setOnAction(ev -> {
                if (!txtNome.getText().toString().equals("") && !txtCittà.getText().toString().equals("") && !txtViaPiazza.getText().toString().equals("")) {
                    new MuseoTab().insertInDB(new Museo(0,txtNome.getText().toString(),txtCittà.getText().toString(),txtViaPiazza.getText().toString()));
                    aggiungiMuseo.close();
                    AllMuseums.getAllMuseums(primaryStage);
                } else {
                    message.setText("Riempi tutti i campi!");
                }
            });
            BorderPane.setMargin(insert, new Insets(0,120,0,120));
            
            gp.add(lblNome, 0, 0);
            gp.add(txtNome, 1, 0);
            gp.add(lblCittà, 0, 1);
            gp.add(txtCittà, 1, 1);
            gp.add(lblViaPiazza, 0, 2);
            gp.add(txtViaPiazza, 1, 2);
            gp.add(message, 1, 3);
            
            Button btnClose1 = new Button("Chiudi");
            btnClose1.setOnAction(cludi -> {
                aggiungiMuseo.close();
            });
            
            GridPane bottomBox1 = new GridPane();
            bottomBox1.setPadding(new Insets(20,20,20,30));
            bottomBox1.setHgap(5);
            bottomBox1.setVgap(5);
            bottomBox1.add(btnClose1, 0, 0);
            bottomBox1.add(insert, 1, 0);
            
            BorderPane bordPane = new BorderPane();
            bordPane.setPadding(new Insets(10,50,50,50));
            bordPane.setTop(gp);
            bordPane.setBottom(bottomBox1);
            
            Scene sceneAggMuseo = new Scene(bordPane);
            aggiungiMuseo.setTitle("Aggiungi Museo");
            aggiungiMuseo.setScene(sceneAggMuseo);
            aggiungiMuseo.sizeToScene();
            aggiungiMuseo.setResizable(false);
            aggiungiMuseo.showAndWait();
        });
        
        Button research = new Button("Ricerca in tutti i musei");
        research.setOnAction(e -> {
            ResearchStage.getResearchStage(primaryStage);
        });
        
        bottomBox.add(btnBack, 0, 0);
        bottomBox.add(addMuseum, 1, 0);
        bottomBox.add(research, 2, 0);
        
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
