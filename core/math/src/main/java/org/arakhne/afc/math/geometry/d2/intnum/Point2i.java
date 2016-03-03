/* 
 * $Id$
 * 
 * Copyright (C) 2011 Janus Core Developers
 * Copyright (C) 2012 Stephane GALLAND.
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
package org.arakhne.afc.math.geometry.d2.intnum;

import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Tuple2D;
import org.arakhne.afc.math.geometry.d2.Vector2D;
import org.eclipse.xtext.xbase.lib.Pure;

/** 2D Point with 2 integers.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public class Point2i extends Tuple2i<Point2D, Point2i> implements Point2D {

	private static final long serialVersionUID = -4977158382149954525L;

	/**
	 */
	public Point2i() {
		//
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Point2i(Tuple2D<?> tuple) {
		super(tuple);
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Point2i(int[] tuple) {
		super(tuple);
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Point2i(double[] tuple) {
		super(tuple);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Point2i(int x, int y) {
		super(x,y);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Point2i(float x, float y) {
		super(x,y);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Point2i(double x, double y) {
		super(x,y);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Point2i(long x, long y) {
		super(x,y);
	}

	@Pure
	@Override
	public double getDistanceSquared(Point2D p1) {
	      double dx = this.x - p1.getX();  
	      double dy = this.y - p1.getY();
	      return (dx*dx+dy*dy);
	}

	@Pure
	@Override
	public double getDistance(Point2D p1) {
	      double dx = this.x - p1.getX();  
	      double dy = this.y - p1.getY();
	      return Math.sqrt(dx*dx+dy*dy);
	}

	@Pure
	@Override
	public double getDistanceL1(Point2D p1) {
	      return (Math.abs(this.x-p1.getX()) + Math.abs(this.y-p1.getY()));
	}

	@Pure
	@Override
	public double getDistanceLinf(Point2D p1) {
	      return (Math.max( Math.abs(this.x-p1.getX()), Math.abs(this.y-p1.getY())));
	}

	@Pure
	@Override
	public int getIdistanceL1(Point2D p1) {
	      return (int) getDistanceL1(p1);
	}

	@Pure
	@Override
	public int getIdistanceLinf(Point2D p1) {
	      return (int) getDistanceLinf(p1);
	}

	@Override
	public void add(Point2D t1, Vector2D t2) {
		this.x = (int) (t1.getX() + t2.getX());
		this.y = (int) (t1.getY() + t2.getY());
	}

	@Override
	public void add(Vector2D t1, Point2D t2) {
		this.x = (int) (t1.getX() + t2.getX());
		this.y = (int) (t1.getY() + t2.getY());
	}

	@Override
	public void add(Vector2D t1) {
		this.x = (int) (this.x + t1.getX());
		this.y = (int) (this.y + t1.getY());
	}

	@Override
	public void scaleAdd(int s, Vector2D t1, Point2D t2) {
		this.x = (int) (s * t1.getX() + t2.getX());
		this.y = (int) (s * t1.getY() + t2.getY());
	}

	@Override
	public void scaleAdd(double s, Vector2D t1, Point2D t2) {
		this.x = (int) (s * t1.getX() + t2.getX());
		this.y = (int) (s * t1.getY() + t2.getY());
	}

	@Override
	public void scaleAdd(int s, Point2D t1, Vector2D t2) {
		this.x = (int) (s * t1.getX() + t2.getX());
		this.y = (int) (s * t1.getY() + t2.getY());
	}

	@Override
	public void scaleAdd(double s, Point2D t1, Vector2D t2) {
		this.x = (int) (s * t1.getX() + t2.getX());
		this.y = (int) (s * t1.getY() + t2.getY());
	}

	@Override
	public void scaleAdd(int s, Vector2D t1) {
		this.x = (int) (s * this.x + t1.getX());
		this.y = (int) (s * this.y + t1.getY());
	}

	@Override
	public void scaleAdd(double s, Vector2D t1) {
		this.x = (int) (s * this.x + t1.getX());
		this.y = (int) (s * this.y + t1.getY());
	}

	@Override
	public void sub(Point2D t1, Vector2D t2) {
		this.x = (int) (t1.getX() - t1.getX());
		this.y = (int) (t1.getY() - t1.getY());
	}

	@Override
	public void sub(Vector2D t1) {
		this.x = (int) (this.x - t1.getX());
		this.y = (int) (this.y - t1.getY());
	}

	@Pure
	@Override
	public Point2D toUnmodifiable() {
		return new UnmodifiablePoint2D() {

			private static final long serialVersionUID = -4844158582025788289L;

			@Override
			public Point2D clone() {
				return Point2i.this.toUnmodifiable();
			}

			@Override
			public double getX() {
				return Point2i.this.getX();
			}

			@Override
			public int ix() {
				return Point2i.this.ix();
			}

			@Override
			public double getY() {
				return Point2i.this.getY();
			}

			@Override
			public int iy() {
				return Point2i.this.ix();
			}
			
		};
	}

}