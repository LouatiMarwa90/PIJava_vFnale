package controller;

import entities.Plat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.ServicesPlat;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class AjouterPlat {

    @FXML
    private TextField CaloriesPlatField;

    @FXML
    private TextField NomPlatField;

    @FXML
    private TextField alergiePlatField;

    @FXML
    private TextArea descfield;

    @FXML
    private CheckBox etatPlatCheckbox;

    @FXML
    private TextField prixPlatField;

    // Email configuration
   /* private static final String USERNAME = "moumnimaher86@gmail.com";
    private static final String PASSWORD = "07377371moumni";*/

    @FXML
    void Ajout(ActionEvent event) {
        String name = NomPlatField.getText();
        String description = descfield.getText();
        String calories = CaloriesPlatField.getText();
        String allergens = alergiePlatField.getText();
        String price = prixPlatField.getText();
        boolean available = etatPlatCheckbox.isSelected();

        if (name.isEmpty() || description.isEmpty() || calories.isEmpty() || allergens.isEmpty() || price.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return;
        }

        try {
            Plat p = new Plat(name, Float.parseFloat(price), description, allergens, false, Integer.parseInt(calories));
            new ServicesPlat().ajouter(p);

            // Send an email
            String messageBody = "Dish added: \nName: " + name + "\nDescription: " + description +
                    "\nCalories: " + calories + "\nAllergens: " + allergens + "\nPrice: " + price +
                    "\nAvailable: " + available;

           // sendEmail("medkhaled.hamrouni@esprit.tn", "Dish Added", messageBody);

            showAlert("Dish added and email sent: \n" + messageBody);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            showAlert("Donnees eronnes !");
        }
    }

    /* private void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }*/

    @FXML
    void goBack(ActionEvent event) {
        showAlert("Going back to the previous screen!");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
