package helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String formatNewsDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Because 'Z' means UTC

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH);

        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return ""; // fallback or return inputDate if you prefer
        }
    }
}
