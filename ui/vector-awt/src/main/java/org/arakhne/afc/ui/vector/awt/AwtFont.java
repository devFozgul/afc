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

package org.arakhne.afc.ui.vector.awt;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.util.Locale;

import org.arakhne.afc.math.continous.object2d.Rectangle2f;
import org.arakhne.afc.math.continous.object2d.Shape2f;
import org.arakhne.afc.ui.awt.AwtUtil;
import org.arakhne.afc.ui.vector.Font;
import org.arakhne.afc.ui.vector.FontStyle;
import org.arakhne.afc.ui.vector.GlyphList;
import org.arakhne.afc.ui.vector.VectorGraphics2D;
import org.arakhne.afc.ui.vector.VectorToolkit;

/** AWT implementation of the generic font.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @deprecated see JavaFX API
 */
@Deprecated
class AwtFont implements Font, NativeWrapper {

	private static int toAWT(FontStyle fs) {
		switch(fs) {
		case BOLD:
			return java.awt.Font.BOLD;
		case BOLD_ITALIC:
			return java.awt.Font.BOLD | java.awt.Font.ITALIC;
		case ITALIC:
			return java.awt.Font.ITALIC;
		case PLAIN:
		default:
			return java.awt.Font.PLAIN;
		}
	}

	private final java.awt.Font font;

	@SuppressWarnings("restriction")
	private sun.font.PhysicalFont physicalFont = null;
	private boolean physicalFontDetected = false;

	/**
	 * @param name
	 * @param style
	 * @param size
	 */
	public AwtFont(String name, FontStyle style, float size) {
		this(new java.awt.Font(name, toAWT(style), (int)Math.ceil(size)));
	}

	/**
	 * @param font
	 */
	public AwtFont(java.awt.Font font) {
		this.font = font;
	}

	/**
	 * Replies the font.
	 * 
	 * @return the font
	 */
	public java.awt.Font getFont() {
		return this.font;
	}

	@Override
	public String toString() {
		return this.font.toString();
	}

	@Override
	public String getFamily() {
		return this.font.getFamily();
	}

	@Override
	public String getFontName() {
		return this.font.getFontName();
	}

	@Override
	public String getName() {
		return this.font.getName();
	}

	@Override
	public float getSize() {
		return this.font.getSize2D();
	}

	@Override
	public boolean isPlain() {
		return this.font.isPlain();
	}

	@Override
	public boolean isBold() {
		return this.font.isBold();
	}

	@Override
	public boolean isItalic() {
		return this.font.isItalic();
	}

	@Override
	public Font deriveFont(float size) {
		java.awt.Font aFont = this.font.deriveFont(size);
		AwtFont f = new AwtFont(aFont);
		f.physicalFont = this.physicalFont;
		f.physicalFontDetected = this.physicalFontDetected;
		return f;
	}

	@Override
	public Font deriveFont(FontStyle style, float size) {
		java.awt.Font aFont = this.font.deriveFont(toAWT(style), size);
		AwtFont f = new AwtFont(aFont);
		f.physicalFont = this.physicalFont;
		f.physicalFontDetected = this.physicalFontDetected;
		return f;
	}

	@Override
	public Font deriveFont(FontStyle style) {
		java.awt.Font aFont = this.font.deriveFont(toAWT(style));
		AwtFont f = new AwtFont(aFont);
		f.physicalFont = this.physicalFont;
		f.physicalFontDetected = this.physicalFontDetected;
		return f;
	}

	@Override
	public <T> T getNativeObject(Class<T> type) {
		return type.cast(this.font);
	}

	@Override
	public Rectangle2f getStringBounds(String str) {
		Rectangle2D r = this.font.getStringBounds(str, 0, str.length(), AwtUtil.getVectorFontRenderContext());
		return new Rectangle2f(
				(float)r.getMinX(), (float)r.getMinY(),
				(float)r.getWidth(), (float)r.getHeight());
	}

	@Override
	public synchronized String getPSName() {
		return this.font.getPSName();
	}

	@Override
	@SuppressWarnings("restriction")
	public synchronized String getPhysicalPSName() {
		sun.font.PhysicalFont font = getPhysicalFont();
		if (font==null) return this.font.getPSName();
		return font.getPostscriptName();
	}

	@Override
	public float getItalicAngle() {
		return this.font.getItalicAngle();
	}

