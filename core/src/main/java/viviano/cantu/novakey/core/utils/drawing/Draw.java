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

package viviano.cantu.novakey.core.utils.drawing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;

import viviano.cantu.novakey.core.utils.Util;
import viviano.cantu.novakey.core.utils.drawing.drawables.Drawable;

public class Draw {

    /*
    Helper method - draws line with given information
     */
    public static void lines(float x, float y, float r, float sr, float gap, int color,
                             Paint p, Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            double angle = (i * 2 * Math.PI) / 5 + Math.PI / 2;
            angle = (angle > Math.PI * 2 ? angle - Math.PI * 2 : angle);
            line(x, y, sr + gap, r - gap, angle, color, p, canvas);
        }
    }


    /*
    Draws shaded line with origin x, y at an angle from start to end
    */
    public static void line(float x, float y, float start, float end, double angle, int color,
                            Paint p, Canvas canvas) {
        p.setColor(color);
        canvas.drawLine(x + (float) Math.cos(angle) * start,
                y - (float) Math.sin(angle) * start,
                x + (float) Math.cos(angle) * end,
                y - (float) Math.sin(angle) * end, p);
    }


    /*
     Helper method - draws all shaded lines with given information
     */
    public static void shadedLines(float x, float y, float r, float sr,
                                   int color, Paint p, Canvas canvas) {
        float gap = (r - sr) / 10;// gap that will separate the line from the center circle
        for (int i = 0; i < 5; i++) {
            double angle = (i * 2 * Math.PI) / 5 + Math.PI / 2;
            angle = (angle > Math.PI * 2 ? angle - Math.PI * 2 : angle);
            shadedLine(x, y, sr + gap, r - gap, angle, color, p, canvas);
        }
    }


    /*
     Draws shaded line with origin x, y at an angle from start to end
     */
    public static void shadedLine(float x, float y, float start, float end, double angle,
                                  int color, Paint p, Canvas canvas) {
        p.setShader(new RadialGradient(
                x + (float) Math.cos(angle) * ((end - start) / 2 + start),
                y - (float) Math.sin(angle) * ((end - start) / 2 + start),
                (end - start) / 2,
                color, color & 0x00FFFFFF, Shader.TileMode.CLAMP));
        canvas.drawLine(x + (float) Math.cos(angle) * start,
                y - (float) Math.sin(angle) * start,
                x + (float) Math.cos(angle) * end,
                y - (float) Math.sin(angle) * end, p);
        p.setShader(null);
    }


    //Draw text with size
    public static void textFlat(String s, float x, float y, float size, Paint p, Canvas canvas) {
        float temp = p.getTextSize();
        p.setTextSize(size);
        textFlat(s, x, y, p, canvas);
        p.setTextSize(temp);
    }


    public static void textFlat(String s, float x, float y, Paint p, Canvas canvas) {
        canvas.drawText(s, x - p.measureText(s) / 2, y, p);
    }


    //Draws text centered
    public static void text(String s, float x, float y, Paint p, Canvas canvas) {
        String[] S = s.split("\n");//lines
        if (S.length <= 1) {//TODO: draw text containting emoji
            canvas.drawText(s, x - p.measureText(s) / 2, y - (p.ascent() + p.descent()) / 2, p);
        } else {
            float l = p.getTextSize() * (10 / 8);//line size
            for (int i = 0; i < S.length; i++) {
                text(S[i], x, ((y - (S.length / 2 * l)) + i * l) - (S.length % 2 != 0 ? l / 2 : 0) + l / 2,
                        p, canvas);
            }
        }
    }


    //Draw text with size
    public static void text(String s, float x, float y, float size, Paint p, Canvas canvas) {
        float temp = p.getTextSize();
        p.setTextSize(size);
        text(s, x, y, p, canvas);
        p.setTextSize(temp);
    }


    public static void colorItem(int color, float x, float y, float radius, Paint p, Canvas canvas) {
        p.setColor(color);
        canvas.drawCircle(x, y, radius, p);
    }


    //draws white checkmark if selected
    public static void colorItem(int color, float x, float y, float radius, boolean selected,
                                 Paint p, Canvas canvas) {
        colorItem(color, x, y, radius, p, canvas);
        if (selected) {//draw checkmark
            float rw = Util.contrastRatio(Color.WHITE, color);
            Drawable ic = Icons.get("check");
            p.setColor(rw < 1.1f ? Color.BLACK : Color.WHITE);
            p.clearShadowLayer();
            ic.draw(x, y, radius * 1.6f, p, canvas);
        }
    }


    public static void floatingButton(float x, float y, float radius, Bitmap icon, int back, int front,
                                      float height, Paint p, Canvas canvas) {
        //Circle
        p.setShadowLayer(height + 2, 0, height, 0x60000000);
        p.setColor(back);
        canvas.drawCircle(x, y - height, radius, p);
        p.clearShadowLayer();

        //Icons
        p.setColorFilter(new LightingColorFilter(front, 0));
        bitmap(icon, x, y - height, 1, p, canvas);
        p.setColorFilter(null);
    }


    //centers bitmap and scales it to scale
    public static void bitmap(Bitmap bmp, float x, float y, float scale, Paint p, Canvas canvas) {
        float width = bmp.getWidth() * scale;
        float height = bmp.getHeight() * scale;

        p.setColorFilter(new LightingColorFilter(p.getColor(), 0));
        canvas.drawBitmap(bmp, null,
                new RectF(x - width / 2, y - height / 2, x + width / 2, y + height / 2), p);
        p.setColorFilter(null);
    }
}
