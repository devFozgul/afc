/* 
 * $Id$
 * 
 * Copyright (C) 2013 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
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
package org.arakhne.afc.math.geometry.d3.continuous;

import java.lang.ref.SoftReference;

import org.arakhne.afc.math.geometry.coordinatesystem.CoordinateSystem3D;
import org.arakhne.afc.math.geometry.d3.FunctionalPoint3D;
import org.arakhne.afc.math.geometry.d3.FunctionalVector3D;
import org.arakhne.afc.math.geometry.d3.Point3D;
import org.eclipse.xtext.xbase.lib.Pure;

import javafx.beans.property.DoubleProperty;

/**
 * A triangle in space. It is defined by three points.
 * <p>
 * A triangle is transformable. So it has a position given
 * by its first point, an orientation given its normal
 * and no scale factor.
 * 
 * @author $Author: hjaffali$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class Triangle3d extends AbstractTriangle3F {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -607694384736216156L;

	/** First point.
	 */
	protected final Point3d p1;

	/** Second point.
	 */
	protected final Point3d p2;

	/** Third point.
	 */
	protected final Point3d p3;

	private SoftReference<Vector3d> normal = null;
	private Point3d pivot = null;
	private SoftReference<Quaternion> orientation = null;

	/**
	 * Construct a triangle 3D.
	 * This constructor does not copy the given points.
	 * The triangle's points will be references to the
	 * given points.
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Triangle3d(Point3d point1, Point3d point2, Point3d point3) {
		this(point1, point2, point3, false);
	}

	/**
	 * Construct a triangle 3D.
	 * This constructor does not copy the given points.
	 * The triangle's points properties will be references to the
	 * given points properties.
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param copyPoints indicates if the given points may be copied
	 * or referenced by this triangle. If <code>true</code> points
	 * will be copied, <code>false</code> points will be referenced.
	 */
	public Triangle3d(Point3d point1, Point3d point2, Point3d point3, boolean copyPoints) {
		if (copyPoints) {
			this.p1 = new Point3d(point1.getX(),point1.getY(),point1.getZ());
			this.p2 = new Point3d(point2.getX(),point2.getY(),point2.getZ());
			this.p3 = new Point3d(point3.getX(),point3.getY(),point3.getZ());
		}
		else {
			this.p1 = new Point3d(point1);
			this.p2 = new Point3d(point2);
			this.p3 = new Point3d(point3);
		}
	}

	/**
	 * Construct a triangle 3D.
	 * 
	 * @param p1x is the x coordinate of the first point.
	 * @param p1y is the y coordinate of the first point.
	 * @param p1z is the z coordinate of the first point.
	 * @param p2x is the x coordinate of the first point.
	 * @param p2y is the y coordinate of the first point.
	 * @param p2z is the z coordinate of the first point.
	 * @param p3x is the x coordinate of the first point.
	 * @param p3y is the y coordinate of the first point.
	 * @param p3z is the z coordinate of the first point.
	 */
	public Triangle3d(
			double p1x, double p1y, double p1z,
			double p2x, double p2y, double p2z,
			double p3x, double p3y, double p3z) {
		this.p1 = new Point3d(p1x, p1y, p1z);
		this.p2 = new Point3d(p2x, p2y, p2z);
		this.p3 = new Point3d(p3x, p3y, p3z);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector3d getNormal() {
		Vector3d v = null;
		if (this.normal!=null) {
			v = this.normal.get();
		}
		if (v==null) {
			v = new Vector3d();
			FunctionalVector3D.crossProduct(
					this.p2.getX() - this.p1.getX(),
					this.p2.getY() - this.p1.getY(),
					this.p2.getZ() - this.p1.getZ(),
					this.p3.getX() - this.p1.getX(),
					this.p3.getY() - this.p1.getY(),
					this.p3.getZ() - this.p1.getZ(),
					v);
			v.normalize();
			this.normal = new SoftReference<>(v);
		}
		return v;
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public Point3d getP1() {
		return this.p1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setP1(Point3D point) {
		setP1(point.getX(), point.getY(), point.getZ());
	}
	
	public void setP1Properties(Point3d point) {
		setP1Properties(point.xProperty, point.yProperty, point.zProperty);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setP1(double x, double y, double z) {
		this.p1.set(x, y, z);
		clearBufferedData();
	}
 
	public void setP1Properties(DoubleProperty x, DoubleProperty y, DoubleProperty z) {
		this.p1.setProperties(x, y, z);
		clearBufferedData();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public Point3d getP2() {
		return this.p2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setP2(Point3D point) {
		setP2(point.getX(), point.getY(), point.getZ());
	}

	public void setP2Properties(Point3d point) {
		setP2Properties(point.xProperty, point.yProperty, point.zProperty);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setP2(double x, double y, double z) {
		this.p2.set(x, y, z);
		clearBufferedData();
	}

	public void setP2Properties(DoubleProperty x, DoubleProperty y, DoubleProperty z) {
		this.p2.setProperties(x, y, z);
		clearBufferedData();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public Point3d getP3() {
		return this.p3;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setP3(Point3D point) {
		setP3(point.getX(), point.getY(), point.getZ());
	}

	public void setP3Properties(Point3d point) {
		setP3Properties(point.xProperty, point.yProperty, point.zProperty);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setP3(double x, double y, double z) {
		this.p3.set(x, y, z);
		clearBufferedData();
	}
	
	public void setP3Properties(DoubleProperty x, DoubleProperty y, DoubleProperty z) {
		this.p3.setProperties(x, y, z);
		clearBufferedData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public double getX1() {
		return this.p1.getX();
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public double getY1() {
		return this.p1.getY();
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public double getZ1() {
		return this.p1.getZ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public double getX2() {
		return this.p2.getX();
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public double getY2() {
		return this.p2.getY();
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public double getZ2() {
		return this.p2.getZ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public double getX3() {
		return this.p3.getX();
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public double getY3() {
		return this.p3.getY();
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public double getZ3() {
		return this.p3.getZ();
	}

	/**
	 * {@inheritDoc}
	 */
	public void set(Point3D point1, Point3D point2, Point3D point3) {
		this.p1.set(point1);
		this.p2.set(point2);
		this.p3.set(point3);
		clearBufferedData();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setProperties(Point3d point1, Point3d point2, Point3d point3) {
		this.p1.setProperties(point1.xProperty,point1.yProperty,point1.zProperty);
		this.p2.setProperties(point2.xProperty,point2.yProperty,point2.zProperty);
		this.p3.setProperties(point3.xProperty,point3.yProperty,point3.zProperty);
		clearBufferedData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Quaternion getOrientation() {
		Quaternion orient = null;
		if (this.orientation!=null) {
			orient = this.orientation.get();
		}
		if (orient==null) {
			Vector3d norm = getNormal();
			assert(norm!=null);
			CoordinateSystem3D cs = CoordinateSystem3D.getDefaultCoordinateSystem();
			assert(cs!=null);
			Vector3f up = cs.getUpVector();
			assert(up!=null);
			Vector3d axis = new Vector3d();
			FunctionalVector3D.crossProduct(
					up.getX(), up.getY(), up.getZ(),
					norm.getX(), norm.getY(), norm.getZ(),
					cs, axis);
			axis.normalize();
			orient = new Quaternion();
			orient.setAxisAngle(
					axis, 
					FunctionalVector3D.signedAngle(
							up.getX(), up.getY(), up.getZ(),
							norm.getX(), norm.getY(), norm.getZ()));
			this.orientation = new SoftReference<>(orient);
		}
		return orient;
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public Point3d getPivot() {
		return this.pivot==null ? this.p1.clone() : this.pivot.clone();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPivot(double x, double y, double z) {
		this.pivot = new Point3d(x, y, z);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPivot(FunctionalPoint3D point) {
		if (point == null) {
			this.pivot = null;
		} else if (this.pivot == null) {
			this.pivot = new Point3d(point);
		} else {
			this.pivot.set(point);
		}
	}
	

	public void setPivotProperties(Point3d point) {
		if (point == null) {
			this.pivot = null;
		} else if (this.pivot == null) {
			this.pivot = new Point3d(point);
		} else {
			this.pivot.set(point);
		}
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setNormal(SoftReference<FunctionalVector3D> normal1) {
		Vector3d v = new Vector3d(normal1.get());
		SoftReference<Vector3d> sr = new SoftReference<>(v);
		this.normal = sr;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOrientation(SoftReference<Quaternion> orientation1) {
		this.orientation = orientation1;
	}

	@Pure
	@Override
	public PathIterator3f getPathIterator(Transform3D transform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Pure
	@Override
	public PathIterator3d getPathIteratorProperty(Transform3D transform) {
		// TODO Auto-generated method stub
		return null;
	}

}
