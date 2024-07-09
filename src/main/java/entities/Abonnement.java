package entities;

import java.sql.ResultSet;
import java.util.Date;

public class Abonnement {
    private int idA;
    private float montant;
    private Date dateExpedition;
    private String codePromo;
    private String typeAbonnement;
    private int idU;


    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public Date getDateExpedition() {
        return dateExpedition;
    }

    public void setDateExpedition(Date dateExpedition) {
        this.dateExpedition = dateExpedition;
    }

    public String getCodePromo() {
        return codePromo;
    }

    public void setCodePromo(String codePromo) {
        this.codePromo = codePromo;
    }

    public String getTypeAbonnement() {
        return typeAbonnement;
    }

    public void setTypeAbonnement(String typeAbonnement) {
        this.typeAbonnement = typeAbonnement;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public ResultSet getDateExpiration() {
        return null;
    }
}
