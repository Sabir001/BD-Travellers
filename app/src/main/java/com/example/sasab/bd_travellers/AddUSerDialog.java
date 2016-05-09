package com.example.sasab.bd_travellers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

/**
 * Created by sasab on 08-May-16.
 */
public class AddUSerDialog extends DialogFragment {
    public interface AddUserDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);

        void onDialogNegativeClick(DialogFragment dialog);
    }

    AddUserDialogListener addUserDialogListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            addUserDialogListener = (AddUserDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle("Join Group");
        builder.setView(inflater.inflate(R.layout.add_user, null));
        builder.setMessage(getString(R.string.dialouge_message))
                .setPositiveButton(getString(R.string.dialouge_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        addUserDialogListener.onDialogPositiveClick(AddUSerDialog.this);

                    }
                })
                .setNegativeButton(getString(R.string.dialouge_cancel_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        addUserDialogListener.onDialogNegativeClick(AddUSerDialog.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }



}
