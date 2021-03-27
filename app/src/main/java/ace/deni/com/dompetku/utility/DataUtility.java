package ace.deni.com.dompetku.utility;

import ace.deni.com.dompetku.model.Transaksi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataUtility {

    public static List<Transaksi>getlistDummyTransaksi(){
    List<Transaksi> list = new ArrayList<>();
        Transaksi txn = new Transaksi(new Date(),
                0,"beli pulsa dan paket data internet",
                "beli pulsa hp dan paket data internet",
                100000);
        txn = new Transaksi(new Date(),
                0,"bayar kontrakan bulan oktober",
                "bayar kontrakan bulan oktober",
                600000);
        list.add(txn);
        txn = new Transaksi(new Date(),
                0,"makan jajan hari selasa",
                "makan dan jajan hari selasa",
                500000);
        list.add(txn);
        txn = new Transaksi(new Date(),
                1,"gaji bulan oktober",
                "gaji bulan oktober",
                4500000);
        list.add(txn);
        txn = new Transaksi(new Date(),
                0,"bayar kas fotokopyan",
                "bayar kas foto kopyan",
                20000);
        list.add(txn);
        txn = new Transaksi(new Date(),
                0,"traktir temen ulang tahun",
                "traktir temen ulang tahun",
                150000);
        list.add(txn);
        txn = new Transaksi(new Date(),
                0,"bayr cicilan mototr",
                "bayar cicilan motor",
                850000);
        list.add(txn);
        txn = new Transaksi(new Date(),
                0,"beli ram laptop",
                "beli ram laptop",
                425000);
        list.add(txn);
        return list;
    }

}
