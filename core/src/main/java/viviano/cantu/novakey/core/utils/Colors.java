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

package viviano.cantu.novakey.core.utils;

import android.graphics.Canvas;
import android.graphics.Paint;

import viviano.cantu.novakey.core.utils.drawing.Draw;

/**
 * Created by Viviano on 7/9/2015.
 */
public class Colors implements PickerItem {

    private int[] colors;
    private static final int main = 4;


    public Colors(int[] colors) {
        this.colors = colors;
    }


    //Returns the color given an index (upper bound is protected)
    public int shade(int shade) {
        if (shade >= size())
            return colors[size() - 1];
        return colors[shade];
    }


    //Returns default main index of Color set
    public int mainIndex() {
        return Math.min(main, size() - 1);
    }


    // Returns size of Color set
    public int size() {
        return colors.length;
    }


    //Finds the index of the given color
    public int index(int color) {
        for (int i = 0; i < size(); i++) {
            if (shade(i) == color)
                return i;
        }
        return -1;
    }


    @Override
    public void drawPickerItem(float x, float y, float dimen, boolean selected, int index, Paint p, Canvas canvas) {
        p.setShadowLayer(dimen * .1f / 2, 0, dimen * .1f / 2, 0x80000000);
        Draw.colorItem(shade(index), x, y, dimen / 2 * .8f, selected, p, canvas);
    }


    //TODO: maybe read off file
    public static void initialize() {
        REDS = new Colors(new int[]{0xffFFCDD2, 0xffEF9A9A, 0xffE57373, 0xffEF5350, 0xffF44336, 0xffE53935, 0xffD32F2F, 0xffC62828, 0xffB71C1C});
        PINKS = new Colors(new int[]{0xffF8BBD0, 0xffF48FB1, 0xffF06292, 0xffEC407A, 0xffE91E63, 0xffD81B60, 0xffC2185B, 0xffAD1457, 0xff880E4F});
        PURPLES = new Colors(new int[]{0xffE1BEE7, 0xffCE93D8, 0xffBA68C8, 0xffAB47BC, 0xff9C27B0, 0xff8E24AA, 0xff7B1FA2, 0xff6A1B9A, 0xff4A148C});
        DEEP_PURPLES = new Colors(new int[]{0xffD1C4E9, 0xffB39DDB, 0xff9575CD, 0xff7E57C2, 0xff673AB7, 0xff5E35B1, 0xff512DA8, 0xff4527A0, 0xff311B92});
        INDIGOS = new Colors(new int[]{0xffC5CAE9, 0xff9FA8DA, 0xff7986CB, 0xff5C6BC0, 0xff3F51B5, 0xff3949AB, 0xff303F9F, 0xff283593, 0xff1A237E});
        BLUES = new Colors(new int[]{0xffBBDEFB, 0xff90CAF9, 0xff64B5F6, 0xff42A5F5, 0xff2196F3, 0xff1E88E5, 0xff1976D2, 0xff1565C0, 0xff0D47A1});
        LIGHT_BLUES = new Colors(new int[]{0xffB3E5FC, 0xff81D4FA, 0xff4FC3F7, 0xff29B6F6, 0xff03A9F4, 0xff039BE5, 0xff0288D1, 0xff0277BD, 0xff01579B});
        CYANS = new Colors(new int[]{0xffB2EBF2, 0xff80DEEA, 0xff4DD0E1, 0xff26C6DA, 0xff00BCD4, 0xff00ACC1, 0xff0097A7, 0xff00838F, 0xff006064});
        TEALS = new Colors(new int[]{0xffB2DFDB, 0xff80CBC4, 0xff4DB6AC, 0xff26A69A, 0xff009688, 0xff00897B, 0xff00796B, 0xff00695C, 0xff004D40});
        GREENS = new Colors(new int[]{0xffC8E6C9, 0xffA5D6A7, 0xff81C784, 0xff66BB6A, 0xff4CAF50, 0xff43A047, 0xff388E3C, 0xff2E7D32, 0xff1B5E20});
        LIGHT_GREENS = new Colors(new int[]{0xffDCEDC8, 0xffC5E1A5, 0xffAED581, 0xff9CCC65, 0xff8BC34A, 0xff7CB342, 0xff689F38, 0xff558B2F, 0xff33691E});
        LIMES = new Colors(new int[]{0xffF0F4C3, 0xffE6EE9C, 0xffDCE775, 0xffD4E157, 0xffCDDC39, 0xffC0CA33, 0xffAFB42B, 0xff9E9D24, 0xff827717});
        YELLOWS = new Colors(new int[]{0xffFFF9C4, 0xffFFF59D, 0xffFFF176, 0xffFFEE58, 0xffFFEB3B, 0xffFDD835, 0xffFBC02D, 0xffF9A825, 0xffF57F17});
        AMBERS = new Colors(new int[]{0xffFFECB3, 0xffFFD54F, 0xffFFD54F, 0xffFFCA28, 0xffFFC107, 0xffFFB300, 0xffFFA000, 0xffFF8F00, 0xffFF6F00});
        ORANGES = new Colors(new int[]{0xffFFE0B2, 0xffFFCC80, 0xffFFB74D, 0xffFFA726, 0xffFF9800, 0xffFB8C00, 0xffF57C00, 0xffEF6C00, 0xffE65100});
        DEEP_ORANGES = new Colors(new int[]{0xffFFCCBC, 0xffFFAB91, 0xffFF8A65, 0xffFF7043, 0xffFF5722, 0xffF4511E, 0xffE64A19, 0xffD84315, 0xffBF360C});
        BROWNS = new Colors(new int[]{0xffD7CCC8, 0xffBCAAA4, 0xffA1887F, 0xff8D6E63, 0xff795548, 0xff6D4C41, 0xff5D4037, 0xff4E342E, 0xff3E2723});
        GREYS = new Colors(new int[]{0xffF5F5F5, 0xffEEEEEE, 0xffE0E0E0, 0xffBDBDBD, 0xff9E9E9E, 0xff757575, 0xff616161, 0xff424242, 0xff212121});
        BLUE_GREYS = new Colors(new int[]{0xffCFD8DC, 0xffB0BEC5, 0xff90A4AE, 0xff78909C, 0xff607D8B, 0xff546E7A, 0xff455A64, 0xff37474F, 0xff263238});
        BLACK = new Colors(new int[]{0xff000000});
        WHITE = new Colors(new int[]{0xffFFFFFF});

        ALL = new Colors[]{REDS, PINKS, PURPLES, DEEP_PURPLES, INDIGOS, BLUES, LIGHT_BLUES,
                CYANS, TEALS, GREENS, LIGHT_GREENS, LIMES, YELLOWS, AMBERS,
                ORANGES, DEEP_ORANGES, BROWNS, GREYS, BLUE_GREYS, BLACK, WHITE};
    }


