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
package org.arakhne.afc.math.continous.object2d;

import java.util.NoSuchElementException;

import org.arakhne.afc.math.MathConstants;
import org.arakhne.afc.math.MathUtil;
import org.arakhne.afc.math.generic.PathWindingRule;
import org.arakhne.afc.math.generic.Point2D;
import org.arakhne.afc.math.matrix.Transform2D;


/** 2D ellipse with double-point points.
 * 
 * @author $Author: galland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @deprecated see {@link org.arakhne.afc.math.geometry.d2.continuous.Ellipse2f}
 */
@Deprecated
public class Ellipse2f extends AbstractRectangularShape2f<Ellipse2f> {

	private static final long serialVersionUID = -2745313055404516167L;

	// ArcIterator.btan(Math.PI/2)
	private static final double CTRL_VAL = 0.5522847498307933f;

	/**
	 * ctrlpts contains the control points for a set of 4 cubic
	 * bezier curves that approximate a circle of radius 0.5
	 * centered at 0.5, 0.5
	 */
	static final double PCV = 0.5f + CTRL_VAL * 0.5f;
	/**
	 * ctrlpts contains the control points for a set of 4 cubic
	 * bezier curves that approximate a circle of radius 0.5
	 * centered at 0.5, 0.5
	 */
	static final double NCV = 0.5f - CTRL_VAL * 0.5f;
	/**
	 * ctrlpts contains the control points for a set of 4 cubic
	 * bezier curves that approximate a circle of radius 0.5
	 * centered at 0.5, 0.5
	 */
	static final double CTRL_PTS[][] = {
		{  1.0f,  PCV,  PCV,  1.0f,  0.5f,  1.0f },
		{  NCV,  1.0f,  0.0f,  PCV,  0.0f,  0.5f },
		{  0.0f,  NCV,  NCV,  0.0f,  0.5f,  0.0f },
		{  PCV,  0.0f,  1.0f,  NCV,  1.0f,  0.5f }
	};

	/** Replies if a rectangle is inside in the ellipse.
	 * 
	 * @param ex is the lowest corner of the ellipse.
	 * @param ey is the lowest corner of the ellipse.
	 * @param ewidth is the width of the ellipse.
	 * @param eheight is the height of the ellipse.
	 * @param rx is the lowest corner of the rectangle.
	 * @param ry is the lowest corner of the rectangle.
	 * @param rwidth is the width of the rectangle.
	 * @param rheight is the height of the rectangle.
	 * @return <code>true</code> if the given rectangle is inside the ellipse;
	 * otherwise <code>false</code>.
	 */
	public static boolean containsEllipseRectangle(double ex, double ey, double ewidth, double eheight, double rx, double ry, double rwidth, double rheight) {
		double ecx = (ex + ewidth/2f);
		double ecy = (ey + eheight/2f);
		double rcx = (rx + rwidth/2f);
		double rcy = (ry + rheight/2f);
		double farX;
		if (ecx<=rcx) farX = rx + rwidth;
		else farX = rx;
		double farY;
		if (ecy<=rcy) farY = ry + rheight;
		else farY = ry;
		return MathUtil.isPointInEllipse(farX, farY, ex, ey, ewidth, eheight);
	}

	/** Replies if two ellipses are intersecting.
	 * 
	 * @param x1 is the first corner of the first ellipse.
	 * @param y1 is the first corner of the first ellipse.
	 * @param x2 is the second corner of the first ellipse.
	 * @param y2 is the second corner of the first ellipse.
	 * @param x3 is the first corner of the second ellipse.
	 * @param y3 is the first corner of the second ellipse.
	 * @param x4 is the second corner of the second ellipse.
	 * @param y4 is the second corner of the second ellipse.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 */
	public static boolean intersectsEllipseEllipse(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double ell2w = Math.abs(x4 - x3);
		double ell2h = Math.abs(y4 - y3);
		double ellw = Math.abs(x2 - x1);
		double ellh = Math.abs(y2 - y1);

		if (ell2w <= 0f || ell2h <= 0f) return false;
		if (ellw <= 0f || ellh <= 0f) return false;

		// Normalize the second ellipse coordinates compared to the ellipse
		// having a center at 0,0 and a radius of 0.5.
		double normx0 = (x3 - x1) / ellw - 0.5f;
		double normx1 = normx0 + ell2w / ellw;
		double normy0 = (y3 - y1) / ellh - 0.5f;
		double normy1 = normy0 + ell2h / ellh;

		// find nearest x (left edge, right edge, 0.0)
		// find nearest y (top edge, bottom edge, 0.0)
		// if nearest x,y is inside circle of radius 0.5, then intersects
		double nearx, neary;
		if (normx0 > 0f) {
			// center to left of X extents
			nearx = normx0;
		} else if (normx1 < 0f) {
			// center to right of X extents
			nearx = normx1;
		} else {
			nearx = 0f;
		}
		if (normy0 > 0f) {
			// center above Y extents
			neary = normy0;
		} else if (normy1 < 0f) {
			// center below Y extents
			neary = normy1;
		} else {
			neary = 0f;
		}
		return (nearx * nearx + neary * neary) < 0.25f;
	}

