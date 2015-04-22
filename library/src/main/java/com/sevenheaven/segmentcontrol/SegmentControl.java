package com.sevenheaven.segmentcontrol;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by caifangmao on 15/4/22.
 */
public class SegmentControl extends ViewGroup {

    private String[] mTexts;
    private TextView[] mTextViews;
    private StateListDrawable[] mBackgroundDrawables;

    private RadiusDrawable mBackgroundDrawable;

    private int mHorizonGap;
    private int mVerticalGap;

    private int mCenterX;
    private int mCenterY;

    private int mChildrenWidth;
    private int mChildrenHeight;

    private int mSingleChildWidth;
    private int mSingleChildHeight;

    private Paint mPaint;

    public enum Direction{
        HORIZON(0), VERTICAL(1);

        int mV;

        private Direction(int v){
            mV = v;
        }
    }

    private Direction mDirection;

    private int mTextSize;
    private ColorStateList mColors;
    private int mCornerRadius;

    public SegmentControl(Context context){
        this(context, null);
    }

    public SegmentControl(Context context,AttributeSet attrs){
        this(context, attrs, 0);
    }

    public SegmentControl(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SegmentControl);

        String textArray = ta.getString(R.styleable.SegmentControl_texts);

        if(textArray != null){
            mTexts = textArray.split("\\|");
        }


