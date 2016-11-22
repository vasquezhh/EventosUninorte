package com.events.uninorte.Utils;


import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by hainerv on 9/10/16.
 */

public class Utils {

    public static String date(String _date) {
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        return date.format(String2Date(_date));
    }

    public static String date(Date _date, String format) {
        DateFormat date = new SimpleDateFormat(format);
        return date.format(_date);
    }

    public static Date String2Date(String _date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = format.parse(_date);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date String2DateSpecial(String _date, String _format) {
        SimpleDateFormat format = new SimpleDateFormat(_format);
        try {
            Date date = format.parse(_date);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }
}
