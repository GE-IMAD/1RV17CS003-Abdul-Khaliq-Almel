package com.example.chatbot_dailogflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class Chatbot extends AppCompatActivity implements AIListener {

    AIService aiService;
    //final AIConfiguration config;
    //AIRequest aiRequest;
    //AIDataService aiDataService;
    public List<ChatMessage> messagesList= new ArrayList<>();
    private RecyclerView chatRecycler;
    private EditText user;
    ChatAdapter chatAdapter;
    RelativeLayout rl;
    ImageView im;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        rl=(RelativeLayout) findViewById(R.id.btnSend);
        im=(ImageView) findViewById(R.id.fab_img);
        user=(EditText) findViewById(R.id.edChat);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            //Log.i(TAG, "Permission to record denied");
            makeRequest();
        }



        chatRecycler = (RecyclerView) findViewById(R.id.rvChat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        chatRecycler.setLayoutManager(linearLayoutManager);
        chatAdapter= new ChatAdapter(getApplicationContext(),messagesList);
        chatRecycler.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
//05701f62c70f46fba9186b99328fe3d4
        final AIConfiguration config = new AIConfiguration("b55883ce54554c98a54e4fef0fdb5e60",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        final AIDataService aiDataService = new AIDataService(config);


        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String input=user.getText().toString().trim();
                //user.setText("");
                if(input.isEmpty()) {
                    //messagesList.add(new ChatMessage(input,1));
                    Toast.makeText(Chatbot.this,"SPEAK", Toast.LENGTH_LONG).show();
                    aiService.startListening();
                    return;

                }else{
                    Toast.makeText(Chatbot.this,"you said :"+input, Toast.LENGTH_LONG).show();
                    final AIRequest aiRequest = new AIRequest();
                    aiRequest.setQuery(input);
                    new AsyncTask<AIRequest, Void, AIResponse>() {
                        @Override
                        protected AIResponse doInBackground(AIRequest... requests) {
                            final AIRequest request = requests[0];
                            try {
                                final AIResponse response = aiDataService.request(aiRequest);
                                return response;
                            } catch (AIServiceException e) {
                            }
                            return null;
                        }
                        @Override
                        protected void onPostExecute(AIResponse aiResponse) {
                            if (aiResponse != null) {
                                Toast.makeText(Chatbot.this,"YOu texted:", Toast.LENGTH_LONG).show();

                                final Result result1=aiResponse.getResult();
                                messagesList.add(new ChatMessage(result1.getResolvedQuery(),1));
                                chatAdapter.notifyDataSetChanged();

                                messagesList.add(new ChatMessage(result1.getFulfillment().getSpeech(),0));
                                chatAdapter.notifyDataSetChanged();

                                for(ChatMessage kkk:messagesList){
                                    Log.v("Message:",kkk.getMessage());
                                }
                                // process aiResponse here
                            }
                        }

                    }.execute(aiRequest);
                    //Toast.makeText(Chatbot.this,"executed", Toast.LENGTH_LONG).show();

                    user.setText("");
                    return;
                }



            }
        });


    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    // Log.i(TAG, "Permission has been denied by user");
                } else {
                    //Log.i(TAG, "Permission has been granted by user");
                }
                return;
            }
        }
    }

    @Override
    public void onResult(AIResponse result) {
        Result result1=result.getResult();
        Toast.makeText(Chatbot.this,"You Said" +result1.getResolvedQuery(), Toast.LENGTH_LONG).show();

        messagesList.add(new ChatMessage(result1.getResolvedQuery(),1));
        chatAdapter.notifyDataSetChanged();

        messagesList.add(new ChatMessage(result1.getFulfillment().getSpeech(),0));
        chatAdapter.notifyDataSetChanged();
        for(ChatMessage kkk:messagesList){
            Log.v("Message:",kkk.getMessage());
        }

    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}
