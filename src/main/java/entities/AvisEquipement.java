package entities;

public class AvisEquipement {
    private int idA;
    private String commentaire;
    private int idU;
    private int idEq; // Nouvel attribut pour relier avec la table equipment

    public AvisEquipement(String commentaire, int idU, int idEq) {
        this.commentaire = commentaire;
        this.idU = idU;
        this.idEq = idEq;
    }

    public AvisEquipement() {
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public int getIdEq() {
        return idEq;
    }

    public void setIdEq(int idEq) {
        this.idEq = idEq;
    }
}