    public static int[] path(int color) {
        for (int i = 0; i < ALL.length; i++) {
            int j = ALL[i].index(color);
            if (j != -1) {
                return new int[]{i, j};
            }
        }
        return new int[]{-1, -1};
    }


    public static Colors REDS = new Colors(new int[]{0xffFFCDD2, 0xffEF9A9A, 0xffE57373, 0xffEF5350, 0xffF44336, 0xffE53935, 0xffD32F2F, 0xffC62828, 0xffB71C1C}),
            PINKS = new Colors(new int[]{0xffF8BBD0, 0xffF48FB1, 0xffF06292, 0xffEC407A, 0xffE91E63, 0xffD81B60, 0xffC2185B, 0xffAD1457, 0xff880E4F}),
            PURPLES = new Colors(new int[]{0xffE1BEE7, 0xffCE93D8, 0xffBA68C8, 0xffAB47BC, 0xff9C27B0, 0xff8E24AA, 0xff7B1FA2, 0xff6A1B9A, 0xff4A148C}),
            DEEP_PURPLES = new Colors(new int[]{0xffD1C4E9, 0xffB39DDB, 0xff9575CD, 0xff7E57C2, 0xff673AB7, 0xff5E35B1, 0xff512DA8, 0xff4527A0, 0xff311B92}),
            INDIGOS = new Colors(new int[]{0xffC5CAE9, 0xff9FA8DA, 0xff7986CB, 0xff5C6BC0, 0xff3F51B5, 0xff3949AB, 0xff303F9F, 0xff283593, 0xff1A237E}),
            BLUES = new Colors(new int[]{0xffBBDEFB, 0xff90CAF9, 0xff64B5F6, 0xff42A5F5, 0xff2196F3, 0xff1E88E5, 0xff1976D2, 0xff1565C0, 0xff0D47A1}),
            LIGHT_BLUES = new Colors(new int[]{0xffB3E5FC, 0xff81D4FA, 0xff4FC3F7, 0xff29B6F6, 0xff03A9F4, 0xff039BE5, 0xff0288D1, 0xff0277BD, 0xff01579B}),
            CYANS = new Colors(new int[]{0xffB2EBF2, 0xff80DEEA, 0xff4DD0E1, 0xff26C6DA, 0xff00BCD4, 0xff00ACC1, 0xff0097A7, 0xff00838F, 0xff006064}),
            TEALS = new Colors(new int[]{0xffB2DFDB, 0xff80CBC4, 0xff4DB6AC, 0xff26A69A, 0xff009688, 0xff00897B, 0xff00796B, 0xff00695C, 0xff004D40}),
            GREENS = new Colors(new int[]{0xffC8E6C9, 0xffA5D6A7, 0xff81C784, 0xff66BB6A, 0xff4CAF50, 0xff43A047, 0xff388E3C, 0xff2E7D32, 0xff1B5E20}),
            LIGHT_GREENS = new Colors(new int[]{0xffDCEDC8, 0xffC5E1A5, 0xffAED581, 0xff9CCC65, 0xff8BC34A, 0xff7CB342, 0xff689F38, 0xff558B2F, 0xff33691E}),
            LIMES = new Colors(new int[]{0xffF0F4C3, 0xffE6EE9C, 0xffDCE775, 0xffD4E157, 0xffCDDC39, 0xffC0CA33, 0xffAFB42B, 0xff9E9D24, 0xff827717}),
            YELLOWS = new Colors(new int[]{0xffFFF9C4, 0xffFFF59D, 0xffFFF176, 0xffFFEE58, 0xffFFEB3B, 0xffFDD835, 0xffFBC02D, 0xffF9A825, 0xffF57F17}),
            AMBERS = new Colors(new int[]{0xffFFECB3, 0xffFFD54F, 0xffFFD54F, 0xffFFCA28, 0xffFFC107, 0xffFFB300, 0xffFFA000, 0xffFF8F00, 0xffFF6F00}),
            ORANGES = new Colors(new int[]{0xffFFE0B2, 0xffFFCC80, 0xffFFB74D, 0xffFFA726, 0xffFF9800, 0xffFB8C00, 0xffF57C00, 0xffEF6C00, 0xffE65100}),
            DEEP_ORANGES = new Colors(new int[]{0xffFFCCBC, 0xffFFAB91, 0xffFF8A65, 0xffFF7043, 0xffFF5722, 0xffF4511E, 0xffE64A19, 0xffD84315, 0xffBF360C}),
            BROWNS = new Colors(new int[]{0xffD7CCC8, 0xffBCAAA4, 0xffA1887F, 0xff8D6E63, 0xff795548, 0xff6D4C41, 0xff5D4037, 0xff4E342E, 0xff3E2723}),
            GREYS = new Colors(new int[]{0xffF5F5F5, 0xffEEEEEE, 0xffE0E0E0, 0xffBDBDBD, 0xff9E9E9E, 0xff757575, 0xff616161, 0xff424242, 0xff212121}),
            BLUE_GREYS = new Colors(new int[]{0xffCFD8DC, 0xffB0BEC5, 0xff90A4AE, 0xff78909C, 0xff607D8B, 0xff546E7A, 0xff455A64, 0xff37474F, 0xff263238}),
            BLACK = new Colors(new int[]{0xff000000}),
            WHITE = new Colors(new int[]{0xffFFFFFF});
    public static Colors[] ALL = new Colors[]{REDS, PINKS, PURPLES, DEEP_PURPLES, INDIGOS, BLUES, LIGHT_BLUES,
            CYANS, TEALS, GREENS, LIGHT_GREENS, LIMES, YELLOWS, AMBERS,
            ORANGES, DEEP_ORANGES, BROWNS, GREYS, BLUE_GREYS, BLACK, WHITE};
}