package app.ctrl;

import app.model.DataKategoriProduk;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KategoriProdukModify {
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private DBHelper helper;
    private String sql="";
    
    public KategoriProdukModify(){
        conn=helper.Konek();
    }

    public ObservableList<DataKategoriProduk> SetTableItem(){
        ObservableList<DataKategoriProduk> data= FXCollections.observableArrayList();
        sql="SELECT id,jenis,kategori FROM kategori ORDER BY jenis ASC,jenis ASC;";
        try {
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                DataKategoriProduk dp=new DataKategoriProduk();
                dp.setId(rs.getInt(1));
                dp.setJenis(rs.getString(2));
                dp.setKategori(rs.getString(3));
                data.add(dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int Simpan(DataKategoriProduk dkm){
        int ret=0;
        sql="INSERT IGNORE INTO kategori(jenis,kategori)VALUES(?,?);";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,dkm.getJenis());
            pst.setString(2,dkm.getKategori());
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int Ubah(DataKategoriProduk dkm){
        int ret=0;
        sql="UPDATE kategori SET jenis=?,kategori=? WHERE id=?;";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,dkm.getJenis());
            pst.setString(2,dkm.getKategori());
            pst.setInt(3,dkm.getId());
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int Hapus(int id){
        int ret=0;
        sql="DELETE FROM kategori WHERE id=?;";
        try {
            pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
