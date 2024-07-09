package controller;




import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import services.ServiceSeance;
import util.Datasource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Statistiqueseance{

        @FXML
        private Button btnabonnement1;

        @FXML
        private Button btnalimentaire1;

        @FXML
        private Button btnequipement1;

        @FXML
        private Button btnevenement1;

        @FXML
        private Button btnplanning;

        @FXML
        private Button btnreclamation1;

        @FXML
        private Button btntdb1;

        @FXML
        private PieChart chart1;

        @FXML
        private ImageView logo1;

        @FXML
        private ImageView planningimg1;

        @FXML
        private ImageView planningimg111;

        @FXML
        private ImageView planningimg1111;

        @FXML
        private ImageView planningimg11111;

        @FXML
        private ImageView planningimg21;

        @FXML
        private ImageView planningimg31;

        @FXML
        void abonnement(ActionEvent event) {

        }

        @FXML
        void alimentaire(ActionEvent event) {

        }

        @FXML
        void equipement(ActionEvent event) {

        }

        @FXML
        void evenement(ActionEvent event) {

        }

        @FXML
        void planning(ActionEvent event) {

        }

        @FXML
        void reclamation(ActionEvent event) {

        }

        @FXML
        void tableaudebord(ActionEvent event) {

        }

        public void initialize() throws IOException  {
                ServiceSeance ps =new ServiceSeance() ;

            ObservableList<PieChart.Data> valueList = null;
            try {
                valueList = FXCollections.observableArrayList(
                        new PieChart.Data("BOXE", ps.countNomS("BOXE")),
                        new PieChart.Data("Bodypump", ps.countNomS("Bodypump")),
                        new PieChart.Data("Bodyattack", ps.countNomS("Bodyattack")),
                        new PieChart.Data("Yoga", ps.countNomS("Yoga")),
                        new PieChart.Data("Spinning", ps.countNomS("Spinning")),
                        new PieChart.Data("Crossfit",ps.countNomS("Crossfit")));
                    chart1.setData(valueList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }



    }

