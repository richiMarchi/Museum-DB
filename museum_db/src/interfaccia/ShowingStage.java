package interfaccia;

import java.util.Iterator;
import java.util.List;

import entità.Biglietto;
import entità.Guida;
import entità.Permanenza;
import entità.Restauratore;
import entità.Restauro;
import entità.Personale;
import entità.SupportoGuida;
import entità.SupportoMultimediale;
import entità.UtilizzoStrumento;
import entità.VisitaSingola;
import entità.Visitatore;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tabelle.GuidaTab;
import tabelle.RestauratoreTab;
import tabelle.VisitatoreTab;
import utilities.GenericUtilities;

public class ShowingStage {

    @SuppressWarnings("unchecked")
    public static void showStage(Stage primaryStage, List<?> elements, ComingTable en) {
        Stage stage1 = new Stage();
        
        GridPane gp1 = new GridPane();
        //gp1.setGridLinesVisible(true);
        gp1.setPadding(new Insets(20,20,20,20));
        gp1.setHgap(50);
        gp1.setVgap(10);
        
        switch(en) {
        case BIGLIETTO :
            Text b1 = new Text("Museo");
            b1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text b2 = new Text("Visitatore");
            b2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text b3 = new Text("Biglietto");
            b3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text b4 = new Text("Gruppo");
            b4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text b5 = new Text("Data Acquisto");
            b5.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text b6 = new Text("Prezzo");
            b6.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(b1, 0, 0);
            gp1.add(b2, 1, 0);
            gp1.add(b3, 2, 0);
            gp1.add(b4, 3, 0);
            gp1.add(b5, 4, 0);
            gp1.add(b6, 5, 0);
            
            if (elements != null) {
                int i = 1;
                Iterator<Biglietto> itBig = (Iterator<Biglietto>) elements.iterator();
                while(itBig.hasNext()) {
                    Biglietto big = itBig.next();
                    gp1.add(new Text("" + big.getCodMuseo()), 0, i);
                    gp1.add(new Text("" + big.getCodFiscale()), 1, i);
                    gp1.add(new Text("" + big.getCodBiglietto()), 2, i);
                    if (big.getCodGruppo() != 0) {
                        gp1.add(new Text("" + big.getCodGruppo()), 3, i);
                    } else {
                        gp1.add(new Text("N/A"), 3, i);
                    }
                    gp1.add(new Text("" + big.getData()), 4, i);
                    gp1.add(new Text("" + big.getPrezzo() + " €"), 5, i);
                    i++;
                }
                Text vs4 = new Text("Totale Biglietti: " + (i-1));
                vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
                gp1.add(vs4, 2, i+1);
            } else {
                gp1.add(new Text("/"), 0, 1);
                gp1.add(new Text("/"), 1, 1);
                gp1.add(new Text("/"), 2, 1);
                gp1.add(new Text("/"), 3, 1);
                gp1.add(new Text("/"), 4, 1);
                gp1.add(new Text("/"), 5, 1);
            }
            break;
        case STAFF :
            Text g1 = new Text("Museo");
            g1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text g2 = new Text("Badge");
            g2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text g3 = new Text("Nome");
            g3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text g4 = new Text("Cognome");
            g4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text g5 = new Text("Assunto il");
            g5.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text g6 = new Text("Licenziato il");
            g6.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text g7 = new Text("Mail");
            g7.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text g8 = new Text("Telefono");
            g8.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(g1, 0, 0);
            gp1.add(g2, 1, 0);
            gp1.add(g3, 2, 0);
            gp1.add(g4, 3, 0);
            gp1.add(g5, 4, 0);
            gp1.add(g6, 5, 0);
            gp1.add(g7, 6, 0);
            gp1.add(g8, 7, 0);
            
            int status = (int) elements.get(0);
            List<Personale> staffs = GenericUtilities.getAllStaffs(status, 0);
            
            if (staffs != null) {
                int x = 1;
                Iterator<Personale> itSta = (Iterator<Personale>) staffs.iterator();
                while(itSta.hasNext()) {
                    Personale big = itSta.next();
                    gp1.add(new Text("" + big.getCodMuseo()), 0, x);
                    gp1.add(new Text("" + big.getNumeroBadge()), 1, x);
                    gp1.add(new Text("" + big.getNome()), 2, x);
                    gp1.add(new Text("" + big.getCognome()), 3, x);
                    gp1.add(new Text("" + big.getInizioLavoro()), 4, x);
                    if (big.getFineLavoro()!=null) {
                        gp1.add(new Text("" + big.getFineLavoro()), 5, x);
                    } else {
                        gp1.add(new Text("N/A"), 5, x);
                    }
                    gp1.add(new Text("" + big.getMail()), 6, x);
                    gp1.add(new Text("" + big.getNumeroTelefono()), 7, x);
                    x++;
                }
                Text vs4 = new Text("Totale Staff: " + (x-1));
                vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
                gp1.add(vs4, 4, x+1);
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
            break;
        case GUIDA :
            Text s1 = new Text("Museo");
            s1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text s2 = new Text("Badge");
            s2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text s3 = new Text("Nome");
            s3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text s4 = new Text("Cognome");
            s4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text s5 = new Text("Assunto il");
            s5.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text s6 = new Text("Licenziato il");
            s6.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text s7 = new Text("Mail");
            s7.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text s8 = new Text("Telefono");
            s8.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(s1, 0, 0);
            gp1.add(s2, 1, 0);
            gp1.add(s3, 2, 0);
            gp1.add(s4, 3, 0);
            gp1.add(s5, 4, 0);
            gp1.add(s6, 5, 0);
            gp1.add(s7, 6, 0);
            gp1.add(s8, 7, 0);
            
            int statusQuo = (int) elements.get(0);
            List<Guida> guids = new GuidaTab().getAllTable(statusQuo);
            
            if (guids != null) {
                int x = 1;
                Iterator<Guida> itSta = (Iterator<Guida>) guids.iterator();
                while(itSta.hasNext()) {
                    Guida big = itSta.next();
                    gp1.add(new Text("" + big.getCodMuseo()), 0, x);
                    gp1.add(new Text("" + big.getNumeroBadge()), 1, x);
                    gp1.add(new Text("" + big.getNome()), 2, x);
                    gp1.add(new Text("" + big.getCognome()), 3, x);
                    gp1.add(new Text("" + big.getInizioLavoro()), 4, x);
                    if (big.getFineLavoro()!=null) {
                        gp1.add(new Text("" + big.getFineLavoro()), 5, x);
                    } else {
                        gp1.add(new Text("N/A"), 5, x);
                    }
                    gp1.add(new Text("" + big.getMail()), 6, x);
                    gp1.add(new Text("" + big.getNumeroTelefono()), 7, x);
                    x++;
                }
                Text vs4 = new Text("Totale Guide: " + (x-1));
                vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
                gp1.add(vs4, 4, x+1);
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
            break;
        case RESTAURATORE :
            Text r1 = new Text("Museo");
            r1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text r2 = new Text("Badge");
            r2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text r3 = new Text("Nome");
            r3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text r4 = new Text("Cognome");
            r4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text r5 = new Text("Assunto il");
            r5.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text r6 = new Text("Licenziato il");
            r6.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text r7 = new Text("Mail");
            r7.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text r8 = new Text("Telefono");
            r8.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(r1, 0, 0);
            gp1.add(r2, 1, 0);
            gp1.add(r3, 2, 0);
            gp1.add(r4, 3, 0);
            gp1.add(r5, 4, 0);
            gp1.add(r6, 5, 0);
            gp1.add(r7, 6, 0);
            gp1.add(r8, 7, 0);
            
            int statusPro = (int) elements.get(0);
            List<Restauratore> resters = new RestauratoreTab().getAllTable(statusPro);
            
            if (resters != null) {
                int x = 1;
                Iterator<Restauratore> itSta = (Iterator<Restauratore>) resters.iterator();
                while(itSta.hasNext()) {
                    Personale big = itSta.next();
                    gp1.add(new Text("" + big.getCodMuseo()), 0, x);
                    gp1.add(new Text("" + big.getNumeroBadge()), 1, x);
                    gp1.add(new Text("" + big.getNome()), 2, x);
                    gp1.add(new Text("" + big.getCognome()), 3, x);
                    gp1.add(new Text("" + big.getInizioLavoro()), 4, x);
                    gp1.add(new Text("" + big.getFineLavoro()), 5, x);
                    gp1.add(new Text("" + big.getMail()), 6, x);
                    gp1.add(new Text("" + big.getNumeroTelefono()), 7, x);
                    x++;
                }
                Text vs4 = new Text("Totale Restauratori: " + (x-1));
                vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
                gp1.add(vs4, 4, x+1);
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
            break;
        case VISITATORE :
            Text v1 = new Text("Cod Fiscale");
            v1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text v2 = new Text("Nome");
            v2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text v3 = new Text("Cognome");
            v3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text v4 = new Text("Data Nascita");
            v4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text v5 = new Text("Mail");
            v5.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text v6 = new Text("Spesa Totale");
            v6.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(v1, 0, 0);
            gp1.add(v2, 1, 0);
            gp1.add(v3, 2, 0);
            gp1.add(v4, 3, 0);
            gp1.add(v5, 4, 0);
            gp1.add(v6, 5, 0);
            
            if (elements != null) {
                int i = 1;
                Iterator<Visitatore> itBig = (Iterator<Visitatore>) elements.iterator();
                while(itBig.hasNext()) {
                    Visitatore big = itBig.next();
                    gp1.add(new Text("" + big.getCodFiscale()), 0, i);
                    gp1.add(new Text("" + big.getNome()), 1, i);
                    gp1.add(new Text("" + big.getCognome()), 2, i);
                    gp1.add(new Text("" + big.getDataNascita()), 3, i);
                    gp1.add(new Text("" + big.getMail()), 4, i);
                    gp1.add(new Text("" + new VisitatoreTab().getSpesaTotale(big.getCodFiscale()) + " €"), 5, i);
                    i++;
                }
                Text vs4 = new Text("Totale Visitatori: " + (i-1));
                vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
                gp1.add(vs4, 2, i+1);
            } else {
                gp1.add(new Text("/"), 0, 1);
                gp1.add(new Text("/"), 1, 1);
                gp1.add(new Text("/"), 2, 1);
                gp1.add(new Text("/"), 3, 1);
                gp1.add(new Text("/"), 4, 1);
                gp1.add(new Text("/"), 5, 1);
            }
            break;
        case VISITA_SINGOLA:
            Text vs1 = new Text("Museo");
            vs1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text vs2 = new Text("Biglietto");
            vs2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text vs3 = new Text("Data Visita");
            vs3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            
            gp1.add(vs1, 0, 0);
            gp1.add(vs2, 1, 0);
            gp1.add(vs3, 2, 0);
            
            
            if (elements != null) {
                int i = 1;
                Iterator<VisitaSingola> itBig = (Iterator<VisitaSingola>) elements.iterator();
                while(itBig.hasNext()) {
                    VisitaSingola big = itBig.next();
                    gp1.add(new Text("" + big.getCodMuseo()), 0, i);
                    gp1.add(new Text("" + big.getCodBiglietto()), 1, i);
                    gp1.add(new Text("" + big.getDataUtilizzo()), 2, i);
                    i++;
                }
                Text vs4 = new Text("Totale Visite: " + (i-1));
                vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
                gp1.add(vs4, 1, i+1);
            } else {
                gp1.add(new Text("/"), 0, 1);
                gp1.add(new Text("/"), 1, 1);
                gp1.add(new Text("/"), 2, 1);
            }
            break;
        case SUPPORTO_MULTIMEDIALE :
            Text sm1 = new Text("Museo");
            sm1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text sm2 = new Text("Strumento");
            sm2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(sm1, 0, 0);
            gp1.add(sm2, 1, 0);
            
            if (elements != null) {
                int i = 1;
                Iterator<SupportoMultimediale> itBig = (Iterator<SupportoMultimediale>) elements.iterator();
                while(itBig.hasNext()) {
                    SupportoMultimediale big = itBig.next();
                    gp1.add(new Text("" + big.getCodMuseo()), 0, i);
                    gp1.add(new Text("" + big.getCodStrumento()), 1, i);
                    i++;
                }
                Text vs4 = new Text("Totale Strumenti: " + (i-1));
                vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
                gp1.add(vs4, 0, i+1);
            } else {
                gp1.add(new Text("/"), 0, 1);
                gp1.add(new Text("/"), 1, 1);
            }
            break;
        case UTILIZZO_STRUMENTO :
            Text us1 = new Text("Museo");
            us1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text us2 = new Text("Strumento");
            us2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text us3 = new Text("Data");
            us3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text us4 = new Text("Affidato alle");
            us4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(us1, 0, 0);
            gp1.add(us2, 1, 0);
            gp1.add(us3, 2, 0);
            gp1.add(us4, 3, 0);
            
            if (elements != null) {
                int i = 1;
                Iterator<UtilizzoStrumento> itBig = (Iterator<UtilizzoStrumento>) elements.iterator();
                while(itBig.hasNext()) {
                    UtilizzoStrumento big = itBig.next();
                    gp1.add(new Text("" + big.getCodMuseo()), 0, i);
                    gp1.add(new Text("" + big.getCodStrumento()), 1, i);
                    gp1.add(new Text("" + big.getDataUtilizzo()), 2, i);
                    gp1.add(new Text("" + big.getOraInizio()), 3, i);
                    i++;
                }
                Text vs4 = new Text("Totale Strumenti Utilizzati: " + (i-1));
                vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
                gp1.add(vs4, 1, i+1);
            } else {
                gp1.add(new Text("/"), 0, 1);
                gp1.add(new Text("/"), 1, 1);
                gp1.add(new Text("/"), 2, 1);
                gp1.add(new Text("/"), 3, 1);
            }
            break;
        case SUPPORTO_GUIDA :
            Text vg1 = new Text("Museo");
            vg1.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text vg2 = new Text("Guida");
            vg2.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text vg3 = new Text("Gruppo");
            vg3.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text vg4 = new Text("Data");
            vg4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            Text vg5 = new Text("Ora Inizio");
            vg5.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
            gp1.add(vg1, 0, 0);
            gp1.add(vg2, 1, 0);
            gp1.add(vg3, 2, 0);
            gp1.add(vg4, 3, 0);
            gp1.add(vg5, 4, 0);
            
            if (elements != null) {
                int i = 1;
                Iterator<SupportoGuida> itBig = (Iterator<SupportoGuida>) elements.iterator();
                while(itBig.hasNext()) {
                    SupportoGuida big = itBig.next();
                    gp1.add(new Text("" + big.getCodMuseo()), 0, i);
                    gp1.add(new Text("" + big.getCodFiscale()), 1, i);
                    gp1.add(new Text("" + big.getNumeroGruppo()), 2, i);
                    gp1.add(new Text("" + big.getData()), 3, i);
                    gp1.add(new Text("" + big.getOraInizio()), 4, i);
                    i++;
                }
                Text vs4 = new Text("Totale Supporti Guida: " + (i-1));
                vs4.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
                gp1.add(vs4, 1, i+1);
            } else {
                gp1.add(new Text("/"), 0, 1);
                gp1.add(new Text("/"), 1, 1);
                gp1.add(new Text("/"), 2, 1);
                gp1.add(new Text("/"), 3, 1);
                gp1.add(new Text("/"), 4, 1);
            }
            break;
        case RESTAURO :
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
            
            if (elements != null) {
                int i = 1;
                Iterator<Restauro> itBig = (Iterator<Restauro>) elements.iterator();
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
            break;
        case OPERA :
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
            break;
        default:
            break;
        }
        BorderPane bp1 = new BorderPane();
        bp1.setPadding(new Insets(10,50,50,50));
        bp1.setTop(gp1);
        Button close1 = new Button("Chiudi");
        close1.setOnAction(closing -> {
            stage1.close();
            ResearchStage.getResearchStage(primaryStage);
        });
        bp1.setBottom(close1);
        
        Scene scene1 = new Scene(bp1);
        stage1.setScene(scene1);
        stage1.sizeToScene();
        stage1.setResizable(false);
        stage1.showAndWait();
    }
}
