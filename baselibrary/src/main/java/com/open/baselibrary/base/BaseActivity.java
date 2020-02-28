package com.open.baselibrary.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.open.baselibrary.R;
import com.open.baselibrary.utils.ThemeUtils;
import com.open.baselibrary.utils.ViewUtils;
import com.open.baselibrary.view.dialogs.OnCancelListener;
import com.open.baselibrary.view.dialogs.ProgressDialogFragment;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements AnimatedEnterExitAvailable {

    private static final String PROGRESS_DIALOG_TAG = "progressDialog";
    protected CompositeDisposable onCreateCompositeDisposable;
    private boolean isBackgroundNotSet = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.INSTANCE.enableFitSystemWindowsForJellyBean(this);
        onCreateCompositeDisposable = new CompositeDisposable();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    protected void addOnCreateSubscription(Disposable subscription) {
        onCreateCompositeDisposable.add(subscription);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onResume() {
        if (isBackgroundNotSet) {
            isBackgroundNotSet = false;
            getWindow().setBackgroundDrawableResource(ThemeUtils.getThemeAttrColorRes(this, R.attr.mainBackground));
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (onCreateCompositeDisposable != null) {
            onCreateCompositeDisposable.clear();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionDefault();
    }

    public void showProgressDialog(String message) {
        ProgressDialogFragment fragment = ProgressDialogFragment.newInstance(message);
        fragment.show(this.getSupportFragmentManager(), PROGRESS_DIALOG_TAG);
    }

    public void showProgressDialog(String message, Boolean isInCommunity) {
        ProgressDialogFragment fragment = ProgressDialogFragment.newInstance(message, isInCommunity);
        fragment.show(this.getSupportFragmentManager(), PROGRESS_DIALOG_TAG);
    }

    public void showProgressDialog(String message, Boolean isInCommunityInPostDetail, OnCancelListener onCancelListener) {
        ProgressDialogFragment fragment = ProgressDialogFragment.newInstance(message, isInCommunityInPostDetail, onCancelListener);
        fragment.show(this.getSupportFragmentManager(), PROGRESS_DIALOG_TAG);
    }

    public void showProgressDialog() {
        ProgressDialogFragment fragment = ProgressDialogFragment.newInstance();
        fragment.show(this.getSupportFragmentManager(), PROGRESS_DIALOG_TAG);
    }

    public void hideProgressDialog() {
        ProgressDialogFragment progressDialogFragment = (ProgressDialogFragment) this.getSupportFragmentManager().findFragmentByTag(PROGRESS_DIALOG_TAG);
        if (progressDialogFragment != null) {
            progressDialogFragment.dismissAllowingStateLoss();
        }
    }

    public final void overridePendingTransitionDefault() {
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @Override
    public void startAnimated(Intent intent) {
        startActivity(intent);
        overridePendingTransitionDefault();
    }

    @Override
    public void finishAnimated() {
        finish();
        overridePendingTransitionDefault();
    }
}
