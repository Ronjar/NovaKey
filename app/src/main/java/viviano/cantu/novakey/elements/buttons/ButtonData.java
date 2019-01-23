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

package viviano.cantu.novakey.elements.buttons;

import viviano.cantu.novakey.view.drawing.shapes.Shape;
import viviano.cantu.novakey.view.posns.RelativePosn;

/**
 * Created by Viviano on 6/22/2016.
 */
public class ButtonData {

    private RelativePosn mPosn;
    private float mSize;
    private Shape mShape;

    /**
     * @return this properties' posn
     */
    public RelativePosn getPosn() {
        return mPosn;
    }

    /**
     * @param posn sets this posn to these properties
     */
    public ButtonData setPosn(RelativePosn posn) {
        mPosn = posn;
        return this;
    }

    /**
     * @return this properties' current size
     */
    public float getSize() {
        return mSize;
    }

    /**
     * @param size sets the size of these properties
     */
    public ButtonData setSize(float size) {
        mSize = size;
        return this;
    }

    /**
     * @param shape sets the shape of these properties
     */
    public ButtonData setShape(Shape shape) {
        mShape = shape;
        return this;
    }

    /**
     * @return the shape of these properties
     */
    public Shape getShape() {
        return mShape;
    }


}