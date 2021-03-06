/* 
 * $Id$
 * 
 * Copyright (C) 2010-2012 Stephane GALLAND.
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
package org.arakhne.afc.math.geometry.d3.continuous;

import java.io.Serializable;

import org.arakhne.afc.math.MathConstants;
import org.arakhne.afc.math.MathUtil;
import org.arakhne.afc.math.geometry.coordinatesystem.CoordinateSystem3D;
import org.arakhne.afc.math.geometry.d3.Vector3D;
import org.arakhne.afc.math.matrix.Matrix3f;
import org.arakhne.afc.math.matrix.Matrix4f;
import org.eclipse.xtext.xbase.lib.Pure;

/** A 4 element unit quaternion represented by single precision floating 
 * point x,y,z,w coordinates.  The quaternion is always normalized.
 * 
 * <h3>Other Rotation Representations</h3>
 * 
 * Other representations of an rotation are available from this class:
 * axis-angle, and Euler angles.
 * 
 * <h4>Axis Angles</h4>
 * The axis–angle representation of a rotation parameterizes a rotation in a three-dimensional
 * Euclidean space by two values: a unit vector, indicating the direction of an axis of rotation, and
 * an angle describing the magnitude of the rotation about the axis.
 * The rotation occurs in the sense prescribed by the (left/right)-hand rule.
 * <img src="doc-files/axis_angle.png" alt="[Axis-Angle Representation]">
 * 
 * <h4>Euler Angles</h4>
 * The term "Euler Angle" is used for any representation of 3 dimensional 
 * rotations where the rotation is decomposed into 3 separate angles. 
 * <p>
 * There is no single set of conventions and standards in this area, 
 * therefore the following conventions was choosen:<ul>
 * <li>angle applied first:	heading;</li>
 * <li>angle applied second: attitude;</li>
 * <li>angle applied last: bank</li>
 * </ul>
 * <p>
 * Examples: NASA aircraft standard and telescope standard
 * <img src="doc-files/euler_plane.gif" alt="[NASA Aircraft Standard]">
 * <img src="doc-files/euler_telescop.gif" alt="[Telescope Standard]">
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class Quaternion implements Cloneable, Serializable {

	private static final long serialVersionUID = 4494919776986180960L;

	private final static double EPS = 0.000001;
	private final static double EPS2 = 1.0e-30;

	/** x coordinate.
	 */
	protected double x;

	/** y coordinate.
	 */
	protected double y;

	/** z coordinate.
	 */
	protected double z;

	/** w coordinate.
	 */
	protected double w;

	/** Construct a zero quaternion.
	 */
	public Quaternion() {
		this.x = this.y = this.z = this.w = 0;
	}

	/** Construct a quaternion with the given components.
	 *
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param w1
	 */
	public Quaternion(double x1, double y1, double z1, double w1) {
		double mag = (1.0/Math.sqrt( x1*x1 + y1*y1 + z1*z1 + w1*w1 ));
		this.x = x1*mag;
		this.y = y1*mag;
		this.z = z1*mag;
		this.w = w1*mag;
	}

	/** Construct a quaternion with the components of the given quaternion.
	 *
	 * @param q
	 */
	public Quaternion(Quaternion q) {
		this.x = q.x;
		this.y = q.y;
		this.z = q.z;
		this.w = q.w;
	}

	/** Construct a quaternion from an axis-angle representation.
	 *
	 * @param axis is the axis of the rotation.
	 * @param angle is the rotation angle around the axis.
	 */
	public Quaternion(Vector3D axis, double angle) {
		setAxisAngle(axis, angle);
	}

	/** Construct a quaternion from Euler angles.
	 * The {@link CoordinateSystem3D#getDefaultCoordinateSystem() default coordinate system}
	 * is used from applying the Euler angles.
	 *
	 * @param attitude is the rotation around left vector.
	 * @param bank is the rotation around front vector.
	 * @param heading is the rotation around top vector.
	 * @see <a href="http://en.wikipedia.org/wiki/Euler_angles">Euler Angles</a>
	 * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToQuaternion/index.htm">Euler to Quaternion</a>
	 * @see CoordinateSystem3D#getDefaultCoordinateSystem()
	 */
	public Quaternion(double attitude, double bank, double heading) {
		this(attitude, bank, heading, null);
	}

	/** Construct a quaternion from Euler angles.
	 *
	 * @param attitude is the rotation around left vector.
	 * @param bank is the rotation around front vector.
	 * @param heading is the rotation around top vector.
	 * @param system the coordinate system to use for applying the Euler angles.
	 * @see <a href="http://en.wikipedia.org/wiki/Euler_angles">Euler Angles</a>
	 * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToQuaternion/index.htm">Euler to Quaternion</a>
	 */
	public Quaternion(double attitude, double bank, double heading, CoordinateSystem3D system) {
		setEulerAngles(attitude, bank, heading, system);
	}

	/** {@inheritDoc}
	 */
	@Pure
	@Override
	public Quaternion clone() {
		try {
			return (Quaternion)super.clone(); 
		}
		catch(CloneNotSupportedException e) {   
			throw new Error(e);
		}
	}

	/** Replies the X coordinate.
	 * 
	 * @return x
	 */
	@Pure
	public double getX() {
		return this.x;
	}

	/** Set the X coordinate.
	 * 
	 * @param x1
	 */
	public void setX(double x1) {
		this.x = x1;
	}

	/** Replies the Y coordinate.
	 * 
	 * @return y
	 */
	@Pure
	public double getY() {
		return this.y;
	}

	/** Set the Y coordinate.
	 * 
	 * @param y1
	 */
	public void setY(double y1) {
		this.y = y1;
	}

	/** Replies the Z coordinate.
	 * 
	 * @return z
	 */
	@Pure
	public double getZ() {
		return this.z;
	}

	/** Set the Z coordinate.
	 * 
	 * @param z1
	 */
	public void setZ(double z1) {
		this.z = z1;
	}

	/** Replies the W coordinate.
	 * 
	 * @return w
	 */
	@Pure
	public double getW() {
		return this.w;
	}

	/** Set the W coordinate.
	 * 
	 * @param w1
	 */
	public void setW(double w1) {
		this.w = w1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public boolean equals(Object t1) {
		try {
			Quaternion t2 = (Quaternion) t1;
			return(this.x == t2.getX() && this.y == t2.getY() && this.z == t2.getZ() && this.w == t2.getW());
		}
		catch(AssertionError e) {
			throw e;
		}
		catch (Throwable e2) {
			return false;
		}
	}

	/**
	 * Returns true if the L-infinite distance between this tuple
	 * and tuple t1 is less than or equal to the epsilon parameter, 
	 * otherwise returns false.  The L-infinite
	 * distance is equal to MAX[abs(x1-x2), abs(y1-y2)]. 
	 * @param t1  the tuple to be compared to this tuple
	 * @param epsilon  the threshold value  
	 * @return  true or false
	 */
	@Pure
	public boolean epsilonEquals(Quaternion t1, double epsilon) {
		double diff;

		diff = this.x - t1.getX();
		if(Double.isNaN(diff)) return false;
		if((diff<0?-diff:diff) > epsilon) return false;

		diff = this.y - t1.getY();
		if(Double.isNaN(diff)) return false;
		if((diff<0?-diff:diff) > epsilon) return false;

		diff = this.z - t1.getZ();
		if(Double.isNaN(diff)) return false;
		if((diff<0?-diff:diff) > epsilon) return false;

		diff = this.w - t1.getW();
		if(Double.isNaN(diff)) return false;
		if((diff<0?-diff:diff) > epsilon) return false;

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public int hashCode() {
		long bits = 1;
		bits = 31 * bits + Double.doubleToLongBits(this.x);
		bits = 31 * bits + Double.doubleToLongBits(this.y);
		bits = 31 * bits + Double.doubleToLongBits(this.z);
		bits = 31 * bits + Double.doubleToLongBits(this.w);
		int b = (int) bits;
		return b ^ (b >> 32);
	}

	/**
	 * {@inheritDoc}
	 */
	@Pure
	@Override
	public String toString() {
		return "(" 
				+this.x
				+";" 
				+this.y
				+";" 
				+this.z
				+";" 
				+this.w
				+")"; 
	}

	/**
	 * Sets the value of this quaternion to the conjugate of quaternion q1.
	 * @param q1 the source vector
	 */
	public final void conjugate(Quaternion q1) {
		this.x = -q1.x;
		this.y = -q1.y;
		this.z = -q1.z;
		this.w = q1.w;
	}

	/**
	 * Sets the value of this quaternion to the conjugate of itself.
	 */
	public final void conjugate() {
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
	}

	/**
	 * Sets the value of this quaternion to the quaternion product of
	 * quaternions q1 and q2 (this = q1 * q2).  
	 * Note that this is safe for aliasing (e.g. this can be q1 or q2).
	 * @param q1 the first quaternion
	 * @param q2 the second quaternion
	 */
	public final void mul(Quaternion q1, Quaternion q2) {
		if (this != q1 && this != q2) {
			this.w = q1.w*q2.w - q1.x*q2.x - q1.y*q2.y - q1.z*q2.z;
			this.x = q1.w*q2.x + q2.w*q1.x + q1.y*q2.z - q1.z*q2.y;
			this.y = q1.w*q2.y + q2.w*q1.y - q1.x*q2.z + q1.z*q2.x;
			this.z = q1.w*q2.z + q2.w*q1.z + q1.x*q2.y - q1.y*q2.x;
		}
		else {
			double	x1, y1, w1;

			w1 = q1.w*q2.w - q1.x*q2.x - q1.y*q2.y - q1.z*q2.z;
			x1 = q1.w*q2.x + q2.w*q1.x + q1.y*q2.z - q1.z*q2.y;
			y1 = q1.w*q2.y + q2.w*q1.y - q1.x*q2.z + q1.z*q2.x;
			this.z = q1.w*q2.z + q2.w*q1.z + q1.x*q2.y - q1.y*q2.x;
			this.w = w1;
			this.x = x1;
			this.y = y1;
		}
	}


	/**
	 * Sets the value of this quaternion to the quaternion product of
	 * itself and q1 (this = this * q1).  
	 * @param q1 the other quaternion
	 */
	public final void mul(Quaternion q1) {
		double x1, y1, w1; 

		w1 = this.w*q1.w - this.x*q1.x - this.y*q1.y - this.z*q1.z;
		x1 = this.w*q1.x + q1.w*this.x + this.y*q1.z - this.z*q1.y;
		y1 = this.w*q1.y + q1.w*this.y - this.x*q1.z + this.z*q1.x;
		this.z = this.w*q1.z + q1.w*this.z + this.x*q1.y - this.y*q1.x;
		this.w = w1;
		this.x = x1;
		this.y = y1;
	} 

	/** 
	 * Multiplies quaternion q1 by the inverse of quaternion q2 and places
	 * the value into this quaternion.  The value of both argument quaternions 
	 * is preservered (this = q1 * q2^-1).
	 * @param q1 the first quaternion
	 * @param q2 the second quaternion
	 */ 
	public final void mulInverse(Quaternion q1, Quaternion q2) 
	{   
		Quaternion  tempQuat = q2.clone();  

		tempQuat.inverse(); 
		this.mul(q1, tempQuat); 
	}



	/**
	 * Multiplies this quaternion by the inverse of quaternion q1 and places
	 * the value into this quaternion.  The value of the argument quaternion
	 * is preserved (this = this * q^-1).
	 * @param q1 the other quaternion
	 */
	public final void mulInverse(Quaternion q1) {  
		Quaternion  tempQuat = q1.clone();

		tempQuat.inverse();
		this.mul(tempQuat);
	}

	/**
	 * Sets the value of this quaternion to quaternion inverse of quaternion q1.
	 * @param q1 the quaternion to be inverted
	 */
	public final void inverse(Quaternion q1) {
		double norm;

		norm = 1f/(q1.w*q1.w + q1.x*q1.x + q1.y*q1.y + q1.z*q1.z);
		this.w =  norm*q1.w;
		this.x = -norm*q1.x;
		this.y = -norm*q1.y;
		this.z = -norm*q1.z;
	}


	/**
	 * Sets the value of this quaternion to the quaternion inverse of itself.
	 */
	public final void inverse() {
		double norm;  

		norm = 1f/(this.w*this.w + this.x*this.x + this.y*this.y + this.z*this.z);
		this.w *=  norm;
		this.x *= -norm;
		this.y *= -norm;
		this.z *= -norm;
	}

	/**
	 * Sets the value of this quaternion to the normalized value
	 * of quaternion q1.
	 * @param q1 the quaternion to be normalized.
	 */
	public final void normalize(Quaternion q1) {
		double norm;

		norm = (q1.x*q1.x + q1.y*q1.y + q1.z*q1.z + q1.w*q1.w);

		if (norm > 0f) {
			norm = 1f/Math.sqrt(norm);
			this.x = norm*q1.x;
			this.y = norm*q1.y;
			this.z = norm*q1.z;
			this.w = norm*q1.w;
		} else {
			this.x = 0f;
			this.y = 0f;
			this.z = 0f;
			this.w = 0f;
		}
	}


	/**
	 * Normalizes the value of this quaternion in place.
	 */
	public final void normalize() {
		double norm;

		norm = (this.x*this.x + this.y*this.y + this.z*this.z + this.w*this.w);

		if (norm > 0f) {
			norm = 1f / Math.sqrt(norm);
			this.x *= norm;
			this.y *= norm;
			this.z *= norm;
			this.w *= norm;
		} else {
			this.x = 0f;
			this.y = 0f;
			this.z = 0f;
			this.w = 0f;
		}
	}

	/**
	 * Sets the value of this quaternion to the rotational component of
	 * the passed matrix.
	 * @param m1 the Matrix4f
	 */
	public final void setFromMatrix(Matrix4f m1) {
		double ww = 0.25f*(m1.m00 + m1.m11 + m1.m22 + m1.m33);

		if (ww >= 0) {
			if (ww >= EPS2) {
				this.w =  Math.sqrt(ww);
				ww =  0.25f/this.w;
				this.x = (m1.m21 - m1.m12)*ww;
				this.y = (m1.m02 - m1.m20)*ww;
				this.z = (m1.m10 - m1.m01)*ww;
				return;
			} 
		}
		else {
			this.w = 0;
			this.x = 0;
			this.y = 0;
			this.z = 1;
			return;
		}

		this.w = 0;
		ww = -0.5f*(m1.m11 + m1.m22);

		if (ww >= 0) {
			if (ww >= EPS2) {
				this.x =  Math.sqrt(ww);
				ww = 1.0f/(2.0f*this.x);
				this.y = m1.m10*ww;
				this.z = m1.m20*ww;
				return;
			}
		} else {
			this.x = 0;
			this.y = 0;
			this.z = 1;
			return;
		}

		this.x = 0;
		ww = 0.5f*(1.0f - m1.m22);

		if (ww >= EPS2) {
			this.y =  Math.sqrt(ww);
			this.z = m1.m21/(2.0f*this.y);
			return;
		}

		this.y = 0;
		this.z = 1;
	}

	/**
	 * Sets the value of this quaternion to the rotational component of
	 * the passed matrix.
	 * @param m1 the Matrix3f
	 */
	public final void setFromMatrix(Matrix3f m1) {
		double ww = 0.25f*(m1.m00 + m1.m11 + m1.m22 + 1.0f);

		if (ww >= 0) {
			if (ww >= EPS2) {
				this.w =  Math.sqrt(ww);
				ww = 0.25f/this.w;
				this.x = (m1.m21 - m1.m12)*ww;
				this.y = (m1.m02 - m1.m20)*ww;
				this.z = (m1.m10 - m1.m01)*ww;
				return;
			}
		} else {
			this.w = 0;
			this.x = 0;
			this.y = 0;
			this.z = 1;
			return;
		}

		this.w = 0;
		ww = -0.5f*(m1.m11 + m1.m22);
		if (ww >= 0) {
			if (ww >= EPS2) {
				this.x =  Math.sqrt(ww);
				ww = 0.5f/this.x;
				this.y = m1.m10*ww;
				this.z = m1.m20*ww;
				return;
			}
		} else {
			this.x = 0;
			this.y = 0;
			this.z = 1;
			return;
		}

		this.x = 0;
		ww =  0.5f*(1.0f - m1.m22);
		if (ww >= EPS2) {
			this.y =  Math.sqrt(ww);
			this.z = m1.m21/(2.0f*this.y);
			return;
		}

		this.y = 0;
		this.z = 1;
	}

	/** Set the quaternion coordinates.
	 * 
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param w1
	 */
	public void set(double x1, double y1, double z1, double w1) {
		double mag = (1.0/Math.sqrt( x1*x1 + y1*y1 + z1*z1 + w1*w1 ));
		this.x = x1*mag;
		this.y = y1*mag;
		this.z = z1*mag;
		this.w = w1*mag;
	}

	/** Set the quaternion coordinates.
	 * 
	 * @param q
	 */
	public void set(Quaternion q) {
		this.x = q.x;
		this.y = q.y;
		this.z = q.z;
		this.w = q.w;
	}

	/**
	 * Sets the value of this quaternion to the equivalent rotation
	 * of the Axis-Angle arguments.
	 * @param axis is the axis of rotation.
	 * @param angle is the rotation around the axis.
	 */
	public final void setAxisAngle(Vector3D axis, double angle) {
		setAxisAngle(axis.getX(),  axis.getY(), axis.getZ(), angle);
	}

	/**
	 * Sets the value of this quaternion to the equivalent rotation
	 * of the Axis-Angle arguments.
	 * @param x1 is the x coordinate of the rotation axis
	 * @param y1 is the y coordinate of the rotation axis
	 * @param z1 is the z coordinate of the rotation axis
	 * @param angle is the rotation around the axis.
	 */
	public final void setAxisAngle(double x1, double y1, double z1, double angle) {
		double mag,amag;
		// Quat = cos(theta/2) + sin(theta/2)(roation_axis) 
		amag = Math.sqrt(x1*x1 + y1*y1 + z1*z1);
		if (amag < EPS ) {
			this.w = 0.0f;
			this.x = 0.0f;
			this.y = 0.0f;
			this.z = 0.0f;
		}
		else {  
			amag = 1.0f/amag; 
			mag = Math.sin(angle/2.0);
			this.w = Math.cos(angle/2.0);
			this.x = x1*amag*mag;
			this.y = y1*amag*mag;
			this.z = z1*amag*mag;
		}
	}

	/** Replies the rotation axis-angle represented by this quaternion.
	 * 
	 * @return the rotation axis-angle.
	 */
	@Pure
	public final Vector3f getAxis() {
		double mag = this.x*this.x + this.y*this.y + this.z*this.z;  

		if ( mag > EPS ) {
			mag = Math.sqrt(mag);
			double invMag = 1f/mag;

			return new Vector3f(
					this.x*invMag,
					this.y*invMag,
					this.z*invMag);
		}
		return new Vector3f(0f, 0f, 1f);
	}

	/** Replies the rotation angle represented by this quaternion.
	 * 
	 * @return the rotation axis
	 * @see #setAxisAngle(Vector3D, double)
	 * @see #setAxisAngle(double, double, double, double)
	 * @see #getAxis()
	 */
	@Pure
	public final double getAngle() {
		double mag = this.x*this.x + this.y*this.y + this.z*this.z;  

		if ( mag > EPS ) {
			mag = Math.sqrt(mag);
			return (2.f*Math.atan2(mag, this.w)); 
		}
		return 0f;
	}

	/** Replies the rotation axis represented by this quaternion.
	 * 
	 * @return the rotation axis
	 * @see #setAxisAngle(Vector3D, double)
	 * @see #setAxisAngle(double, double, double, double)
	 * @see #getAngle()
	 */
	@SuppressWarnings("synthetic-access")
	@Pure
	public final AxisAngle getAxisAngle() {
		double mag = this.x*this.x + this.y*this.y + this.z*this.z;  

		if ( mag > EPS ) {
			mag = Math.sqrt(mag);
			double invMag = 1f/mag;

			return new AxisAngle(
					this.x*invMag,
					this.y*invMag,
					this.z*invMag,
					(2.*Math.atan2(mag, this.w)));
		}
		return new AxisAngle(0, 0, 1, 0);
	}

	/**
	 *  Performs a great circle interpolation between this quaternion
	 *  and the quaternion parameter and places the result into this
	 *  quaternion.
	 *  @param q1  the other quaternion
	 *  @param alpha  the alpha interpolation parameter
	 */
	public final void interpolate(Quaternion q1, double alpha) {
		// From "Advanced Animation and Rendering Techniques"
		// by Watt and Watt pg. 364, function as implemented appeared to be 
		// incorrect.  Fails to choose the same quaternion for the double
		// covering. Resulting in change of direction for rotations.
		// Fixed function to negate the first quaternion in the case that the
		// dot product of q1 and this is negative. Second case was not needed. 

		double dot,s1,s2,om,sinom;

		dot = this.x*q1.x + this.y*q1.y + this.z*q1.z + this.w*q1.w;

		if ( dot < 0 ) {
			// negate quaternion
			q1.x = -q1.x;  q1.y = -q1.y;  q1.z = -q1.z;  q1.w = -q1.w;
			dot = -dot;
		}

		if ( (1.0 - dot) > EPS ) {
			om = Math.acos(dot);
			sinom = Math.sin(om);
			s1 = Math.sin((1.0-alpha)*om)/sinom;
			s2 = Math.sin( alpha*om)/sinom;
		} else{
			s1 = 1.0 - alpha;
			s2 = alpha;
		}

		this.w = (s1*this.w + s2*q1.w);
		this.x = (s1*this.x + s2*q1.x);
		this.y = (s1*this.y + s2*q1.y);
		this.z = (s1*this.z + s2*q1.z);
	}



	/** 
	 *  Performs a great circle interpolation between quaternion q1
	 *  and quaternion q2 and places the result into this quaternion. 
	 *  @param q1  the first quaternion
	 *  @param q2  the second quaternion
	 *  @param alpha  the alpha interpolation parameter 
	 */   
	public final void interpolate(Quaternion q1, Quaternion q2, double alpha) { 
		// From "Advanced Animation and Rendering Techniques"
		// by Watt and Watt pg. 364, function as implemented appeared to be 
		// incorrect.  Fails to choose the same quaternion for the double
		// covering. Resulting in change of direction for rotations.
		// Fixed function to negate the first quaternion in the case that the
		// dot product of q1 and this is negative. Second case was not needed. 

		double dot,s1,s2,om,sinom;

		dot = q2.x*q1.x + q2.y*q1.y + q2.z*q1.z + q2.w*q1.w;

		if ( dot < 0 ) {
			// negate quaternion
			q1.x = -q1.x;  q1.y = -q1.y;  q1.z = -q1.z;  q1.w = -q1.w;
			dot = -dot;
		}

		if ( (1.0 - dot) > EPS ) {
			om = Math.acos(dot);
			sinom = Math.sin(om);
			s1 = Math.sin((1.0-alpha)*om)/sinom;
			s2 = Math.sin( alpha*om)/sinom;
		} else{
			s1 = 1.0 - alpha;
			s2 = alpha;
		}
		this.w = s1*q1.w + s2*q2.w;
		this.x = s1*q1.x + s2*q2.x;
		this.y = s1*q1.y + s2*q2.y;
		this.z = s1*q1.z + s2*q2.z;
	}

	/** Set the quaternion with the Euler angles.
	 *
	 * @param angles the Euler angles.
	 * @see <a href="http://en.wikipedia.org/wiki/Euler_angles">Euler Angles</a>
	 * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToQuaternion/index.htm">Euler to Quaternion</a>
	 */
	@SuppressWarnings("synthetic-access")
	public void setEulerAngles(EulerAngles angles) {
		setEulerAngles(
				angles.getAttitude(),
				angles.getBank(),
				angles.getHeading(),
				angles.getSystem());
	}

	/** Set the quaternion with the Euler angles.
	 * The {@link CoordinateSystem3D#getDefaultCoordinateSystem() default coordinate system}
	 * is used from applying the Euler angles.
	 *
	 * @param attitude is the rotation around left vector.
	 * @param bank is the rotation around front vector.
	 * @param heading is the rotation around top vector.
	 * @see CoordinateSystem3D#getDefaultCoordinateSystem()
	 * @see <a href="http://en.wikipedia.org/wiki/Euler_angles">Euler Angles</a>
	 * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToQuaternion/index.htm">Euler to Quaternion</a>
	 */
	public void setEulerAngles(double attitude, double bank, double heading) {
		setEulerAngles(attitude, bank, heading, null);
	}

	/** Set the quaternion with the Euler angles.
	 *
	 * @param attitude is the rotation around left vector.
	 * @param bank is the rotation around front vector.
	 * @param heading is the rotation around top vector.
	 * @param system the coordinate system to use for applying the Euler angles.
	 * @see <a href="http://en.wikipedia.org/wiki/Euler_angles">Euler Angles</a>
	 * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToQuaternion/index.htm">Euler to Quaternion</a>
	 */
	public void setEulerAngles(double attitude, double bank, double heading, CoordinateSystem3D system) {
		CoordinateSystem3D cs = (system == null) ? CoordinateSystem3D.getDefaultCoordinateSystem() : system;

		double c1 = Math.cos(heading / 2.);
		double s1 = Math.sin(heading / 2.);
		double c2 = Math.cos(attitude / 2.);
		double s2 = Math.sin(attitude / 2.);
		double c3 = Math.cos(bank / 2.);
		double s3 = Math.sin(bank / 2.);

		double x1, y1, z1, w1;

		// Source: http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToQuaternion/index.htm
		// Standard used: XZY_RIGHT_HAND
		double c1c2 = c1 * c2;
		double s1s2 = s1 * s2;
		w1 = c1c2 * c3 - s1s2 * s3;
		x1 = c1c2 * s3 + s1s2 * c3;
		y1 = s1 * c2 * c3 + c1 * s2 * s3;
		z1 = c1 * s2 * c3 - s1 * c2 * s3;

		set(x1, y1, z1, w1);
		CoordinateSystem3D.XZY_RIGHT_HAND.toSystem(this, cs);
	}

	/**
	 * Replies the Euler's angles that corresponds to the quaternion.
	 * The {@link CoordinateSystem3D#getDefaultCoordinateSystem() default coordinate system}
	 * is used from applying the Euler angles.
	 * 
	 * @return the heading, attitude and bank angles.
	 * @see CoordinateSystem3D#getDefaultCoordinateSystem()
	 * @see <a href="http://en.wikipedia.org/wiki/Euler_angles">Euler Angles</a>
	 * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToEuler/index.htm">Quaternion to Euler</a>
	 */
	@Pure
	public EulerAngles getEulerAngles() {
		return getEulerAngles(CoordinateSystem3D.getDefaultCoordinateSystem());
	}

	/**
	 * Replies the Euler's angles that corresponds to the quaternion.
	 * 
	 * @param system is the coordinate system used to define the up,left and front vectors.
	 * @return the heading, attitude and bank angles.
	 * @see <a href="http://en.wikipedia.org/wiki/Euler_angles">Euler Angles</a>
	 * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToEuler/index.htm">Quaternion to Euler</a>
	 */
	@SuppressWarnings("synthetic-access")
	@Pure
	public EulerAngles getEulerAngles(CoordinateSystem3D system) {
		// See http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToEuler/index.htm
		// Standard used: XZY_RIGHT_HAND
		Quaternion q = new Quaternion(this);
		system.toSystem(q, CoordinateSystem3D.XZY_RIGHT_HAND);

		double sqw = q.w * q.w;
		double sqx = q.x * q.x;
		double sqy = q.y * q.y;
		double sqz = q.z * q.z;
		double unit = sqx + sqy + sqz + sqw; // if normalised is one, otherwise is correction factor
		double test = q.x * q.y + q.z * q.w;

		if (MathUtil.compareEpsilon(test, .5 * unit) >= 0) { // singularity at north pole
			return new EulerAngles(
					2. * Math.atan2(q.x, q.w), // heading
					MathConstants.DEMI_PI, // attitude
					0.,
					system);
		}
		if (MathUtil.compareEpsilon(test, -.5 * unit) <= 0) { // singularity at south pole
			return new EulerAngles(
					-2. * Math.atan2(q.x, q.w), // heading
					-MathConstants.DEMI_PI, // attitude
					0.,
					system);
		}
		return new EulerAngles(
				Math.atan2(2. * q.y * q.w - 2. * q.x * q.z, sqx - sqy - sqz + sqw),
				Math.asin(2. * test / unit),
				Math.atan2(2. * q.x * q.w - 2. * q.y * q.z, -sqx + sqy - sqz + sqw),
				system);
	}

	/** A representation of Euler Angles.
	 * The term "Euler Angle" is used for any representation of 3 dimensional 
	 * rotations where the rotation is decomposed into 3 separate angles. 
	 * <p>
	 * There is no single set of conventions and standards in this area, 
	 * therefore the following conventions was choosen:<ul>
	 * <li>angle applied first:	heading;</li>
	 * <li>angle applied second: attitude;</li>
	 * <li>angle applied last: bank</li>
	 * </ul>
	 * <p>
	 * Examples: NASA aircraft standard and telescope standard
	 * <img src="doc-files/euler_plane.gif" alt="[NASA Aircraft Standard]">
	 * <img src="doc-files/euler_telescop.gif" alt="[Telescope Standard]">
	 * 
	 * <strong>For creating an instance of this class, you must invoke
	 * {@link Quaternion#getEulerAngles(CoordinateSystem3D)}. 
	 * 
	 * @author $Author: sgalland$
	 * @version $Name$ $Revision$ $Date$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	public static final class EulerAngles implements Cloneable, Serializable {

		private static final long serialVersionUID = -1532832128836084395L;

		private final double attitude;
		private final double bank;
		private final double heading;
		private final CoordinateSystem3D system;

		private EulerAngles(double attitude1, double bank1, double heading1, CoordinateSystem3D system1) {
			this.attitude = attitude1;
			this.bank = bank1;
			this.heading = heading1;
			this.system = system1;
		}

		/** Replies the attitude, the rotation around left vector.
		 *
		 * @return the attitude angle.
		 */
		@Pure
		public double getAttitude() {
			return this.attitude;
		}

		/** Replies the bank, the rotation around front vector.
		 *
		 * @return the bank angle.
		 */
		@Pure
		public double getBank() {
			return this.bank;
		}

		/** Replies the heading, the rotation around top vector.
		 *
		 * @return the heading angle.
		 */
		@Pure
		public double getHeading() {
			return this.heading;
		}

		/** Replies coordinate system used for obtaining the euler angles.
		 *
		 * @return the coordinate system.
		 */
		@Pure
		private CoordinateSystem3D getSystem() {
			return this.system;
		}

	}

	/** A representation of axis-angle.
	 * The axis–angle representation of a rotation parameterizes a rotation in a three-dimensional
	 * Euclidean space by two values: a unit vector, indicating the direction of an axis of rotation, and
	 * an angle describing the magnitude of the rotation about the axis.
	 * The rotation occurs in the sense prescribed by the (left/right)-hand rule.
	 * <img src="doc-files/axis_angle.png" alt="[Axis-Angle Representation]">
	 * 
	 * <strong>For creating an instance of this class, you must invoke
	 * {@link Quaternion#getAxisAngle()}. 
	 * 
	 * @author $Author: sgalland$
	 * @version $Name$ $Revision$ $Date$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	public static final class AxisAngle implements Cloneable, Serializable {

		private static final long serialVersionUID = -7228694369177792159L;
		
		private final double x;
		private final double y;
		private final double z;
		private final double angle;

		private AxisAngle(double x1, double y1, double z1, double angle1) {
			this.x = x1;
			this.y = y1;
			this.z = z1;
			this.angle = angle1;
		}

		/** Replies the rotation axis.
		 *
		 * @return the rotation axis.
		 */
		@Pure
		public Vector3f getAxis() {
			return new Vector3f(this.x, this.y, this.z);
		}

		/** Replies the rotation angle.
		 *
		 * @return the rotation angle.
		 */
		@Pure
		public double getAngle() {
			return this.angle;
		}

	}

}