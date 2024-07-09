package entites;

import java.util.Date;

public class Utilisateur {
    private int idU;
    private String nomU;
    private String prenomU;
    private String mdp;
    private String mailU;
    private long tel;
    private boolean statut;
    private Date dateNaissance;
    private boolean isconnected; // Nouvelle propriété pour l'état de connexion

    // Getters and setters
    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getNomU() {
        return nomU;
    }

    public void setNomU(String nomU) {
        this.nomU = nomU;
    }

    public String getPrenomU() {
        return prenomU;
    }

    public void setPrenomU(String prenomU) {
        this.prenomU = prenomU;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getMailU() {
        return mailU;
    }

    public void setMailU(String mailU) {
        this.mailU = mailU;
    }

    public String getTel() {
        return String.valueOf(tel);
    }

    public void setTel(String tel) {
        this.tel = Long.parseLong(tel);
    }


    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public boolean isConnected() {
        return isconnected;
    }

    public void setConnected(boolean isConnected) {
        this.isconnected = isConnected;
    }
}
