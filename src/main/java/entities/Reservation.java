package entities;

import java.util.Objects;

public class Reservation {
    private int idRs;
    private String name,prenom,email;
    private float tel;
    private enum sexe{HOMME,FEMME};
    static String etat="En cours";
    private int idU;
    private Seance seance;

    public Reservation(int idRs, String name, String prenom, String email, float tel, int idU, Seance seance) {
        this.idRs = idRs;
        this.name = name;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.idU = idU;
        this.seance = seance;
    }

    public int getIdRs() {
        return idRs;
    }

    public void setIdRs(int idRs) {
        this.idRs = idRs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getTel() {
        return tel;
    }

    public void setTel(float tel) {
        this.tel = tel;
    }

    public static String getEtat() {
        return etat;
    }

    public static void setEtat(String etat) {
        Reservation.etat = etat;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idRs=" + idRs +
                ", name='" + name + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", tel=" + tel +
                ", idU=" + idU +
                ", seance=" + seance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return idRs == that.idRs && Float.compare(tel, that.tel) == 0 && idU == that.idU && Objects.equals(name, that.name) && Objects.equals(prenom, that.prenom) && Objects.equals(email, that.email) && Objects.equals(seance, that.seance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRs, name, prenom, email, tel, idU, seance);
    }
}


