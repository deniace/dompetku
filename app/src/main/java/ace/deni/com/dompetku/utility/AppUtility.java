package ace.deni.com.dompetku.utility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppUtility {
    SimpleDateFormat df = new SimpleDateFormat("EEEE");
    SimpleDateFormat df1 = new SimpleDateFormat("dd MMM yyyy");
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.GERMANY);
    DecimalFormat dec = new DecimalFormat("#,###.##", symbols);

    public String getDayFormat(Date date){
        return df.format(date);
    }

    public String getFormateDate(Date date){
        return df1.format(date);
    }

    public Date getDateFormat(String text)throws ParseException{
        return df1.parse(text);
    }

    public String formatDecimal(Double data){
        return dec.format(data);
    }
}
