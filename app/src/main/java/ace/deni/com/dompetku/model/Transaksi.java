package ace.deni.com.dompetku.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name = "Transaksi")
public class Transaksi extends Model {
    @Column (name = "Date")
    private Date date;

    @Column (name = "Type")
    private int type;

    @Column (name = "Title")
    private String title;

    @Column (name = "Description")
    private String description;

    @Column (name = "Amount")
    private double amount;

    public Transaksi(){

    }

    public Transaksi(Date date, int type, String title, String description, double amount){
        this.date = date;
        this.type = type;
        this.title = title;
        this.description = description;
        this.amount = amount;
    }

    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }

    public int getType(){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }

    public String getTitle(){
        return  title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public double getAmount(){
        return amount;
    }
    public void setAmount(double amount){
        this.amount = amount;
    }


}
