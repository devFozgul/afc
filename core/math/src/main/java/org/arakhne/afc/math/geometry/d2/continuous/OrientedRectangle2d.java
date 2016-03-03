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

import java.util.Arrays;
import java.util.Iterator;

import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Vector2D;
import org.eclipse.xtext.xbase.lib.Pure;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author Hamza JAFFALI (hjaffali)
 *
 */
public class OrientedRectangle2d extends AbstractOrientedRectangle2F<OrientedRectangle2d> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5333289284821444521L;

	
	/**
	 * Center of the OBR
	 */
	private DoubleProperty cxProperty;

	/**
	 * Center of the OBR
	 */
	private DoubleProperty cyProperty;

	/**
	 * X coordinate of the first axis of the OBR
	 */
	private DoubleProperty rxProperty;

	/**
	 * Y coordinate of the first axis of the OBR
	 */
	private DoubleProperty ryProperty;

	/**
	 * X coordinate of the second axis of the OBR
	 */
	private DoubleProperty sxProperty;

	/**
	 * Y coordinate of the second axis of the OBR
	 */
	private DoubleProperty syProperty;

	/**
	 * Half-size of the first axis of the OBR
	 */
	private DoubleProperty extentRProperty;

	/**
	 * Half-size of the second axis of the OBR
	 */
	private DoubleProperty extentSProperty;

	/** Create an empty oriented rectangle.
	 */
	public OrientedRectangle2d() {
		this.cxProperty = new SimpleDoubleProperty(0f);
		this.cyProperty = new SimpleDoubleProperty(0f);
		this.rxProperty = new SimpleDoubleProperty(0f);
		this.ryProperty = new SimpleDoubleProperty(0f);
		this.sxProperty = new SimpleDoubleProperty(0f);
		this.syProperty = new SimpleDoubleProperty(0f);
		this.extentRProperty = new SimpleDoubleProperty(0f);
		this.extentSProperty = new SimpleDoubleProperty(0f);
	}

	/** Create an oriented rectangle from the given OBR.
	 * 
	 * @param obr
	 */
	public OrientedRectangle2d(OrientedRectangle2f obr) {
		this();
		this.set(obr.getCenterX(),obr.getCenterY(),obr.getFirstAxisX(),obr.getFirstAxisY(),obr.getSecondAxisX(),obr.getSecondAxisY(),obr.getFirstAxisExtent(),obr.getSecondAxisExtent());
	}
	
	/** Create an oriented rectangle from the given OBR.
	 * 
	 * @param obr
	 */
	public OrientedRectangle2d(OrientedRectangle2d obr) {
		this(obr.getCenter(),obr.getFirstAxis(),obr.extentRProperty,obr.extentSProperty);
	}

	/** Construct an oriented rectangle from the given cloud of points.
	 *
	 * @param pointCloud - the cloud of points.
	 */
	public OrientedRectangle2d(Iterable<? extends Point2D> pointCloud) {
		this();
		setFromPointCloud(pointCloud);
	}

	/** Construct an oriented rectangle from the given cloud of points.
	 *
	 * @param pointCloud - the cloud of points.
	 */
	public OrientedRectangle2d(Point2D[] pointCloud) {
		this();
		setFromPointCloud(Arrays.asList(pointCloud));
	}

	/** Construct an oriented rectangle.
	 *
	 * @param centerX is the X coordinate of the OBR center.
	 * @param centerY is the Y coordinate of the OBR center.
	 * @param axis1X is the X coordinate of first axis of the OBR.
	 * @param axis1Y is the Y coordinate of first axis of the OBR.
	 * @param axis1Extent is the extent of the first axis.
	 * @param axis2Extent is the extent of the second axis.
	 */
	public OrientedRectangle2d(double centerX, double centerY,
			double axis1X, double axis1Y, double axis1Extent,
			double axis2Extent) {
		this();
		this.set(centerX, centerY, axis1X, axis1Y, axis1Extent, axis2Extent);
	}

	/** Construct an oriented rectangle.
	 *
	 * @param center is the OBR center.
	 * @param axis1 is the first axis of the OBR.
	 * @param axis1Extent is the extent of the first axis.
	 * @param axis2Extent is the extent of the second axis.
	 */
	public OrientedRectangle2d(Point2f center, Vector2f axis1, double axis1Extent, double axis2Extent) {
		this();
		this.set(center, axis1, axis1Extent, axis2Extent);
	}
	
	public OrientedRectangle2d(Point2d center, Vector2d axis1, DoubleProperty axis1Extent, DoubleProperty axis2Extent) {
		this();
		this.setCenterProperties(center);
		this.setFirstAxisProperties(axis1, axis1Extent);
		this.setSecondAxisExtentProperty(axis2Extent);
	}

	/** Replies the center.
	 *
	 * @return the center.
	 */
	@Pure
	public Point2d getCenter() {
		return new Point2d(this.cxProperty, this.cyProperty);
	}

	/** Replies the center x.
	 *
	 * @return the center x.
	 */
	@Pure
	public double getCenterX() {
		return this.cxProperty.doubleValue();
	}

	/** Replies the center y.
	 *
	 * @return the center y.
	 */
	@Pure
	public double getCenterY() {
		return this.cyProperty.doubleValue();
	}

	/** Set the center.
	 * 
	 * @param cx1 the center x.
	 * @param cy1 the center y.
	 */
	public void setCenter(double cx1, double cy1) {
		this.cxProperty.set(cx1);
		this.cyProperty.set(cy1);
	}

	/** Set the center.
	 * 
	 * @param center
	 */
	public void setCenter(Point2D center) {
		setCenter(center.getX(), center.getY());
	}
	
	/** Set the center.
	 * 
	 * @param center
	 */
	public void setCenterProperties(Point2d center) {
		this.cxProperty = center.xProperty;
		this.cyProperty = center.yProperty;
	}

	/** Replies the first axis of the oriented rectangle.
	 *
	 * @return the unit vector of the first axis. 
	 */
	@Pure
	public Vector2d getFirstAxis() {
		return new Vector2d(this.rxProperty, this.ryProperty);
	}

	/** Replies coordinate x of the first axis of the oriented rectangle.
	 *
	 * @return the coordinate x of the unit vector of the first axis. 
	 */
	@Pure
	public double getFirstAxisX() {
		return this.rxProperty.doubleValue();
	}

	/** Replies coordinate y of the first axis of the oriented rectangle.
	 *
	 * @return the coordinate y of the unit vector of the first axis. 
	 */
	@Pure
	public double getFirstAxisY() {
		return this.ryProperty.doubleValue();
	}

	/** Replies the second axis of the oriented rectangle.
	 *
	 * @return the unit vector of the second axis. 
	 */
	@Pure
	public Vector2d getSecondAxis() {
		return new Vector2d(this.sxProperty, this.syProperty);
	}

	/** Replies coordinate x of the second axis of the oriented rectangle.
	 *
	 * @return the coordinate x of the unit vector of the second axis. 
	 */
	@Pure
	public double getSecondAxisX() {
		return this.sxProperty.doubleValue();
	}

	/** Replies coordinate y of the second axis of the oriented rectangle.
	 *
	 * @return the coordinate y of the unit vector of the second axis. 
	 */
	@Pure
	public double getSecondAxisY() {
		return this.syProperty.doubleValue();
	}

	/** Replies the demi-size of the rectangle along its first axis.
	 * 
	 * @return the extent along the first axis.
	 */
	@Pure
	public double getFirstAxisExtent() {
		return this.extentRProperty.doubleValue();
	}

	/** Change the demi-size of the rectangle along its first axis.
	 * 
	 * @param extent - the extent along the first axis.
	 */
	public void setFirstAxisExtent(double extent) {
		this.extentRProperty.set(Math.max(extent, 0));
	}
	
	public void setFirstAxisExtentProperty(DoubleProperty extent) {
		this.extentRProperty = extent;
	}

	/** Replies the demi-size of the rectangle along its second axis.
	 * 
	 * @return the extent along the second axis.
	 */
	@Pure
	public double getSecondAxisExtent() {
		return this.extentSProperty.doubleValue();
	}

	public void setSecondAxisExtentProperty(DoubleProperty extent) {
		this.extentSProperty = extent;
	}

	/** Change the demi-size of the rectangle along its second axis.
	 * 
	 * @param extent - the extent along the second axis.
	 */
	public void setSecondAxisExtent(double extent) {
		this.extentSProperty.set(Math.max(extent, 0));
	}

	/** Set the first axis of the rectangle.
	 * The second axis is updated to be perpendicular to the new first axis.
	 * 
	 * @param axis - the new values for the first axis.
	 */
	public void setFirstAxis(Vector2D axis) {
		this.setFirstAxis(axis.getX(), axis.getY(), getFirstAxisExtent());
	}
	
	public void setFirstAxisProperties(Vector2d axis) {
		this.setFirstAxisProperties(axis, this.extentRProperty);
	}

	/** Set the first axis of the rectangle.
	 * The second axis is updated to be perpendicular to the new first axis.
	 * 
	 * @param axis - the new values for the first axis.
	 * @param extent - the extent of the axis.
	 */
	public void setFirstAxis(Vector2D axis, double extent) {
		this.setFirstAxis(axis.getX(), axis.getY(), extent);
	}
	
	public void setFirstAxisProperties(Vector2d axis, DoubleProperty extent) {
		assert(axis.isUnitVector());

		this.rxProperty = axis.xProperty;
		this.ryProperty = axis.xProperty;

		Vector2d axis2 = axis.clone();
		axis2.perpendicularize();
		this.sxProperty.set(axis2.getX());
		this.syProperty.set(axis2.getY());

		this.extentRProperty = extent;
	}

	/** Set the first axis of the rectangle.
	 * The second axis is updated to be perpendicular to the new first axis.
	 * 
	 * @param x
	 * @param y
	 */
	public void setFirstAxis(double x, double y) {
		this.setFirstAxis(x, y, getFirstAxisExtent());
	}

	/** Set the first axis of the rectangle.
	 * The second axis is updated to be perpendicular to the new first axis.
	 * 
	 * @param x
	 * @param y
	 * @param extent
	 */
	public void setFirstAxis(double x, double y, double extent) {
		Vector2f axis2 = new Vector2f(x, y);

		assert(axis2.isUnitVector());

		this.rxProperty.set(x);
		this.ryProperty.set(y);

		axis2.perpendicularize();
		this.sxProperty.set(axis2.getX());
		this.syProperty.set(axis2.getY());

		this.extentRProperty.set(extent);
	}

	/** Set the second axis of the rectangle.
	 * The first axis is updated to be perpendicular to the new second axis.
	 * 
	 * @param axis - the new values for the first axis.
	 */
	public void setSecondAxis(Vector2D axis) {
		this.setSecondAxis(axis.getX(), axis.getY(), getSecondAxisExtent());
	}

	public void setSecondAxisProperties(Vector2d axis) {
		this.setSecondAxisProperties(axis, this.extentRProperty);
	}
	
	/** Set the second axis of the rectangle.
	 * The first axis is updated to be perpendicular to the new second axis.
	 * 
	 * @param axis - the new values for the first axis.
	 * @param extent - the extent of the axis.
	 */
	public void setSecondAxis(Vector2D axis, double extent) {
		this.setSecondAxis(axis.getX(), axis.getY(), extent);
	}

	/** Set the second axis of the rectangle.
	 * The first axis is updated to be perpendicular to the new second axis.
	 * 
	 * @param x - the new values for the first axis.
	 * @param y - the new values for the first axis.
	 */
	public void setSecondAxis(double x, double y) {
		this.setSecondAxis(x, y, getSecondAxisExtent());
	}

	/** Set the second axis of the rectangle.
	 * The first axis is updated to be perpendicular to the new second axis.
	 * 
	 * @param x
	 * @param y
	 * @param extent
	 */
	public void setSecondAxis(double x, double y, double extent) {
		Vector2f axis1 = new Vector2f(x, y);

		assert(axis1.isUnitVector());
		this.sxProperty.set(x);
		this.syProperty.set(y);

		axis1.perpendicularize();
		axis1.negate();
		this.rxProperty.set(axis1.getX());
		this.ryProperty.set(axis1.getY());

		this.extentSProperty.set(extent);
	}

	public void setSecondAxisProperties(Vector2d axis, DoubleProperty extent) {
		assert(axis.isUnitVector());

		this.sxProperty = axis.xProperty;
		this.syProperty = axis.xProperty;

		Vector2d axis2 = axis.clone();
		axis2.perpendicularize();
		this.rxProperty.set(axis2.getX());
		this.ryProperty.set(axis2.getY());

		this.extentSProperty = extent;
	}
	
	
	@Override
	public void clear() {
		this.set(0f,0f,0f,0f,0f,0f);
	}

	@Override
	public void set(final Shape2F s) {
		if (s instanceof OrientedRectangle2f) {
			OrientedRectangle2d obr = (OrientedRectangle2d) s;
			set(obr.getCenterX(), obr.getCenterY(),
					obr.getFirstAxisX(), obr.getFirstAxisY(), obr.getFirstAxisExtent(),
					obr.getSecondAxisX(), obr.getSecondAxisY(), obr.getSecondAxisExtent());
		} else {
			setFromPointCloud(new Iterable<Point2f>() {
				@Override
				public Iterator<Point2f> iterator() {
					return new PointIterator(s.getPathIterator());
				}
			});
		}
	}

	/** Set the oriented rectangle.
	 * The second axis is automatically computed.
	 *
	 * @param center is the OBR center.
	 * @param axis1 is the first axis of the OBR.
	 * @param axis1Extent is the extent of the first axis.
	 * @param axis2Extent is the extent of the second axis.
	 */
	public void set(Point2D center, Vector2D axis1, double axis1Extent, double axis2Extent) {
		set(center.getX(), center.getY(),
				axis1.getX(), axis1.getY(),
				axis1Extent, axis2Extent);
	}

	/** Set the oriented rectangle.
	 * The second axis is automatically computed.
	 *
	 * @param centerX is the X coordinate of the OBR center.
	 * @param centerY is the Y coordinate of the OBR center.
	 * @param axis1X is the X coordinate of first axis of the OBR.
	 * @param axis1Y is the Y coordinate of first axis of the OBR.
	 * @param axis1Extent is the extent of the first axis.
	 * @param axis2Extent is the extent of the second axis.
	 */
	public void set(double centerX, double centerY,
			double axis1X, double axis1Y, double axis1Extent,
			double axis2Extent) {
		assert (new Vector2f(axis1X, axis1Y).isUnitVector());
			Vector2f axis2 = new Vector2f(axis1X, axis1Y);
		axis2.perpendicularize();
		assert (axis2.isUnitVector());
		set(centerX, centerY,
				axis1X, axis1Y, axis1Extent,
				axis2.getX(), axis2.getY(), axis2Extent);
	}
	
	/** Set the oriented rectangle.
	 *
	 * @param centerX is the X coordinate of the OBR center.
	 * @param centerY is the Y coordinate of the OBR center.
	 * @param axis1X is the X coordinate of first axis of the OBR.
	 * @param axis1Y is the Y coordinate of first axis of the OBR.
	 * @param axis1Extent is the extent of the first axis.
	 * @param axis2X is the X coordinate of second axis of the OBR.
	 * @param axis2Y is the Y coordinate of second axis of the OBR.
	 * @param axis2Extent is the extent of the second axis.
	 */
	protected void set(double centerX, double centerY,
			double axis1X, double axis1Y, double axis1Extent,
			double axis2X, double axis2Y, double axis2Extent) {
		assert (new Vector2f(axis1X, axis1Y).isUnitVector());
		assert (axis1Extent >= 0.);
		assert (new Vector2f(axis2X, axis2Y).isUnitVector());
		assert (axis2Extent >= 0.);

		this.set(centerX,centerY,axis1X,axis1Y,axis2X,axis2Y,axis1Extent,axis2Extent);
	}

	/** Set the oriented rectangle from a could of points.
 *
	 * @param pointCloud - the cloud of points.
	 */
	public void setFromPointCloud(Iterable<? extends Point2D> pointCloud) {
		Vector2f r = new Vector2f();
		Vector2f s = new Vector2f();
		computeOBRAxis(pointCloud, r, s);
		Point2f center = new Point2f();
		Vector2f extents = new Vector2f();
		computeOBRCenterExtents(pointCloud, r, s, center, extents);
		set(center.getX(), center.getY(),
				r.getX(), r.getY(), extents.getX(),
				s.getX(), s.getY(), extents.getY());
	}

	/** Set the oriented rectangle from a could of points.
	 *
	 * @param pointCloud - the cloud of points.
	 */
	public void setFromPointCloud(Point2D... pointCloud) {
		setFromPointCloud(Arrays.asList(pointCloud));
	}
	

}