package com.example.videoplayerappraqs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class BouncingImageView extends View {
    private Drawable image;
    private int x, y;
    private int dx = 10, dy = 10;
    private final int FRAME_RATE = 30;
    private final Handler handler = new Handler();

    public BouncingImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        image = context.getResources().getDrawable(R.drawable.splash_image);
        handler.postDelayed(updateRunnable, FRAME_RATE);
    }

    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
            handler.postDelayed(this, FRAME_RATE);
        }
    };

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        x = (w - image.getIntrinsicWidth()) / 2;
        y = (h - image.getIntrinsicHeight()) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int imgWidth = image.getIntrinsicWidth();
        int imgHeight = image.getIntrinsicHeight();

        x += dx;
        y += dy;

        if (x <= 0 || x + imgWidth >= width) {
            dx = -dx;
        }
        if (y <= 0 || y + imgHeight >= height) {
            dy = -dy;
        }

        image.setBounds(x, y, x + imgWidth, y + imgHeight);
        image.draw(canvas);
    }
}
