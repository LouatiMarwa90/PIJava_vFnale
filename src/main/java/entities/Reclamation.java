package entities;

public class Reclamation {


    private int idRec;
    private String categorieR;
    private String description;
    private int idU;
    private String etat;
    private boolean deleted;

    public Reclamation(String nString, String string, int anInt, String string1) {
    }

    public boolean Deleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    private Utilisateur Utilisateur;

    public entities.Utilisateur getUtilisateur() {
        return Utilisateur;
    }

    public void setUtilisateur(entities.Utilisateur utilisateur) {
        Utilisateur = utilisateur;
    }


    public Reclamation() {
    }

    public Reclamation(String categorieR, String description, int idU, String etat, Utilisateur utilisateur) {
        this.idRec = idRec;
        this.categorieR = categorieR;
        this.description = description;
        this.idU = idU;
        this.etat = etat;
        Utilisateur = utilisateur;
    }



    public int getIdRec() {
        return idRec;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public String getCategorieR() {
        return categorieR;
    }

    public void setCategorieR(String categorieR) {
        this.categorieR = categorieR;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "idRec=" + idRec +
                ", categorieR='" + categorieR + '\'' +
                ", description='" + description + '\'' +
                ", idU=" + idU +
                ", etat='" + etat + '\'' +
                '}';
    }
}

