package controller;

import entities.Reservation;
import entities.Seance;
import entities.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import services.ServiceReservation;
import services.ServiceSeance;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Reservationplan {

    @FXML
    private Button btnabonnement;

    @FXML
    private Button btnajouterreservation;

    @FXML
    private Button btnalimentaire;

    @FXML
    private TableView<Reservation> tablereservation;

    @FXML
    private Button btnchercher;

    @FXML
    private Button btnequipement;

    @FXML
    private Button btnevenement;

    @FXML
    private Button btnmodifierreservation;

    @FXML
    private Button btnplanning;

    @FXML
    private Button btnreclamation;

    @FXML
    private Button btnreservation;

    @FXML
    private Button btnstats;

    @FXML
    private Button btnsupprimerreservation;

    @FXML
    private Button btntdb;

    @FXML
    private Button btntrier;

    @FXML
    private TextField chercherfield;

    @FXML
    private ComboBox<Integer> combIDU;

    @FXML
    private ComboBox<?> combotri;

    @FXML
    private TextField emailT;

    @FXML
    private ComboBox<String> etatcombo;

    @FXML
    private ComboBox<Integer> etatcombo1;

    @FXML
    private TableColumn<?, ?> idemailU;

    @FXML
    private TableColumn<?, ?> idetatU;

    @FXML
    private TableColumn<?, ?> ididU;

    @FXML
    private TableColumn<?, ?> idnomU;

    @FXML
    private TableColumn<?, ?> idrenomU;

    @FXML
    private AnchorPane ids;

    @FXML
    private TableColumn<?, ?> idsexeU;

    @FXML
    private TextField nomT;

    @FXML
    private Label notification;

    @FXML
    private TextField prenomT;

    @FXML
    private TextField sexeT;

    @FXML
    private TableView<?> tableseance;

    @FXML
    void abonnement(ActionEvent event) {

    }

    @FXML
    void ajouterR(ActionEvent event) {
        Object id_u = combIDU.getValue();
        String nom = nomT.getText();
        String prenom = prenomT.getText();
        String email = emailT.getText();
        String sexe = sexeT.getText();
        Object etat_r = etatcombo.getValue();
        Object seance_id = etatcombo1.getValue();

        boolean test = false;

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() ||
                sexe.isEmpty() || id_u == null || etat_r == null || seance_id == null) {
            notification.setTextFill(Color.RED);
            notification.setText("Please correct the input field !.");
            notification.setVisible(true);
        } else
            test = true;
        if (test == true) {
            int idf=Integer.parseInt(id_u.toString());
ServiceReservation rp=new ServiceReservation();
            Reservation.sexe s= Reservation.sexe.valueOf(sexe);
            Reservation.etat e= Reservation.etat.valueOf(etat_r.toString());
            Utilisateur myuser=new Utilisateur();

            myuser.setIdU(idf);
            try {
                System.out.println("ppp"+myuser.getIdU());
                rp.ajouter(new Reservation(nom,prenom,email,s,e,myuser));
                List<Reservation> listres = rp.getAll();
                System.out.println(listres);
                ObservableList<Reservation> res_list = FXCollections.observableArrayList(listres);
                tablereservation.setItems(res_list);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    @FXML
    void alimentaire(ActionEvent event) {

    }

    @FXML
    void btnstat(ActionEvent event) {

    }

    @FXML
    void chercherid(ActionEvent event) {

    }

    @FXML
    void combidu(ActionEvent event) {

    }

    @FXML
    void equipement(ActionEvent event) {

    }

    @FXML
    void evenement(ActionEvent event) {

    }

    @FXML
    void idseanceComb(ActionEvent event) {

    }

    @FXML
    void modifierR(ActionEvent event) {

    }

    @FXML
    void onbtntrierid(ActionEvent event) {

    }

    @FXML
    void planning(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void reservation(ActionEvent event) {

    }

    @FXML
    void supprimerR(ActionEvent event) {

    }

    @FXML
    void tableaudebord(ActionEvent event) {

    }

    @FXML
    void trireservation(ActionEvent event) {

    }
    public void initialize() throws IOException {
        //System.out.println("step 1");
        ServiceReservation ps=new ServiceReservation();
        combIDU.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Integer  id_u = combIDU.getSelectionModel().getSelectedItem();
                Utilisateur found= new Utilisateur();
                try {
                    found = ps.getUserbyId(id_u);
                    System.out.println(found.getNomU());
                    nomT.setText(found.getNomU());
                    prenomT.setText(found.getPrenomU());
                    emailT.setText(found.getMailU());
                    sexeT.setText(found.getSexe());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        List<Utilisateur>mesuser=new ArrayList<Utilisateur>();
        List<Integer>mesuseer_ids=new ArrayList<Integer>();
        ObservableList<Integer> data = null; //List of String
        try {
            mesuser=ps.getAllUser();
            for(int i=0;i<mesuser.size();i++){
               mesuseer_ids.add(mesuser.get(i).getIdU()) ;
            }
            data = FXCollections.observableArrayList(mesuseer_ids);

            combIDU.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServiceSeance sp=new ServiceSeance();
        List<Seance> messeances=new ArrayList<Seance>();
        List<Integer> messeances_ids=new ArrayList<Integer>();
        try {
            messeances=sp.getAll();
            for(int i=0;i<messeances.size();i++){
                messeances_ids.add(messeances.get(i).getIdS()) ;
            }
            ObservableList<Integer> data2 = null;
            data2 = FXCollections.observableArrayList(messeances_ids);
            etatcombo1.setItems(data2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<String> elmentetat = FXCollections.observableArrayList("EN_COURS", "CONFIRMEE", "ANNULEE");
        etatcombo.setItems(elmentetat);
        ididU.setCellValueFactory(new PropertyValueFactory<>("idU"));
        idnomU.setCellValueFactory(new PropertyValueFactory<>("name"));
        idrenomU.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        idemailU.setCellValueFactory(new PropertyValueFactory<>("email"));

        idsexeU.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        idetatU.setCellValueFactory(new PropertyValueFactory<>("etat"));

        loadData();
    }


    private void loadData(){



        try {
            ServiceReservation fs=new ServiceReservation();
            List<Reservation> list= fs.getAllJust();


           // System.out.println(list);
            ObservableList <Reservation> reservatgion_list=FXCollections.observableArrayList(list);
            tablereservation.setItems(reservatgion_list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}