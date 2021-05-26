package com.example.snookercounter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.mbms.StreamingServiceInfo;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LaskuriActivity extends AppCompatActivity {

    Button pun, kel, vih, rus, sin, pin, mus;
    Button start, vaihda;
    ToggleButton virhe;

    TextView  p1Nimi, p2Nimi;
    TextView  p1Pisteet, p2Pisteet;

    TextView breikki1, breikkiArvo, breikki2, breikkiArvo2;
    Chronometer ajastin;

    final String[] voittaja = new String[1];

    ArrayList<String> arrayList = new ArrayList<>();

    boolean going = false;
    boolean p1Active = true;
    boolean punainen = true;
    boolean virhetila = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laskuri);

        getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(LaskuriActivity.this, R.color.colorPrimary));

        startingTheFrame();
        vaihda.setVisibility(View.INVISIBLE);
        virhe.setVisibility(View.INVISIBLE);
        breikki1.setVisibility(View.INVISIBLE);
        breikki2.setVisibility(View.INVISIBLE);
        breikkiArvo.setVisibility(View.INVISIBLE);
        breikkiArvo2.setVisibility(View.INVISIBLE);

        getHuippuTuloksetLista();
    }

    private ArrayList getHuippuTuloksetLista() {

        HashMap<String, Integer> mappi = new HashMap<String, Integer>();

        String g = readFromFile(this);
        String[] gg = g.split(";");
        for (String o : gg) {
            if (!(g.equals(""))) {
                String[] o2 = new String[2];
                int i = o.indexOf(":");
                o2[0] = o.substring(0, i - 1);
                o2[1] = o.substring(i + 2);

                mappi.put(o2[0], Integer.parseInt(o2[1]));
                //arrayList.add(o);
            } else {
                Toast.makeText(this, "nothing to show", Toast.LENGTH_LONG).show();
            }
        }

        List<Integer> mapValues = new ArrayList<Integer>(mappi.values());
        Collections.sort(mapValues, Collections.<Integer>reverseOrder());


        for (Integer i : mapValues) {
            if (!arrayList.contains(i)) {
                arrayList.add(getKeyByValue(mappi, i) + " : " + i);
            }

        }

        return arrayList;
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void startingTheFrame() {

        start = findViewById(R.id.startButton);
        vaihda = findViewById(R.id.changePlayer);

        virhe = findViewById(R.id.virhe);

        breikki1 = findViewById(R.id.breikki);
        breikkiArvo = findViewById(R.id.breikki3);
        breikki2 = findViewById(R.id.breikki2);
        breikkiArvo2 = findViewById(R.id.breikki4);
        p1Nimi = findViewById(R.id.player1name);
        p1Pisteet = findViewById(R.id.player1score);
        p2Nimi = findViewById(R.id.player2name);
        p2Pisteet = findViewById(R.id.player2score);
        ajastin = findViewById(R.id.simpleChronometer);

        HashMap<String, Integer> arvot = new HashMap<>();
        String g = readFromFile(this);
        String[] gg = g.split(";");
        for (String ggg : gg) {
            String[] gggg = new String[2];
            if (ggg != "") {
                int i = ggg.indexOf(":");
                gggg[0] = ggg.substring(0,i);
                gggg[1] = ggg.substring(i+2);

                arvot.put(gggg[0], Integer.parseInt(gggg[1]));
            }
        }



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!going) {
                    p1Pisteet.setText("0");
                    p2Pisteet.setText("0");
                    breikkiArvo.setText("0");
                    breikkiArvo2.setText("0");

                    ajastin.setBase(SystemClock.elapsedRealtime());
                    ajastin.start();
                    going = true;
                    start.setText("Stop");

                    boldAndUpscale(p1Nimi);
                    addingPoints();
                    changePlayer();

                    virhe.setVisibility(View.VISIBLE);
                    vaihda.setVisibility(View.VISIBLE);
                    breikki1.setVisibility(View.VISIBLE);
                    breikkiArvo.setVisibility(View.VISIBLE);
                    pun.setClickable(true);
                    kel.setClickable(true);
                    vih.setClickable(true);
                    rus.setClickable(true);
                    sin.setClickable(true);
                    pin.setClickable(true);
                    mus.setClickable(true);
                    //breikki2.setVisibility(View.VISIBLE);

                } else {
                    start.setText("Start");
                    ajastin.stop();
                    going = false;
                    p1Active = true;
                    punainen = true;

                    normalize(p1Nimi);
                    normalize(p2Nimi);

                    vaihda.setVisibility(View.INVISIBLE);
                    virhe.setVisibility(View.INVISIBLE);
                    breikki1.setVisibility(View.INVISIBLE);
                    breikkiArvo.setVisibility(View.INVISIBLE);
                    breikki2.setVisibility(View.INVISIBLE);
                    breikkiArvo2.setVisibility(View.INVISIBLE);

                    pun.setClickable(false);
                    kel.setClickable(false);
                    vih.setClickable(false);
                    rus.setClickable(false);
                    sin.setClickable(false);
                    pin.setClickable(false);
                    mus.setClickable(false);

                    if (Integer.parseInt(p1Pisteet.getText().toString()) == 0 &&
                        (Integer.parseInt(p2Pisteet.getText().toString()) == 0)) {
                        return;
                    } else {
                        final String tulos;

                        if (Integer.parseInt(p1Pisteet.getText().toString()) > Integer.parseInt(p2Pisteet.getText().toString())) {
                            tulos = p1Pisteet.getText().toString();
                        } else {
                            tulos = p2Pisteet.getText().toString();
                        }
                        if (arrayList.size() <= 4) {
                            tallennaData(tulos);
                        } else {
                            vertailija(arrayList, tulos);
                        }
                    }
                }
            }
        });

        virhe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(LaskuriActivity.this, R.color.colorPrimary));

                    näytäTurha();
                    normaalitPallot();
                    virhetila = false;

                    getActivePlayersBreakTW().setVisibility(View.VISIBLE);
                    getActivePlayersBreak().setVisibility(View.VISIBLE);

                } else {
                    getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(LaskuriActivity.this, R.color.colorTint));

                    piilotaTurha();
                    virhePallot();
                    virhetila = true;
                }
            }
        });
    }

    private void normaalitPallot() {
        rus.setText("");
        rus.setBackgroundResource(R.color.add4primary);
        sin.setText("");
        sin.setBackgroundResource(R.color.add5primary);
        pin.setText("");
        pin.setBackgroundResource(R.color.add6primary);
        mus.setText("");
        mus.setBackgroundResource(R.color.add7primary);
    }

    private void virhePallot() {
        rus.setText("4");
        rus.setBackgroundResource(R.color.virhe);
        sin.setText("5");
        sin.setBackgroundResource(R.color.virhe);
        pin.setText("6");
        pin.setBackgroundResource(R.color.virhe);
        mus.setText("7");
        mus.setBackgroundResource(R.color.virhe);
    }

    private void näytäTurha() {
        pun.setVisibility(View.VISIBLE);
        kel.setVisibility(View.VISIBLE);
        vih.setVisibility(View.VISIBLE);
    }

    private void piilotaTurha() {
        breikki1.setVisibility(View.INVISIBLE);
        breikkiArvo.setVisibility(View.INVISIBLE);
        breikki2.setVisibility(View.INVISIBLE);
        breikkiArvo2.setVisibility(View.INVISIBLE);

        pun.setVisibility(View.INVISIBLE);
        kel.setVisibility(View.INVISIBLE);
        vih.setVisibility(View.INVISIBLE);
    }

    public void tallennaData(final String tulos) {

        int millis = (int) (SystemClock.elapsedRealtime() - ajastin.getBase());
        int minuutit = millis / 1000 / 60;
        int sekunnit = millis / 1000 - (minuutit * 60);

        AlertDialog.Builder builder = new AlertDialog.Builder(LaskuriActivity.this);

        final EditText input = new EditText(LaskuriActivity.this);
        builder.setMessage("Freimiin meni aikaa " + minuutit + ":" + sekunnit + ", voittava tulos oli " + tulos +
                ". Anna voittajan nimi ");

        builder.setView(input);

        builder.setPositiveButton("Tallenna", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                voittaja[0] = input.getText().toString();

                dataWriter(voittaja[0], tulos,LaskuriActivity.this);
            }
        });
        builder.show().getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.style.AlertDialog);
    }

    public void vertailija(final ArrayList list, final String tulos) {

        int millis = (int) (SystemClock.elapsedRealtime() - ajastin.getBase());
        int minuutit = millis / 1000 / 60;
        int sekunnit = millis / 1000 - (minuutit * 60);

        AlertDialog.Builder builder = new AlertDialog.Builder(LaskuriActivity.this);

        final EditText input = new EditText(LaskuriActivity.this);
        builder.setMessage("Freimiin meni aikaa " + minuutit + ":" +  sekunnit + ", voittava tulos oli " + tulos +
                ". Anna voittajan nimi ");

        builder.setView(input);

        //final String[] strings = null;
        builder.setPositiveButton("Tallenna", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input != null) {
                    voittaja[0] = input.getText().toString();

                    String tulosString = tulos;
                    String tuloss = voittaja[0] + " : " + tulos;
                    String uusi;

                    for (int i = 0; i < arrayList.size(); i++) {
                        String ii = arrayList.get(i);
                        int index = ii.indexOf(":");
                        String tulos2 = ii.substring(index + 2);
                        if (Integer.parseInt(tulosString) > Integer.parseInt(tulos2)) {
                            uusi = ii;
                            arrayList.set(i, tuloss);
                            tuloss = uusi;
                            int p = uusi.indexOf(":");
                            tulosString = uusi.substring(p + 2);
                        }
                    }
                    dataWriter(arrayList, LaskuriActivity.this);
                }
            }
        });
        builder.show().getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.style.AlertDialog);

    }


    public void addingPoints() {

            pun = findViewById(R.id.add1);
            kel = findViewById(R.id.add2);
            vih = findViewById(R.id.add3);
            rus = findViewById(R.id.add4);
            sin = findViewById(R.id.add5);
            pin = findViewById(R.id.add6);
            mus = findViewById(R.id.add7);

            pun.setOnClickListener(onClickListener);
            kel.setOnClickListener(onClickListener);
            vih.setOnClickListener(onClickListener);
            rus.setOnClickListener(onClickListener);
            sin.setOnClickListener(onClickListener);
            pin.setOnClickListener(onClickListener);
            mus.setOnClickListener(onClickListener);
            vaihda.setOnClickListener(onClickListener);



        //} else {
         //   Toast.makeText(this, "freimi ei ole käynnissä", Toast.LENGTH_LONG).show();
       // }
    }

    public void changePlayer() {
        vaihda = findViewById(R.id.changePlayer);

        vaihda.setOnClickListener(onClickListener);
    }

    public void boldAndUpscale(TextView textView) {
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
    }

    public void normalize(TextView textView) {
        textView.setTextSize(15);
        textView.setTypeface(null, Typeface.NORMAL);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (punainen) {
                switch (v.getId()) {
                    case (R.id.add1):
                        TextView aktiivinenPelaaja = getActiveplayer();

                        int pisteet = Integer.parseInt(aktiivinenPelaaja.getText().toString());
                        String pisteetPlus = String.valueOf(pisteet + 1);
                        aktiivinenPelaaja.setText(pisteetPlus);

                        TextView aktiivinenBreikki = getActivePlayersBreak();

                        int breikki = Integer.parseInt(aktiivinenBreikki.getText().toString());
                        String breikkiPlus = String.valueOf(breikki + 1);
                        aktiivinenBreikki.setText(breikkiPlus);
                        punainen = false;

                        break;
                }
            } else {
                    switch (v.getId()) {
                         case(R.id.add2):
                    TextView aktiivinenPelaaja = getActiveplayer();
                    int pisteet = Integer.parseInt(aktiivinenPelaaja.getText().toString());
                    String pisteetPlus = String.valueOf(pisteet + 2);
                    aktiivinenPelaaja.setText(pisteetPlus);

                    TextView aktiivinenBreikki = getActivePlayersBreak();

                    int breikki = Integer.parseInt(aktiivinenBreikki.getText().toString());
                    String breikkiPlus = String.valueOf(breikki + 2);
                    aktiivinenBreikki.setText(breikkiPlus);
                    punainen = true;
                    break;
                case(R.id.add3):
                    aktiivinenPelaaja = getActiveplayer();
                    pisteet = Integer.parseInt(aktiivinenPelaaja.getText().toString());
                    pisteetPlus = String.valueOf(pisteet + 3);
                    aktiivinenPelaaja.setText(pisteetPlus);

                    aktiivinenBreikki = getActivePlayersBreak();

                    breikki = Integer.parseInt(aktiivinenBreikki.getText().toString());
                    breikkiPlus = String.valueOf(breikki + 3);
                    aktiivinenBreikki.setText(breikkiPlus);
                    punainen = true;
                    break;
                case(R.id.add4):
                    aktiivinenPelaaja = getActiveplayer();
                    pisteet = Integer.parseInt(aktiivinenPelaaja.getText().toString());
                    pisteetPlus = String.valueOf(pisteet + 4);
                    aktiivinenPelaaja.setText(pisteetPlus);

                    aktiivinenBreikki = getActivePlayersBreak();

                    breikki = Integer.parseInt(aktiivinenBreikki.getText().toString());
                    breikkiPlus = String.valueOf(breikki + 4);
                    aktiivinenBreikki.setText(breikkiPlus);
                    punainen = true;
                    break;
                case(R.id.add5):
                    aktiivinenPelaaja = getActiveplayer();
                    pisteet = Integer.parseInt(aktiivinenPelaaja.getText().toString());
                    pisteetPlus = String.valueOf(pisteet + 5);
                    aktiivinenPelaaja.setText(pisteetPlus);

                    aktiivinenBreikki = getActivePlayersBreak();

                    breikki = Integer.parseInt(aktiivinenBreikki.getText().toString());
                    breikkiPlus = String.valueOf(breikki + 5);
                    aktiivinenBreikki.setText(breikkiPlus);
                    punainen = true;
                    break;
                case(R.id.add6):
                    aktiivinenPelaaja = getActiveplayer();
                    pisteet = Integer.parseInt(aktiivinenPelaaja.getText().toString());
                    pisteetPlus = String.valueOf(pisteet + 6);
                    aktiivinenPelaaja.setText(pisteetPlus);

                    aktiivinenBreikki = getActivePlayersBreak();

                    breikki = Integer.parseInt(aktiivinenBreikki.getText().toString());
                    breikkiPlus = String.valueOf(breikki + 6);
                    aktiivinenBreikki.setText(breikkiPlus);
                    punainen = true;
                    break;
                case(R.id.add7):
                    aktiivinenPelaaja = getActiveplayer();
                    pisteet = Integer.parseInt(aktiivinenPelaaja.getText().toString());
                    pisteetPlus = String.valueOf(pisteet + 7);
                    aktiivinenPelaaja.setText(pisteetPlus);
                    aktiivinenBreikki = getActivePlayersBreak();

                    breikki = Integer.parseInt(aktiivinenBreikki.getText().toString());
                    breikkiPlus = String.valueOf(breikki + 7);
                    aktiivinenBreikki.setText(breikkiPlus);
                    punainen = true;
                    break;
                }
            }
            switch (v.getId()) {
                case (R.id.changePlayer):
                    TextView pelaaja = null;
                    if (p1Active) {
                        pelaaja = p2Nimi;
                        p1Active = false;
                        breikkiArvo.setText("0");
                        breikkiArvo2.setText("0");
                        breikki1.setVisibility(View.INVISIBLE);
                        breikkiArvo.setVisibility(View.INVISIBLE);
                        if (!virhetila) {
                            breikki2.setVisibility(View.VISIBLE);
                            breikkiArvo2.setVisibility(View.VISIBLE);
                        }
                    } else {
                        pelaaja = p1Nimi;
                        p1Active = true;
                        breikkiArvo.setText("0");
                        breikkiArvo2.setText("0");
                        if (!virhetila) {
                            breikki1.setVisibility(View.VISIBLE);
                            breikkiArvo.setVisibility(View.VISIBLE);
                        }
                        breikki2.setVisibility(View.INVISIBLE);
                        breikkiArvo2.setVisibility(View.INVISIBLE);
                    }
                    normalize(p1Nimi);
                    normalize(p2Nimi);
                    boldAndUpscale(pelaaja);
                    punainen = true;
                    break;
            }
            if (virhetila) {
                switch (v.getId()) {
                case(R.id.add4):
                    TextView aktiivinenPelaaja = virhetilaPisteGetter();
                    int pisteet = Integer.parseInt(aktiivinenPelaaja.getText().toString());
                    String pisteetPlus = String.valueOf(pisteet + 4);
                    aktiivinenPelaaja.setText(pisteetPlus);
                    break;
                case(R.id.add5):
                    aktiivinenPelaaja = virhetilaPisteGetter();
                    pisteet = Integer.parseInt(aktiivinenPelaaja.getText().toString());
                    pisteetPlus = String.valueOf(pisteet + 5);
                    aktiivinenPelaaja.setText(pisteetPlus);
                    break;
                case(R.id.add6):
                    aktiivinenPelaaja = virhetilaPisteGetter();
                    pisteet = Integer.parseInt(aktiivinenPelaaja.getText().toString());
                    pisteetPlus = String.valueOf(pisteet + 6);
                    aktiivinenPelaaja.setText(pisteetPlus);
                    break;
                case(R.id.add7):
                    aktiivinenPelaaja = virhetilaPisteGetter();
                    pisteet = Integer.parseInt(aktiivinenPelaaja.getText().toString());
                    pisteetPlus = String.valueOf(pisteet + 7);
                    aktiivinenPelaaja.setText(pisteetPlus);
                    break;
            }
            }
        }
    };

    private TextView getActiveplayer() {

        if (p1Active) {
            return p1Pisteet;
        } else {
            return p2Pisteet;
        }
    }

    private TextView getActivePlayersBreak() {
        if (p1Active) {
            return breikkiArvo;
        } else {
            return breikkiArvo2;
        }
    }

    private TextView getActivePlayersBreakTW() {
        if (p1Active) {
            return breikki1;
        } else {
            return breikki2;
        }
    }
    private TextView virhetilaPisteGetter() {

        if (p1Active) {
            return p2Pisteet;
        } else {
            return p1Pisteet;
        }
    }

    public void dataWriter(String nimi, String data, Context context) {
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("data.txt", Context.MODE_APPEND));
            outputStreamWriter.append(nimi + " : " + data + ";");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void dataWriter(ArrayList list, Context context) {
        try {
            String lista = null;
            for (String g : arrayList) {
                lista += g + ";";
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("data.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(lista);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("data.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


}
