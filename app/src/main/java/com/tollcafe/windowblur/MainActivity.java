package com.tollcafe.windowblur;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.util.function.Consumer;

public class MainActivity extends Activity {
    private final int mBackgroundBlurRadius = 150;
    private final int mBlurBehindRadius = 50;
    private final float mDimAmountWithBlur = 0.1f;
    private final float mDimAmountNoBlur = 0.6f;

    private Consumer<Boolean> mCrossWindowBlurEnabledListener = enabled -> {
        getWindow().setDimAmount(enabled ? mDimAmountWithBlur : mDimAmountNoBlur);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        getWindow().getAttributes().setBlurBehindRadius(mBlurBehindRadius);
        getWindow().setBackgroundBlurRadius(mBackgroundBlurRadius);
        getWindow().getDecorView().addOnAttachStateChangeListener(
                new View.OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View v) {
                        getWindowManager().addCrossWindowBlurEnabledListener(
                                mCrossWindowBlurEnabledListener);
                    }

                    @Override
                    public void onViewDetachedFromWindow(View v) {
                        getWindowManager().removeCrossWindowBlurEnabledListener(
                                mCrossWindowBlurEnabledListener);
                    }
                });

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}