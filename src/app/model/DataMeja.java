package app.model;

public class DataMeja {
    private String nomor,status;
    private int meja_kat_id;

    public DataMeja(){}

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMeja_kat_id() {
        return meja_kat_id;
    }

    public void setMeja_kat_id(int meja_kat_id) {
        this.meja_kat_id = meja_kat_id;
    }
}
