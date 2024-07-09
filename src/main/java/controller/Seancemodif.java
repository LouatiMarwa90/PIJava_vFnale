package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import entities.Seance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import services.ServiceSeance;

public class Seancemodif {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnabonnement;

    @FXML
    private Button btnajouterseance;

    @FXML
    private Button btnalimentaire;
    @FXML
    private Button btntrier;
    @FXML
    private ComboBox<String> combotri;
    @FXML
    private Button btnstats;


    @FXML
    private Button btnchercher;

    @FXML
    private Button btnequipement;

    @FXML
    private Button btnevenement;

    @FXML
    private Button btnmodifierseance;

    @FXML
    private Button btnplanning;

    @FXML
    private Button btnreclamation;

    @FXML
    private Button btnrefraichir;

    @FXML
    private Button btnreservation;



    @FXML
    private Button btnsupprimer;

    @FXML
    private Button btntdb;

    @FXML
    private TextField chercherfield;

    @FXML
    private TextField coachid;

    @FXML
    private DatePicker dateid;

    @FXML
    private Spinner<Integer> dureeid;

    @FXML
    private TableColumn<?, ?> dureeseancetable;

    @FXML
    private TableColumn<?, ?> coachseancetable;

    @FXML
    private TableColumn<?, ?> nbseancetable;

    @FXML
    private TableColumn<?, ?> jourseancetable;

    @FXML
    private Spinner<Integer> nb_place_id;

    @FXML
    private ComboBox<String> nomid;

    @FXML
    private TableColumn<?, ?> nomseancetable;

    @FXML
    private TextField numsalleid;

    @FXML
    private TableColumn<?, ?> numsalleseance;

    @FXML
    private TableView<Seance> tableseance;
    @FXML
    private Label notification;


    private ServiceSeance ps =new ServiceSeance();

    @FXML
    private ComboBox<?> tri;

    @FXML
    void abonnement(ActionEvent event) {


    }


