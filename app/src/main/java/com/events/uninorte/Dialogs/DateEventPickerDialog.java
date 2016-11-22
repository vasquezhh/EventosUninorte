package com.events.uninorte.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by hainerv on 31/10/16.
 */

public class DateEventPickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Fragment parent;

    public DateEventPickerDialog(Fragment parent) {
        this.parent = parent;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        System.out.println("YEAR: " + year);
        System.out.println("MONTH: " + monthOfYear);
        System.out.println("DAY: " + dayOfMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        ((DatePickerListener) parent).setDate(calendar.getTime());
    }

    public interface DatePickerListener {
        void setDate(Date date);
    }
}
