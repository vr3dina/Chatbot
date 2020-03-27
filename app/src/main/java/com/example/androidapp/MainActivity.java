package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    protected Button sendButton;
    protected EditText questionText;
    protected TextView chatWindow;
    protected TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.sendButton);
        questionText = findViewById(R.id.questionField);
        chatWindow = findViewById(R.id.chatWindow);

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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("messageArray", chatWindow.getText());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CharSequence text = savedInstanceState.getCharSequence("messageArray");
        if (text != null)
            chatWindow.append(text);
    }

    protected void onSend() {
        String question = questionText.getText().toString();
        String answer = AI.getAnswer(question);
        chatWindow.append(">>" + question + "\n");
        chatWindow.append("<<" + answer + "\n");
        textToSpeech.speak(answer, TextToSpeech.QUEUE_FLUSH,null, null );
        questionText.setText("");
    }
}