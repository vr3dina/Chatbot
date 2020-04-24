package chatbot.services.forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastApi {
    @GET("/current?access_key=d9e548947559ac59dfecbd1c3c9a1ad9")
    Call<Forecast> getCurrentWeather(@Query("query") String city);
}
