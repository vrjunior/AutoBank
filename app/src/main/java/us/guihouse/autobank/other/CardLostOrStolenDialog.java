package us.guihouse.autobank.other;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import us.guihouse.autobank.R;

/**
 * Created by aluno on 28/11/16.
 */

public class CardLostOrStolenDialog extends DialogFragment {
    private ProgressBar progress;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View inflated = inflater.inflate(R.layout.dialog_card_lost, null);

        progress = (ProgressBar) inflated.findViewById(R.id.dialog_card_lost_progress);
        progress.setVisibility(View.GONE);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflated)
                // Add action buttons
                .setPositiveButton(R.string.block_card, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        progress.setVisibility(View.VISIBLE);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CardLostOrStolenDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