	/** Replies if an ellipse and a line are intersecting.
	 * 
	 * @param ex is the lowest corner of the ellipse.
	 * @param ey is the lowest corner of the ellipse.
	 * @param ew is the width of the ellipse.
	 * @param eh is the height of the ellipse.
	 * @param x1 is the first point of the line.
	 * @param y1 is the first point of the line.
	 * @param x2 is the second point of the line.
	 * @param y2 is the second point of the line.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 * @see "http://blog.csharphelper.com/2012/09/24/calculate-where-a-line-segment-and-an-ellipse-intersect-in-c.aspx"
	 */
	public static boolean intersectsEllipseLine(double ex, double ey, double ew, double eh, double x1, double y1, double x2, double y2) {
		// If the ellipse or line segment are empty, return no intersections.
		if (eh<=0f || ew<=0f) {
			return false;
		}

		// Get the semimajor and semiminor axes.
		double a = ew / 2f;
		double b = eh / 2f;

		// Translate so the ellipse is centered at the origin.
		double ecx = ex + a;
		double ecy = ey + b;
		double px1 = x1 - ecx;
		double py1 = y1 - ecy;
		double px2 = x2 - ecx;
		double py2 = y2 - ecy;
		
		double sq_a = a*a;
		double sq_b = b*b;
		double vx = px2 - px1;
		double vy = py2 - py1;
		
		assert(sq_a!=0f && sq_b!=0f);

		// Calculate the quadratic parameters.
		double A = vx * vx / sq_a +
				vy * vy / sq_b;
		double B = 2f * px1 * vx / sq_a +
				2f * py1 * vy / sq_b;
		double C = px1 * px1 / sq_a + py1 * py1 / sq_b - 1;

		// Calculate the discriminant.
		double discriminant = B * B - 4f * A * C;
		return (discriminant>=0f);
	}

	/** Replies if an ellipse and a segment are intersecting.
	 * 
	 * @param ex is the lowest corner of the ellipse.
	 * @param ey is the lowest corner of the ellipse.
	 * @param ew is the width of the ellipse.
	 * @param eh is the height of the ellipse.
	 * @param x1 is the first point of the segment.
	 * @param y1 is the first point of the segment.
	 * @param x2 is the second point of the segment.
	 * @param y2 is the second point of the segment.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 * @see "http://blog.csharphelper.com/2012/09/24/calculate-where-a-line-segment-and-an-ellipse-intersect-in-c.aspx"
	 */
	public static boolean intersectsEllipseSegment(double ex, double ey, double ew, double eh, double x1, double y1, double x2, double y2) {
		// If the ellipse or line segment are empty, return no intersections.
		if (eh<=0f || ew<=0f) {
			return false;
		}

		// Get the semimajor and semiminor axes.
		double a = ew / 2f;
		double b = eh / 2f;

		// Translate so the ellipse is centered at the origin.
		double ecx = ex + a;
		double ecy = ey + b;
		double px1 = x1 - ecx;
		double py1 = y1 - ecy;
		double px2 = x2 - ecx;
		double py2 = y2 - ecy;
		
		double sq_a = a*a;
		double sq_b = b*b;
		double vx = px2 - px1;
		double vy = py2 - py1;
		
		assert(sq_a!=0f && sq_b!=0f);

		// Calculate the quadratic parameters.
		double A = vx * vx / sq_a + vy * vy / sq_b;
		double B = 2f * px1 * vx / sq_a + 2f * py1 * vy / sq_b;
		double C = px1 * px1 / sq_a + py1 * py1 / sq_b - 1;

		// Calculate the discriminant.
		double discriminant = B * B - 4f * A * C;
		if (discriminant<0f) {
			// No solution
			return false;
		}
		
		if (discriminant==0f) {
			// One real solution.
			double t = -B / 2f / A;
			return ((t >= 0f) && (t <= 1f));
		}

		assert(discriminant>0f);
		
		// Two real solutions.
		double t1 = (-B + (double)Math.sqrt(discriminant)) / 2f / A;
		double t2 = (-B - (double)Math.sqrt(discriminant)) / 2f / A;
		
		return (t1>=0 || t2>=0) && (t1<=1 || t2<=1);
	}

