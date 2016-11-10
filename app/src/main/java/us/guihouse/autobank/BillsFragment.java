package us.guihouse.autobank;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import us.guihouse.autobank.bean.GenericBills;
import us.guihouse.autobank.fetchers.AuthorizedFetcher;
import us.guihouse.autobank.fetchers.BasicFetcher;
import us.guihouse.autobank.http.BasePostRequest;
import us.guihouse.autobank.http.ListBillsRequest;

public class BillsFragment extends Fragment {

    private RecyclerView rvBills;
    private GenericBills genericBills;

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
        this.rvBills = (RecyclerView) getActivity().findViewById(R.id.rvBills);
        ListBillsRequest listBillsRequest = new ListBillsRequest();
        AuthorizedFetcher<ListBillsRequest, GenericBills> billsFetcher = new AuthorizedFetcher<>(getActivity() ,new AuthorizedFetcher.AuthorizedFetchCallback<GenericBills>() {

            @Override
            public void onSuccess(GenericBills data) {
                genericBills = data;
            }

            @Override
            public void onNoConnection() {
                Toast.makeText(getActivity(), "Verifique sua conexão com a internet", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError() {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }
        });
        billsFetcher.execute(listBillsRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bills, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
