package us.guihouse.autobank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FinantialStatementsActivity extends AppCompatActivity {

    private Long idBill;
    private TextView tvMonthBill;
    private TextView tvYearBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finantial_statements);

        tvMonthBill = (TextView) findViewById(R.id.tvMonthBill);
        tvYearBill = (TextView) findViewById(R.id.tvMonthYear);

        Intent intent = this.getIntent();
        this.idBill = intent.getLongExtra(BillsFragment.BILL_ID_EXTRA, -1);
        this.tvYearBill.setText(getResources().getString(R.string.month1));

    }
}
