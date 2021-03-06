/*
 * NovaKey - An alternative touchscreen input method
 * Copyright (C) 2019  Viviano Cantu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>
 *
 * Any questions about the program or source may be directed to <strellastudios@gmail.com>
 */

package viviano.cantu.novakey.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import viviano.cantu.novakey.core.R;
import viviano.cantu.novakey.core.model.MainDimensions;
import viviano.cantu.novakey.core.model.loaders.Loader;
import viviano.cantu.novakey.core.model.loaders.MainDimensionsLoader;
import viviano.cantu.novakey.core.utils.Util;
import viviano.cantu.novakey.core.view.themes.MasterTheme;
import viviano.cantu.novakey.core.view.themes.Themeable;


public class NovaKeyEditView extends View implements View.OnTouchListener, Themeable {

    private MasterTheme mTheme;


    //Dimensions
    private final int screenWidth, screenHeight;//in pixels
    private final Loader<MainDimensions> mMainDimensionsLoader;
    private final MainDimensions mDimens;
    private int viewWidth, viewHeight;
    private float centerX, centerY, padding;
    private int height;
    private float radius, smallRadius;
    //Drawing
    private Paint p;

    //editing
    private boolean moving = false, resizing = false;
    private float moveX, moveY;//moving
    private float resizeDist, oldRadius;//resizing


    public NovaKeyEditView(Context context) {
        this(context, null);
    }


    public NovaKeyEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public NovaKeyEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        p = new Paint();
        p.setFlags(Paint.ANTI_ALIAS_FLAG);
        //set Listener
        setOnTouchListener(this);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        mMainDimensionsLoader = new MainDimensionsLoader(context);
        mDimens = mMainDimensionsLoader.load();
    }


    /**
     * Will set this object's theme
     *
     * @param theme a Master Theme
     */
    @Override
    public void setTheme(MasterTheme theme) {
        mTheme = theme;
    }


    //When created or resized
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        radius = mDimens.getRadius();
        smallRadius = mDimens.getRadius() / mDimens.getSmallRadius();

        //sets location to saved size
        centerX = mDimens.getX();
        padding = mDimens.getPadding();

        //set view Dimens
        viewWidth = screenWidth;
        viewHeight = screenHeight;
        //centerY will be set after method
        setMeasuredDimension(viewWidth, viewHeight);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);//fixes title bar

        height = mDimens.getHeight();

        // centerY needs to be overriden to actual centerY for touch logic
        centerY = viewHeight - height + mDimens.getY();
    }


    public void resetDimens() {
        radius = getResources().getDimension(R.dimen.default_radius);
        centerX = viewWidth / 2;
        centerY = viewHeight - radius;
        smallRadius = 3;
        height = (int) (radius * 2 + padding);
        invalidate();
    }


    public void saveDimens() {
        mDimens.setRadius(radius);
        mDimens.setSmallRadius(smallRadius);
        mDimens.setX(centerX);
        mDimens.setY(radius + padding);
        mDimens.setHeight((int) (viewHeight - (centerY - radius - padding)));
        mMainDimensionsLoader.save(mDimens);
    }


    public float getRadius() {
        return radius;
    }


    public float getSmallRadius() {
        return smallRadius;
    }


    public void setSmallRadius(float sr) {
        smallRadius = sr;
    }


    @Override
    public void onDraw(Canvas canvas) {
        mTheme.getBackgroundTheme()
                .drawBackground(0, (centerY - radius - padding), viewWidth, viewHeight,
                        centerX, centerY,
                        radius, radius / smallRadius, canvas);

        mTheme.getBoardTheme().drawBoard(centerX, centerY, radius, radius / smallRadius, canvas);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float currX = event.getX(0), currY = event.getY(0);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (Util.distance(currX, currY, centerX, centerY) <= radius) {
                    moveX = currX - centerX;
                    moveY = currY - centerY;
                    moving = true;
                }
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                if (moving || resizing) {
                    if (moving) {
                        centerX = currX - moveX;
                        centerY = currY - moveY;
                    } else if (resizing && event.getPointerCount() > 1) {
                        radius = oldRadius + (Util.distance(currX, currY, event.getX(1), event.getY(1)) - resizeDist) / 2;
                    }
                    //Checks Edit Bounds
                    if (centerX + radius > viewWidth)
                        centerX = viewWidth - radius;
                    if (centerX - radius < 0)
                        centerX = radius;
                    if (centerY + radius > viewHeight)
                        centerY = viewHeight - radius;
                    if (centerY - radius < 0)
                        centerY = radius;

                    //center
                    if (Math.abs(centerX - viewWidth / 2) < getResources().getDimension(R.dimen.center_threshold))
                        centerX = viewWidth / 2;

                    // max radius
                    if (radius * 2 > viewWidth) {
                        radius = viewWidth / 2;
                        centerX = radius;
                    }
                    if (radius * 2 > viewHeight) {
                        radius = viewHeight / 2;
                        centerY = radius;
                    }
                    //min radius
                    if (radius < getResources().getDimension(R.dimen.min_radius))
                        radius = getResources().getDimension(R.dimen.min_radius);


                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                moving = false;
                resizing = false;
                break;

            //for multitouch
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() > 1 && event.getY(1) >= centerY - radius) {
                    oldRadius = radius;
                    resizeDist = Util.distance(currX, currY, event.getX(1), event.getY(1));
                    moving = false;
                    resizing = true;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                moving = false;
                resizing = false;
                break;
        }
        return true;
    }
}
