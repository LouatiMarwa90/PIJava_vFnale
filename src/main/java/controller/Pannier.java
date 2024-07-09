package controller;

import com.sun.javafx.menu.MenuItemBase;
import entities.Plat;
import entities.PlatWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import test.MainFx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class Pannier implements Initializable {


    @FXML
    private VBox choixplat;

    @FXML
    private ImageView imageplat;

    @FXML
    private Label nameplat;

    @FXML
    private Label prixplat;
    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;



    private final platlist<Plat> plats = new platlist<>();

    public Pannier() {
    }

    public List<platlist<Plat>> getData(){
     Plat plat = new Plat();
    for (int i=0; i<20; i++) {
        plat = new Plat();
        plat.setNomP("poulet pane");
        plat.setPrixP(20F);
        plat.setImgSrc("@./img/poulet.png");
        plat.setColor("6A7324");
        plat.add(plat);

        plat = new Plat();
        plat.setNomP("poulet avec legume");
        plat.setPrixP(20F);
        plat.setImgSrc("@./img/poulet avec legume.PNG");
        plat.setColor("6A7324");
        plat.add(plat);

        plat = new Plat();
        plat.setNomP("pres poulet");
        plat.setPrixP(25F);
        plat.setImgSrc("@./img/prez avec poulet panné et legume.PNG");
        plat.setColor("6A7324");
        plat.add(plat);

        plat = new Plat();
        plat.setNomP("Jus de fruit");
        plat.setPrixP(18F);
        plat.setImgSrc("@./img/jus de fruit.PNG");
        plat.setColor("6A7324");
        plat.add(plat);

        plat = new Plat();
        plat.setNomP("Jus cooctel");
        plat.setPrixP(18F);
        plat.setImgSrc("@./img/jus cooctel.PNG");
        plat.setColor("6A7324");
        plat.add(plat);


        plat = new Plat();
        plat.setNomP("Jus");
        plat.setPrixP(15F);
        plat.setImgSrc("@./img/jus.PNG");
        plat.setColor("6A7324");
        plat.add(plat);

        plat = new Plat();
        plat.setNomP("salade de fruit");
        plat.setPrixP(25F);
        plat.setImgSrc("@./img/salade de fruit.PNG");
        plat.setColor("6A7324");
        plat.add(plat);

        plat = new Plat();
        plat.setNomP("salade fruit 2");
        plat.setPrixP(25F);
        plat.setImgSrc("@./img/salade fruit 2.PNG");
        plat.setColor("6A7324");
        plat.add(plat);

        plat = new Plat();
        plat.setNomP("salade fruit 3");
        plat.setPrixP(25F);
        plat.setImgSrc("@./img/salade fruit 3.PNG");
        plat.setColor("6A7324");
        plat.add(plat);







    }
    return Collections.singletonList(plats);
    }
    private void setChoixplat(Plat plat) {
        prixplat.setText(MainFx.CURRENCY + plat.getPrixP());
        nameplat.setText(plat.getNomP().toString());
        imageplat = new ImageView(plat.getImgSrc());

        choixplat.getChildren().add(imageplat);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        plats.addAll(getData());
        if (plats.size()>0){
            setChoixplat(plats.get(0));
        }
        int column = 0;
        int row = 1;
        try {
        for (int i = 0; i < plats.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressources/Item.fxmll"));
            fxmlLoader.setLocation(getClass().getResource("/ressources/Item.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            Item item = (Item) fxmlLoader.getController();
            Item.setData(Plat.get(i));

            if (column == 3) {
                column = 0;
                row++;
            }

            grid.add(anchorPane, column++, row);
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);

            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_COMPUTED_SIZE);


            GridPane.setMargin(anchorPane,new Insets(10));
        }
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    private class platlist<T> {
        private List<platlist<T>> data;

        public int size() {
            return 0;
        }

        public void addAll(List<platlist<T>> Data) {
        }

        public T get(int i) {
            return null;
        }
        public void addAll() {
        }

        public void add(platlist<T> plat) {

        }
    }

    public List<PlatWrapper> getData1() {
        List<PlatWrapper> platWrappers = new ArrayList<>();

        Plat plat1 = new Plat("poulet pane", 20F, "@./img/poulet.png", 5);
        Plat plat2 = new Plat("poulet avec legume", 20F, "@./img/poulet avec legume.PNG", 100);
        Plat plat3 = new Plat("pres poulet", 25F, "@./img/prez avec poulet panné et legume.PNG", 10);
        Plat plat4 = new Plat("Jus de fruit", 18F, "@./img/jus de fruit.PNG", 10);
        Plat plat5 = new Plat("Jus cooctel", 18F, "@./img/jus cooctel.PNG", 30);
        Plat plat6 = new Plat("Jus", 15F, "@./img/jus.PNG", 11);
        Plat plat7 = new Plat("salade de fruit", 25F, "@./img/salade de fruit.PNG", 40);
        Plat plat8 = new Plat("salade fruit 2", 25F, "@./img/salade fruit 2.PNG", 50);
        Plat plat9 = new Plat("salade fruit 3", 25F, "@./img/salade fruit 3.PNG", 60);

        platWrappers.add(new PlatWrapper(plat1));
        platWrappers.add(new PlatWrapper(plat2));
        platWrappers.add(new PlatWrapper(plat3));
        platWrappers.add(new PlatWrapper(plat4));
        platWrappers.add(new PlatWrapper(plat5));
        platWrappers.add(new PlatWrapper(plat6));
        platWrappers.add(new PlatWrapper(plat7));
        platWrappers.add(new PlatWrapper(plat8));
        platWrappers.add(new PlatWrapper(plat9));

        return platWrappers;
    }


}





