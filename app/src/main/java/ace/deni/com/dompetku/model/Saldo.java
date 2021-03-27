package ace.deni.com.dompetku.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name = "Saldo")
public class Saldo extends Model{
    @Column (name = "amount")
    private double amoutn;

    @Column (name = "lastUpdate")
    private Date lastUpdate;

    public Saldo(){}

    public Saldo (double amoutn, Date lastUpdate){
        this.amoutn = amoutn;
        this.lastUpdate = lastUpdate;
    }

    public double getAmoutn(){return amoutn;}

    public void setAmoutn(double amoutn){this.amoutn = amoutn;}

    public Date getLastUpdate (){return lastUpdate;}

    public void setLastUpdate (Date lastUpdate){this.lastUpdate = lastUpdate;}
}
