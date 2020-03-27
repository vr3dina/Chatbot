package com.example.androidapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class AI {

    private static Map<String, String> answers =  new HashMap<String, String>() {{
        put("привет", "Привет");
        put("приветик", "Привет");
        put("как дела?", "Не плохо");
        put("чем занимаешься?", "Отвечаю на вопросы");
        put("а чем занимаешься?", "Отвечаю на вопросы");
        put("какой сегодня день", getCurrentDay());
        put("который час", getTime());
        put("какой день недели", getDayOfWeek());
        put("сколько дней до лета", getDaysToSummer());
    }};


    static String getAnswer(String question) {
        String ans = answers.get(question.toLowerCase());
        return ans == null ? "Вопрос понял. Думаю..." : ans;
    }

    private static String getTime() {
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", new Locale("ru"));
        return dateFormat.format(now);
    }

    private static String getCurrentDay() {
        Date now = new Date();
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", new Locale("ru"));
        return dateFormat.format(now);
    }

    private static String getDayOfWeek() {
        Date now = new Date();
        DateFormat format2=new SimpleDateFormat("EEEE", new Locale("ru"));
        return format2.format(now);
    }

    private static String getDaysToSummer() {
        Date now = new Date();
        Date june = new GregorianCalendar(2020, Calendar.JUNE, 1).getTime();
        long diff = getDateDiff(now, june);
        return Long.toString(diff);
    }

    private static long getDateDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}