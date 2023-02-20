package com.loza.linechartcustomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//public class SkyView extends View {
//    private static final int NUM_STARS = 30;
//    private Paint backgroundPaint;
//    private Path backgroundPath;
//    private Paint circlePaint;
//    private final Random random = new Random();
//
//    private Bitmap offscreenBitmap;
//    private Canvas offScreenCanvas;
//
//    private final MutableLiveData<Boolean> flagOnMeasure = new MutableLiveData<>();
//
//    private List<Entity> entities = new ArrayList<>();
//
//    public SkyView(Context context) {
//        super(context);
//        init();
//    }
//
//    public SkyView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    public SkyView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    private void init() {
//        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        backgroundPaint.setColor(Color.parseColor("#202A44"));
//        backgroundPath = new Path();
//        for (int i = 0; i < NUM_STARS; i++) {
//            entities.add(new SkyEntity(this));
//        }
//        setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        flagOnMeasure.observeForever(new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                if(aBoolean) {
//                    offscreenBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
//                    offScreenCanvas = new Canvas(offscreenBitmap);
//
//                    flagOnMeasure.removeObserver(this);
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        flagOnMeasure.postValue(true);
////        offscreenBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        offscreenBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        offScreenCanvas = new Canvas(offscreenBitmap);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if(offscreenBitmap == null) {
//            return;
//        }
//        backgroundPath.reset();
//        backgroundPath.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CW);
//        offScreenCanvas.drawPath(backgroundPath, backgroundPaint);
//        circlePaint.setColor(Color.RED);
//
//        for (Entity entity : entities) {
//            entity.draw(offScreenCanvas, circlePaint);
//        }
//        canvas.drawBitmap(offscreenBitmap, 0, 0, null);
//    }
//
//    public void update(long frameTimeMillis, int chartSpeed) {
//        for (Entity entity : entities) {
//            entity.onFrame(frameTimeMillis, chartSpeed);
//        }
//
//        invalidate();
//    }
//
//
//
//    private interface Entity {
//        void onFrame(long frameTimeMillis, int chartSpeed);
//        void draw(Canvas canvas, Paint paint);
//    }
//
//    private class SkyEntity implements Entity {
//        private View parent;
//        private float x = -1f;
//        private float y = 0f;
//        private float radius = 0f;
//        private float speed = 0f;
//
//        public SkyEntity(View parent) {
//            this.parent = parent;
//            generateNewValues(true);
//        }
//
//        private void generateNewValues(boolean initial) {
//            speed = dpToPx(parent, 6) * (4 + random.nextFloat());
//            radius = dpToPx(parent, 3) * (1 + random.nextFloat());
//
//            int dx = initial ? 0 : 1;
//            x = parent.getWidth() * (dx + random.nextFloat());
//            y = parent.getHeight() * random.nextFloat();
//        }
//
//        @Override
//        public void onFrame(long frameTimeMillis, int chartSpeed) {
//            if (x < -radius) {
//                generateNewValues(false);
//            }
//            x -= speed * (frameTimeMillis / 1000f) * chartSpeed;
//        }
//
//        @Override
//        public void draw(Canvas canvas, Paint paint) {
//            canvas.drawCircle(x, y, radius, paint);
//        }
//
//        private float dpToPx(View view, int dp) {
//            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, view.getResources().getDisplayMetrics());
//        }
//    }
//}

public class SkyView extends TextureView implements TextureView.SurfaceTextureListener {

    private static final int NUM_STARS = 18;
    private Paint backgroundPaint;
    private Path backgroundPath;
    private Paint circlePaint;
    private final Paint innerCirclePaint = new Paint();
    private final Paint outerCirclePaint = new Paint();
    private final PorterDuffXfermode xFerMode = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);
    private Bitmap offscreenBitmap;
    private Canvas offScreenCanvas;
    private Canvas circleGlowingCanvas;
    private final Random random = new Random();

    private Bitmap circleBitmap;

    private float CIRCLE_GLOWING_RAD;

    private final MutableLiveData<Boolean> flagOnMeasure = new MutableLiveData<>();

    private List<Entity> entities = new ArrayList<>();

    public SkyView(Context context) {
        super(context);
        init();
    }

    public SkyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SkyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        CIRCLE_GLOWING_RAD = dpToPx(this, 30);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.parseColor("#202A44"));
        backgroundPath = new Path();
        for (int i = 0; i < NUM_STARS; i++) {
            entities.add(new SkyEntity(this));
        }
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        flagOnMeasure.observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    offscreenBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                    offScreenCanvas = new Canvas(offscreenBitmap);

                    flagOnMeasure.removeObserver(this);
                }
            }
        });
        circleBitmap = Bitmap.createBitmap((int) CIRCLE_GLOWING_RAD, (int) CIRCLE_GLOWING_RAD, Bitmap.Config.ARGB_8888);
        circleGlowingCanvas = new Canvas(circleBitmap);
        innerCirclePaint.setColor(Color.RED); // dark color
        innerCirclePaint.setAntiAlias(true);

        outerCirclePaint.setColor(Color.parseColor("#22888888")); // translucent color
        outerCirclePaint.setAntiAlias(true);
        outerCirclePaint.setStyle(Paint.Style.FILL);
        setSurfaceTextureListener(this);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        flagOnMeasure.postValue(true);
