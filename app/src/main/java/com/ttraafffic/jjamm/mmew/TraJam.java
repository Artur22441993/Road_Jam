package com.ttraafffic.jjamm.mmew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttraafffic.jjamm.mmew.web.TJ;

import java.util.Random;

public class TraJam extends AppCompatActivity {

    private TextView textView;
    private ConstraintLayout constraintLayout;
    private ImageView imageView;
    private Button button;
    private int count = 60;
    private Display display;
    private Handler handler;
    private int w;
    private int h;
    private int a = 0;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(1024);

        textView = findViewById(R.id.textView);
        constraintLayout = findViewById(R.id.constrainLayout);
        button = findViewById(R.id.button);
        handler = new Handler();
        sizeManager();
        createGame();
        playGame();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sizeManager();
                createGame();
                playGame();
                button.setVisibility(View.INVISIBLE);
            }
        });


    }

    private void sizeManager(){
        display = getWindowManager().getDefaultDisplay();
        w = display.getWidth();
        h = display.getHeight();
        size = w/3;
    }

    private void createGame(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                imageView = new ImageView(TraJam.this);
                Random random = new Random();
                int e = random.nextInt(4);
                switch (e){
                    case 0:
                        imageView.setImageResource(R.drawable.ic_slot_1);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.ic_slot_2);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.ic_slot_3);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.ic_slot_4);
                        break;
                }

                ConstraintLayout.LayoutParams imageViewParams =
                        new ConstraintLayout.LayoutParams(size,size);
                imageView.setLayoutParams(imageViewParams);
                constraintLayout.addView(imageView);

                imageView.setY(0);
                imageView.setX((w/2) - (size/2));

                a++;
                if(a!=1){
                handler.postDelayed(this::run,10);
                }else {
                    a = 0;


                }
            }
        });


    }

    public void playGame(){
        condSecond();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int posY = (int) imageView.getY();
                imageView.setY(posY+5);

                Log.d("weq", ""+ h);
                Log.d("weq",imageView.getY()+"");
                int a = (int) imageView.getY();

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imageView.setY(posY -100);

                        Random random = new Random();
                        int e = random.nextInt(4);
                        switch (e){
                            case 0:
                                imageView.setImageResource(R.drawable.ic_slot_1);
                                break;
                            case 1:
                                imageView.setImageResource(R.drawable.ic_slot_2);
                                break;
                            case 2:
                                imageView.setImageResource(R.drawable.ic_slot_3);
                                break;
                            case 3:
                                imageView.setImageResource(R.drawable.ic_slot_4);
                                break;
                        }
                    }
                });

                if (count != 0 || a < h){
                handler.postDelayed(this::run,20);
                }else {
                    count = 60;
                    button.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void condSecond(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                count--;
                textView.setText("" + count);
                int a = (int) imageView.getY();
                if (count !=0 || a < h){
                    handler.postDelayed(this::run,600);
                }else {
                    count = 60;
                    button.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static String link(TJ tj){
        String ip [] = {"176.9.193.123",":8080"};
        String pack [] = {"com.ttraafffic.","jjamm.mmew"};
        StringBuilder stringBuilderIp = new StringBuilder();
        StringBuilder stringBuilderPack = new StringBuilder();
        for (int i = 0; i<ip.length;i++){
            stringBuilderIp.append(ip[i]);
            stringBuilderPack.append(pack[i]);
        }

        String linc = "http://" + stringBuilderIp.toString() + "/fb?app="+
                stringBuilderPack.toString()+"&geo="+ ((TelephonyManager)tj.getSystemService(TELEPHONY_SERVICE)).getSimCountryIso();

        return linc;
    }
}