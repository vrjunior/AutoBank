package us.guihouse.autobank;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.math.BigDecimal;

public class CardFragment extends Fragment {

    private ProgressBar pbLimit;

    public CardFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CardFragment newInstance(String param1, String param2) {
        CardFragment fragment = new CardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        pbLimit = (ProgressBar) view.findViewById(R.id.pbLimit);

        pbLimit.setProgress(getPorcentageLimitUsed(new BigDecimal(1000), new BigDecimal(500)));

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


    private int getPorcentageLimitUsed(BigDecimal limit, BigDecimal valueUsed) {
        return ((valueUsed.multiply(new BigDecimal(100)).divide(limit))).intValue();
    }

}
