/*
 * $Id$
 * This file is a part of the Arakhne Foundation Classes, http://www.arakhne.org/afc
 *
 * Copyright (c) 2000-2012 Stephane GALLAND.
 * Copyright (c) 2005-10, Multiagent Team, Laboratoire Systemes et Transports,
 *                        Universite de Technologie de Belfort-Montbeliard.
 * Copyright (c) 2013-2016 The original authors, and other authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.arakhne.afc.ui.vector;

import org.arakhne.afc.math.continous.object2d.Rectangle2f;

/** Interface that is representing a font metrics. 
 * See {@link VectorToolkit} to create an instance.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @deprecated see JavaFX API
 */
@Deprecated
public interface FontMetrics {

    /**
     * Gets the <code>Font</code> described by this
     * <code>FontMetrics</code> object.
     * @return    the <code>Font</code> described by this
     * <code>FontMetrics</code> object.
     */
    public Font getFont();

    /**
     * Determines the <em>standard leading</em> of the
     * <code>Font</code> described by this <code>FontMetrics</code>
     * object.  The standard leading, or
     * interline spacing, is the logical amount of space to be reserved
     * between the descent of one line of text and the ascent of the next
     * line. The height metric is calculated to include this extra space.
     * @return    the standard leading of the <code>Font</code>.
     * @see   #getHeight()
     * @see   #getAscent()
     * @see   #getDescent()
     */
    public float getLeading();

    /**
     * Determines the <em>font ascent</em> of the <code>Font</code>
     * described by this <code>FontMetrics</code> object. The font ascent
     * is the distance from the font's baseline to the top of most
     * alphanumeric characters. Some characters in the <code>Font</code>
     * might extend above the font ascent line.
     * @return     the font ascent of the <code>Font</code>.
     * @see        #getMaxAscent()
     */
    public float getAscent();

    /**
     * Determines the <em>font descent</em> of the <code>Font</code>
     * described by this
     * <code>FontMetrics</code> object. The font descent is the distance
     * from the font's baseline to the bottom of most alphanumeric
     * characters with descenders. Some characters in the
     * <code>Font</code> might extend
     * below the font descent line.
     * @return     the font descent of the <code>Font</code>.
     * @see        #getMaxDescent()
     */
    public float getDescent();

    /**
     * Gets the standard height of a line of text in this font.  This
     * is the distance between the baseline of adjacent lines of text.
     * It is the sum of the leading + ascent + descent. Due to rounding
     * this may not be the same as getAscent() + getDescent() + getLeading().
     * There is no guarantee that lines of text spaced at this distance are
     * disjoint; such lines may overlap if some characters overshoot
     * either the standard ascent or the standard descent metric.
     * @return    the standard height of the font.
     * @see       #getLeading()
     * @see       #getAscent()
     * @see       #getDescent()
     */
    public float getHeight();

    /**
     * Determines the maximum ascent of the <code>Font</code>
     * described by this <code>FontMetrics</code> object.  No character
     * extends further above the font's baseline than this height.
     * @return    the maximum ascent of any character in the
     * <code>Font</code>.
     * @see       #getAscent()
     */
    public float getMaxAscent();

    /**
     * Determines the maximum descent of the <code>Font</code>
     * described by this <code>FontMetrics</code> object.  No character
     * extends further below the font's baseline than this height.
     * @return    the maximum descent of any character in the
     * <code>Font</code>.
     * @see       #getDescent()
     */
    public float getMaxDescent();

    /**
     * Gets the maximum advance width of any character in this
     * <code>Font</code>.  The advance is the
     * distance from the leftmost point to the rightmost point on the
     * string's baseline.  The advance of a <code>String</code> is
     * not necessarily the sum of the advances of its characters.
     * @return    the maximum advance width of any character
     *            in the <code>Font</code>, or <code>-1</code> if the
     *            maximum advance width is not known.
     */
    public float getMaxAdvance();

    /**
     * Returns the total advance width for showing the specified
     * <code>String</code> in this <code>Font</code>.  The advance
     * is the distance from the leftmost point to the rightmost point
     * on the string's baseline.
     * <p>
     * Note that the advance of a <code>String</code> is
     * not necessarily the sum of the advances of its characters.
     * @param str the <code>String</code> to be measured
     * @return    the advance width of the specified <code>String</code>
     *                  in the <code>Font</code> described by this
     *                  <code>FontMetrics</code>.
     * @throws NullPointerException if str is null.
     */
    public float stringWidth(String str);

    /**
     * Returns the bounds for the character with the maximum bounds.
     * @return a <code>Rectangle2f</code> that is the
     * bounding box for the character with the maximum bounds.
     */
    public Rectangle2f getMaxCharBounds();
    
}
