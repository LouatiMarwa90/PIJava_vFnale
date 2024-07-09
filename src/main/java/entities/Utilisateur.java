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
    private Date Date_naissance;

    private int is_connected;

    public int getIs_connected() {
        return is_connected;
    }

    public void setIs_connected(int is_connected) {
        this.is_connected = is_connected;
    }

    public void setTel(long tel) {
        this.tel = tel;
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

    public Date Date_naissance() {
        return Date_naissance;
    }

    public void setDateNaissance(Date Date_naissance) {
        this.Date_naissance = Date_naissance;
    }

    public Utilisateur() {
    }

    public Utilisateur(int idU, String nomU, String prenomU, String mdp, String mailU, long tel, boolean statut, Date date_naissance, int is_connected) {
        this.idU = idU;
        this.nomU = nomU;
        this.prenomU = prenomU;
        this.mdp = mdp;
        this.mailU = mailU;
        this.tel = tel;
        this.statut = statut;
        Date_naissance = date_naissance;
        this.is_connected = is_connected;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idU=" + idU +
                ", nomU='" + nomU + '\'' +
                ", prenomU='" + prenomU + '\'' +
                ", mdp='" + mdp + '\'' +
                ", mailU='" + mailU + '\'' +
                ", tel=" + tel +
                ", statut=" + statut +
                ", Date_naissance=" + Date_naissance +
                ", is_connected=" + is_connected +
                '}';
    }
}

