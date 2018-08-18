package com.vivek.alisha.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.dnkilic.waveform.WaveView;
import com.vivek.alisha.R;
import com.vivek.alisha.adapter.ChatAdapter;
import com.vivek.alisha.model.ChatModel;
import com.vivek.alisha.pojo.airesponse.AIResponse;
import com.vivek.alisha.utils.Global;
import com.vivek.alisha.utils.Constants;
import com.vivek.alisha.volley.VolleyRequest;
import com.vivek.alisha.volley.VolleyResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements RecognitionListener,
        TextToSpeech.OnInitListener, VolleyResponse {

    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;
    @BindView(R.id.imgBluetooth)
    ImageView imgBluetooth;
    @BindView(R.id.imgMic)
    ImageView imgMic;
    @BindView(R.id.waveView)
    WaveView waveView;
    @BindView(R.id.recyclerChatView)
    RecyclerView recyclerChatView;
    TextToSpeech tts;
    final int delay = 2000;
    DisplayMetrics dm;
    LinearLayoutManager linearLayoutManager;
    ChatAdapter chatAdapter;
    List<ChatModel> chatModelList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //text to voice
        tts = new TextToSpeech(this, this);
        //voice to text
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());

        //get display metrics
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                waveView.initialize(dm);

            }
        }, delay);

        //adapter setup
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerChatView.setLayoutManager(linearLayoutManager);
        chatAdapter = new ChatAdapter(this, chatModelList);
        recyclerChatView.setAdapter(chatAdapter);

    }


    @OnClick({R.id.imgMic, R.id.imgBluetooth})
    public void getViewClick(View view) {

        switch (view.getId()) {

            case R.id.imgBluetooth:
                startActivity(new Intent(this, BluetoothActivity.class));
                break;
            case R.id.imgMic:
                speechRecognizer.startListening(speechRecognizerIntent);
                break;
        }
    }

    private void openApp(String appName) {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        for (ResolveInfo info : getPackageManager().queryIntentActivities(mainIntent, 0)) {
            if (info.loadLabel(getPackageManager()).toString().toLowerCase().equals(appName)) {
                Intent launchIntent = getPackageManager()
                        .getLaunchIntentForPackage(info.activityInfo.applicationInfo.packageName);
                startActivity(launchIntent);
                return;
            }
        }
    }

    public String getNumber(String name) {

        String number = "";
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor people = getContentResolver().query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract
                .CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract
                .CommonDataKinds.Phone.NUMBER);

        people.moveToFirst();
        do {
            String Name = people.getString(indexName);
            String Number = people.getString(indexNumber);
            if (Name.toLowerCase().contains(name.toLowerCase())) {
                return Number.replace("-", "");
            }
        } while (people.moveToNext());

        if (!number.equalsIgnoreCase("")) {
            return number.replace("-", "");
        } else return number;
    }

    private void call(String number) {

        Uri call = Uri.parse("tel:" + number);
        Intent surf = new Intent(Intent.ACTION_DIAL, call);
        startActivity(surf);
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        waveView.initialize(dm);
    }

    @Override
    public void onBeginningOfSpeech() {
        waveView.speechStarted();
    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
        waveView.speechEnded();
        waveView.initialize(dm);
    }

    @Override
    public void onError(int i) {
        waveView.initialize(dm);
    }

    @Override
    public void onResults(Bundle results) {

        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        //  Toast.makeText(MainActivity.this,matches.get(0),Toast.LENGTH_SHORT).show();

        //set adapter
        addAdapterValue(matches.get(0), Constants.USER);
        //call api
        new VolleyRequest(this, this).requestAI(matches.get(0));

    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    @Override
    public void onInit(int i) {

        if (i == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.US);
            addAdapterValue("hello my name is alisha", Constants.AI);
            tts.speak("hello ,my name is alisha", TextToSpeech.QUEUE_FLUSH, null, "");
        }
    }

    private void addAdapterValue(String data, int type) {

        chatModelList.add(new ChatModel(data, type));
        chatAdapter.notifyDataSetChanged();
        recyclerChatView.smoothScrollToPosition(chatModelList.size() - 1);
    }

    private String capitalFirstLetter(String letter) {

        return letter.substring(0, 1).toUpperCase() + letter.substring(1).toLowerCase();
    }

    @Override
    public void getVolleyResponse(boolean status, String data) {

        if (status) {

            AIResponse aiResponse = Global.getGson().fromJson(data, AIResponse.class);
            addAdapterValue(aiResponse.getReply(), Constants.AI);
            tts.speak(aiResponse.getReply(), TextToSpeech.QUEUE_FLUSH, null, "");

            switch (aiResponse.getApp().getActionCode()) {

                case Constants.NORMAL_CODE:

                    break;
                case Constants.CALL_CODE:
                    call(getNumber(aiResponse.getApp().getCmd()));
                    break;
                case Constants.OPEN_CODE:
                    openApp(aiResponse.getApp().getCmd());
                    break;
            }
        }
    }
}
