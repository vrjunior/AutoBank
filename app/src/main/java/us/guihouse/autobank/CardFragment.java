package us.guihouse.autobank;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import us.guihouse.autobank.bean.Card;
import us.guihouse.autobank.fetchers.AuthorizedFetcher;
import us.guihouse.autobank.http.CardRequest;

public class CardFragment extends Fragment {
    private static final String SAVED_CARD = "SAVED_CARD";
    private Card card;

    private ProgressBar progress;
    private View details;

    private TextView cardNumber;
    private TextView emission;
    private TextView expiration;
    private TextView limit;
    private TextView interestRate;
    private TextView closingDay;
    private TextView availableValue;
    private TextView usedValue;

    private AuthorizedFetcher<CardRequest, Card> fetcher;
    private boolean destroyed = false;
    private DateFormat dateFormat;
    private NumberFormat numberFormat;
    private NumberFormat percentFormat;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateFormat = DateFormat.getDateInstance();

        numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setCurrency(Currency.getInstance("BRL"));

        percentFormat = NumberFormat.getPercentInstance(Locale.getDefault());
    }

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

        cardNumber = (TextView) inflated.findViewById(R.id.fragment_card_ending_digits);
        emission = (TextView) inflated.findViewById(R.id.fragment_card_emission);
        expiration = (TextView) inflated.findViewById(R.id.fragment_card_expire);
        limit = (TextView) inflated.findViewById(R.id.fragment_card_limit);
        interestRate = (TextView) inflated.findViewById(R.id.fragment_card_interest_rate);
        closingDay = (TextView) inflated.findViewById(R.id.fragment_card_closing_date);
        availableValue = (TextView) inflated.findViewById(R.id.fragment_card_available);
        usedValue = (TextView) inflated.findViewById(R.id.fragment_card_used);

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

        cardNumber.setText(card.getCardNumber());
        emission.setText(dateFormat.format(card.getEmission()));
        expiration.setText(dateFormat.format(card.getExpiration()));

        limit.setText(numberFormat.format(card.getLimit()));
        interestRate.setText(percentFormat.format(card.getInterestRate()));
        closingDay.setText(card.getClosingDay().toString());
        availableValue.setText(numberFormat.format(card.getAvailableValue()));
        usedValue.setText(numberFormat.format(card.getUsedValue()));
    }

    private void setError() {
        if (destroyed) {
            return;
        }
        progress.setVisibility(View.GONE);
        details.setVisibility(View.GONE);
    }
}
