package us.guihouse.autobank;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import us.guihouse.autobank.adapters.BillsAdapter;
import us.guihouse.autobank.bean.GenericBills;
import us.guihouse.autobank.callbacks.BillsCallback;
import us.guihouse.autobank.fetchers.AuthorizedFetcher;
import us.guihouse.autobank.fetchers.BasicFetcher;
import us.guihouse.autobank.http.BasePostRequest;
import us.guihouse.autobank.http.ListBillsRequest;

public class BillsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BillsCallback {

    private RecyclerView rvBills;
    private GenericBills genericBills;
    private BillsAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout srlBills;
    public static final String BILL_ID_EXTRA = "BILL_ID_EXTRA";

    public BillsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BillsFragment newInstance(String param1, String param2) {
        BillsFragment fragment = new BillsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bills, container, false);

        this.rvBills = (RecyclerView) view.findViewById(R.id.rvBills);
        this.srlBills = (SwipeRefreshLayout) view.findViewById(R.id.srlBills);
        this.adapter = new BillsAdapter(this.getActivity(), this);
        this.rvBills.setAdapter(this.adapter);

        //Diz para a recyclerView que o tamanho do layout não irá mudar durante a execução.
        //Isso melhora a performance do app
        rvBills.setHasFixedSize(true);

        //Define o layout manager, que irá consumir do adapter, conforme necessário
        mLayoutManager = new LinearLayoutManager(getContext());
        rvBills.setLayoutManager(mLayoutManager);

        this.srlBills.setOnRefreshListener(this);

        this.refreshData();
        this.srlBills.setRefreshing(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void refreshData() {
        ListBillsRequest listBillsRequest = new ListBillsRequest();
        AuthorizedFetcher<ListBillsRequest, GenericBills> billsFetcher = new AuthorizedFetcher<>(getActivity(), new AuthorizedFetcher.AuthorizedFetchCallback<GenericBills>() {
            @Override
            public void onSuccess(GenericBills data) {
                setOrUpdateData(data);
                srlBills.setRefreshing(false);
            }

            @Override
            public void onNoConnection() {
                Toast.makeText(getActivity(), "Verifique sua conexão com a internet", Toast.LENGTH_LONG).show();
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

    private void setOrUpdateData(GenericBills gb) {
        this.genericBills = gb;
        this.adapter.setContentList(gb.getGenericList());
    }

    @Override
    public void onRefresh() { //SWIPEVIEW
        this.refreshData();
    }

    @Override
    public void onRowClick(Long billId) {
        Intent openStatementsIntent = new Intent(this.getActivity(), FinantialStatementsActivity.class);
        openStatementsIntent.putExtra(this.BILL_ID_EXTRA, billId);
        this.startActivity(openStatementsIntent);
    }
}
