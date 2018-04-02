package practicaltest01var04.eim.systems.cs.pub.ro.practicaltest01var04;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var04MainActivity extends Activity {

    private Button pressMeButton, pressMeTooButton, secondaryActivityButton;
    private TextView pressMeTextView, pressMeTooTextView;
    private Integer counterPressMe = 0, counterPressMeToo = 0;
    private int serviceStatus = 0;
    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.pressMeButton:
                    counterPressMe = Integer.parseInt(pressMeTextView.getText().toString());
                    counterPressMe++;
                    pressMeTextView.setText(String.valueOf(counterPressMe));
                    break;
                case R.id.pressMeTooButton:
                    counterPressMeToo = Integer.parseInt(pressMeTooTextView.getText().toString());
                    counterPressMeToo++;
                    pressMeTooTextView.setText(String.valueOf(counterPressMeToo));
                    break;
            }
            if (counterPressMe + counterPressMeToo > 25
                    && serviceStatus == 0) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
                intent.putExtra("firstNumber", counterPressMe);
                intent.putExtra("secondNumber", counterPressMeToo);
                getApplicationContext().startService(intent);
                serviceStatus = 1;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

        pressMeButton = findViewById(R.id.pressMeButton);
        pressMeTooButton = findViewById(R.id.pressMeTooButton);
        secondaryActivityButton = findViewById(R.id.secondaryActivityButton);
        pressMeTextView = findViewById(R.id.pressMeTextView);
        pressMeTooTextView = findViewById(R.id.pressMeTooTextView);

        pressMeTextView.setText(counterPressMe.toString());
        pressMeTooTextView.setText(counterPressMeToo.toString());

        pressMeButton.setOnClickListener(buttonClickListener);
        pressMeTooButton.setOnClickListener(buttonClickListener);

        secondaryActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04SecondaryActivity.class);
                intent.putExtra("countTotal", String.valueOf(Integer.valueOf(pressMeTextView.getText().toString())
                        + Integer.valueOf(pressMeTooTextView.getText().toString())));
                startActivityForResult(intent, 101);
            }
        });

        intentFilter.addAction("medii");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            Toast.makeText(getApplicationContext(), "The activity returned with result " + resultCode,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("leftCount", pressMeTextView.getText().toString());
        savedInstanceState.putString("rightCount", pressMeTooTextView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey("leftCount")) {
            pressMeTextView.setText(savedInstanceState.getString("leftCount"));
        } else {
            pressMeTextView.setText("0");
        }
        if (savedInstanceState.containsKey("rightCount")) {
            pressMeTooTextView.setText(savedInstanceState.getString("rightCount"));
        } else {
            pressMeTooButton.setText("0");
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var04Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}
