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
public class DynamicGridViewHv extends DynamicGridView {

	private Paint linePaint;
	private float lineSize = 1;
	private int dividerColor = 0xFFcccccc;

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

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DynamicGridViewHv);
		dividerColor = ta.getColor(R.styleable.DynamicGridViewHv_divider_color, dividerColor);
		ta.recycle();

		lineSize = getResources().getDisplayMetrics().density;
		linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setColor(dividerColor);
		linePaint.setStyle(Paint.Style.STROKE);
		linePaint.setStrokeWidth(lineSize);

		setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				invalidate();
			}
		});
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
		linePaint.setColor(dividerColor);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int colCount = Build.VERSION.SDK_INT >= 11 ? getNumColumns() : 1;
		int mh = getMeasuredHeight();
		int mw = getMeasuredWidth();

		float boxWidth = ((float) getMeasuredWidth() / (float) colCount);
		float boxHeight = boxWidth * 0.75f;
		int yCount = (int) (getMeasuredHeight() / boxHeight);

		for(int i=1; i<colCount; i++) {
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

	@Override
	public int computeVerticalScrollOffset() {
		return super.computeVerticalScrollOffset();
	}
}
