package entities;

public class Categorieplat {
    private int idC;
    private String nomC;
    private Boolean etatC;
    private String descC;

    public Categorieplat( int idC, String nomC, String descC, Boolean etatC) {
        this.idC = idC;
        this.nomC = nomC;
        this.descC = descC;
        this.etatC = etatC;
    }

    public Categorieplat() {

    }


    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public String getNomC() {
        return nomC;
    }

    public void setNomC(String nomC) {
        this.nomC = nomC;
    }

    public String getDescC() {
        return descC;
    }

    public void setDescC(String descC) {
        this.descC = descC;
    }

    public Boolean getetatC() {
        return etatC;
    }

    public void setetatC(Boolean etatC) {
        this.etatC = etatC;
    }

    @Override
    public String toString() {
        return "Plat{" +
                "idC=" + idC +
                ", nomC='" + nomC + '\'' +
                ", descC='" + descC + '\'' +
                ", etatC=" + etatC +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        entities.Plat plat = (Plat) o;
        return idC == plat.getIdP();
    }

    @Override
    public int hashCode() {
        return idC;
    }
}


