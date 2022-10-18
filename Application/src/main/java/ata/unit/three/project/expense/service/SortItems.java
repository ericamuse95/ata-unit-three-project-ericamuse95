package ata.unit.three.project.expense.service;

import ata.unit.three.project.expense.dynamodb.ExpenseItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class SortItems implements Comparator <ExpenseItem> {

    @Override
    public int compare(ExpenseItem o1, ExpenseItem o2) {
        Date dateOne = new Date();
        Date dateTwo = new Date();
        try {
            dateOne = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                    .parse(o1.getExpenseDate());
            dateTwo = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                    .parse(o2.getExpenseDate());

            if (dateOne.before(dateTwo)) {
                return -1;
            } else if (dateOne.after(dateTwo)) {
                return 1;
            } else {
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateOne.compareTo(dateTwo);
    }
}