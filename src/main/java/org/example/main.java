package org.example;

import Services.AbonnementService;
import Services.UtilisateurService;
import entites.Abonnement;
import entites.Utilisateur;
import util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UtilisateurService utilisateurService = new UtilisateurService();
        AbonnementService abonnementService = new AbonnementService();
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DataSource.getInstance().getConnexion()) {
            // Authentification de l'utilisateur
            System.out.print("Entrez votre email: ");
            String email = scanner.nextLine();
            System.out.print("Entrez votre mot de passe: ");
            String password = scanner.nextLine();

            boolean isAuthenticated = authenticateUser(connection, email, password);

            if (isAuthenticated) {
                System.out.println("Authentifié avec succès");

                while (true) {
                    // Affichage du menu
                    System.out.println("\nMenu:");
                    System.out.println("1. Créer un nouvel utilisateur");
                    System.out.println("2. Supprimer un utilisateur");
                    System.out.println("3. Consulter un utilisateur");
                    System.out.println("4. Rechercher des utilisateurs par nom");
                    System.out.println("5. Trier les utilisateurs par nom");
                    System.out.println("6. Vérifier un ID");
                    System.out.println("7. Mettre à jour le mot de passe");
                    System.out.println("8. Modifier un utilisateur");
                    System.out.println("9. Ajouter un nouvel abonnement");
                    System.out.println("10. Modifier un abonnement");
                    System.out.println("11. Consulter un abonnement");
                    System.out.println("12. Supprimer un abonnement");
                    System.out.println("13. Abonnement personnalisé");
                    System.out.println("14. Rappel automatique");
                    System.out.println("15. Paiement abonnement");
                    System.out.println("16. Quitter");

                    System.out.print("Entrez votre choix : ");
                    int choix = scanner.nextInt();
                    scanner.nextLine(); // Consuming newline left-over

                    switch (choix) {
                        case 1:
                            // Créer un nouvel utilisateur
                            Utilisateur nouvelUtilisateur = new Utilisateur();
                            System.out.print("Nom: ");
                            nouvelUtilisateur.setNomU(scanner.nextLine());
                            System.out.print("Prénom: ");
                            nouvelUtilisateur.setPrenomU(scanner.nextLine());
                            System.out.print("Mot de passe: ");
                            nouvelUtilisateur.setMdp(scanner.nextLine());
                            System.out.print("Email: ");
                            nouvelUtilisateur.setMailU(scanner.nextLine());
                            System.out.print("Téléphone: ");
                            nouvelUtilisateur.setTel(String.valueOf(scanner.nextInt()));
                            System.out.print("Statut (true/false): ");
                            nouvelUtilisateur.setStatut(scanner.nextBoolean());
                            System.out.print("Date de naissance (yyyy-mm-dd): ");
                            scanner.nextLine(); // Consuming newline left-over

                            System.out.println("Nouvel utilisateur ajouté avec ID : " + nouvelUtilisateur.getIdU());
                            break;
                        case 2:
                            // Supprimer un utilisateur
                            System.out.print("Entrez l'ID de l'utilisateur à supprimer : ");
                            int idUtilisateurASupprimer = scanner.nextInt();
                            utilisateurService.supprimer(idUtilisateurASupprimer);
                            System.out.println("Utilisateur supprimé");
                            break;
                        case 3:
                            // Consulter un utilisateur
                            System.out.print("Entrez l'ID de l'utilisateur à consulter : ");
                            int idUtilisateurAConsulter = scanner.nextInt();
                            Utilisateur utilisateur = utilisateurService.consulter(String.valueOf(idUtilisateurAConsulter));
                            if (utilisateur != null) {
                                System.out.println("Utilisateur consulté : " + utilisateur.getNomU());
                            } else {
                                System.out.println("Utilisateur non trouvé");
                            }
                            break;
                        case 4:
                            // Rechercher des utilisateurs par nom
                            System.out.print("Entrez le nom de l'utilisateur à rechercher : ");
                            String nomRecherche = scanner.nextLine();
                            List<Utilisateur> utilisateursTrouves = utilisateurService.rechercher(nomRecherche);
                            System.out.println("Utilisateurs trouvés sous nom tapé : " + utilisateursTrouves.size());
                            for (Utilisateur user : utilisateursTrouves) {
                                System.out.println(user.getNomU() + " " + user.getPrenomU());
                            }
                            break;
                        case 5:
                            // Trier les utilisateurs par nom
                            List<Utilisateur> utilisateursTries = utilisateurService.trierParNom();
                            System.out.println("Utilisateurs triés : " + utilisateursTries.size());
                            for (Utilisateur user : utilisateursTries) {
                                System.out.println(user.getNomU() + " " + user.getPrenomU());
                            }
                            break;
                        case 6:
                            // Vérifier un ID
                            System.out.print("Entrez l'ID à vérifier : ");
                            int idAVerifier = scanner.nextInt();
                            boolean idExiste = utilisateurService.verifId(idAVerifier);
                            System.out.println("ID existe : " + idExiste);
                            break;
                        case 7:
                            // Mettre à jour le mot de passe
                            System.out.print("Entrez l'ID de l'utilisateur pour réinitialiser le mot de passe : ");
                            int idUtilisateurMdp = scanner.nextInt();
                            System.out.print("Entrez le nouveau mot de passe : ");
                            scanner.nextLine(); // Consuming newline left-over
                            String nouveauMdp = scanner.nextLine();
                            utilisateurService.oublierMdp(idUtilisateurMdp, nouveauMdp);
                            System.out.println("Mot de passe mis à jour");
                            break;
                        case 8:
                            // Modifier un utilisateur
                            System.out.print("Entrez l'ID de l'utilisateur à modifier : ");
                            int idUtilisateurAModifier = scanner.nextInt();
                            scanner.nextLine(); // Consuming newline left-over
                            Utilisateur utilisateurModifie = new Utilisateur();
                            System.out.print("Nom: ");
                            utilisateurModifie.setNomU(scanner.nextLine());
                            System.out.print("Prénom: ");
                            utilisateurModifie.setPrenomU(scanner.nextLine());
                            System.out.print("Mot de passe: ");
                            utilisateurModifie.setMdp(scanner.nextLine());
                            System.out.print("Email: ");
                            utilisateurModifie.setMailU(scanner.nextLine());
                            System.out.print("Téléphone: ");
                            utilisateurModifie.setTel(String.valueOf(scanner.nextInt()));
                            System.out.print("Statut (true/false): ");
                            utilisateurModifie.setStatut(scanner.nextBoolean());
                            System.out.print("Date de naissance (yyyy-mm-dd): ");
                            scanner.nextLine(); // Consuming newline left-over
                            utilisateurModifie.setDateNaissance(java.sql.Date.valueOf(scanner.nextLine()));

                            System.out.println("Utilisateur modifié");
                            break;
                        case 9:
                            // Ajouter un nouvel abonnement
                            Abonnement newAbonnement = new Abonnement();
                            System.out.print("Montant: ");
                            newAbonnement.setMontant(scanner.nextFloat());
                            System.out.print("Date d'expiration (yyyy-mm-dd): ");
                            scanner.nextLine(); // Consuming newline left-over
                            newAbonnement.setDateExpiration(java.sql.Date.valueOf(scanner.nextLine()));
                            System.out.print("Code promo: ");
                            newAbonnement.setCodePromo(scanner.nextLine());
                            System.out.print("Type d'abonnement: ");
                            newAbonnement.setTypeAbonnement(scanner.nextLine());
                            System.out.print("ID Utilisateur: ");
                            newAbonnement.setIdU(scanner.nextInt());
                            abonnementService.ajouterS(newAbonnement);
                            System.out.println("Nouvel abonnement ajouté avec ID : " + newAbonnement.getIdA());
                            break;
                        case 10:
                            // Modifier un abonnement
                            System.out.print("Entrez l'ID de l'abonnement à modifier : ");
                            int idAbonnementAModifier = scanner.nextInt();
                            scanner.nextLine(); // Consuming newline left-over
                            Abonnement modifiedAbonnement = new Abonnement();
                            System.out.print("Montant: ");
                            modifiedAbonnement.setMontant(scanner.nextFloat());
                            System.out.print("Date d'expiration (yyyy-mm-dd): ");
                            scanner.nextLine(); // Consuming newline left-over
                            modifiedAbonnement.setDateExpiration(java.sql.Date.valueOf(scanner.nextLine()));
                            System.out.print("Code promo: ");
                            modifiedAbonnement.setCodePromo(scanner.nextLine());
                            System.out.print("Type d'abonnement: ");
                            modifiedAbonnement.setTypeAbonnement(scanner.nextLine());
                            System.out.print("ID Utilisateur: ");
                            modifiedAbonnement.setIdU(scanner.nextInt());
                            abonnementService.modifierS(idAbonnementAModifier, modifiedAbonnement);
                            System.out.println("Abonnement modifié");
                            break;
                        case 11:
                            // Consulter un abonnement
                            System.out.print("Entrez l'ID de l'abonnement à consulter : ");
                            int idAbonnementAConsulter = scanner.nextInt();
                            Abonnement abonnement = abonnementService.consulter(idAbonnementAConsulter);
                            if (abonnement != null) {
                                System.out.println("Abonnement consulté : " + abonnement.getTypeAbonnement());
                            } else {
                                System.out.println("Abonnement non trouvé");
                            }
                            break;
                        case 12:
                            // Supprimer un abonnement
                            System.out.print("Entrez l'ID de l'abonnement à supprimer : ");
                            int idAbonnementASupprimer = scanner.nextInt();
                            abonnementService.supprimerS(idAbonnementASupprimer);
                            System.out.println("Abonnement supprimé");
                            break;
                        case 13:
                            // Abonnement personnalisé
                            System.out.print("Entrez l'ID de l'abonnement à personnaliser : ");
                            int idAbonnementPersonnalise = scanner.nextInt();
                            System.out.print("Entrez le nouveau code promo : ");
                            scanner.nextLine(); // Consuming newline left-over
                            String newCodePromo = scanner.nextLine();
                            abonnementService.abonnementPersonnalise(idAbonnementPersonnalise, newCodePromo);
                            System.out.println("Abonnement personnalisé");
                            break;
                        case 14:
                            // Rappel automatique
                            abonnementService.rappelAutomatique();
                            System.out.println("Rappel automatique effectué");
                            break;
                        case 15:
                            // Paiement abonnement
                            System.out.print("Entrez l'ID de l'abonnement pour effectuer le paiement : ");
                            int idAbonnementPaiement = scanner.nextInt();
                            abonnementService.paiementAbonnement(idAbonnementPaiement);
                            System.out.println("Paiement effectué");
                            break;
                        case 16:
                            // Quitter
                            System.out.println("Au revoir!");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Choix invalide. Veuillez réessayer.");
                    }
                }
            } else {
                System.out.println("Erreur : email ou mot de passe invalide");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public static boolean authenticateUser(Connection connection, String email, String password) {
        boolean isAuthenticated = false;
        try {
            // Requête SQL pour vérifier l'utilisateur
            String sql = "SELECT * FROM utilisateur WHERE mailU = ? AND mdp = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            // Exécution de la requête
            ResultSet rs = pstmt.executeQuery();

            // Vérification si l'utilisateur existe
            if (rs.next()) {
                isAuthenticated = true;
            }

            // Fermeture des ressources
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }
}
