package entities;

import java.util.Objects;
import entities.*;

    public class Reservation {
        private int idRS;
        private String name, prenom, email;
        private sexe sexe;
        private float tel;
        private  etat etat;
        private Utilisateur idU;
        private Seance seance;

        public Reservation(Utilisateur idU, String name, String prenom, String email, Reservation.sexe sexe, Reservation.etat etat) {

            this.name = name;
            this.prenom = prenom;
            this.email = email;
            this.sexe = sexe;
            this.etat=etat;


            this.idU = idU;
        }


        public enum sexe {homme, femme};

        public enum etat {EN_COURS, CONFIRMEE, ANNULEE};

        public Reservation(int idRS, String name, String prenom, String email, Reservation.sexe sexe, float tel,Reservation.etat etat, Utilisateur idU) {
            this.idRS = idRS;
            this.name = name;
            this.prenom = prenom;
            this.email = email;
            this.sexe = sexe;
            this.etat=etat;
            this.tel = tel;

            this.idU = idU;
        }

        public Reservation(String name, String prenom, String email, Reservation.sexe sexe, Reservation.etat etat, Utilisateur idU) {
            this.name = name;
            this.prenom = prenom;
            this.email = email;
            this.sexe = sexe;
            this.etat = etat;
            this.idU = idU;
        }

        public int getIdRS() {
            return idRS;
        }

        public void setIdRS(int idRS) {
            this.idRS = idRS;
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

        public Reservation.sexe getSexe() {
            return sexe;
        }

        public void setSexe(Reservation.sexe sexe) {
            this.sexe = sexe;
        }

        public float getTel() {
            return tel;
        }

        public void setTel(float tel) {
            this.tel = tel;
        }

        public Reservation.etat getEtat() {
            return etat;
        }

        public void setEtat(Reservation.etat etat) {
            this.etat = etat;
        }

        public Utilisateur getIdU() {
            return idU;
        }

        public void setIdU(Utilisateur idU) {
            this.idU = idU;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Reservation that = (Reservation) o;
            return idRS == that.idRS && Float.compare(tel, that.tel) == 0 && Objects.equals(name, that.name) && Objects.equals(prenom, that.prenom) && Objects.equals(email, that.email) && sexe == that.sexe && etat == that.etat && Objects.equals(idU, that.idU);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idRS, name, prenom, email, sexe, tel, etat, idU);
            
        }

        @Override
        public String toString() {
            return "Reservation{" +
                    "idRS=" + idRS +
                    ", name='" + name + '\'' +
                    ", prenom='" + prenom + '\'' +
                    ", email='" + email + '\'' +
                    ", sexe=" + sexe +
                    ", tel=" + tel +
                    ", etat=" + etat +
                    ", idU=" + idU +
                    '}';
        }
        
    }

