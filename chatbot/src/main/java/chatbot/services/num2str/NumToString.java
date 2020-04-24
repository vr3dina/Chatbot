package chatbot.services.num2str;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class NumToString implements Serializable {
    @SerializedName("status")
    @Expose
    Integer status;

    @SerializedName("str")
    @Expose
    String convertedNumber;
}
