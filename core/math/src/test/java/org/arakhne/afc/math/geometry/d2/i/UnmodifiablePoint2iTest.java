/**
 * 
 * fr.utbm.v3g.core.math.Tuple2dTest.java
 *
 * Copyright (c) 2008-10, Multiagent Team - Systems and Transportation Laboratory (SeT)
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of the Systems and Transportation Laboratory ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with the SeT.
 * 
 * http://www.multiagent.fr/
 *
 * Primary author : Olivier LAMOTTE (olivier.lamotte@utbm.fr) - 2015
 *
 */
package org.arakhne.afc.math.geometry.d2.i;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.arakhne.afc.math.geometry.d2.AbstractUnmodifiablePoint2DTest;
import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Shape2D;
import org.arakhne.afc.math.geometry.d2.Vector2D;

@SuppressWarnings("all")
public class UnmodifiablePoint2iTest extends AbstractUnmodifiablePoint2DTest<Point2i, Vector2i> {

	@Override
	public boolean isIntCoordinates() {
		return true;
	}
	
	@Override
	public Point2D createTuple(double x, double y) {
		return new Point2i(x, y).toUnmodifiable();
	}
	
	@Override
	public Vector2i createVector(double x, double y) {
		return new Vector2i(x, y);
	}

	@Override
	public Point2i createPoint(double x, double y) {
		return new Point2i(x, y);
	}

	@Override
	public void operator_andShape2D() {
		Shape2D shape = new Circle2i(5, 8, 5);
		assertFalse(createPoint(0,0).operator_and(shape));
		assertFalse(createPoint(11,10).operator_and(shape));
		assertFalse(createPoint(11,50).operator_and(shape));
		assertFalse(createPoint(9,12).operator_and(shape));
		assertTrue(createPoint(9,11).operator_and(shape));
		assertTrue(createPoint(8,12).operator_and(shape));
		assertTrue(createPoint(3,7).operator_and(shape));
		assertFalse(createPoint(10,11).operator_and(shape));
		assertTrue(createPoint(9,10).operator_and(shape));
	}
	
	@Override
	public void operator_upToShape2D() {
		Shape2D shape = new Circle2i(5, 8, 5);
		assertEpsilonEquals(0f, createPoint(5,8).operator_upTo(shape));
		assertEpsilonEquals(0f, createPoint(10,10).operator_upTo(shape));
		assertEpsilonEquals(0f, createPoint(4,8).operator_upTo(shape));
		assertEpsilonEquals(4.242640687f, createPoint(0,0).operator_upTo(shape));
		assertEpsilonEquals(1f, createPoint(5,14).operator_upTo(shape));
	}

}