//        offscreenBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        offscreenBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        offScreenCanvas = new Canvas(offscreenBitmap);
    }


    public void update(long frameTimeMillis, int chartSpeed) {
        for (Entity entity : entities) {
            entity.onFrame(frameTimeMillis, chartSpeed);
        }
        if (offscreenBitmap == null) {
            return;
        }

        backgroundPath.reset();
        backgroundPath.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CW);
        offScreenCanvas.drawPath(backgroundPath, backgroundPaint);
        circlePaint.setColor(Color.GRAY);

        for (Entity entity : entities) {
            entity.draw(offScreenCanvas, circlePaint);
        }

        if(circleBitmap != null) {
            float centerX = getWidth() / 2f;
            float centerY = getHeight() / 2f;
            float innerRadius = CIRCLE_GLOWING_RAD;
            float outerRadius = CIRCLE_GLOWING_RAD * 1.3f;
            innerCirclePaint.setColor(Color.RED); // dark color
            circleGlowingCanvas.drawCircle(centerX, centerY, innerRadius, innerCirclePaint);
//            outerCirclePaint.setColor(Color.parseColor("#22888888")); // translucent color
//            outerCirclePaint.setStyle(Paint.Style.FILL);
//            circleGlowingCanvas.drawCircle(centerX, centerY, outerRadius, outerCirclePaint);

        }

        Canvas canvas = lockCanvas();
        if (canvas != null) {
            if(circleBitmap != null) {
                innerCirclePaint.setXfermode(xFerMode);
                canvas.drawBitmap(circleBitmap, 0, 0, null);
            }
            canvas.drawBitmap(offscreenBitmap, 0, 0, null);
            unlockCanvasAndPost(canvas);
        }
//        circleGlowingCanvas.drawCircle(getWidth() / 2f, getHeight() / 2f, CIRCLE_GLOWING_RAD, innerCirclePaint);
//        circleGlowingCanvas.drawCircle(getWidth() / 2f, getHeight() / 2f, CIRCLE_GLOWING_RAD * 1.3f, outerCirclePaint);
//        innerCirclePaint.setXfermode(xFerMode);
//        circleGlowingCanvas.drawCircle(getWidth() / 2f, getHeight() / 2f, CIRCLE_GLOWING_RAD, innerCirclePaint);

    }

    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
        offscreenBitmap = Bitmap.createBitmap(i, i1, Bitmap.Config.ARGB_8888);
        offScreenCanvas = new Canvas(offscreenBitmap);

        circleBitmap = Bitmap.createBitmap((int) CIRCLE_GLOWING_RAD, (int) CIRCLE_GLOWING_RAD, Bitmap.Config.ARGB_8888);
        circleGlowingCanvas = new Canvas(circleBitmap);


    }

    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
        offscreenBitmap = Bitmap.createBitmap(i, i1, Bitmap.Config.ARGB_8888);
        offScreenCanvas = new Canvas(offscreenBitmap);

        circleBitmap = Bitmap.createBitmap((int) CIRCLE_GLOWING_RAD, (int) CIRCLE_GLOWING_RAD, Bitmap.Config.ARGB_8888);
        circleGlowingCanvas = new Canvas(circleBitmap);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
        offscreenBitmap.recycle();
        offscreenBitmap = null;
        offScreenCanvas = null;
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {

    }


    private interface Entity {
        void onFrame(long frameTimeMillis, int chartSpeed);
        void draw(Canvas canvas, Paint paint);
    }

    private class SkyEntity implements Entity {
        private View parent;
        private float x = -1f;
        private float y = 0f;
        private float radius = 0f;
        private float speed = 0f;

        public SkyEntity(View parent) {
            this.parent = parent;
            generateNewValues(true);
        }

        private void generateNewValues(boolean initial) {
            speed = dpToPx(parent, 6) * (4 + random.nextFloat());
            radius = dpToPx(parent, 3) * (1 + random.nextFloat());

            int dx = initial ? 0 : 1;
            x = parent.getWidth() * (dx + random.nextFloat());
            y = parent.getHeight() * random.nextFloat();
        }

        @Override
        public void onFrame(long frameTimeMillis, int chartSpeed) {
            if (x < -radius) {
                generateNewValues(false);
            }
            x -= speed * (frameTimeMillis / 1000f) * chartSpeed;
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            canvas.drawCircle(x, y, radius, paint);
        }

        private float dpToPx(View view, int dp) {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, view.getResources().getDisplayMetrics());
        }
    }

    private float dpToPx(View view, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, view.getResources().getDisplayMetrics());
    }
}