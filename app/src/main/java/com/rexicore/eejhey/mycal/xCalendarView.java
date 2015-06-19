package com.rexicore.eejhey.mycal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Html;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.TranslateAnimation;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Eddie on 6/2/2015.
 * THIS CLASS IS NOT TO BE USED
 */
public class xCalendarView extends TableLayout {

    public int day, month, year;
    private Context context;
    private TextView tv;
    private int monthIds[] = {R.string.Jan, R.string.Feb, R.string.Mar, R.string.Apr, R.string.May, R.string.Jun, R.string.Jul,
                                R.string.Aug, R.string.Sep, R.string.Oct, R.string.Nov, R.string.Dec};
    private String months[] = new String[12];
    private int dayIds[] = {R.string.Sun, R.string.Mon, R.string.Tue, R.string.Wed, R.string.Thu, R.string.Fri, R.string.Sat};
    private String days[] = new String[7];
    private Calendar mCal, prevCal, today;
    private TableRow tr;

    public xCalendarView(Context context) {
        super(context);
        init(context);
    }

    public xCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        Resources res = getResources();
        for( int i = 0; i < 12; i++ ){
            months[i] = res.getString(monthIds[i]);
        }

        setStretchAllColumns(true); // Fit screen
        today = Calendar.getInstance(); // Get current date and time
        today.clear(Calendar.HOUR);
        today.clear(Calendar.MINUTE);
        today.clear(Calendar.SECOND);
        today.clear(Calendar.MILLISECOND);
        mCal = (Calendar) today.clone();

        DisplayMonth();
    }

    private void DisplayMonth(){

        /*TranslateAnimation animSet1, animSet2;
        animSet1 = new TranslateAnimation( 0, getWidth(), 1, 1 );
        animSet2 = new TranslateAnimation( 0, -getWidth(), 1, 1 );
        animSet1.setDuration(300);
        animSet2.setDuration(300);*/

        Resources r = getResources();
        for( int i = 0; i < 7; i++ ){
            days[i] = r.getString(dayIds[i]).substring(0,2);
        }

        removeAllViews();

        int firstDayOfWeek, prevMonthDay, nextMonthDay, week;

        mCal.set( Calendar.DAY_OF_MONTH, 1 );   // First day is the 1st
        firstDayOfWeek = mCal.get(Calendar.DAY_OF_WEEK);    // SUNDAY == 1, SATURDAY == 7
        week = mCal.get(Calendar.WEEK_OF_YEAR); // First week == 1

        prevCal = (Calendar) mCal.clone();
        prevCal.add(Calendar.MONTH, -1);
        prevMonthDay = prevCal.getActualMaximum(Calendar.DAY_OF_MONTH) - firstDayOfWeek;
        nextMonthDay = 1;

        TableRow.LayoutParams lp;
        tr = new TableRow(context);
        tr.setWeightSum(0.7f);
        lp = new TableRow.LayoutParams();
        lp.weight = 0.1f;
        // Naming days of the week
        for( int i = 0; i < 7; i++ ){
            tv = new TextView(context);
            tv.setPadding(10, 3, 10, 3);
            tv.setLayoutParams(lp);
            tv.setTextColor(Color.GRAY);
            tv.setText(days[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            tv.setGravity(Gravity.CENTER);
            tr.addView(tv);
        }
        addView(tr);

        tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        // Listing the days of the calendar
        day = 1;
        lp = new TableRow.LayoutParams();
        lp.weight = 0.1f;
        for( int i = 0; i < 5; i++ ){
            tr = new TableRow(context);
            tr.setWeightSum(0.7f);

            for( int j = 0; j < 7; j++ ){
                tv = new TextView(context);
                tv.setLayoutParams(lp);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tv.setTextColor(Color.DKGRAY);
                if( j < firstDayOfWeek && day == 1 )
                    tv.setText(Html.fromHtml(String.valueOf("<b>" + prevMonthDay++ + "</b>" )));

            }
        }
    }
}
