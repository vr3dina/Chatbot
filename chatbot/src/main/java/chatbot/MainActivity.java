package chatbot;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import chatbot.db.DBHelper;
import chatbot.db.MessageEntity;
import chatbot.models.message.Message;
import chatbot.models.message.MessageListAdapter;

public class MainActivity extends AppCompatActivity {

    protected Button sendButton;
    protected EditText questionText;
    protected RecyclerView chatMessageList;
    protected TextToSpeech textToSpeech;
    protected MessageListAdapter messageListAdapter;

    DBHelper dBHelper;
    SQLiteDatabase database;

    SharedPreferences sPref;
    public static final String APP_PREFERENCES = "my_settings";

    private boolean isLight = true;
    private String THEME = "THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("LOG", "onCreate");

        sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        isLight = sPref.getBoolean(THEME, true);
        getDelegate().setLocalNightMode(isLight ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.sendButton);
        questionText = findViewById(R.id.questionField);
        chatMessageList = findViewById(R.id.chatMessageList);

        messageListAdapter = new MessageListAdapter();
        chatMessageList.setLayoutManager(new LinearLayoutManager(this));
        chatMessageList.setAdapter(messageListAdapter);

        dBHelper = new DBHelper(this);
        database = dBHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_MESSAGES, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            int messageIndex = cursor.getColumnIndex(DBHelper.FIELD_MESSAGE);
            int dateIndex = cursor.getColumnIndex(DBHelper.FIELD_DATE);
            int sendIndex = cursor.getColumnIndex(DBHelper.FIELD_SEND);

            do {
                MessageEntity entity = new MessageEntity(
                        cursor.getString(messageIndex),
                        cursor.getString(dateIndex),
                        cursor.getInt(sendIndex));
                Message message = null;
                try {
                    message = new Message(entity);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                messageListAdapter.messageList.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();

        sendButton.setOnClickListener(view -> onSend());

        textToSpeech = new TextToSpeech(getApplicationContext(), i -> {
            if (i!= TextToSpeech.ERROR) {
                textToSpeech.setLanguage(new Locale("ru"));
            }
        });
    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
        Log.i("LOG", "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LOG", "onStop");
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(THEME, isLight);
        editor.apply();

        database.delete(DBHelper.TABLE_MESSAGES, null, null);
        for (Message message : messageListAdapter.messageList) {
            MessageEntity entity = new MessageEntity(message);

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.FIELD_MESSAGE, entity.text);
            contentValues.put(DBHelper.FIELD_SEND, entity.isSend);
            contentValues.put(DBHelper.FIELD_DATE, entity.date);

            database.insert(DBHelper.TABLE_MESSAGES,null,contentValues);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i("LOG", "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.day_settings:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                isLight = true;
                break;
            case R.id.night_settings:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                isLight = false;
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("LOG", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) messageListAdapter.messageList);
        Log.i("LOG", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("LOG", "onRestoreInstanceState");
        messageListAdapter.messageList = savedInstanceState.getParcelableArrayList("list");
        messageListAdapter.notifyDataSetChanged();
        chatMessageList.scrollToPosition(messageListAdapter.messageList.size() - 1);
    }

    protected void onSend() {
        Log.i("LOG", "onSend");
        final String text = questionText.getText().toString();
        AI.getAnswer(text, answer -> {
            messageListAdapter.messageList.add(new Message(text, true));
            messageListAdapter.messageList.add(new Message(answer, false));
            messageListAdapter.notifyDataSetChanged();
            chatMessageList.scrollToPosition(messageListAdapter.messageList.size() - 1);
            textToSpeech.speak(answer, TextToSpeech.QUEUE_FLUSH,null, null );
            questionText.setText("");
        });
    }
}