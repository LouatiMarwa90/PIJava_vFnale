package org.example;

import entities.Reclamation;
import entities.Reponse;
import services.ReclamationService;
import services.ReponseService;

import java.util.Scanner;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) throws SQLException {
        launch(args);
                Scanner scanner = new Scanner(System.in);

        Reclamation r1=new Reclamation("qualitè de service","bien",1,"refusé");
        ReclamationService REC = new ReclamationService();

        Reponse rp = new Reponse();
        ReponseService Rep = new ReponseService();


            System.out.println("1.Ajouter une nouvelle Reclamation");
            System.out.println("2. Modifier une Reclamation ");
            System.out.println("3. Afficher tous les reclamations ");
            System.out.println("4. Afficher reclamation by id");
            System.out.println("5. Supprimer reclamation");
            System.out.println("***********************************************");
             System.out.println("6.Ajouter une nouvelle reponse");
             System.out.println("7. Supprimer ue reponse");
        System.out.println("8. Afficher reponse by id");
        System.out.println("9.get all reponse");
        System.out.println("10. Modifier une Reclamation ");


        System.out.println("0. Quitter ");
            System.out.print("Choisis Crud: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    try {
                        // Insert the new recs into the database
                        REC.insert(r1);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    try {
                        // Update the Person in the database
                        Reclamation reclamation = new Reclamation();

                        System.out.print("Donner l'id de la rec à modifier ");
                        int id = scanner.nextInt();
                        reclamation.setIdRec(id); // Spécifiez l'ID de la réclamation à modifier
                        reclamation.setCategorieR("Problème technique"); // Spécifiez la nouvelle catégorie
                        reclamation.setDescription("moyen"); // Spécifiez la nouvelle description
                        reclamation.setIdU(1); // Spécifiez le nouvel utilisateur associé
                        reclamation.setEtat("traité"); // Spécifiez le nouvel état
                        // Créez une instance de votre service de réclamation
                        ReclamationService service = new ReclamationService();
                        service.modifier(reclamation);

                        // Affichez un message de confirmation
                        System.out.println("Reclamation modifiée avec succès !");
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la modification de la réclamation : " + e.getMessage());

                    }
                    break;
                case 3:
                    try {
                        // Read all recs from the database
                        System.out.println("Tous les reclamation :");
                        for (Reclamation reclamation : REC.getAll()) {
                            System.out.println(reclamation);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    // Read the recs by id from the database
                    System.out.print("Donner l'id de recs voulu: ");
                    int idRec = scanner.nextInt();
                    Reclamation readrec = REC.readById(idRec);
                    System.out.println("afficher rec avec id: " + readrec);
                    break;
                case 20:
                    // Read the recs by id from the database
                    System.out.print("Donner l'id de recs voulu: ");
                    int etRec = scanner.nextInt();
                    Reclamation readet = (Reclamation) REC.readByEtat(String.valueOf(etRec));
                    System.out.println("afficher rec avec etat: " + readet);
                    break;
                case 5:
                    // Delete the recs from the database
                    System.out.print("Donner l'id de la Rec à supprimer : ");
                    int id = scanner.nextInt();
                    REC.supprimer(id);
                    System.out.println("supprimé avec id:  " + id);
                    break;
                case 6:
                    try {
                        // Insert the new recs into the database

                        Rep.insert(rp);
                        System.out.println("Reponse inséré et mail envoyé ");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 7 :
                    System.out.print("Donner l'id de la rep à supprimer : ");
                    int idRep = scanner.nextInt();
                    Rep.supprimer(idRep);
                    System.out.println("supprimé avec id: " + idRep);
                    break;
                case 8 :
                    System.out.print("Donner l'id de reps voulu: ");
                    idRep = scanner.nextInt();
                    Reponse readrep = Rep.readById(idRep);
                    System.out.println("afficher rep avec id: " + readrep);
                    break;
                    case 9:
                    try {
                        // Read all recs from the database
                        System.out.println("Tous les reclamarion :");
                        for (Reponse reponse : Rep.getAll()) {
                            System.out.println(reponse);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 10:
                    try {
                        // Update the Person in the database
                        Reponse reponse = new Reponse();

                        System.out.print("Donner l'id de la rep à modifier ");
                        idRep = scanner.nextInt();
                        reponse.setIdRep(idRep); // Spécifiez l'ID de la réclamation à modifier
                        reponse.setReponse("changé"); // Spécifiez la nouvelle catégorie

                        ReponseService reponseService = new ReponseService();
                        reponseService.modifier(reponse);
                    System.out.println("rep changé");}
                    catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("choix invalide , donner un num entre  0 et 10 0.");
            }
            //*********************************************
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mesrec.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root,  300,300);
            primaryStage.setScene(scene);
            primaryStage.setTitle("GoFit");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}




//package org.example;
//
//
//import entities.Reclamation;
//import entities.Reponse;
//import services.ReclamationService;
//import services.ReponseService;
//
//import java.util.Scanner;
//import java.sql.SQLException;
//
//public class Main {
//    public static void main(String[] args) throws SQLException {
//
//        Scanner scanner = new Scanner(System.in);
//
//        Reclamation r1=new Reclamation("qualitè de service","bien",1,"refusé");
//        ReclamationService REC = new ReclamationService();
//
//        Reponse rp = new Reponse("ok",8);
//        ReponseService Rep = new ReponseService();
//
//
//            System.out.println("1.Ajouter une nouvelle Reclamation");
//            System.out.println("2. Modifier une Reclamation ");
//            System.out.println("3. Afficher tous les reclamations ");
//            System.out.println("4. Afficher reclamation by id");
//            System.out.println("5. Supprimer reclamation");
//            System.out.println("***********************************************");
//             System.out.println("6.Ajouter une nouvelle reponse");
//             System.out.println("7. Supprimer ue reponse");
//        System.out.println("8. Afficher reponse by id");
//
//
//        System.out.println("0. Quitter ");
//            System.out.print("Choisis Crud: ");
//            int choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1:
//                    try {
//                        // Insert the new recs into the database
//                        REC.insert(r1);
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                    break;
//                case 2:
//                    try {
//                        // Update the Person in the database
//                       //Reclamation reclamation = new Reclamation(resultSet.getString("categorieR"), resultSet.getString("description"), resultSet.getString("etat"), resultSet.getInt("idU"));
//                        Reclamation reclamation = new Reclamation();
//
//                        System.out.print("Donner l'id de la rec à modifier ");
//                        int id = scanner.nextInt();
//                        reclamation.setIdRec(id); // Spécifiez l'ID de la réclamation à modifier
//                        reclamation.setCategorieR("Problème technique"); // Spécifiez la nouvelle catégorie
//                        reclamation.setDescription("moyen"); // Spécifiez la nouvelle description
//                        reclamation.setIdU(1); // Spécifiez le nouvel utilisateur associé
//                        reclamation.setEtat("traité"); // Spécifiez le nouvel état
//                        // Créez une instance de votre service de réclamation
//                        ReclamationService service = new ReclamationService();
//                        service.modifier(reclamation);
//
//                        // Affichez un message de confirmation
//                        System.out.println("Reclamation modifiée avec succès !");
//                    } catch (Exception e) {
//                        System.out.println("Erreur lors de la modification de la réclamation : " + e.getMessage());
//
//                    }
//                    break;
//                case 3:
//                    try {
//                        // Read all recs from the database
//                        System.out.println("Tous les reclamarion :");
//                        for (Reclamation reclamation : REC.getAll()) {
//                            System.out.println(reclamation);
//                        }
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case 4:
//                    // Read the recs by id from the database
//                    System.out.print("Donner l'id de recs voulu: ");
//                    int idRec = scanner.nextInt();
//                    Reclamation readrec = REC.readById(idRec);
//                    System.out.println("afficher rec avec id: " + readrec);
//                    break;
//                case 5:
//                    // Delete the recs from the database
//                    System.out.print("Donner l'id de la Rec à supprimer : ");
//                    int id = scanner.nextInt();
//                    REC.supprimer(id);
//                    System.out.println("supprimé avec id:  " + id);
//                    break;
//                case 6:
//                    try {
//                        // Insert the new recs into the database
//
//                        Rep.insert(rp);
//                        Rep.envoyerNotificationEmail(rp.getIdRec(), "traité", "mounahamrouni43@gmail.com");
//                        System.out.println("Reponse inséré et mail envoyé ");
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                    break;
//                case 7 :
//                    System.out.print("Donner l'id de la rep à supprimer : ");
//                    int idRep = scanner.nextInt();
//                    Rep.supprimer(idRep);
//                    System.out.println("supprimé avec id: " + idRep);
//                    break;
//                case 8 :
//                    System.out.print("Donner l'id de reps voulu: ");
//                    idRep = scanner.nextInt();
//                    Reponse readrep = Rep.readById(idRep);
//                    System.out.println("afficher rep avec id: " + readrep);
//                    break;
//                case 0:
//                    System.out.println("Exiting...");
//                    return;
//                default:
//                    System.out.println("choix invalide , donner un num entre  0 et 10 0.");
//            }
//            //*********************************************
//
//        }
//    }
