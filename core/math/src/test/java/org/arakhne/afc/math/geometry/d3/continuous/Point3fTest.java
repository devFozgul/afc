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

import static org.junit.Assert.*;

import org.arakhne.afc.math.AbstractMathTestCase;
import org.arakhne.afc.math.geometry.d3.Point3D;
import org.arakhne.afc.math.geometry.d3.Tuple3D;
import org.arakhne.afc.math.geometry.d3.Vector3D;
import org.junit.Test;

/**
 * @author $Author: galland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("all")
public class Point3fTest extends AbstractMathTestCase {

	
	@Test
	// First try to program a test, from hjaffali 
	public void testClone() {
		Point3f point = new Point3f(1,2,3);
		Point3f pointClone = point.clone();
		
		assertTrue(point.equals(pointClone));
	}

	@Test
	public void distanceSquaredPoint3D() {
		int tab[] = {1,2,3};
		Point3f point = new Point3f(1.0,2.0,3.0);
		Point3f point2 = new Point3f(tab);
		Point3f point3 = new Point3f(0,0,0);
		Point3f point4 = new Point3f(-1,0,1);
		
		assertEpsilonEquals(14,point.distanceSquared(point3));
		assertEpsilonEquals(0,point.distanceSquared(point2));
		assertEpsilonEquals(0,point2.distanceSquared(point));
		assertEpsilonEquals(12,point.distanceSquared(point4));		
	}

	@Test
	// FIXME: From where we get the distance ? With an UnmodifiablePoint3f ?
	public void getDistanceSquaredPoint3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	public void distancePoint3D() {
		int tab[] = {1,2,3};
		Point3f point = new Point3f(1.0,2.0,3.0);
		Point3f point2 = new Point3f(tab);
		Point3f point3 = new Point3f(0,0,0);
		Point3f point4 = new Point3f(-1,0,1);
		
		assertEpsilonEquals(3,point.distance(point3));
		assertEpsilonEquals(0,point.distance(point2));
		assertEpsilonEquals(0,point2.distance(point));
		assertEpsilonEquals(3,point.distance(point4));
	}

	@Test
	// SAME
	public void getDistancePoint3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	public void distanceL1Point3D() {
		int tab[] = {1,2,3};
		Point3f point = new Point3f(1.0,2.0,3.0);
		Point3f point2 = new Point3f(tab);
		Point3f point3 = new Point3f(0,0,0);
		Point3f point4 = new Point3f(-1,0,1);
		
		assertEpsilonEquals(6,point.distanceL1(point3));
		assertEpsilonEquals(0,point.distanceL1(point2));
		assertEpsilonEquals(0,point2.distanceL1(point));
		assertEpsilonEquals(6,point.distanceL1(point4));
	}

	@Test
	// SAME
	public void getDistanceL1Point3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	public void distanceLinfPoint3D() {
		int tab[] = {1,2,3};
		Point3f point = new Point3f(1.0,2.0,3.0);
		Point3f point2 = new Point3f(tab);
		Point3f point3 = new Point3f(0,0,0);
		Point3f point4 = new Point3f(-1,0,1);
		
		assertEpsilonEquals(3,point.distanceLinf(point3));
		assertEpsilonEquals(0,point.distanceLinf(point2));
		assertEpsilonEquals(0,point2.distanceLinf(point));
		assertEpsilonEquals(2,point.distanceLinf(point4));
	}

	@Test
	// SAME
	public void getDistanceLinfPoint3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	public void addPoint3DVector3D() {
		Point3f point = new Point3f(0,0,0);
		Point3f point2 = new Point3f(-1,0,1);
		Vector3f vector = new Vector3f(1.2,1.2,1.2);
		Vector3f vector2 = new Vector3f(2.0,1.5,5.125);
		Point3f newPoint = new Point3f(0.0,0.0,0.0);
		
		newPoint.add(point,vector);
		assertTrue(newPoint.equals(new Point3f(1.2,1.2,1.2)));
		
		newPoint.add(point2,vector2);
		assertTrue(newPoint.equals(new Point3f(1.0,1.5,6.125)));  	
	}

	@Test
	public void addVector3DPoint3D() {
		Point3f point = new Point3f(0,0,0);
		Point3f point2 = new Point3f(-1,0,1);
		Vector3f vector = new Vector3f(1.2,1.2,1.2);
		Vector3f vector2 = new Vector3f(2.0,1.5,5.125);
		Point3f newPoint = new Point3f(0.0,0.0,0.0);
		
		newPoint.add(vector,point);
		assertTrue(newPoint.equals(new Point3f(1.2,1.2,1.2)));
		
		newPoint.add(vector2,point2);
		assertTrue(newPoint.equals(new Point3f(1.0,1.5,6.125))); 
	}

	@Test
	public void addVector3D() {
		Point3f point = new Point3f(0,0,0);
		Point3f point2 = new Point3f(-1,0,1);
		Vector3f vector = new Vector3f(1.2,1.2,1.2);
		Vector3f vector2 = new Vector3f(2.0,1.5,5.125);
		Point3f newPoint = new Point3f(0.0,0.0,0.0);
		
		point.add(vector);
		assertTrue(point.equals(new Point3f(1.2,1.2,1.2)));
		
		point2.add(vector2);
		assertTrue(point2.equals(new Point3f(1.0,1.5,6.125))); 
	}

	@Test
	public void scaleAddIntVector3DPoint3D() {
		Point3f point = new Point3f(-1,0,1);
		Vector3f vector = new Vector3f(1.0,1.2,1.0);
		Point3f newPoint = new Point3f(0.0,0.0,0.0);
		
		newPoint.scaleAdd(0,vector,point);
		assertTrue(newPoint.equals(new Point3f(-1,0,1)));
		
		newPoint.scaleAdd(1,vector,point);
		assertTrue(newPoint.equals(new Point3f(0.0,1.2,2.0)));
		
		newPoint.scaleAdd(-1,vector,point);
		assertTrue(newPoint.equals(new Point3f(-2.0,-1.2,0.0)));
		
		newPoint.scaleAdd(10,vector,point);
		assertTrue(newPoint.equals(new Point3f(9,12,11)));
	}

	@Test
	public void scaleAddDoubleVector3DPoint3D() {
		Point3f point = new Point3f(1,0,1);
		Vector3f vector = new Vector3f(-1,1,1);
		Point3f newPoint = new Point3f(0.0,0.0,0.0);
		
		newPoint.scaleAdd(0.0,vector,point);
		assertTrue(newPoint.equals(new Point3f(1,0,1)));
		
		newPoint.scaleAdd(1.5,vector,point);
		assertTrue(newPoint.equals(new Point3f(-0.5,1.5,2.5)));
		
		newPoint.scaleAdd(-1.5,vector,point);
		assertTrue(newPoint.equals(new Point3f(2.5,-1.5,-0.5)));
		
		newPoint.scaleAdd(0.1,vector,point);
		assertTrue(newPoint.equals(new Point3f(0.9,0.1,1.1)));
	}

	@Test
	public void scaleAddIntPoint3DVector3D() {
		Point3f point = new Point3f(1.0,1.2,1.0);
		Vector3f vector = new Vector3f(-1,0,1);
		Point3f newPoint = new Point3f(0.0,0.0,0.0);
		
		newPoint.scaleAdd(0,point,vector);
		assertTrue(newPoint.equals(new Point3f(-1,0,1)));
		
		newPoint.scaleAdd(1,point,vector);
		assertTrue(newPoint.equals(new Point3f(0.0,1.2,2.0)));
		
		newPoint.scaleAdd(-1,point,vector);
		assertTrue(newPoint.equals(new Point3f(-2.0,-1.2,0.0)));
		
		newPoint.scaleAdd(10,point,vector);
		assertTrue(newPoint.equals(new Point3f(9,12,11)));
	}

	@Test
	public void scaleAddDoublePoint3DVector3D() {
		Point3f point = new Point3f(-1,1,1);
		Vector3f vector = new Vector3f(1,0,1);
		Point3f newPoint = new Point3f(0.0,0.0,0.0);
		
		newPoint.scaleAdd(0.0,point,vector);
		assertTrue(newPoint.equals(new Point3f(1,0,1)));
		
		newPoint.scaleAdd(1.5,point,vector);
		assertTrue(newPoint.equals(new Point3f(-0.5,1.5,2.5)));
		
		newPoint.scaleAdd(-1.5,point,vector);
		assertTrue(newPoint.equals(new Point3f(2.5,-1.5,-0.5)));
		
		newPoint.scaleAdd(0.1,point,vector);
		assertTrue(newPoint.equals(new Point3f(0.9,0.1,1.1)));
	}

	@Test
	public void scaleAddIntVector3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	public void scaleAddDoubleVector3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	public void subPoint3DVector3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	public void subVector3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	public void isCollinearPointsDoubleDoubleDoubleDoubleDoubleDoubleDoubleDoubleDouble() {
		throw new UnsupportedOperationException();
	}

	@Test
	public void distancePointPoint() {
		throw new UnsupportedOperationException();
	}

	@Test
	public void distanceSquaredPointPoint() {
		throw new UnsupportedOperationException();
	}

	@Test
	public void distanceL1PointPoint() {
		throw new UnsupportedOperationException();
	}

	@Test
	public void distanceLinfPointPoint() {
		throw new UnsupportedOperationException();
	}

}