package us.guihouse.autobank;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import us.guihouse.autobank.adapters.FinantialStatementAdapter;
import us.guihouse.autobank.bean.FinantialStatement;
import us.guihouse.autobank.bean.auxiliar.GenericFinantialStatements;
import us.guihouse.autobank.fetchers.AuthorizedFetcher;
import us.guihouse.autobank.http.FinantialStatementsRequest;

public class FinantialStatementsActivity extends AppCompatActivity {

    private Long idBill;
    private int month;
    private int year;
    private TextView tvMonthYearBill;
    private SwipeRefreshLayout srlFinantialStatements;
    private RecyclerView rvFinantialStatements;
    private RecyclerView.LayoutManager mLayoutManager;
    private FinantialStatementAdapter finantialStatementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finantial_statements);

        this.tvMonthYearBill = (TextView) findViewById(R.id.tvMonthYearBill);
        this.srlFinantialStatements = (SwipeRefreshLayout) findViewById(R.id.srlFinantialStements);
        this.rvFinantialStatements = (RecyclerView) findViewById(R.id.rvFinantialStatements);


        //Define o layout manager, que irá consumir do adapter, conforme necessário
        mLayoutManager = new LinearLayoutManager(this);
        rvFinantialStatements.setLayoutManager(mLayoutManager);

        finantialStatementAdapter = new FinantialStatementAdapter(this);
        rvFinantialStatements.setAdapter(finantialStatementAdapter);


        this.srlFinantialStatements.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        Intent intent = this.getIntent();
        if(intent != null) {
            this.idBill = intent.getLongExtra(BillsFragment.BILL_ID_EXTRA, -1);
            this.month = intent.getIntExtra(BillsFragment.BILL_MONTH_EXTRA, -1);
            this.year = intent.getIntExtra(BillsFragment.BILL_YEAR_EXTRA, -1);

            this.tvMonthYearBill.setText(this.getMonthName(this.month) + " " + this.year);

            this.refreshData();
        }
    }

    private void refreshData() {
        this.srlFinantialStatements.setRefreshing(true);
        final FinantialStatementsRequest finantialStatementsRequest = new FinantialStatementsRequest(this.idBill);
        AuthorizedFetcher<FinantialStatementsRequest, GenericFinantialStatements> finantialStatementsFetcher = new AuthorizedFetcher<>(this, new AuthorizedFetcher.AuthorizedFetchCallback<GenericFinantialStatements>() {
            @Override
            public void onSuccess(GenericFinantialStatements data) {
                finantialStatementAdapter.setObjectsData(data.getGenericObjectList());
                srlFinantialStatements.setRefreshing(false);
            }

            @Override
            public void onNoConnection() {

            }

            @Override
            public void onError() {

            }
        }
        );
        finantialStatementsFetcher.execute(finantialStatementsRequest);
    }

    private String getMonthName(int i) {
        return getResources().getStringArray(R.array.months_name)[i-1];
    }
}
