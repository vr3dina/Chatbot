package chatbot.services.num2str;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Objects;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConvertNumberToString {
    public static void getConvertedNumber(Integer number, boolean showRubles, final Consumer<String> callback){
        NumToStringAPI api = NumToStringService.getApi();
        Call<NumToString> call = api.getStringConvertedNumber(number);
        call.enqueue(new Callback<NumToString>() {
            @Override
            public void onResponse(@NonNull Call<NumToString> call, @NonNull Response<NumToString> response) {
                NumToString result = response.body();
                if (result != null && result.status == 200) {
                    String res = showRubles ? result.convertedNumber : deleteRubles(result.convertedNumber);
                    callback.accept(res);
                }
                else {
                    callback.accept("Не могу конвертировать число!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NumToString> call, @NonNull Throwable t) {
                Log.w("NUM2STRING", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private static String deleteRubles(String convertedNumber) {
        return convertedNumber.substring(0, convertedNumber.indexOf("рубл") - 1);
    }
}