	@SuppressWarnings("restriction")
	private synchronized sun.font.PhysicalFont getPhysicalFont() {
		if (this.physicalFont==null && !this.physicalFontDetected) {
			this.physicalFontDetected = true;
			Locale loc = Locale.getDefault();
			String logFontName = this.font.getFontName();
			sun.font.Font2D[] candidates = sun.font.SunFontManager.getInstance().getRegisteredFonts();
			sun.font.Font2D candidate;
			for(int i=0; this.physicalFont==null && i<candidates.length; ++i) {
				candidate = candidates[i];
				if (candidate instanceof sun.font.CompositeFont
						&& candidate.getFontName(loc).equals(logFontName)) {
					this.physicalFont = ((sun.font.CompositeFont) candidate).getSlotFont(0);
				}
			}
		}
		return this.physicalFont;
	}

	@Override
	public GlyphList createGlyphList(VectorGraphics2D g, char... characters) {
		return new AwtGlyphList((Graphics2D)g.getNativeGraphics2D(), characters);
	}

	@Override
	public GlyphList createGlyphList(VectorGraphics2D g, String text) {
		return new AwtGlyphList((Graphics2D)g.getNativeGraphics2D(), text);
	}

	/** Internal implementation of a GlyphList for AWT.
	 * 
	 * @author $Author: sgalland$
	 * @version $Name$ $Revision$ $Date$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private class AwtGlyphList implements GlyphList {

		private final GlyphVector vector;

		/**
		 * @param g
		 * @param characters
		 */
		public AwtGlyphList(Graphics2D g, char... characters) {
			FontRenderContext frc;
			if (g==null) {
				frc = new FontRenderContext(null, true, false);
			}
			else {
				frc = g.getFontRenderContext();
			}
			this.vector = AwtFont.this.getFont().createGlyphVector(frc, characters);
		}

		/**
		 * @param g
		 * @param text
		 */
		public AwtGlyphList(Graphics2D g, String text) {
			FontRenderContext frc;
			if (g==null) {
				frc = new FontRenderContext(null, true, false);
			}
			else {
				frc = g.getFontRenderContext();
			}
			this.vector = AwtFont.this.getFont().createGlyphVector(frc, text);
		}

		@Override
		public int size() {
			return this.vector.getNumGlyphs();
		}

		@Override
		public Font getFont() {
			return AwtFont.this;
		}

		@Override
		public char getCharAt(int index) {
			return (char)this.vector.getGlyphCharIndex(index);
		}

		@Override
		public float getWidthAt(int index) {
			Rectangle2D r = this.vector.getGlyphMetrics(index).getBounds2D();
			return (float)r.getWidth();
		}

		@Override
		public Shape2f getOutlineAt(int index) {
			Shape s = this.vector.getGlyphOutline(index);
			assert(s!=null);
			return VectorToolkit.shape(s);
		}

		@Override
		public Shape2f getOutlineAt(int index, float x, float y) {
			Shape s = this.vector.getGlyphOutline(index, x, y);
			assert(s!=null);
			return VectorToolkit.shape(s);
		}

		@Override
		public Shape2f getOutline() {
			Shape s = this.vector.getOutline();
			assert(s!=null);
			return VectorToolkit.shape(s);
		}

		@Override
		public Shape2f getOutline(float x, float y) {
			Shape s = this.vector.getOutline(x, y);
			assert(s!=null);
			return VectorToolkit.shape(s);
		}

		@Override
		public Rectangle2f getBoundsAt(int index) {
			Shape s = this.vector.getGlyphLogicalBounds(index);
			Rectangle2D r = s.getBounds2D();
			return new Rectangle2f(
					(float)r.getMinX(),
					(float)r.getMinY(),
					(float)r.getWidth(),
					(float)r.getHeight());
		}

		@Override
		public Rectangle2f getBoundsAt(int index, float x, float y) {
			Shape s = this.vector.getGlyphLogicalBounds(index);
			Rectangle2D r = s.getBounds2D();
			return new Rectangle2f(
					(float)r.getMinX() + x,
					(float)r.getMinY() + y,
					(float)r.getWidth(),
					(float)r.getHeight());
		}

		@Override
		public Rectangle2f getBounds() {
			Shape s = this.vector.getLogicalBounds();
			Rectangle2D r = s.getBounds2D();
			return new Rectangle2f(
					(float)r.getMinX(),
					(float)r.getMinY(),
					(float)r.getWidth(),
					(float)r.getHeight());
		}

		@Override
		public Rectangle2f getBounds(float x, float y) {
			Shape s = this.vector.getLogicalBounds();
			Rectangle2D r = s.getBounds2D();
			return new Rectangle2f(
					(float)r.getMinX() + x,
					(float)r.getMinY() + y,
					(float)r.getWidth(),
					(float)r.getHeight());
		}

	} // class AwtGlyphList

}