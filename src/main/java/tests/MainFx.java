package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // loader = new FXMLLoader(getClass().getResource("/AjouterEquipe.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEquipement.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipement.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutAvis.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheAllAvis.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dash.fxml"));



        try {
            Parent root = loader.load();
            Scene scene = new Scene(root );

            primaryStage.setTitle("Salle de sport");
            primaryStage.setScene(scene);
            primaryStage.show();



        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}

