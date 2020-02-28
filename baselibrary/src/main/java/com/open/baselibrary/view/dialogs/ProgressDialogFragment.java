package com.open.baselibrary.view.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.open.baselibrary.R;
import com.open.baselibrary.base.BaseDialogFragment;

public class ProgressDialogFragment extends BaseDialogFragment {

    private static final String MSG_KEY = "titleKey";
    public static final String COMMUNITY = "community";
    public static final String COMMUNITY_IN_POSTDEATIL = "community_in_postdeatil";
    private OnCancelListener onCancelListener = null;

    public static ProgressDialogFragment newInstance() {
        return new ProgressDialogFragment();
    }

    public static ProgressDialogFragment newInstance(String message) {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString(MSG_KEY, message);
        fragment.setArguments(args);

        return fragment;
    }

    public static ProgressDialogFragment newInstance(String message, boolean isInCommunity) {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString(MSG_KEY, message);
        args.putBoolean(COMMUNITY, isInCommunity);
        fragment.setArguments(args);
        return fragment;
    }

    public static ProgressDialogFragment newInstance(String message, boolean isInCommunityInPostDetail, OnCancelListener onCancelListener) {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString(MSG_KEY, message);
        args.putBoolean(COMMUNITY, true);
        args.putBoolean(COMMUNITY_IN_POSTDEATIL, isInCommunityInPostDetail);
        fragment.setArguments(args);
        fragment.onCancelListener = onCancelListener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String message = null;
        boolean isInCommunity = false;
        boolean isInCommunityInPostDetail = false;
        if (getArguments() != null) {
            message = getArguments().getString(MSG_KEY);
            isInCommunity = getArguments().getBoolean(COMMUNITY, false);
            isInCommunityInPostDetail = getArguments().getBoolean(COMMUNITY_IN_POSTDEATIL, false);
        }

        Dialog progressDialog;
        if (TextUtils.isEmpty(message)) {
            progressDialog = new ProgressDialog(getActivity(), R.style.TransparentProgressDialog);
        } else {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.progress_dialog, new LinearLayout(getContext()), false);
            ((TextView) view.findViewById(R.id.messageView)).setText(message);
//            if (isInCommunity) {
//                view.setBackgroundColor(getContext().getResources().getColor(R.color.community_white));
//                ((TextView) view.findViewById(R.id.messageView)).setTextColor(Color.parseColor("#1f1f1f"));
//            }
            progressDialog = new AlertDialog.Builder(getActivity())
                    .setView(view).create();
        }
        if (isInCommunityInPostDetail) {
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (onCancelListener != null) {
                            onCancelListener.onCancelListener(dialog);
                        }
                    }
                    return false;
                }
            });
        }
        //setCancelable(true);
        return progressDialog;
    }
}


