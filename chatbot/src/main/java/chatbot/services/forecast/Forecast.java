package chatbot.services.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

class Forecast implements Serializable  {
    @SerializedName("current")
    @Expose
    Weather current;

    public class Weather{
        @SerializedName("temperature")
        @Expose
        Integer temperature;

        @SerializedName("weather_descriptions")
        @Expose
        List<String> weather_descriptions;
    }
}
