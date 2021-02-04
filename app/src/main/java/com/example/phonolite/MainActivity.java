package com.example.phonolite;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextToSpeech mTTS;
//    private SeekBar sbPitch;
//    private SeekBar sbSpeed;

    private ImageView A;
    private ImageView btnListen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Click Anywhere to listen", Toast.LENGTH_SHORT).show();



//        sbPitch = findViewById(R.id.seekBarPitch);
//        sbSpeed = findViewById(R.id.seekBarSpeed);
        btnListen = findViewById(R.id.btnListen);


        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        Log.d(TAG, "onInit: mTTS Success");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
        btnListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuprofile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }
        return true;
    }

    private void speak() {
        String text = "A. for Apple, A. for Ashoka, A. for Airplane.......... An ant is standing near Ashoka tree and look at the apple" +
                "...while, an airplane is flying high in the sky";
//        float pitch = (float) sbPitch.getProgress() / 50;
//        Log.d(TAG, "speak:"+ pitch);
//        if (pitch < 0.1) pitch = 0.1f;
//        float speed = (float) sbSpeed.getProgress() / 50;
//        Log.d(TAG, "speak: "+speed);
//        if (speed < 0.1) speed = 0.1f;
        mTTS.setPitch(0.8f);
        mTTS.setSpeechRate(0.8f);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}