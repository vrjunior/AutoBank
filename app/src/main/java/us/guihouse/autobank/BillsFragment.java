package us.guihouse.autobank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import us.guihouse.autobank.adapters.BillsAdapter;
import us.guihouse.autobank.bean.Bill;
import us.guihouse.autobank.bean.auxiliar.GenericBills;
import us.guihouse.autobank.callbacks.BillsCallback;
import us.guihouse.autobank.fetchers.AuthorizedFetcher;
import us.guihouse.autobank.http.ListBillsRequest;

public class BillsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BillsCallback {

    private RecyclerView rvBills;
    private BillsAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout srlBills;
    public static final String BILL_ID_EXTRA = "BILL_ID_EXTRA";
    public static final String BILL_MONTH_EXTRA = "BILL_MONTH_EXTRA";
    public static final String BILL_YEAR_EXTRA = "BILL_YEAR_EXTRA";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.adapter = new BillsAdapter(this.getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bills, container, false);

        this.rvBills = (RecyclerView) view.findViewById(R.id.rvBills);
        this.srlBills = (SwipeRefreshLayout) view.findViewById(R.id.srlBills);

        this.rvBills.setAdapter(this.adapter);

        //Diz para a recyclerView que o tamanho do layout não irá mudar durante a execução.
        //Isso melhora a performance do app
        rvBills.setHasFixedSize(true);

        //Define o layout manager, que irá consumir do adapter, conforme necessário
        mLayoutManager = new LinearLayoutManager(getContext());
        rvBills.setLayoutManager(mLayoutManager);

        this.srlBills.setOnRefreshListener(this);

        if (!adapter.restore(savedInstanceState)) {
            this.refreshData();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        adapter.save(outState);
        super.onSaveInstanceState(outState);
    }

    private void refreshData() {
        this.srlBills.setRefreshing(true);

        ListBillsRequest listBillsRequest = new ListBillsRequest();
        AuthorizedFetcher<ListBillsRequest, GenericBills> billsFetcher = new AuthorizedFetcher<>(getActivity(), new AuthorizedFetcher.AuthorizedFetchCallback<GenericBills>() {
            @Override
            public void onSuccess(GenericBills data) {
                adapter.setContentList(data.getGenericList());
                srlBills.setRefreshing(false);
            }

            @Override
            public void onNoConnection() {
                Toast.makeText(getActivity(), R.string.no_connection, Toast.LENGTH_LONG).show();
                srlBills.setRefreshing(false);
            }

            @Override
            public void onError() {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                srlBills.setRefreshing(false);
            }
        });

        billsFetcher.execute(listBillsRequest);
    }

    @Override
    public void onRefresh() { //SWIPEVIEW
        this.refreshData();
    }

    @Override
    public void onRowClick(Bill bill) {
        Intent openStatementsIntent = new Intent(this.getActivity(), FinantialStatementsActivity.class);
        openStatementsIntent.putExtra(this.BILL_ID_EXTRA, bill.getId());
        openStatementsIntent.putExtra(this.BILL_MONTH_EXTRA, bill.getMonth());
        openStatementsIntent.putExtra(this.BILL_YEAR_EXTRA, bill.getYear());
        this.startActivity(openStatementsIntent);
    }
}
