package us.guihouse.autobank;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import us.guihouse.autobank.bean.Login;
import us.guihouse.autobank.bean.Session;

import static android.content.Context.MODE_PRIVATE;

public class CardActivity extends AppCompatActivity {

    private SharedPreferences sharedPrefe;
    private Session currentSession;
    private TextView tvTestTolken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        tvTestTolken = (TextView) findViewById(R.id.testTolken);
        sharedPrefe = getSharedPreferences(LoginActivity.SHARED_PREFE_FILE, MODE_PRIVATE);
        tvTestTolken.setText(sharedPrefe.getString("tolken", "0"));
    }
}
