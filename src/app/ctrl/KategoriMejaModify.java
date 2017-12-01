package app.ctrl;

import app.model.DataKategoriMeja;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KategoriMejaModify {
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private DBHelper helper;
    private String sql="";
    
    public KategoriMejaModify(){conn=helper.Konek();}

    public ObservableList<DataKategoriMeja> SetTableItem(){
        ObservableList<DataKategoriMeja> data= FXCollections.observableArrayList();
        sql="SELECT id,nama,tarif FROM meja_kat ORDER BY nama ASC;";
        try {
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                DataKategoriMeja dp=new DataKategoriMeja();
                dp.setId(rs.getInt(1));
                dp.setNama(rs.getString(2));
                dp.setTarif(rs.getDouble(3));
                data.add(dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int Simpan(DataKategoriMeja dkm){
        int ret=0;
        sql="INSERT IGNORE INTO meja_kat(nama,tarif)VALUES(?,?);";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,dkm.getNama());
            pst.setDouble(2,dkm.getTarif());
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int Ubah(DataKategoriMeja dkm){
        int ret=0;
        sql="UPDATE meja_kat SET nama=?,tarif=? WHERE id=?;";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,dkm.getNama());
            pst.setDouble(2,dkm.getTarif());
            pst.setInt(3,dkm.getId());
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int Hapus(int id){
        int ret=0;
        sql="DELETE FROM meja_kat WHERE id=?;";
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
