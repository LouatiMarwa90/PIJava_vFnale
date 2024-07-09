
package entities;

public class Equipment {
    private int idEq;
    private String nomEq;
    private String descEq;
    private String documentation;
    private byte[] imageEq; // Image is stored as a byte array
    private int noteEq;
    private EquipmentCategory categorie;

    // Constructor for fully initializing an Equipment object
    public Equipment(String nomEq, String descEq, String documentation, byte[] imageEq, EquipmentCategory categorie, int noteEq) {
        this.nomEq = nomEq;
        this.descEq = descEq;
        this.documentation = documentation;
        this.imageEq = imageEq; // Assign the byte array directly
        this.noteEq = noteEq;
        this.categorie = categorie;
    }

    // Default constructor
    public Equipment() {
    }

    // Overloaded constructor that also sets the ID
    public Equipment(String nomEq, String descEq, String documentation, byte[] imageEq, EquipmentCategory categorie, int noteEq, int idEq) {
        this.nomEq = nomEq;
        this.descEq = descEq;
        this.documentation = documentation;
        this.imageEq = imageEq;
        this.noteEq = noteEq;
        this.categorie = categorie;
        this.idEq = idEq;
    }

    // Getters and setters
    public int getIdEq() {
        return idEq;
    }

    public void setIdEq(int idEq) {
        this.idEq = idEq;
    }

    public String getNomEq() {
        return nomEq;
    }

    public void setNomEq(String nomEq) {
        this.nomEq = nomEq;
    }

    public String getDescEq() {
        return descEq;
    }

    public void setDescEq(String descEq) {
        this.descEq = descEq;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public byte[] getImageEq() {
        return imageEq;
    }

    public void setImageEq(byte[] imageEq) {
        this.imageEq = imageEq;
    }

    public int getNoteEq() {
        return noteEq;
    }

    public void setNoteEq(int noteEq) {
        this.noteEq = noteEq;
    }

    public EquipmentCategory getCategorie() {
        return categorie;
    }

    public void setCategorie(EquipmentCategory categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        // Simplified to avoid printing binary data
        return "Equipment{" +
                "idEq=" + idEq +
                ", nomEq='" + nomEq + '\'' +
                ", descEq='" + descEq + '\'' +
                ", documentation='" + documentation + '\'' +
                ", imageEq=[data]" + // Indicate that image data exists but do not print it
                ", noteEq=" + noteEq +
                ", categorie=" + categorie +
                '}';
    }
}