package practicaltest01var04.eim.systems.cs.pub.ro.practicaltest01var04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var04SecondaryActivity extends Activity {

    private Button okButton, cancelButton;
    private TextView secondaryTextView;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.okButton:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancelButton:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        secondaryTextView = findViewById(R.id.secondaryTextView);
        okButton = findViewById(R.id.okButton);
        cancelButton = findViewById(R.id.cancelButton);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("countTotal")) {
            secondaryTextView.setText(intent.getStringExtra("countTotal"));
        } else {
            secondaryTextView.setText("0");
        }

        okButton.setOnClickListener(buttonClickListener);
        cancelButton.setOnClickListener(buttonClickListener);
    }
}
