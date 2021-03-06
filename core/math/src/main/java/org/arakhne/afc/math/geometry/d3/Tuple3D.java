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

package org.arakhne.afc.math.geometry.d3;

import java.io.Serializable;

import org.eclipse.xtext.xbase.lib.Pure;

/** 3D tuple.
 *
 * @param <TT> is the type of data that can be added or substracted to this tuple.
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("checkstyle:methodcount")
public interface Tuple3D<TT extends Tuple3D<? super TT>> extends Cloneable, Serializable {

	/** Clone this point.
	 *
	 * @return the clone.
	 */
	@Pure
	TT clone();

	/**
	 *  Sets each component of this tuple to its absolute value.
	 */
	void absolute();

	/**
	 *  Sets each component of the tuple parameter to its absolute
	 *  value and places the modified values into this tuple.
	 *  @param t   the source tuple, which will not be modified
	 */
	void absolute(TT t);

	/**
	 * Sets the value of this tuple to the sum of itself and x and y.
	 * @param x x coordinate to add.
	 * @param y y coordinate to add.
	 * @param z z coordinate to add.
	 */
	void add(int x, int y, int z);

	/**
	 * Sets the value of this tuple to the sum of itself and x and y.
	 * @param x x coordinate to add.
	 * @param y y coordinate to add.
	 * @param z z coordinate to add.
	 */
	void add(double x, double y, double z);

	/**
	 * Sets the x value of this tuple to the sum of itself and x.
	 * @param x x coordinate to add.
	 */
	void addX(int x);

	/**
	 * Sets the x value of this tuple to the sum of itself and x.
	 * @param x x coordinate to add.
	 */
	void addX(double x);

	/**
	 * Sets the y value of this tuple to the sum of itself and y.
	 * @param y y coordinate to add.
	 */
	void addY(int y);

	/**
	 * Sets the y value of this tuple to the sum of itself and y.
	 * @param y y coordinate to add.
	 */
	void addY(double y);

	/**
	 * Sets the z value of this tuple to the sum of itself and z.
	 * @param z z coordinate to add.
	 */
	void addZ(int z);

	/**
	 * Sets the z value of this tuple to the sum of itself and z.
	 * @param z z coordinate to add.
	 */
	void addZ(double z);

	/**
	 *  Clamps this tuple to the range [low, high].
	 *  @param min  the lowest value in this tuple after clamping
	 *  @param max  the highest value in this tuple after clamping
	 */
	void clamp(int min, int max);

	/**
	 *  Clamps this tuple to the range [low, high].
	 *  @param min  the lowest value in this tuple after clamping
	 *  @param max  the highest value in this tuple after clamping
	 */
	void clamp(double min, double max);

	/**
	 *  Clamps the tuple parameter to the range [low, high] and
	 *  places the values into this tuple.
	 *  @param min   the lowest value in the tuple after clamping
	 *  @param max  the highest value in the tuple after clamping
	 *  @param t   the source tuple, which will not be modified
	 */
	void clamp(int min, int max, TT t);

	/**
	 *  Clamps the tuple parameter to the range [low, high] and
	 *  places the values into this tuple.
	 *  @param min   the lowest value in the tuple after clamping
	 *  @param max  the highest value in the tuple after clamping
	 *  @param t   the source tuple, which will not be modified
	 */
	void clamp(double min, double max, TT t);

	/**
	 *  Clamps the minimum value of this tuple to the min parameter.
	 *  @param min   the lowest value in this tuple after clamping
	 */
	void clampMin(int min);

	/**
	 *  Clamps the minimum value of this tuple to the min parameter.
	 *  @param min   the lowest value in this tuple after clamping
	 */
	void clampMin(double min);

	/**
	 *  Clamps the minimum value of the tuple parameter to the min
	 *  parameter and places the values into this tuple.
	 *  @param min   the lowest value in the tuple after clamping
	 *  @param t   the source tuple, which will not be modified
	 */
	void clampMin(int min, TT t);

	/**
	 *  Clamps the minimum value of the tuple parameter to the min
	 *  parameter and places the values into this tuple.
	 *  @param min   the lowest value in the tuple after clamping
	 *  @param t   the source tuple, which will not be modified
	 */
	void clampMin(double min, TT t);

	/**
	 *  Clamps the maximum value of this tuple to the max parameter.
	 *  @param max   the highest value in the tuple after clamping
	 */
	void clampMax(int max);

	/**
	 *  Clamps the maximum value of this tuple to the max parameter.
	 *  @param max   the highest value in the tuple after clamping
	 */
	void clampMax(double max);

	/**
	 *  Clamps the maximum value of the tuple parameter to the max
	 *  parameter and places the values into this tuple.
	 *  @param max   the highest value in the tuple after clamping
	 *  @param t   the source tuple, which will not be modified
	 */
	void clampMax(int max, TT t);

	/**
	 *  Clamps the maximum value of the tuple parameter to the max
	 *  parameter and places the values into this tuple.
	 *  @param max   the highest value in the tuple after clamping
	 *  @param t   the source tuple, which will not be modified
	 */
	void clampMax(double max, TT t);

	/**
	 * Copies the values of this tuple into the tuple t.
	 * @param t is the target tuple
	 */
	void get(TT t);

	/**
	 *  Copies the value of the elements of this tuple into the array t.
	 *  @param t the array that will contain the values of the vector
	 */
	void get(int[] t);

	/**
	 *  Copies the value of the elements of this tuple into the array t.
	 *  @param t the array that will contain the values of the vector
	 */
	void get(double[] t);

	/**
	 * Sets the value of this tuple to the negation of tuple t1.
	 * @param t1 the source tuple
	 */
	void negate(TT t1);

	/**
	 * Negates the value of this tuple in place.
	 */
	void negate();

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of tuple t1.
	 * @param scale the scalar value
	 * @param t1 the source tuple
	 */
	void scale(int scale, TT t1);

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of tuple t1.
	 * @param scale the scalar value
	 * @param t1 the source tuple
	 */
	void scale(double scale, TT t1);

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of the scale factor with this.
	 * @param scale the scalar value
	 */
	void scale(int scale);

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of the scale factor with this.
	 * @param scale the scalar value
	 */
	void scale(double scale);

	/**
	 * Sets the value of this tuple to the value of tuple t1.
	 * @param t1 the tuple to be copied
	 */
	void set(Tuple3D<?> t1);

	/**
	 * Sets the value of this tuple to the specified x and y
	 * coordinates.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 */
	void set(int x, int y, int z);

	/**
	 * Sets the value of this tuple to the specified x and y
	 * coordinates.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 */
	void set(double x, double y, double z);

	/**
	 * Sets the value of this tuple from the 2 values specified in
	 * the array.
	 * @param t the array of length 2 containing xy in order
	 */
	void set(int[] t);

	/**
	 * Sets the value of this tuple from the 2 values specified in
	 * the array.
	 * @param t the array of length 2 containing xy in order
	 */
	void set(double[] t);

	/**
	 * Get the <i>x</i> coordinate.
	 *
	 * @return the x coordinate.
	 */
	@Pure
	double getX();

	/**
	 * Get the <i>x</i> coordinate.
	 *
	 * @return the x coordinate.
	 */
	@Pure
	int ix();

	/**
	 * Set the <i>x</i> coordinate.
	 *
	 * @param x  value to <i>x</i> coordinate.
	 */
	void setX(int x);

	/**
	 * Set the <i>x</i> coordinate.
	 *
	 * @param x  value to <i>x</i> coordinate.
	 */
	void setX(double x);

	/**
	 * Get the <i>y</i> coordinate.
	 *
	 * @return  the <i>y</i> coordinate.
	 */
	@Pure
	double getY();

	/**
	 * Get the <i>y</i> coordinate.
	 *
	 * @return  the <i>y</i> coordinate.
	 */
	@Pure
	int iy();

	/**
	 * Set the <i>y</i> coordinate.
	 *
	 * @param y value to <i>y</i> coordinate.
	 */
	void setY(int y);

	/**
	 * Set the <i>y</i> coordinate.
	 *
	 * @param y value to <i>y</i> coordinate.
	 */
	void setY(double y);

	/**
	 * Get the <i>z</i> coordinate.
	 *
	 * @return  the <i>z</i> coordinate.
	 */
	@Pure
	double getZ();

	/**
	 * Get the <i>z</i> coordinate.
	 *
	 * @return  the <i>z</i> coordinate.
	 */
	@Pure
	int iz();

	/**
	 * Set the <i>z</i> coordinate.
	 *
	 * @param z value to <i>z</i> coordinate.
	 */
	void setZ(int z);

	/**
	 * Set the <i>z</i> coordinate.
	 *
	 * @param z value to <i>z</i> coordinate.
	 */
	void setZ(double z);

	/**
	 * Sets the value of this tuple to the difference of itself and x, y and z.
	 * @param x x coordinate to substract.
	 * @param y y coordinate to substract.
	 * @param z y coordinate to substract.
	 */
	void sub(int x, int y, int z);

	/**
	 * Sets the value of this tuple to the difference of itself and x, y and z.
	 * @param x x coordinate to substract.
	 * @param y y coordinate to substract.
	 * @param z y coordinate to substract.
	 */
	void sub(double x, double y, double z);

	/**
	 * Sets the x value of this tuple to the difference of itself and x.
	 * @param x x coordinate to substract.
	 */
	void subX(int x);

	/**
	 * Sets the x value of this tuple to the difference of itself and x.
	 * @param x x coordinate to substract.
	 */
	void subX(double x);

	/**
	 * Sets the y value of this tuple to the difference of itself and y.
	 * @param y y coordinate to substract.
	 */
	void subY(int y);

	/**
	 * Sets the y value of this tuple to the difference of itself and y.
	 * @param y y coordinate to substract.
	 */
	void subY(double y);

	/**
	 * Sets the z value of this tuple to the difference of itself and z.
	 * @param z y coordinate to substract.
	 */
	void subZ(int z);

	/**
	 * Sets the z value of this tuple to the difference of itself and z.
	 * @param z y coordinate to substract.
	 */
	void subZ(double z);

	/**
	 *  Linearly interpolates between tuples t1 and t2 and places the
	 *  result into this tuple:  this = (1-alpha)*t1 + alpha*t2.
	 *  @param t1  the first tuple
	 *  @param t2  the second tuple
	 *  @param alpha  the alpha interpolation parameter
	 */
	void interpolate(TT t1, TT t2, double alpha);

	/**
	 *  Linearly interpolates between this tuple and tuple t1 and
	 *  places the result into this tuple:  this = (1-alpha)*this + alpha*t1.
	 *  @param t1  the first tuple
	 *  @param alpha  the alpha interpolation parameter
	 */
	void interpolate(TT t1, double alpha);

	/**
	 * Returns true if all of the data members of Tuple2f t1 are
	 * equal to the corresponding data members in this Tuple2f.
	 * @param t1  the vector with which the comparison is made
	 * @return  true or false
	 */
	@Pure
	boolean equals(Tuple3D<?> t1);

	/**
	 * Returns true if the Object t1 is of type Tuple2f and all of the
	 * data members of t1 are equal to the corresponding data members in
	 * this Tuple2f.
	 * @param t1  the object with which the comparison is made
	 * @return  true or false
	 */
	@Pure
	@Override
	boolean equals(Object t1);

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
	boolean epsilonEquals(TT t1, double epsilon);

	/**
	 * Returns a hash code value based on the data values in this
	 * object.  Two different Tuple2f objects with identical data values
	 * (i.e., Tuple2f.equals returns true) will return the same hash
	 * code value.  Two objects with different data members may return the
	 * same hash value, although this is not likely.
	 * @return the integer hash code value
	 */
	@Pure
	@Override
	int hashCode();

}
