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
package org.arakhne.afc.math.geometry.d2;

@SuppressWarnings("all")
public class ImmutablePoint2DTest extends AbstractUnmodifiablePoint2DTest {
	
	@Override
	protected Vector2D createVector(final double tx, final double ty) {
		return new Vector2DStub(tx,  ty);
	}

	@Override
	protected Point2D createPoint(final double tx, final double ty) {
		return new ImmutablePoint2D(tx, ty);
	}

}