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
}
