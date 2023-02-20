package com.loza.linechartcustomview;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AssetColumnChartView extends View {
//    private List<Asset> assetList;
//    private Paint columnPaint;
//    private Map<String, Integer> typeColorMap;
//    private float heightScaleFactor = 0.8f;
//    private int animationStep = 0;
//    private Rect columnRect;
//    private int columnWidth;
//    private int maxColumnHeight;
//    private Random random;
//
    public AssetColumnChartView(Context context) {
        super(context);
//        init();
    }

//    public AssetColumnChartView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    public AssetColumnChartView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    private void init() {
//        assetList = new ArrayList<>();
//        typeColorMap = new HashMap<>();
//        columnPaint = new Paint();
//        columnRect = new Rect();
//        columnWidth = 100;
//        maxColumnHeight = 0;
//        random = new Random();
//        setBackgroundColor(Color.BLACK);
//    }
//
//    public void setAssetList(List<Asset> assetList) {
//        this.assetList = assetList;
//        calculateMaxColumnHeight();
//        invalidate();
//    }
//
//    private void startAnimation() {
//        for (String type : typeColorMap.keySet()) {
//            ValueAnimator animator = ValueAnimator.ofInt(0, maxColumnHeight);
//            animator.setDuration(0);
//            final Rect column = typeColumns.get(type);
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    int height = (int) animation.getAnimatedValue();
//                    column.top = maxColumnHeight - height;
//                    column.bottom = maxColumnHeight;
//                    invalidate();
//                }
//            });
//            animator.start();
//        }
//    }
//
//    public void setHeightScaleFactor(float heightScaleFactor) {
//        this.heightScaleFactor = heightScaleFactor;
//        invalidate();
//    }
//
//    private void calculateMaxColumnHeight() {
//        int assetCount = 0;
//        for (Asset asset : assetList) {
//            if (!typeColorMap.containsKey(asset.getType())) {
//                typeColorMap.put(asset.getType(), Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
//            }
//            assetCount++;
//        }
//        maxColumnHeight = (int) (getHeight() * heightScaleFactor);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        int left = 0;
//        int top = 0;
//        int right = 0;
//        int bottom = 0;
//        for (Asset asset : assetList) {
//            String type = asset.getType();
//            int color = typeColorMap.get(type);
//            columnPaint.setColor(color);
//            right = left + columnWidth;
//            bottom = (int) (maxColumnHeight * (animationStep / 100.0f));
//            columnRect.set(left, getHeight() - bottom, right, getHeight());
//        }
//    }
}
