package app.ctrl;

import app.model.DataNonTunai;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NonTunaiModify {
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private DBHelper helper;
    private String sql="";

    public NonTunaiModify(){conn=helper.Konek();}

    public ObservableList<DataNonTunai> SetTableItem(){
        ObservableList<DataNonTunai> data= FXCollections.observableArrayList();
        sql="SELECT id,nama FROM non_tunai ORDER BY nama ASC;";
        try {
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                DataNonTunai dp=new DataNonTunai();
                dp.setId(rs.getInt(1));
                dp.setNama(rs.getString(2));
                data.add(dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int Simpan(DataNonTunai dkm){
        int ret=0;
        sql="INSERT IGNORE INTO non_tunai(nama)VALUES(?);";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,dkm.getNama());
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int Ubah(DataNonTunai dkm){
        int ret=0;
        sql="UPDATE non_tunai SET nama=? WHERE id=?;";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,dkm.getNama());
            pst.setInt(2,dkm.getId());
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int Hapus(int id){
        int ret=0;
        sql="DELETE FROM non_tunai WHERE id=?;";
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