	/** Replies if two ellipses are intersecting.
	 * 
	 * @param x1 is the first corner of the first ellipse.
	 * @param y1 is the first corner of the first ellipse.
	 * @param x2 is the second corner of the first ellipse.
	 * @param y2 is the second corner of the first ellipse.
	 * @param x3 is the first corner of the second rectangle.
	 * @param y3 is the first corner of the second rectangle.
	 * @param x4 is the second corner of the second rectangle.
	 * @param y4 is the second corner of the second rectangle.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 */
	public static boolean intersectsEllipseRectangle(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		// From AWT Ellipse2D

		double rectw = Math.abs(x4 - x3);
		double recth = Math.abs(y4 - y3);
		double ellw = Math.abs(x2 - x1);
		double ellh = Math.abs(y2 - y1);

		if (rectw <= 0f || recth <= 0f) return false;
		if (ellw <= 0f || ellh <= 0f) return false;

		// Normalize the rectangular coordinates compared to the ellipse
		// having a center at 0,0 and a radius of 0.5.
		double normx0 = (x3 - x1) / ellw - 0.5f;
		double normx1 = normx0 + rectw / ellw;
		double normy0 = (y3 - y1) / ellh - 0.5f;
		double normy1 = normy0 + recth / ellh;
		// find nearest x (left edge, right edge, 0.0)
		// find nearest y (top edge, bottom edge, 0.0)
		// if nearest x,y is inside circle of radius 0.5, then intersects
		double nearx, neary;
		if (normx0 > 0f) {
			// center to left of X extents
			nearx = normx0;
		} else if (normx1 < 0f) {
			// center to right of X extents
			nearx = normx1;
		} else {
			nearx = 0f;
		}
		if (normy0 > 0f) {
			// center above Y extents
			neary = normy0;
		} else if (normy1 < 0f) {
			// center below Y extents
			neary = normy1;
		} else {
			neary = 0f;
		}
		return (nearx * nearx + neary * neary) < 0.25f;
	}

	/**
	 */
	public Ellipse2f() {
		//
	}

