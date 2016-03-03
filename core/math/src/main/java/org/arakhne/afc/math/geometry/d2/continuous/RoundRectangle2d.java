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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/** 2D round rectangle with DoubleProperty points.
 * 
 * @author $Author: hjaffali$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class RoundRectangle2d extends AbstractRoundRectangle2F<RoundRectangle2d> {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 101021133567302859L;
	
	
	/** Lowest x-coordinate covered by this rectangular shape. */
	protected DoubleProperty minxProperty;
	/** Lowest y-coordinate covered by this rectangular shape. */
	protected DoubleProperty minyProperty;
	/** Highest x-coordinate covered by this rectangular shape. */
	protected DoubleProperty maxxProperty;
	/** Highest y-coordinate covered by this rectangular shape. */
	protected DoubleProperty maxyProperty;
	
	/** Width of the arcs at the corner of the box. */
	protected DoubleProperty arcWidthProperty;
	/** Height of the arcs at the corner of the box. */
	protected DoubleProperty arcHeightProperty;

	/**
	 */
	public RoundRectangle2d() {
		this.minxProperty = new SimpleDoubleProperty(0f);
		this.minyProperty = new SimpleDoubleProperty(0f);
		this.maxxProperty = new SimpleDoubleProperty(0f);
		this.maxyProperty = new SimpleDoubleProperty(0f);
		this.arcWidthProperty = new SimpleDoubleProperty(0f);
		this.arcHeightProperty = new SimpleDoubleProperty(0f);
	}

	/**
	 * @param min is the min corner of the rectangle.
	 * @param max is the max corner of the rectangle.
	 * @param arcWidth1
	 * @param arcHeight1
	 */
	public RoundRectangle2d(Point2f min, Point2f max, double arcWidth1, double arcHeight1) {
		this();
		
		setFromCorners(min.getX(), min.getY(), max.getX(), max.getY());
		this.setArcWidth(arcWidth1);
		this.setArcHeight(arcHeight1);
	}
	
	/**
	 * @param min is the min corner of the rectangle.
	 * @param max is the max corner of the rectangle.
	 * @param arcWidth1
	 * @param arcHeight1
	 */
	public RoundRectangle2d(Point2d min, Point2d max, DoubleProperty arcWidth1, DoubleProperty arcHeight1) {
		this();
		
		setFromCornersProperties(min, max);
		this.setArcWidthProperty(arcWidth1);
		this.setArcWidthProperty(arcHeight1);
	}

	/**
	 * @param rr
	 */
	public RoundRectangle2d(RoundRectangle2f rr) {
		this();
		setFromCorners(rr.getMinX(),rr.getMinY(),rr.getMaxX(),rr.getMaxY());
	}
	
	/**
	 * @param rr
	 */
	public RoundRectangle2d(RoundRectangle2d rr) {
		this();
		this.minxProperty = rr.minxProperty;
		this.minyProperty = rr.minyProperty;
		this.maxxProperty = rr.maxxProperty;
		this.maxyProperty = rr.maxyProperty;
		this.arcHeightProperty = rr.arcHeightProperty;
		this.arcWidthProperty = rr.arcWidthProperty;
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param arcWidth1
	 * @param arcHeight1
	 */
	public RoundRectangle2d(double x, double y, double width, double height, double arcWidth1, double arcHeight1) {
		this();
		
		setFromCorners(x, y, x+width, y+height);
		this.setArcWidth(arcWidth1);
		this.setArcHeight(arcHeight1);
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
	
	/** Set the frame of te rectangle related to the Point2d points min and max.
	 * 
	 * When the points are changed, the RoundRectangle2d will be also changed.
	 * 
	 * @param min is the min corner of the rectangle.
	 * @param max is the max corner of the rectangle.
	 */
	public void setProperties(Point2d min, Point2d max) {
		setFromCornersProperties(min, max);
	}
	
	/** Change the width of the rectangle, not the min corner.
	 * 
	 * @param width
	 */
	@Override
	public void setWidth(double width) {
		this.setMaxX(this.getMinX() + Math.max(0f, width));
	}

	/** Change the height of the rectangle, not the min corner.
	 * 
	 * @param height
	 */
	@Override
	public void setHeight(double height) {
		this.setMaxY(this.getMinY() + Math.max(0f, height));
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

	/** Set the frame of te rectangle related to the Point2d points min and max.
	 * 
	 * When the points are changed, the RoundRectangle2d will be also changed.
	 * 
	 * @param min is the min corner of the rectangle.
	 * @param max is the max corner of the rectangle.
	 */
	public void setFromCornersProperties(Point2d p1, Point2d p2) {
		if (p1.getX()<p2.getX()) {
			this.minxProperty = p1.xProperty;
			this.maxxProperty = p2.xProperty;
		}
		else {
			this.minxProperty = p2.xProperty;
			this.maxxProperty = p1.xProperty;
		}
		if (p1.getY()<p2.getY()) {
			this.minyProperty = p1.yProperty;
			this.maxyProperty = p2.yProperty;
		}
		else {
			this.minyProperty = p2.yProperty;
			this.maxyProperty = p1.yProperty;
		}
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
			this.minxProperty.set(x1);
			this.maxxProperty.set(x2);
		}
		else {
			this.minxProperty.set(x2);
			this.maxxProperty.set(x1);
		}
		if (y1<y2) {
			this.minyProperty.set(y1);
			this.maxyProperty.set(y2);
		}
		else {
			this.minyProperty.set(y2);
			this.maxyProperty.set(y1);
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
		return this.minxProperty.doubleValue();
	}

	/** Set the min X.
	 * 
	 * @param x the min x.
	 */
	@Override
	public void setMinX(double x) {
		double o = this.getMaxX();
		if (o<x) {
			this.minxProperty.set(o);
			this.maxxProperty.set(x);
		}
		else {
			this.minxProperty.set(x);
		}
	}

	/** Replies the center x.
	 * 
	 * @return the center x.
	 */
	@Pure
	@Override
	public double getCenterX() {
		return (this.minxProperty.doubleValue() + this.maxxProperty.doubleValue()) / 2f;
	}

	/** Replies the max x.
	 * 
	 * @return the max x.
	 */
	@Pure
	@Override
	public double getMaxX() {
		return this.maxxProperty.doubleValue();
	}

	/** Set the max X.
	 * 
	 * @param x the max x.
	 */
	@Override
	public void setMaxX(double x) {
		double o = this.getMinX();
		if (o>x) {
			this.maxxProperty.set(o);
			this.minxProperty.set(x);
		}
		else {
			this.maxxProperty.set(x);
		}
	}

	/** Replies the min y.
	 * 
	 * @return the min y.
	 */
	@Pure
	@Override
	public double getMinY() {
		return this.minyProperty.doubleValue();
	}

	/** Set the min Y.
	 * 
	 * @param y the min y.
	 */
	@Override
	public void setMinY(double y) {
		double o = this.getMaxY();
		if (o<y) {
			this.minyProperty.set(o);
			this.maxyProperty.set(y);
		}
		else {
			this.minyProperty.set(y);
		}
	}

	/** Replies the center y.
	 * 
	 * @return the center y.
	 */
	@Pure
	@Override
	public double getCenterY() {
		return (this.minyProperty.doubleValue() + this.maxyProperty.doubleValue()) / 2f;
	}

	/** Replies the max y.
	 * 
	 * @return the max y.
	 */
	@Pure
	@Override
	public double getMaxY() {
		return this.maxyProperty.doubleValue();
	}
	
	/** Set the max Y.
	 * 
	 * @param y the max y.
	 */
	@Override
	public void setMaxY(double y) {
		double o = this.getMinY();
		if (o>y) {
			this.maxyProperty.set(o);
			this.minyProperty.set(y);
		}
		else {
			this.maxyProperty.set(y);
		}
	}

	/** Replies the width.
	 * 
	 * @return the width.
	 */
	@Pure
	@Override
	public double getWidth() {
		return this.maxxProperty.doubleValue() - this.minxProperty.doubleValue();
	}

	/** Replies the height.
	 * 
	 * @return the height.
	 */
	@Pure
	@Override
	public double getHeight() {
		return this.maxyProperty.doubleValue() - this.minyProperty.doubleValue();
	}
	
	/**
	 * Gets the width of the arc that rounds off the corners.
	 * @return the width of the arc that rounds off the corners
	 * of this <code>RoundRectangle2f</code>.
	 */
	@Pure
	public double getArcWidth() {
		return this.arcWidthProperty.doubleValue();
	}

	/**
	 * Gets the height of the arc that rounds off the corners.
	 * @return the height of the arc that rounds off the corners
	 * of this <code>RoundRectangle2f</code>.
	 */
	@Pure
	public double getArcHeight() {
		return this.arcHeightProperty.doubleValue();
	}

	/**
	 * Set the width of the arc that rounds off the corners.
	 * @param a is the width of the arc that rounds off the corners
	 * of this <code>RoundRectangle2d</code>.
	 */
	public void setArcWidth(double a) {
		this.arcWidthProperty.set(a);
	}
	
	public void setArcWidthProperty(DoubleProperty a) {
		this.arcWidthProperty = a;
	}

	/**
	 * Set the height of the arc that rounds off the corners.
	 * @param a is the height of the arc that rounds off the corners
	 * of this <code>RoundRectangle2d</code>.
	 */
	public void setArcHeight(double a) {
		this.arcHeightProperty.set(a);
	}
	
	public void setArcHeightProperty(DoubleProperty a) {
		this.arcHeightProperty = a;
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
		this.arcWidthProperty.set(arcWidth1);
		this.arcHeightProperty.set(arcHeight1);
	}

	@Override
	public void set(Shape2F s) {
		if (s instanceof RoundRectangle2d) {
			RoundRectangle2d r = (RoundRectangle2d) s;
			set(r.getMinX(), r.getMinY(),
					r.getWidth(), r.getHeight(),
					r.getArcWidth(), r.getArcHeight());
		} else {
			AbstractRectangle2F<?> r = s.toBoundingBox();
			setFromCorners(r.getMinX(), r.getMinY(), r.getMaxX(), r.getMaxY());
		}
	}


}
