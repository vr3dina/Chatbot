package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    protected Button sendButton;
    protected EditText questionText;
    protected RecyclerView chatMessageList;
    protected TextToSpeech textToSpeech;
    protected MessageListAdapter messageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.sendButton);
        questionText = findViewById(R.id.questionField);
        chatMessageList = findViewById(R.id.chatMessageList);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSend();
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new
                TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i!= TextToSpeech.ERROR) {
                            textToSpeech.setLanguage(new Locale("ru"));
                        }
                    }
                });

        messageListAdapter = new MessageListAdapter();
        chatMessageList.setLayoutManager(new LinearLayoutManager(this));
        chatMessageList.setAdapter(messageListAdapter);
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
////        outState.putCharSequence("messageArray", chatMessageList.getTooltipText());
//        ArrayList<Integer> id=new ArrayList<>();
//        ArrayList<String> title=new ArrayList<>();
////        for(int i=0;i<arr.size();i++){
////            id.add(arr.get(i).id);
////            title.add(arr.get(i).title);
////        }
//        outState.putIntegerArrayList("id",id);
//        outState.putStringArrayList("title",title);
////        outState.putParcelableArrayList("List", new ArrayList<Message>(messageListAdapter.messageList));
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
////        CharSequence text = savedInstanceState.getCharSequence("messageArray");
//        ArrayList<? extends Parcelable> messages =  savedInstanceState.getParcelableArrayList("List");
////        if (text != null)
////            chatMessageList.append(text);
//    }

    protected void onSend() {
        String text = questionText.getText().toString();
        String answer = AI.getAnswer(text);
        messageListAdapter.messageList.add(new Message(text, true));
        messageListAdapter.messageList.add(new Message(answer, false));
        messageListAdapter.notifyDataSetChanged();
        chatMessageList.scrollToPosition(messageListAdapter.messageList.size() - 1);
        textToSpeech.speak(answer, TextToSpeech.QUEUE_FLUSH,null, null );
        questionText.setText("");
    }
}