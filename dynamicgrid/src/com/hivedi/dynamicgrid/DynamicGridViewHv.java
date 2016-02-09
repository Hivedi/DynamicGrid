package com.hivedi.dynamicgrid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

import org.askerov.dynamicgrid.DynamicGridView;
import org.askerov.dynamicgrid.R;

/**
 * Created by Hivedi2 on 2015-11-24.
 *
 */
public class DynamicGridViewHv extends DynamicGridView implements AbsListView.OnScrollListener {

	private Paint linePaint;
	private float lineSize = 1;
	private int dividerColor = 0xFFcccccc;
    private boolean drawDividers = true;

	public DynamicGridViewHv(Context context) {
		super(context);
		initComponent(context, null);
	}

	public DynamicGridViewHv(Context context, AttributeSet attrs) {
		super(context, attrs);
		initComponent(context,attrs);
	}

	public DynamicGridViewHv(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initComponent(context, attrs);
	}

	private void initComponent(Context context, AttributeSet attrs) {
        setOnScrollListener(this);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DynamicGridViewHv);
		dividerColor = ta.getColor(R.styleable.DynamicGridViewHv_divider_color, dividerColor);
		ta.recycle();

		lineSize = getResources().getDisplayMetrics().density;
		linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(dividerColor);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineSize);
	}

	public void setDividerColor(int c) {
		dividerColor = c;
		linePaint.setColor(dividerColor);
		invalidate();
	}

	public int getDividerColor() {
		return dividerColor;
	}

	@Override
	public void refreshDrawableState() {
		super.refreshDrawableState();
        if (isDrawDividers()) {
            linePaint.setColor(dividerColor);
            invalidate();
        }
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
        if (isDrawDividers()) {
            int colCount = 1;
            if (Build.VERSION.SDK_INT >= 11) {
                colCount = getNumColumns();
            }
            int mh = getMeasuredHeight();
            int mw = getMeasuredWidth();

            float boxWidth = ((float) mw / (float) colCount);
            float boxHeight = boxWidth * 0.75f;
            int yCount = (int) (mh / boxHeight);

            for (int i = 1; i < colCount; i++) {
                canvas.drawLine(boxWidth * i, 0, boxWidth * i, mh, linePaint);
            }

            if (getChildCount() >= yCount * colCount) {
                for (int i = 0; i < getChildCount() / colCount; i++) {
                    View v = getChildAt(i * colCount);
                    float y = v.getTop() + v.getMeasuredHeight() + (lineSize / 2f);
                    canvas.drawLine(0, y, mw, y, linePaint);
                }
            } else {
                // draw static horizontal grid lines
                for (int i = 0; i < yCount + 1; i++) {
                    float y = (boxHeight * (i + 1));
                    canvas.drawLine(0, y, mw, y, linePaint);
                }
            }
        }
	}

	@Override
	public int computeVerticalScrollOffset() {
		return super.computeVerticalScrollOffset();
	}

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // noop
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isDrawDividers()) {
            invalidate();
        }
    }

    public boolean isDrawDividers() {
        return drawDividers;
    }

    public void setDrawDividers(boolean drawDividers) {
        this.drawDividers = drawDividers;
    }
}
