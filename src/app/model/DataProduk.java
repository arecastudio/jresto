package app.model;

/**
 * Created by android on 11/23/17.
 */
public class DataProduk {
    private int id,kategori_id;
    private String nama,waktu_buat,waktu_ubah,operator,tambahan,kategori,jenis;
    private double harga;
    private Object gambar;

    public DataProduk(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(int kategori_id) {
        this.kategori_id = kategori_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWaktu_buat() {
        return waktu_buat;
    }

    public void setWaktu_buat(String waktu_buat) {
        this.waktu_buat = waktu_buat;
    }

    public String getWaktu_ubah() {
        return waktu_ubah;
    }

    public void setWaktu_ubah(String waktu_ubah) {
        this.waktu_ubah = waktu_ubah;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTambahan() {
        return tambahan;
    }

    public void setTambahan(String tambahan) {
        this.tambahan = tambahan;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public Object getGambar() {
        return gambar;
    }

    public void setGambar(Object gambar) {
        this.gambar = gambar;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