	/**
	 * @param min is the min corner of the ellipse.
	 * @param max is the max corner of the ellipse.
	 */
	public Ellipse2f(Point2f min, Point2f max) {
		super(min, max);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Ellipse2f(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	/**
	 * @param e
	 */
	public Ellipse2f(Ellipse2f e) {
		super(e);
	}

	/** {@inheritDoc}
	 */
	@Override
	public double distanceSquared(Point2D p) {
		Point2D r = getClosestPointTo(p);
		return r.distanceSquared(p);
	}

	/** {@inheritDoc}
	 */
	@Override
	public double distanceL1(Point2D p) {
		Point2D r = getClosestPointTo(p);
		return r.distanceL1(p);
	}

	/** {@inheritDoc}
	 */
	@Override
	public double distanceLinf(Point2D p) {
		Point2D r = getClosestPointTo(p);
		return r.distanceLinf(p);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(double x, double y) {
		return MathUtil.isPointInEllipse(
				x, y,
				getMinX(), getMinY(), getWidth(), getHeight());
	}

	@Override
	public boolean contains(Rectangle2f r) {
		return containsEllipseRectangle(
				getMinX(), getMinY(), getWidth(), getHeight(),
				r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
	}

	/** {@inheritDoc}
	 */
	@Override
	public Point2D getClosestPointTo(Point2D p) {
		org.arakhne.afc.math.geometry.d2.Point2D pp = MathUtil.getClosestPointToSolidEllipse(
				p.getX(), p.getY(),
				getMinX(), getMinY(),
				getWidth(), getHeight());
		return new Point2f(pp.getX(), pp.getY());
	}

	@Override
	public PathIterator2f getPathIterator(Transform2D transform) {
		if (transform==null) {
			return new CopyPathIterator(
					getMinX(), getMinY(),
					getMaxX(), getMaxY());
		}
		return new TransformPathIterator(
				getMinX(), getMinY(),
				getMaxX(), getMaxY(),
				transform);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Ellipse2f) {
			Ellipse2f rr2d = (Ellipse2f) obj;
			return ((getMinX() == rr2d.getMinX()) &&
					(getMinY() == rr2d.getMinY()) &&
					(getWidth() == rr2d.getWidth()) &&
					(getHeight() == rr2d.getHeight()));
		}
		return false;
	}

	@Override
	public int hashCode() {
		long bits = 1L;
		bits = 31L * bits + floatToIntBits(getMinX());
		bits = 31L * bits + floatToIntBits(getMinY());
		bits = 31L * bits + floatToIntBits(getMaxX());
		bits = 31L * bits + floatToIntBits(getMaxY());
		return (int) (bits ^ (bits >> 32));
	}

	@Override
	public boolean intersects(Rectangle2f s) {
		return intersectsEllipseRectangle(
				getMinX(), getMinY(),
				getMaxX(), getMaxY(),
				s.getMinX(), s.getMinY(),
				s.getMaxX(), s.getMaxY());
	}

	@Override
	public boolean intersects(Ellipse2f s) {
		return intersectsEllipseRectangle(
				getMinX(), getMinY(),
				getMaxX(), getMaxY(),
				s.getMinX(), s.getMinY(),
				s.getMaxX(), s.getMaxY());
	}

	@Override
	public boolean intersects(Circle2f s) {
		return intersectsEllipseEllipse(
				getMinX(), getMinY(),
				getMaxX(), getMaxY(),
				s.getX()-s.getRadius(), s.getY()-s.getRadius(),
				s.getX()+s.getRadius(), s.getY()+s.getRadius());
	}

	@Override
	public boolean intersects(Segment2f s) {
		return intersectsEllipseSegment(
				getMinX(), getMinY(),
				getWidth(), getHeight(),
				s.getX1(), s.getY1(),
				s.getX2(), s.getY2());
	}

	@Override
	public boolean intersects(Path2f s) {
		return intersects(s.getPathIterator((double) MathConstants.SPLINE_APPROXIMATION_RATIO));
	}

	@Override
	public boolean intersects(PathIterator2f s) {
		int mask = (s.getWindingRule() == PathWindingRule.NON_ZERO ? -1 : 2);
		int crossings = Path2f.computeCrossingsFromEllipse(
				0,
				s,
				getMinX(), getMinY(), getWidth(), getHeight(),
				false, true);
		return (crossings == MathConstants.SHAPE_INTERSECTS ||
				(crossings & mask) != 0);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("["); //$NON-NLS-1$
		b.append(getMinX());
		b.append(";"); //$NON-NLS-1$
		b.append(getMinY());
		b.append(";"); //$NON-NLS-1$
		b.append(getMaxX());
		b.append(";"); //$NON-NLS-1$
		b.append(getMaxY());
		b.append("]"); //$NON-NLS-1$
		return b.toString();
	}

	/**
	 * @author $Author: galland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	public static class CopyPathIterator implements PathIterator2f {

		private final double x1;
		private final double y1;
		private final double w;
		private final double h;
		private int index;
		private double lastX, lastY;

		/**
		 * @param x1
		 * @param y1
		 * @param x2
		 * @param y2
		 */
		public CopyPathIterator(double x1, double y1, double x2, double y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.w = x2 - x1;
			this.h = y2 - y1;
			if (this.w==0f && this.h==0f) {
				this.index = 6;
			}
		}

		@Override
		public boolean hasNext() {
			return this.index<=5;
		}

		@Override
		public PathElement2f next() {
			if (this.index>5) throw new NoSuchElementException();
			int idx = this.index;
			++this.index;

			if (idx==0) {
				double ctrls[] = CTRL_PTS[3];
				this.lastX = this.x1 + ctrls[4] * this.w;
				this.lastY = this.y1 + ctrls[5] * this.h;
				return new PathElement2f.MovePathElement2f(
						this.lastX,  this.lastY);
			}
			else if (idx<5) {
				double ctrls[] = CTRL_PTS[idx - 1];
				double ix = this.lastX;
				double iy = this.lastY;
				this.lastX = (this.x1 + ctrls[4] * this.w);
				this.lastY = (this.y1 + ctrls[5] * this.h);
				return new PathElement2f.CurvePathElement2f(
						ix,  iy,
						(this.x1 + ctrls[0] * this.w),
						(this.y1 + ctrls[1] * this.h),
						(this.x1 + ctrls[2] * this.w),
						(this.y1 + ctrls[3] * this.h),
						this.lastX,
						this.lastY);
			}

			return new PathElement2f.ClosePathElement2f(
					this.lastX, this.lastY,
					this.lastX, this.lastY);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public PathWindingRule getWindingRule() {
			return PathWindingRule.NON_ZERO;
		}

		@Override
		public boolean isPolyline() {
			return false;
		}

	}

	/**
	 * @author $Author: galland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	public static class TransformPathIterator implements PathIterator2f {

		private final Point2D lastPoint = new Point2f();
		private final Point2D ptmp1 = new Point2f();
		private final Point2D ptmp2 = new Point2f();
		private final Transform2D transform;
		private final double x1;
		private final double y1;
		private final double w;
		private final double h;
		private int index;

		/**
		 * @param x1
		 * @param y1
		 * @param x2
		 * @param y2
		 * @param transform
		 */
		public TransformPathIterator(double x1, double y1, double x2, double y2, Transform2D transform) {
			this.transform = transform;
			this.x1 = x1;
			this.y1 = y1;
			this.w = x2 - x1;
			this.h = y2 - y1;
			if (this.w==0f && this.h==0f) {
				this.index = 6;
			}
		}

		@Override
		public boolean hasNext() {
			return this.index<=5;
		}

		@Override
		public PathElement2f next() {
			if (this.index>5) throw new NoSuchElementException();
			int idx = this.index;
			++this.index;

			if (idx==0) {
				double ctrls[] = CTRL_PTS[3];
				this.lastPoint.set(
						this.x1 + ctrls[4] * this.w,
						this.y1 + ctrls[5] * this.h);
				this.transform.transform(this.lastPoint);
				return new PathElement2f.MovePathElement2f(
						this.lastPoint.getX(), this.lastPoint.getY());
			}
			else if (idx<5) {
				double ctrls[] = CTRL_PTS[idx - 1];
				double ix = this.lastPoint.getX();
				double iy = this.lastPoint.getY();
				this.lastPoint.set(
						(this.x1 + ctrls[4] * this.w),
						(this.y1 + ctrls[5] * this.h));
				this.transform.transform(this.lastPoint);
				this.ptmp1.set(
						(this.x1 + ctrls[0] * this.w),
						(this.y1 + ctrls[1] * this.h));
				this.transform.transform(this.ptmp1);
				this.ptmp2.set(
						(this.x1 + ctrls[2] * this.w),
						(this.y1 + ctrls[3] * this.h));
				this.transform.transform(this.ptmp2);
				return new PathElement2f.CurvePathElement2f(
						ix,  iy,
						this.ptmp1.getX(), this.ptmp1.getY(),
						this.ptmp2.getX(), this.ptmp2.getY(),
						this.lastPoint.getX(), this.lastPoint.getY());
			}

			double ix = this.lastPoint.getX();
			double iy = this.lastPoint.getY();
			return new PathElement2f.ClosePathElement2f(
					ix, iy,
					ix, iy);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public PathWindingRule getWindingRule() {
			return PathWindingRule.NON_ZERO;
		}

		@Override
		public boolean isPolyline() {
			return false;
		}

	}

}