package com.robj.radicallyreusable.base.components;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.robj.radicallyreusable.R;

/**
 * Created by jj on 29/09/16.
 */
public class SnackBarView extends LinearLayout implements View.OnClickListener {

    TextView snackBarText;
    Button snackBarIgnore;
    Button snackBarEnable;

    private ObjectAnimator snackBarAnim;
    private OnSnackBarActionListener onSnackBarActionListener;

    public SnackBarView(Context context) {
        super(context);
        init();
    }

    public SnackBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnackBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SnackBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.rr_view_snack_bar, this, true);
        snackBarText = findViewById(R.id.snack_bar_text);
        snackBarIgnore = findViewById(R.id.snack_bar_ignore);
        snackBarEnable = findViewById(R.id.snack_bar_enable);
        setOrientation(LinearLayout.VERTICAL);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        setPadding(padding, padding, padding, padding);
        setListeners();
        setBackgroundResource(R.color.snackbar_bg_color);
    }

    private void setListeners() {
        snackBarEnable.setOnClickListener(this);
        snackBarIgnore.setOnClickListener(this);
    }

    public void show() {
        if(getVisibility() != View.VISIBLE) {
            setVisibility(View.VISIBLE);
            snackBarAnim = ObjectAnimator.ofFloat(SnackBarView.this, View.Y, getBottom(), getTop());
            snackBarAnim.setDuration(300);
            snackBarAnim.start();
        }
    }

    public void hide() {
        if(getVisibility() == View.VISIBLE) {
            if(snackBarAnim != null) {
                snackBarAnim.reverse();
                snackBarAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        snackBarAnim.removeListener(this);
                        setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        snackBarAnim.removeListener(this);
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) { }
                });
            } else
                setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.snack_bar_enable) {
            if (onSnackBarActionListener != null)
                onSnackBarActionListener.onSnackBarClick();

        } else if (i == R.id.snack_bar_ignore) {
            hide();
            if (onSnackBarActionListener != null)
                onSnackBarActionListener.onSnackBarDismissed();

        }
    }

    public void setText(String text) {
        snackBarText.setText(text);
    }

    public void setText(@StringRes  int stringResId) {
        snackBarText.setText(stringResId);
    }

    public void setButtonText(@StringRes int stringResId) {
        snackBarEnable.setText(stringResId);
    }

    public void setOnSnackBarActionListener(OnSnackBarActionListener onSnackBarActionListener) {
        this.onSnackBarActionListener = onSnackBarActionListener;
    }

    public void setNeutralButtonText(@StringRes int stringResId) {
        snackBarIgnore.setText(stringResId);
    }

    public void hideActionButton() {
        snackBarEnable.setVisibility(View.GONE);
    }

    public void showActionButton() {
        snackBarEnable.setVisibility(View.VISIBLE);
    }

    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    public interface OnSnackBarActionListener {
        void onSnackBarClick();
        void onSnackBarDismissed();
    }

}
