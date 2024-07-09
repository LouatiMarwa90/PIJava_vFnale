package entities;

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
    private String Sexe;

    public Utilisateur() {
    }

    public Utilisateur(String nomU, String prenomU, String mailU, String sexe) {
        this.nomU = nomU;
        this.prenomU = prenomU;
        this.mailU = mailU;
        Sexe = sexe;
    }

    public Utilisateur(int idU, String nomU, String prenomU, String mailU, String sexe) {
        this.idU = idU;
        this.nomU = nomU;
        this.prenomU = prenomU;
        this.mailU = mailU;
        Sexe = sexe;
    }

    public String getSexe() {
        return Sexe;
    }

    public void setSexe(String sexe) {
        Sexe = sexe;
    }

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

    public long getTel() {
        return tel;
    }

    public void setTel(long tel) {
        this.tel = tel;
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

    @Override
    public String toString() {
        return ""+ idU ;

    }
}