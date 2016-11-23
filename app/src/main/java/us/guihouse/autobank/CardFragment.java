package us.guihouse.autobank;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import us.guihouse.autobank.bean.Card;
import us.guihouse.autobank.fetchers.AuthorizedFetcher;
import us.guihouse.autobank.http.CardRequest;

public class CardFragment extends Fragment {
    private static final String SAVED_CARD = "SAVED_CARD";
    private Card card;

    private ProgressBar progress;
    private View details;

    private AuthorizedFetcher<CardRequest, Card> fetcher;
    private boolean destroyed = false;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(SAVED_CARD, card);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            card = (Card) savedInstanceState.getSerializable(SAVED_CARD);
        }

        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_card, container, false);
        progress = (ProgressBar) inflated.findViewById(R.id.fragment_card_progress);
        details = inflated.findViewById(R.id.fragment_card_details);

        destroyed = false;
        fetchCardIfNeeded();

        return inflated;
    }

    @Override
    public void onDestroyView() {
        if (fetcher != null) {
            fetcher.cancel(true);
        }

        destroyed = true;
        super.onDestroyView();
    }

    private void fetchCardIfNeeded() {
        if (card != null) {
            setSuccess();
            return;
        }

        setLoading();

        fetcher = new AuthorizedFetcher<>(this.getActivity(),
                new AuthorizedFetcher.AuthorizedFetchCallback<Card>() {
            @Override
            public void onSuccess(Card data) {
                card = data;
                fetcher = null;
                setSuccess();
            }

            @Override
            public void onNoConnection() {
                card = null;
                fetcher = null;
                setError();
            }

            @Override
            public void onError() {
                card = null;
                fetcher = null;
                setError();
            }
        });

        fetcher.execute(new CardRequest());
    }

    private void setLoading() {
        progress.setVisibility(View.VISIBLE);
        details.setVisibility(View.GONE);
    }

    private void setSuccess() {
        if (destroyed) {
            return;
        }
        progress.setVisibility(View.GONE);
        details.setVisibility(View.VISIBLE);
    }

    private void setError() {
        if (destroyed) {
            return;
        }
        progress.setVisibility(View.GONE);
        details.setVisibility(View.GONE);
    }
}