        @FXML
    void ajouterseance(ActionEvent event) {

        Object nom = nomid.getValue();
        LocalDate selectedDate = dateid.getValue();
        String datefinal = String.valueOf(dateid.getValue());
        // LocalDate date = LocalDate.parse(str, DateTimeFormatter.ISO_DATE);
        //java.sql.Date sqlDate = java.sql.Date.valueOf( date );
        Object duree = dureeid.getValue();
        int d=Integer.parseInt(duree.toString());
        String nomcoach = coachid.getText();
        String salle =numsalleid.getText();
        Object nbplaces = nb_place_id.getValue();
        int nb=Integer.parseInt(nbplaces.toString());

        boolean test=false;

            if (salle.isEmpty() || nomcoach.isEmpty() ||nom==null || duree==null || nbplaces==null ) {
                notification.setTextFill(Color.RED);
                notification.setText("Please correct the input field !.");
                notification.setVisible(true);
            }
            else
                test=true;
            if(test==true) {
                notification.setVisible(false);
                try {
                    Seance s1 =new Seance(nom.toString(),datefinal,d,nomcoach,salle,nb);
                    ps.ajouter(s1);
                    List<Seance> list = ps.getAll();
                    System.out.println(list);
                    ObservableList<Seance> seances_list = FXCollections.observableArrayList(list);
                    tableseance.setItems(seances_list);
                    viderFormulaire();
                    //ps.supprimer(5);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
    }
    @FXML
    void alimentaire(ActionEvent event) {

    }

    @FXML
    void chercherseance(ActionEvent event) {

    }

    @FXML
    void equipement(ActionEvent event) {

    }

    @FXML
    void evenement(ActionEvent event) {

    }
@FXML
void onbtntrier (ActionEvent event) {

    System.out.println("step 1");
    String critere=combotri.getValue();
    if(combotri.getValue()==null)
    {notification.setTextFill(Color.RED);
        notification.setText("Please select an item to sort value");
        notification.setVisible(true);}
    else{
        notification.setVisible(false);
        System.out.println(critere);
        List<Seance> mylist=ps.getSeanceOrderBy(critere);

        System.out.println(mylist);
        ObservableList<Seance> seances_list = FXCollections.observableArrayList(mylist);
        tableseance.setItems(seances_list);
    }

}
    @FXML
    void modifierseance(ActionEvent event) {

        Seance s=new Seance();
        boolean test=false;
        Seance s2 = new Seance();
        if (tableseance.getSelectionModel().getSelectedItem() == null) {
            notification.setTextFill(Color.RED);
            notification.setText("Please select an item.");
            notification.setVisible(true);

        } else {

            if (coachid.getText().isEmpty() || numsalleseance.getText().isEmpty()
                    || nomid == null || dureeid == null || dateid == null) {
                notification.setTextFill(Color.RED);
                notification.setText("Please correct the input field !.");
                notification.setVisible(true);
            } else {
                s = tableseance.getSelectionModel().getSelectedItem();
                notification.setVisible(false);

s2.setIdS(s.getIdS());
                s2.setNomS(nomid.getValue());
                s2.setCoach(coachid.getText());
                s2.setSalle(numsalleid.getText());
                LocalDate selectedDate = dateid.getValue();
                String datefinal = String.valueOf(selectedDate);
                s2.setDate(datefinal);
                System.out.println(datefinal);
                System.out.println(s2);
                Object duree = dureeid.getValue();
                int d = Integer.parseInt(duree.toString());
                s2.setDuree(d);
                Object nb_place = nb_place_id.getValue();
                int nb = Integer.parseInt(nb_place.toString());
                s2.setNb_place(nb);
                try {

                    ps.modifier(s2);
                    List<Seance> list = ps.getAll();
                    System.out.println(list);
                    ObservableList<Seance> seances_list = FXCollections.observableArrayList(list);
                    tableseance.setItems(seances_list);
                    viderFormulaire();

                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }


        }


    }


    @FXML
    void planning(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void refraichir(ActionEvent event) {

    }

    @FXML
    void reservation(ActionEvent event) {


        FXMLLoader loader = new FXMLLoader( getClass().getResource("/Reservationplan.fxml") );
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void btnstat(ActionEvent event) {
        System.out.println("stat");

       FXMLLoader loader = new FXMLLoader( getClass().getResource("/Statistiqueseance.fxml") );
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène pour afficher la nouvelle page FXML
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Créer une nouvelle scène avec la nouvelle page FXML

    }



    @FXML
    void supprimerseance(ActionEvent event) {

        if (tableseance.getSelectionModel().getSelectedItem() == null) {
            notification.setTextFill(Color.RED);
            notification.setText("Please select an item.");
            notification.setVisible(true);
        } else {
            Seance s = tableseance.getSelectionModel().getSelectedItem();
            notification.setVisible(false);

            try {
                ps.supprimer(s.getIdS());
                List<Seance> list = ps.getAll();
                System.out.println(list);
                ObservableList<Seance> seances_list = FXCollections.observableArrayList(list);
                tableseance.setItems(seances_list);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void tableaudebord(ActionEvent event) {

    }

    @FXML
    void triseance(ActionEvent event) {
       /* System.out.println("step 1");
        String critere=combotri.getValue();
        if(combotri ==null)
        {notification.setTextFill(Color.RED);
            notification.setText("Please select an item to sort value");
            notification.setVisible(true);}
        else{
            notification.setVisible(false);
            List<Seance> mylist=ps.getSeanceOrderBy(critere);


            ObservableList<Seance> seances_list = FXCollections.observableArrayList(mylist);
            tableseance.setItems(seances_list);
        }*/

    }
    public void initialize() throws IOException{
        ObservableList<String> elmentnomS = FXCollections.observableArrayList("BOXE", "Bodypump", "Bodyattack", "Yoga", "Spinning", "Crossfit");
        nomid.setItems(elmentnomS);
        ObservableList<String> elmenttrieC = FXCollections.observableArrayList("nomS", "coach", "date", "duree", "salle", "nb_place");
        combotri.setItems(elmenttrieC);

        SpinnerValueFactory<Integer>valueFactory=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,6);
valueFactory.setValue(1);
dureeid.setValueFactory(valueFactory);
        SpinnerValueFactory<Integer>valueFactorynbplace=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,20);
        valueFactorynbplace.setValue(1);
        nb_place_id.setValueFactory(valueFactorynbplace);

        nomseancetable.setCellValueFactory(new PropertyValueFactory<>("nomS"));
        coachseancetable.setCellValueFactory(new PropertyValueFactory<>("coach"));
        jourseancetable.setCellValueFactory(new PropertyValueFactory<>("date"));
       dureeseancetable.setCellValueFactory(new PropertyValueFactory<>("duree"));

        numsalleseance.setCellValueFactory(new PropertyValueFactory<>("salle"));
        nbseancetable.setCellValueFactory(new PropertyValueFactory<>("nb_place"));

        loadData();
        tableseance.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               Seance s = tableseance.getSelectionModel().getSelectedItem();
                notification.setVisible(false);

                nomid.setValue(s.getNomS());
                coachid.setText(s.getCoach());
                numsalleid.setText(s.getSalle());
                LocalDate selectedDate = LocalDate.parse(s.getDate());
                dateid.setValue(selectedDate);
                dureeid.getValueFactory().setValue(s.getDuree());
                nb_place_id.getValueFactory().setValue(s.getNb_place());
            }
        });
        chercherfield.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        loadDataSearch(newValue);
        });
    }
    private void loadData(){



        try {
            List<Seance> list= ps.getAll();
            System.out.println(list);
            ObservableList <Seance> seances_list=FXCollections.observableArrayList(list);
            tableseance.setItems(seances_list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void loadDataSearch(String text){




            List<Seance> list= ps.getSeanceByAll(text);
            System.out.println(list);

        ObservableList<Seance> seances_list = FXCollections.observableArrayList(list);
        tableseance.setItems(seances_list);

    }
    private void viderFormulaire(){

         nomid.setValue(null);
        dateid.setValue(null);

     dureeid.getEditor().clear();

     coachid.setText(" ");
       numsalleid.setText(" ");
      nb_place_id.getEditor().clear();


    }

   // @FXML
//    void initialize() {
//        assert btnabonnement != null : "fx:id=\"btnabonnement\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert btnajouterseance != null : "fx:id=\"btnajouterseance\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert btnalimentaire != null : "fx:id=\"btnalimentaire\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert btnchercher != null : "fx:id=\"btnchercher\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert btnequipement != null : "fx:id=\"btnequipement\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert btnevenement != null : "fx:id=\"btnevenement\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert btnmodifierseance != null : "fx:id=\"btnmodifierseance\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert btnplanning != null : "fx:id=\"btnplanning\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert btnreclamation != null : "fx:id=\"btnreclamation\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert btnrefraichir != null : "fx:id=\"btnrefraichir\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert btnreservation != null : "fx:id=\"btnreservation\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert btnstats != null : "fx:id=\"btnstats\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert btnsupprimer != null : "fx:id=\"btnsupprimer\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert btntdb != null : "fx:id=\"btntdb\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert chercherfield != null : "fx:id=\"chercherfield\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert coachid != null : "fx:id=\"coachid\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert dateid != null : "fx:id=\"dateid\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert dureeid != null : "fx:id=\"dureeid\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert dureeseancetable != null : "fx:id=\"dureeseancetable\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert horaireseancetable != null : "fx:id=\"horaireseancetable\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert imageseancetable != null : "fx:id=\"imageseancetable\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert jourseancetable != null : "fx:id=\"jourseancetable\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert nb_place_id != null : "fx:id=\"nb_place_id\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert nomid != null : "fx:id=\"nomid\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert nomseancetable != null : "fx:id=\"nomseancetable\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert numsalleid != null : "fx:id=\"numsalleid\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert numsalleseance != null : "fx:id=\"numsalleseance\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert tableseance != null : "fx:id=\"tableseance\" was not injected: check your FXML file 'seancemodif.fxml'.";
//        assert tri != null : "fx:id=\"tri\" was not injected: check your FXML file 'seancemodif.fxml'.";
//
//    }

}
