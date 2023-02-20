package com.loza.linechartcustomview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class LineChartView extends View {

    private List<Asset> assetList;
    private Paint linePaint;
    private Random random;
    private Map<String, Integer> typeCounts;
    private Map<String, Paint> typeColors;

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        linePaint = new Paint();
        typeCounts = new HashMap<>();
        typeColors = new HashMap<>();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);
        random = new Random();
    }

    public LineChartView(Context context) {
        super(context, null);
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);
        random = new Random();
    }

    public void setAssetList(List<Asset> assetList) {
        this.assetList = assetList;
        calculateTypeCounts();
        startAnimation();
    }

    private void calculateTypeCounts() {
        typeCounts.clear();
        for (Asset asset : assetList) {
            String type = asset.getType();
            int count = typeCounts.getOrDefault(type, 0);
            typeCounts.put(type, count + 1);
        }
    }

    private void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                // Update the chart with the fraction value
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Define a map to store the number of assets for each type
        Map<String, Integer> assetCounts = new HashMap<>();

        // Count the number of assets for each type
        for (Asset asset : assetList) {
            String type = asset.getType();
            int count = assetCounts.getOrDefault(type, 0);
            assetCounts.put(type, count + 1);
        }

        // Get the maximum number of assets for a type
        int maxAssetCount = Collections.max(assetCounts.values());

        // Calculate the width of a single column
        int columnWidth = contentWidth / assetCounts.size();

        // Define a map to store the colors for each type
        Map<String, Integer> typeColors = new HashMap<>();

        // Draw the columns for each type
        int x = paddingLeft;
        for (Map.Entry<String, Integer> entry : assetCounts.entrySet()) {
            String type = entry.getKey();
            int count = entry.getValue();

            // Generate a random color for the type if it doesn't already have a color
            if (!typeColors.containsKey(type)) {
                typeColors.put(type, Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            }

            int color = typeColors.get(type);
            Paint columnPaint = new Paint();
            columnPaint.setColor(color);

            // Calculate the height of the column
            int columnHeight = (count * contentHeight) / maxAssetCount;

            // Draw the column
            int y = contentHeight - columnHeight + paddingTop;
            canvas.drawRect(x, y, x + columnWidth, contentHeight + paddingTop, columnPaint);

            x += columnWidth;
        }
    }


//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (assetList == null) {
//            return;
//        }
//        Path linePath = new Path();
//
//        // Get the number of assets by type
//        Map<String, Integer> assetCountByType = new HashMap<>();
//        for (Asset asset : assetList) {
//            String type = asset.getType();
//            int count = assetCountByType.getOrDefault(type, 0);
//            assetCountByType.put(type, count + 1);
//        }
//
//        // Calculate the x-coordinate step based on the number of types
//        int xStep = getWidth() / assetCountByType.size();
//
//        // Set the starting point of the line chart
//        int x = 0;
//        int y = getHeight() / 2;
//        linePath.moveTo(x, y);
//
//        // Iterate through the number of assets by type and draw the line chart
//        for (Map.Entry<String, Integer> entry : assetCountByType.entrySet()) {
//            String type = entry.getKey();
//            int count = entry.getValue();
//
//            // Generate a random color for the line chart of this type
//            Random rnd = new Random();
//            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//            linePaint.setColor(color);
//
//            // Calculate the y-coordinate based on the count of assets of this type
//            y = (int) (getHeight() * (1 - (count / (double) assetList.size())));
//
//            // Draw the line chart for this type
//            x += xStep;
//            linePath.lineTo(x, y);
//
//            // Draw the line chart for this type with the color
//            canvas.drawPath(linePath, linePaint);
//        }
//
//    }
}
