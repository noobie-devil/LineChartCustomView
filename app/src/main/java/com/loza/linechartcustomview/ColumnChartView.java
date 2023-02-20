package com.loza.linechartcustomview;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ColumnChartView extends View {

    private static final int COLUMN_WIDTH = 100;
    private static final int COLUMN_SPACING = 20;
    private static final int ANIMATION_DURATION = 1000;
    private static final int ANIMATION_FRAME_RATE = 60;

    private Paint mPaint;
    private Map<String, Integer> mTypeCountMap;
    private Map<String, Integer> mTypeColorMap;
    private List<Asset> mAssets;
    private long mStartTime;
    private float mAnimationProgress;

    public ColumnChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mTypeCountMap = new HashMap<>();
        mTypeColorMap = new HashMap<>();
    }

    public void setAssets(List<Asset> assets) {
        mAssets = assets;
        mTypeCountMap.clear();
        mTypeColorMap.clear();
        Random random = new Random();
        for (Asset asset : assets) {
            String type = asset.getType();
            int count = mTypeCountMap.getOrDefault(type, 0) + 1;
            mTypeCountMap.put(type, count);
            if (!mTypeColorMap.containsKey(type)) {
                int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                mTypeColorMap.put(type, color);
            }
        }
        mStartTime = System.currentTimeMillis();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAssets == null || mAssets.isEmpty()) {
            return;
        }
        long elapsedTime = System.currentTimeMillis() - mStartTime;
        if (elapsedTime < ANIMATION_DURATION) {
            mAnimationProgress = (float) elapsedTime / ANIMATION_DURATION;
            postInvalidateOnAnimation();
        } else {
            mAnimationProgress = 1.0f;
        }
        int width = getWidth();
        int height = getHeight();
        int columnCount = mTypeCountMap.size();
        int columnWidth = (width - (columnCount - 1) * COLUMN_SPACING) / columnCount;
        int maxCount = 0;
        for (int count : mTypeCountMap.values()) {
            maxCount = Math.max(maxCount, count);
        }
        int x = 0;
        for (Map.Entry<String, Integer> entry : mTypeCountMap.entrySet()) {
            String type = entry.getKey();
            int count = entry.getValue();
            int color = mTypeColorMap.get(type);
            float columnHeight = mAnimationProgress * count * height / maxCount;
            mPaint.setColor(color);
            canvas.drawRect(x, height - columnHeight, x + columnWidth, height, mPaint);
            x += columnWidth + COLUMN_SPACING;
        }
    }


}




