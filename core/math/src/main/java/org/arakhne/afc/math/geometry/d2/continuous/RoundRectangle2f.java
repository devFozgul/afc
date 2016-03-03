/* 
 * $Id$
 * 
 * Copyright (C) 2010-2013 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package org.arakhne.afc.math.geometry.d2.continuous;

import org.arakhne.afc.math.geometry.d2.Point2D;
import org.eclipse.xtext.xbase.lib.Pure;


/** 2D round rectangle with floating-point points.
 * 
 * @author $Author: galland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class RoundRectangle2f extends AbstractRoundRectangle2F<RoundRectangle2f> {

	private static final long serialVersionUID = 4681356809053380781L;


	/** Lowest x-coordinate covered by this rectangular shape. */
	protected double minx = 0f;
	/** Lowest y-coordinate covered by this rectangular shape. */
	protected double miny = 0f;
	/** Highest x-coordinate covered by this rectangular shape. */
	protected double maxx = 0f;
	/** Highest y-coordinate covered by this rectangular shape. */
	protected double maxy = 0f;

	/** Width of the arcs at the corner of the box. */
	protected double arcWidth = 0f;
	/** Height of the arcs at the corner of the box. */
	protected double arcHeight = 0f;

	/**
	 */
	public RoundRectangle2f() {
		//
	}

	/**
	 * @param min is the min corner of the rectangle.
	 * @param max is the max corner of the rectangle.
	 * @param arcWidth1
	 * @param arcHeight1
	 */
	public RoundRectangle2f(Point2f min, Point2f max, double arcWidth1, double arcHeight1) {
		super(min, max, arcWidth1,arcHeight1);
	}

	/**
	 * @param rr
	 */
	public RoundRectangle2f(RoundRectangle2f rr) {
		super(rr);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param arcWidth1
	 * @param arcHeight1
	 */
	public RoundRectangle2f(double x, double y, double width, double height, double arcWidth1, double arcHeight1) {
		super(x, y, width, height,arcWidth1,arcHeight1);
	}



	/** Change the frame of the rectangle.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	@Override
	public void set(double x, double y, double width, double height) {
		setFromCorners(x, y, x+width, y+height);
	}

	/** Change the frame of te rectangle.
	 * 
	 * @param min is the min corner of the rectangle.
	 * @param max is the max corner of the rectangle.
	 */
	@Override
	public void set(Point2f min, Point2f max) {
		setFromCorners(min.getX(), min.getY(), max.getX(), max.getY());
	}

	/** Change the width of the rectangle, not the min corner.
	 * 
	 * @param width
	 */
	@Override
	public void setWidth(double width) {
		this.maxx = this.minx + Math.max(0f, width);
	}

	/** Change the height of the rectangle, not the min corner.
	 * 
	 * @param height
	 */
	@Override
	public void setHeight(double height) {
		this.maxy = this.miny + Math.max(0f, height);
	}

	/** Change the frame of the rectangle.
	 * 
	 * @param p1 is the coordinate of the first corner.
	 * @param p2 is the coordinate of the second corner.
	 */
	@Override
	public void setFromCorners(Point2D p1, Point2D p2) {
		setFromCorners(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	/** Change the frame of the rectangle.
	 * 
	 * @param x1 is the coordinate of the first corner.
	 * @param y1 is the coordinate of the first corner.
	 * @param x2 is the coordinate of the second corner.
	 * @param y2 is the coordinate of the second corner.
	 */
	@Override
	public void setFromCorners(double x1, double y1, double x2, double y2) {
		if (x1<x2) {
			this.minx = x1;
			this.maxx = x2;
		}
		else {
			this.minx = x2;
			this.maxx = x1;
		}
		if (y1<y2) {
			this.miny = y1;
			this.maxy = y2;
		}
		else {
			this.miny = y2;
			this.maxy = y1;
		}
	}

	/**
	 * Sets the framing rectangle of this <code>Shape</code>
	 * based on the specified center point coordinates and corner point
	 * coordinates.  The framing rectangle is used by the subclasses of
	 * <code>RectangularShape</code> to define their geometry.
	 *
	 * @param centerX the X coordinate of the specified center point
	 * @param centerY the Y coordinate of the specified center point
	 * @param cornerX the X coordinate of the specified corner point
	 * @param cornerY the Y coordinate of the specified corner point
	 */
	@Override
	public void setFromCenter(double centerX, double centerY, double cornerX, double cornerY) {
		double dx = centerX - cornerX;
		double dy = centerY - cornerY;
		setFromCorners(cornerX, cornerY, centerX + dx, centerY + dy);
	}

	/** Replies the min X.
	 * 
	 * @return the min x.
	 */
	@Pure
	@Override
	public double getMinX() {
		return this.minx;
	}

	/** Set the min X.
	 * 
	 * @param x the min x.
	 */
	@Override
	public void setMinX(double x) {
		double o = this.maxx;
		if (o<x) {
			this.minx = o;
			this.maxx = x;
		}
		else {
			this.minx = x;
		}
	}

	/** Replies the center x.
	 * 
	 * @return the center x.
	 */
	@Pure
	@Override
	public double getCenterX() {
		return (this.minx + this.maxx) / 2f;
	}

	/** Replies the max x.
	 * 
	 * @return the max x.
	 */
	@Pure
	@Override
	public double getMaxX() {
		return this.maxx;
	}

	/** Set the max X.
	 * 
	 * @param x the max x.
	 */
	@Override
	public void setMaxX(double x) {
		double o = this.minx;
		if (o>x) {
			this.maxx = o;
			this.minx = x;
		}
		else {
			this.maxx = x;
		}
	}

	/** Replies the min y.
	 * 
	 * @return the min y.
	 */
	@Pure
	@Override
	public double getMinY() {
		return this.miny;
	}

	/** Set the min Y.
	 * 
	 * @param y the min y.
	 */
	@Override
	public void setMinY(double y) {
		double o = this.maxy;
		if (o<y) {
			this.miny = o;
			this.maxy = y;
		}
		else {
			this.miny = y;
		}
	}

	/** Replies the center y.
	 * 
	 * @return the center y.
	 */
	@Pure
	@Override
	public double getCenterY() {
		return (this.miny + this.maxy) / 2f;
	}

	/** Replies the max y.
	 * 
	 * @return the max y.
	 */
	@Pure
	@Override
	public double getMaxY() {
		return this.maxy;
	}

	/** Set the max Y.
	 * 
	 * @param y the max y.
	 */
	@Override
	public void setMaxY(double y) {
		double o = this.miny;
		if (o>y) {
			this.maxy = o;
			this.miny = y;
		}
		else {
			this.maxy = y;
		}
	}

	/** Replies the width.
	 * 
	 * @return the width.
	 */
	@Pure
	@Override
	public double getWidth() {
		return this.maxx - this.minx;
	}

	/** Replies the height.
	 * 
	 * @return the height.
	 */
	@Pure
	@Override
	public double getHeight() {
		return this.maxy - this.miny;
	}

	/**
	 * Gets the width of the arc that rounds off the corners.
	 * @return the width of the arc that rounds off the corners
	 * of this <code>RoundRectangle2f</code>.
	 */
	@Pure
	public double getArcWidth() {
		return this.arcWidth;
	}

	/**
	 * Gets the height of the arc that rounds off the corners.
	 * @return the height of the arc that rounds off the corners
	 * of this <code>RoundRectangle2f</code>.
	 */
	@Pure
	public double getArcHeight() {
		return this.arcHeight;
	}

	/**
	 * Set the width of the arc that rounds off the corners.
	 * @param a is the width of the arc that rounds off the corners
	 * of this <code>RoundRectangle2f</code>.
	 */
	public void setArcWidth(double a) {
		this.arcWidth = a;
	}

	/**
	 * Set the height of the arc that rounds off the corners.
	 * @param a is the height of the arc that rounds off the corners
	 * of this <code>RoundRectangle2f</code>.
	 */
	public void setArcHeight(double a) {
		this.arcHeight = a;
	}

	/** Change the frame of the rectangle.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param arcWidth1 is the width of the arc that rounds off the corners
	 * of this <code>RoundRectangle2f</code>.
	 * @param arcHeight1 is the height of the arc that rounds off the corners
	 * of this <code>RoundRectangle2f</code>.
	 */
	public void set(double x, double y, double width, double height, double arcWidth1, double arcHeight1) {
		setFromCorners(x, y, x+width, y+height); 
		this.arcWidth = arcWidth1;
		this.arcHeight = arcHeight1;
	}

	@Override
	public void set(Shape2F s) {
		if (s instanceof RoundRectangle2f) {
			RoundRectangle2f r = (RoundRectangle2f) s;
			set(r.getMinX(), r.getMinY(),
					r.getWidth(), r.getHeight(),
					r.getArcWidth(), r.getArcHeight());
		} else {
			AbstractRectangle2F<?> r = s.toBoundingBox();
			setFromCorners(r.getMinX(), r.getMinY(), r.getMaxX(), r.getMaxY());
		}
	}



}