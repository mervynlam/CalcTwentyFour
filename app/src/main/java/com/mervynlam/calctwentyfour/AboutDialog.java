package com.mervynlam.calctwentyfour;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Mervynlam on 2017/8/7.
 */

public class AboutDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String message = getResources().getString(R.string.about_details);
		message = String.format(message, GetVersion.getVersion(getActivity()));
		builder.setTitle(R.string.dlg_about_title).setMessage(message).setCancelable(true).setPositiveButton(R.string.dlg_about_positive, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {

			}
		});
		return builder.create();
	}
}
