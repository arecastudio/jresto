package app.ctrl;

import app.model.DataKategoriProduk;
import app.model.DataProduk;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by android on 11/23/17.
 */
public class ProdukModify {
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private DBHelper helper;
    private String sql="";

    public ProdukModify(){
        conn=helper.Konek();
    }

    public ObservableList<DataProduk> SetTableItem(){
        ObservableList<DataProduk> data= FXCollections.observableArrayList();
        sql="SELECT p.id,UCASE(p.nama),p.harga,p.gambar,p.kategori_id,k.kategori,k.jenis,p.waktu_buat,p.waktu_ubah,p.operator,p.tambahan FROM produk AS p LEFT OUTER JOIN kategori AS k ON k.id=p.kategori_id ORDER BY p.id ASC;";
        try {
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                DataProduk dp=new DataProduk();
                dp.setId(rs.getInt(1));
                dp.setNama(rs.getString(2));
                dp.setHarga(rs.getDouble(3));
                dp.setKategori_id(rs.getInt(5));
                dp.setKategori(rs.getString(6));
                dp.setJenis(rs.getString(7));
                dp.setTambahan(rs.getString(11));
                data.add(dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ObservableList<DataKategoriProduk> SetComboItem(){
        ObservableList<DataKategoriProduk> data=FXCollections.observableArrayList();
        sql="SELECT id,jenis,kategori FROM kategori ORDER BY id ASC;";
        try {
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                DataKategoriProduk dkp=new DataKategoriProduk();
                dkp.setId(rs.getInt(1));
                dkp.setJenis(rs.getString(2));
                dkp.setKategori(rs.getString(3));
                data.add(dkp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int Simpan(DataProduk dp){
        int ret=0;
        sql="INSERT IGNORE INTO produk(nama,harga,kategori_id,tambahan,gambar)VALUES(?,?,?,?,?);";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,dp.getNama());
            pst.setDouble(2,dp.getHarga());
            pst.setInt(3,dp.getKategori_id());
            pst.setString(4,dp.getTambahan());

            pst.setBinaryStream(5,(InputStream)dp.getGambar());

            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  ret;
    }

    public int Ubah(DataProduk dp){
        int ret=0;
        sql="UPDATE produk SET nama=?,harga=?,kategori_id=?,tambahan=? WHERE id=?;";

        if (dp.getGambar()!=null)sql="UPDATE produk SET nama=?,harga=?,kategori_id=?,tambahan=?,gambar=? WHERE id=?;";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,dp.getNama());
            pst.setDouble(2,dp.getHarga());
            pst.setInt(3,dp.getKategori_id());
            pst.setString(4,dp.getTambahan());

            if (dp.getGambar()!=null){
                pst.setBinaryStream(5,(InputStream)dp.getGambar());
                pst.setInt(6,dp.getId());
            }else pst.setInt(5,dp.getId());

            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int Hapus(int id){
        int ret=0;
        sql="DELETE FROM produk WHERE id=?";
        try {
            pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public byte[] GetImage(String id) {
        byte[] b=null;
        try {
            String sql = "SELECT gambar FROM produk WHERE id=?;";
            pst=conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(id));
            rs=pst.executeQuery();
            if (rs.next()){
                b=rs.getBytes(1);
            }
            pst.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return b;
    }
}
