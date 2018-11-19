package com.agnitio.calendar.interfaces;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalModConstants {

    public static class PageParams {
        public static String THEME_TYPE_APP = "app";
        public static String THEME_TYPE_DEFAULT = "default";
        public static String BUNDLE_PARAMS_CLIENT_ID = "client_id";
        public static String BUNDLE_PARAMS_LOGIN_TYPE = "login_type";
        public static String BUNDLE_PARAMS_THEME_COLOR_PRIMARY = "theme_color_primary";
        public static String BUNDLE_PARAMS_THEME_COLOR_PRIMARY_DARK = "theme_color_primary_dark";
        public static String BUNDLE_PARAMS_THEME_COLOR_ACCENT = "theme_color_accent";
        public static String BUNDLE_PARAMS_USER_TYPE = "user_type";
        public static String BUNDLE_PARAMS_USER_ID = "user_id";
        public static String BUNDLE_PARAMS_THEME_TYPE = "theme_type";
    }

    public static final class MenuConstants {
        public static final String MENU_TITLE_HOME = "Home";
        public static final String MENU_TITLE_DAY = "Day";
        public static final String MENU_TITLE_MONTH = "Month";
        public static final String MENU_TITLE_CALENDAR = "Calendar";
    }
    public static final class FragmentConstants{
        public static final String FRAGMENT_TAG_HOME = "home_fragment";
        public static final String FRAGMENT_TAG_DAY = "day_fragment";
        public static final String FRAGMENT_TAG_MONTH = "month_fragment";
        public static final String FRAGMENT_TAG_CALENDAR = "calendar_fragment";

        public static final String FRAGMENT_NAME_HOME="CalModHomeFragment";
        public static final String FRAGMENT_NAME_DAY="CalModDayFragment";
        public static final String FRAGMENT_NAME_MONTH="CalModMonthFragment";
        public static final String FRAGMENT_NAME_CALENDAR="CalModCalendarFragment";
    }
    public static final class DateTimeFormatter {

        public static final String converttodateformat(String format, Date date){
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(date);
        }

        public static final String converttotimeformat(String format,Date date){
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(date);

        }
    }


    public static final class DialogConstants{
        public static int CATEGORY_POSITION=-1;

        public static int SUB_CATEGORY_POSITION=-1;

        public static int PAYMENT_METHOD_POSITION=-1;

        public static String PAYMENT_DESCRIPTION="";

        public static String PAYMENT_AMOUNT="";
    }




}
