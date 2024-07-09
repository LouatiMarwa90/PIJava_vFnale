package entities;

import javafx.beans.property.*;

public class PlatWrapper {
    private final Plat plat;

    private final IntegerProperty idP;
    private final StringProperty nomP;
    private final FloatProperty prixP;
    private final IntegerProperty calories;

    public PlatWrapper(Plat plat) {
        this.plat = plat;
        this.idP = new SimpleIntegerProperty(plat.getIdP());
        this.nomP = new SimpleStringProperty(plat.getNomP());
        this.prixP = new SimpleFloatProperty(plat.getPrixP());
        this.calories = new SimpleIntegerProperty(plat.getCalories());
    }

    public int getIdP() {
        return idP.get();
    }

    public IntegerProperty idPProperty() {
        return idP;
    }

    public String getNomP() {
        return nomP.get();
    }

    public StringProperty nomPProperty() {
        return nomP;
    }

    public float getPrixP() {
        return prixP.get();
    }

    public FloatProperty prixPProperty() {
        return prixP;
    }

    public int getCalories() {
        return calories.get();
    }

    public IntegerProperty caloriesProperty() {
        return calories;
    }

    public Plat getPlat() {
        return plat;
    }
}



