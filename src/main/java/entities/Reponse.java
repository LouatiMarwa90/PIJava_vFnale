package entities;

public class Reponse {
    private int idRep;
    private String reponse;
    private int idRec;

    private Reclamation reclamation;

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }



    public Reponse() {
        this.idRep = idRep;
        this.reponse = reponse;
        this.idRec = idRec;
        this.reclamation = reclamation;
    }

    public Reponse(String mouaa, Reclamation i) {

    }


    public int getIdRep() {
        return idRep;
    }

    public void setIdRep(int idRep) {
        this.idRep = idRep;
    }


    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public int getIdRec() {
        return idRec;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "idRep=" + idRep +
                ", reponse='" + reponse + '\'' +
                ", idRec=" + idRec +
                ", reclamation=" + (reclamation != null ? reclamation.getIdRec() : "null") +
                '}';
    }
}
