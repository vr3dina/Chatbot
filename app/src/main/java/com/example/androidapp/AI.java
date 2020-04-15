package com.example.androidapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class AI {

    private static Map<String, String> answers =  new HashMap<String, String>() {{
        put("привет", "Привет");
        put("как дела", "Не плохо");
        put("чем занимаешься", "Отвечаю на вопросы");
        put("какой сегодня день", getCurrentDay());
        put("который час", getTime());
        put("какой день недели", getDayOfWeek());
        put("сколько дней до лета", getDaysToSummer());
    }};

    @RequiresApi(api = Build.VERSION_CODES.N)
    static void getAnswer(String question, final Consumer<String> callback) {
        final List<String> ans = answers.keySet()
                .stream()
                .filter(question::contains)
                .map(t -> answers.get(t))
                .collect(Collectors.toList());
        Collections.reverse(ans);

        Pattern cityPattern = Pattern.compile("погода в городе (\\p{L}+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = cityPattern.matcher(question);
        if (matcher.find()) {
            String cityName = matcher.group(1);

            ForecastToString.getForecast(cityName, new Consumer<String>() {
                @Override
                public void accept(String s) {
                    ans.add(s);
                    callback.accept(String.join(", ", ans));
                }
            });
        }
        callback.accept(String.join(", ", ans));
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

    private static  String getDayOfWeek() {
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