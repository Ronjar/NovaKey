package viviano.cantu.novakey.themes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import viviano.cantu.novakey.utils.Util;

/**
 * Created by Viviano on 6/14/2015.
 */
public class MulticolorDonutTheme extends BaseTheme {


    /**
     * @return Its name identifier, if it inherits from another theme
     * it must include its parents name in the format ParentName.ThisName
     */
    @Override
    public String themeName() {
        return super.themeName() + ".MulticolorDonut";
    }

    /**
     * @return an integer ID unique to this theme
     */
    @Override
    public int themeID() {
        return 4;
    }

    /**
     * Override to customize how the auto colors are distributed
     *
     * @param primary primary color
     * @param accent accent color
     * @param contrast contrast color
     */
    protected void setAutoColors(int primary, int accent, int contrast) {
        setColors(primary, primary, contrast);
    }

    private int[] colors;
    private boolean autoColor = true;

    public void setColors() {
        if (autoColor) {
            //automatic colors here
            colors = new int[5];
            int addTo = 1;
            for (int i = 0; i < colors.length; i++) {
                int test = lighter(accentColor(), addTo + i);
                if (test == Color.WHITE) {
                    addTo = colors.length * -1;
                    test = lighter(accentColor(), addTo + i);
                }
                colors[i] = test;
            }
        }
    }

    //allowes speical themes to override the colors
    public void setColors(int[] colors) {
        this.colors = colors;
        autoColor = false;
    }

    @Override
    public void drawBoardBack(float x, float y, float r, float sr, Canvas canvas) {
        setColors();

        if (is3D()) {
            pB.setShadowLayer(r * .025f, 0, r * .025f, 0x80000000);
            pB.setStrokeWidth(r - sr);
            pB.setStyle(Paint.Style.STROKE);
            pB.setColor(0);
            canvas.drawCircle(x, y, r - sr, pB);
            //reset
            pB.setStrokeWidth(0);
            pB.clearShadowLayer();
        }

        pB.setStyle(Paint.Style.FILL);

        Path path = new Path();
        for (int i=0; i<5; i++) {
//            if (is3D())
//                y -= (r * .005f);
            pB.setColor(colors[i]);
            double angle =  Math.PI / 2 + (i * 2 * Math.PI) / 5;
            angle = (angle > Math.PI * 2 ? angle - Math.PI * 2 : angle);
            angle = -angle;

            path.arcTo(new RectF(x -  r, y - r, x + r, y + r),
                    (float)Math.toDegrees(angle), -360 / 5);
            path.arcTo(new RectF(x - sr, y - sr, x + sr, y + sr),
                    (float)Math.toDegrees(angle) -360 / 5, 360 / 5);
            path.close();

            canvas.drawPath(path, pB);
            path.reset();
        }
    }

    @Override
    public void drawLines(float x, float y, float r, float sr, float w, Canvas canvas) {
        //do nothing
    }

    @Override
    public void setTextColors() {
        super.setTextColors();
        textColors[0] = middleColor();
    }
    @Override
    public int textColor() {
        return Util.bestColor(primaryColor(), contrastColor(), accentColor());
    }
    protected int middleColor() {
        return buttonColor();
    }
    @Override
    public int buttonColor() {
        return Util.bestColor(contrastColor(), accentColor(), primaryColor());
    }

    private int lighter(int c, int f) {
        if (c == Color.BLACK)
            c = 0xFF202020;
        float mult = 1 + f * .075f;
        return redestributeRGB((int) (Color.red(c) * mult), (int) (Color.green(c) * mult),
                (int) (Color.blue(c) * mult));
    }

    private int clampRGB(int r, int g, int b) {
        return Color.argb(255, Math.min(r, 255), Math.min(g, 255), Math.min(b, 255));
    }

    private int redestributeRGB(int r, int g, int b) {
        int m = Math.max(r, Math.max(g, b));
        if (m <= 255)
            return Color.argb(255, r, g, b);
        int total = r + g + b;
        if (total >= 3 * 255)
            return Color.argb(255, 255, 255, 255);//white
        int x = (3 * 255 - total) / (3 * m - total);
        int gray = 255 - x * m;
        return Color.argb(255, gray + x * r, gray + x * g, gray + x * b);
    }
}