        mTextSize = ta.getDimensionPixelSize(R.styleable.SegmentControl_android_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics()));
        mColors = ta.getColorStateList(R.styleable.SegmentControl_colors);
        mCornerRadius = ta.getDimensionPixelSize(R.styleable.SegmentControl_cornerRadius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics()));
        mDirection = Direction.values()[ta.getInt(R.styleable.SegmentControl_direction, 0)];

        mHorizonGap = ta.getDimensionPixelSize(R.styleable.SegmentControl_horizonGap, 0);
        mVerticalGap = ta.getDimensionPixelSize(R.styleable.SegmentControl_verticalGap, 0);

        int gap = ta.getDimensionPixelSize(R.styleable.SegmentControl_gaps, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics()));

        if(mHorizonGap == 0) mHorizonGap = gap;
        if(mVerticalGap == 0) mVerticalGap = gap;

        ta.recycle();

        mBackgroundDrawable = new RadiusDrawable(mCornerRadius, true, 0);
        mBackgroundDrawable.setStrokeWidth(2);
        mBackgroundDrawable.setStrokeColor(mColors.getDefaultColor());

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setText(String... texts){
        mTexts = texts;

        if(mTexts != null){
            requestLayout();
        }else{
            if(mTextViews != null){
                for(TextView tv : mTextViews){
                    if(tv != null){
                        tv.setText("");
                    }
                }
            }
        }
    }

    public void setColors(ColorStateList colors){
        mColors = colors;

        if(mBackgroundDrawable != null){
            mBackgroundDrawable.setStrokeColor(colors.getDefaultColor());
        }

        mPaint.setColor(colors.getDefaultColor());

        requestLayout();
        invalidate();
    }

    public void setCornerRadius(int cornerRadius){
        mCornerRadius = cornerRadius;

        if(mBackgroundDrawable != null){
            mBackgroundDrawable.setRadius(cornerRadius);
        }

        requestLayout();
        invalidate();
    }

    public void setDirection(Direction direction){
        Direction tDirection = mDirection;

        mDirection = direction;

        if(tDirection != direction){
            requestLayout();
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if(mTexts != null){

            if(mTextViews == null || mTexts.length != mTextViews.length){
                mTextViews = new TextView[mTexts.length];

                removeAllViews();
            }

            if(mBackgroundDrawables == null || mBackgroundDrawables.length != mTexts.length){
                mBackgroundDrawables = new StateListDrawable[mTexts.length];
            }

            for(int i = 0; i < mTexts.length; i++){
                String text = mTexts[i];

                if(text != null){
                    if(mTextViews[i] == null){
                        mTextViews[i] = new TextView(getContext());
                        addView(mTextViews[i]);
                    }

                    if(mBackgroundDrawables[i] == null){
                        mBackgroundDrawables[i] = new StateListDrawable();

                        int topLeftRadius = 0;
                        int topRightRadius = 0;
                        int bottomLeftRadius = 0;
                        int bottomRightRadius = 0;

                        if(mDirection == Direction.HORIZON){
                            if(i == 0){
                                topLeftRadius = mCornerRadius;
                                bottomLeftRadius = mCornerRadius;
                            }else if(i == mTexts.length - 1){
                                topRightRadius = mCornerRadius;
                                bottomRightRadius = mCornerRadius;
                            }
                        }else{
                            if(i == 0){
                                topLeftRadius = mCornerRadius;
                                topRightRadius = mCornerRadius;
                            }else if(i == mTexts.length - 1){
                                bottomLeftRadius = mCornerRadius;
                                bottomRightRadius = mCornerRadius;
                            }
                        }

                        RadiusDrawable normalDrawable = new RadiusDrawable(topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius, false, 0);

                        RadiusDrawable highlightDrawable = new RadiusDrawable(topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius, false, mColors.getDefaultColor());
                        highlightDrawable.setStrokeWidth(3);

                        mBackgroundDrawables[i].addState(new int[]{-android.R.attr.state_selected}, normalDrawable);
                        mBackgroundDrawables[i].addState(new int[]{android.R.attr.state_selected}, highlightDrawable);
                    }

                    mTextViews[i].setText(text);
                    mTextViews[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
                    mTextViews[i].setTextColor(mColors);
                    mTextViews[i].setSingleLine(true);
                    mTextViews[i].setGravity(Gravity.CENTER);
                    mTextViews[i].setSelected(i == 0 ? true : false);
                    if(Build.VERSION.SDK_INT >= 16){
                        mTextViews[i].setBackground(mBackgroundDrawables[i]);
                    }else{
                        mTextViews[i].setBackgroundDrawable(mBackgroundDrawables[i]);
                    }

                    mTextViews[i].measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST));

                    if(mSingleChildWidth < mTextViews[i].getMeasuredWidth()) mSingleChildWidth = mTextViews[i].getMeasuredWidth() + mHorizonGap * 2 - 4;
                    if(mSingleChildHeight < mTextViews[i].getMeasuredHeight()) mSingleChildHeight = mTextViews[i].getMeasuredHeight() + mVerticalGap * 2 - 4;


                }
            }

            Log.d("hGap:" + mHorizonGap, "vGap:" + mVerticalGap);

            switch(widthMode){
                case MeasureSpec.AT_MOST:


                    if(mDirection == Direction.HORIZON){
                        if(widthSize <= mSingleChildWidth * mTexts.length){
                            mSingleChildWidth = widthSize / mTexts.length;
                            width = widthSize;
                        }else{
                            width = mSingleChildWidth * mTexts.length;
                        }
                    }else{
                        width = widthSize <= mSingleChildWidth ? widthSize : mSingleChildWidth;
                    }
                    break;
                case MeasureSpec.EXACTLY:
                    width = widthSize;
                    break;
                case MeasureSpec.UNSPECIFIED:
                    if(mDirection == Direction.HORIZON){
                        width = mSingleChildWidth * mTexts.length;
                    }else{
                        width = widthSize <= mSingleChildWidth ? widthSize : mSingleChildWidth;
                    }
                    break;
            }

            switch(heightMode){
                case MeasureSpec.AT_MOST:
                    if(mDirection == Direction.VERTICAL){
                        if(heightSize <= mSingleChildHeight * mTexts.length){
                            mSingleChildHeight = heightSize / mTexts.length;
                            height = heightSize;
                        }else{
                            height = mSingleChildHeight * mTexts.length;
                        }
                    }else{
                        height = heightSize <= mSingleChildHeight ? heightSize : mSingleChildHeight;
                    }
                    break;
                case MeasureSpec.EXACTLY:
                    height = heightSize;
                    break;
                case MeasureSpec.UNSPECIFIED:
                    if(mDirection == Direction.VERTICAL){
                        height = mSingleChildHeight * mTexts.length;
                    }else{
                        height = heightSize <= mSingleChildHeight ? heightSize : mSingleChildHeight;
                    }
                    break;
            }

            mChildrenWidth = mDirection == Direction.HORIZON ? mSingleChildWidth * mTexts.length : mSingleChildWidth;
            mChildrenHeight = mDirection == Direction.VERTICAL ? mSingleChildHeight * mTexts.length : mSingleChildHeight;
        }else{
            width = widthMode == MeasureSpec.UNSPECIFIED ? 0 : widthSize;
            height = heightMode == MeasureSpec.UNSPECIFIED ? 0 : heightSize;
        }

        mCenterX = width / 2;
        mCenterY = height / 2;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom){

        int startX = mCenterX - mChildrenWidth / 2;
        int startY = mCenterY - mChildrenHeight / 2;

        for(int i = 0; i < getChildCount(); i++){
            View child = getChildAt(i);

            int cLeft = 0;
            int cTop = 0;
            int cRight = 0;
            int cBottom = 0;

            if(mDirection == Direction.HORIZON){
                cLeft = startX + (i * mSingleChildWidth);
                cTop = startY;
            }else{
                cLeft = startX;
                cTop = startY + (i * mSingleChildHeight);
            }

            cRight = cLeft + mSingleChildWidth;
            cBottom = cTop + mSingleChildHeight;

            Log.d("left:" + cLeft + ", top:" + cTop, "right:" + cRight + ",bottom:" + cBottom);

            child.layout(cLeft, cTop, cRight, cBottom);
        }
    }

    @Override
    public void dispatchDraw(Canvas canvas){
        super.dispatchDraw(canvas);

        if(mBackgroundDrawable != null){

            int halfWidth = mChildrenWidth / 2;
            int halfHeight = mChildrenHeight / 2;

            mBackgroundDrawable.setBounds(mCenterX - halfWidth, mCenterY - halfHeight, mCenterX + halfWidth, mCenterY + halfHeight);
            mBackgroundDrawable.draw(canvas);
        }

        if(mTextViews != null && mTextViews.length > 0){
            for(int i = 0; i < mTextViews.length - 1; i++){
                TextView tv = mTextViews[i];

                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(2);
                mPaint.setColor(mColors.getDefaultColor());

                if(mDirection == Direction.HORIZON){
                    canvas.drawLine(tv.getRight(), tv.getTop(), tv.getRight(), tv.getBottom() - 2, mPaint);
                }else{
                    canvas.drawLine(tv.getLeft(), tv.getBottom(), tv.getRight() - 2, tv.getBottom(), mPaint);
                }
            }
        }


    }
}
