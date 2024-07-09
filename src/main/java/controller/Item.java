package controller;

import entities.Plat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import test.MainFx;

public class Item {
    
        @javafx.fxml.FXML
        private ImageView imageplat;

        @javafx.fxml.FXML
        private Label nameplat;

        @FXML
        private Label prixplat;

        private Plat plat;

    public static void setData(Object o) {
    }

    public void setData(Plat plat) {
            this.plat = plat;
            nameplat.setText(plat.getNomP());
            prixplat.setText(MainFx.CURRENCY + plat.getPrixP());
            Image image = new Image(getClass().getResourceAsStream(plat.getImgSrc()));
            imageplat.setImage(image);


        }

    }




