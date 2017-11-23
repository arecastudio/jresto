package app.ctrl;

import app.model.DataProduk;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        sql="SELECT p.id,UCASE(p.nama),p.harga,p.gambar,p.kategori_id,k.kategori,p.waktu_buat,p.waktu_ubah,p.operator,p.tambahan FROM produk AS p LEFT OUTER JOIN kategori AS k ON k.id=p.kategori_id ORDER BY p.id ASC;";
        try {
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                DataProduk dp=new DataProduk();
                dp.setId(rs.getInt(1));
                dp.setNama(rs.getString(2));
                dp.setHarga(rs.getDouble(3));
                dp.setKategori(rs.getString(6));
                dp.setTambahan(rs.getString(10));
                data.add(dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
