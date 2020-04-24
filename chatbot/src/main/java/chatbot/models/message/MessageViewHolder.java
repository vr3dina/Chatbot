package chatbot.models.message;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import chatbot.R;

class MessageViewHolder extends RecyclerView.ViewHolder {

    private TextView messageText;
    private TextView messageDate;

    MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.messageTextView);
        messageDate = itemView.findViewById(R.id.messageDateView);
    }

    void bind(Message message) {
        messageText.setText(message.text);
        DateFormat fmt = new SimpleDateFormat("dd.MM hh:mm", new Locale("rus"));
        messageDate.setText(fmt.format(message.date));
    }
}
