package us.guihouse.autobank.other;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import us.guihouse.autobank.R;
import us.guihouse.autobank.bean.Card;
import us.guihouse.autobank.bean.CardLostOrStolen;
import us.guihouse.autobank.fetchers.AuthorizedFetcher;
import us.guihouse.autobank.http.CardLostOrStolenRequest;

/**
 * Created by aluno on 28/11/16.
 */

public class CardLostOrStolenDialog extends DialogFragment {
    private static final String CARD_EXTRA = "CARD_EXTRA";
    private Card card;

    private ProgressDialog progressDialog;
    private AuthorizedFetcher<CardLostOrStolenRequest, Boolean> fetcher;

    public static CardLostOrStolenDialog newInstance(Card card) {
        Bundle args = new Bundle();
        args.putSerializable(CARD_EXTRA, card);
        CardLostOrStolenDialog dialog = new CardLostOrStolenDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        card = (Card) getArguments().getSerializable(CARD_EXTRA);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        progressDialog = ProgressDialog.show(getContext(), getString(R.string.blocking_card), getString(R.string.cancel), true, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancelCall();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View inflated = inflater.inflate(R.layout.dialog_card_lost, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflated)
                // Add action buttons
                .setPositiveButton(R.string.block_card, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText text = (EditText) CardLostOrStolenDialog.this
                                .getDialog().findViewById(R.id.dialog_card_lost_reason);

                        call(text.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CardLostOrStolenDialog.this.getDialog().dismiss();
                        CardLostOrStolenDialog.this.progressDialog.dismiss();
                    }
                })
                .setTitle(R.string.block_card)
                .setCancelable(false);
        return builder.create();
    }

    private void cancelCall() {
        if (fetcher != null) {
            fetcher.cancel(false);
        }
    }

    private void call(String reason) {
        fetcher = new AuthorizedFetcher<>(getActivity(), new AuthorizedFetcher.AuthorizedFetchCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                progressDialog.dismiss();
                Toast.makeText(progressDialog.getContext(), R.string.card_blocked, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNoConnection() {
                progressDialog.dismiss();
                Toast.makeText(progressDialog.getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError() {
                progressDialog.dismiss();
                Toast.makeText(progressDialog.getContext(), R.string.unknow_error, Toast.LENGTH_LONG).show();
            }
        });

        progressDialog.show();
        fetcher.execute(new CardLostOrStolenRequest(new CardLostOrStolen(card.getId(), reason)));
    }
}
