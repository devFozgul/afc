/* 
 * $Id$
 * 
 * Copyright (c) 2006-10, Multiagent Team, Laboratoire Systemes et Transports, Universite de Technologie de Belfort-Montbeliard.
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
package org.arakhne.afc.math.matrix;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.arakhne.afc.math.AbstractMathTestCase;
import org.junit.Test;

/**
 * @author $Author: hjaffali$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings({"nls","static-method"})
public class Matrix4fTest extends AbstractMathTestCase{
	
	
	
	@Test
	public void toStringTest() {
		Matrix4f matrix = new Matrix4f(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
		String s = "0.0, 1.0, 2.0, 3.0\n4.0, 5.0, 6.0, 7.0\n8.0, 9.0, 10.0, 11.0\n12.0, 13.0, 14.0, 15.0\n";
				
		assertTrue(s.equals(matrix.toString()));
	}
	
	@Test
	public void setIdentity() {
		Matrix4f matrix = this.randomMatrix4f();
		matrix.setIdentity();
		
		assertTrue((new Matrix4f(1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1)).equals(matrix));
	}
	
	@Test
	public void addDouble() {
		Matrix4f m1 = this.randomMatrix4f();
		Matrix4f m2 = m1.clone();
		double s = this.random.nextDouble();
		
		m2.set(m2.m00+s, m2.m01+s, m2.m02+s, m2.m03+s, m2.m10+s, m2.m11+s, m2.m12+s, m2.m13+s, m2.m20+s, m2.m21+s, m2.m22+s, m2.m23+s, m2.m30+s, m2.m31+s, m2.m32+s, m2.m33+s);
		m1.add(s);
		
		assertTrue(m2.equals(m1));
	}
	
	@Test
	public void addDoubleMatrix4D() {
		Matrix4f m1 = this.randomMatrix4f();
		Matrix4f m2 = m1.clone();
		double s = this.random.nextDouble();
		
		m2.set(m2.m00+s, m2.m01+s, m2.m02+s, m2.m03+s, m2.m10+s, m2.m11+s, m2.m12+s, m2.m13+s, m2.m20+s, m2.m21+s, m2.m22+s, m2.m23+s, m2.m30+s, m2.m31+s, m2.m32+s, m2.m33+s);
		m1.add(s,m1);
		
		assertTrue(m2.equals(m1));
	}
	
	@Test
	public void addMatrix4DMatrix4D() {
		Matrix4f m1 = this.randomMatrix4f();
		Matrix4f m2 = this.randomMatrix4f();
		Matrix4f m3 = m2.clone();
		
		m2.set(m2.m00+m1.m00, m2.m01+m1.m01, m2.m02+m1.m02, m2.m03+m1.m03, m2.m10+m1.m10, m2.m11+m1.m11, m2.m12+m1.m12, m2.m13+m1.m13, m2.m20+m1.m20, m2.m21+m1.m21, m2.m22+m1.m22, m2.m23+m1.m23, m2.m30+m1.m30, m2.m31+m1.m31, m2.m32+m1.m32, m2.m33+m1.m33);
		
		m3.add(m1,m3);
		
		assertTrue(m2.equals(m3));
	}
	
	@Test
	public void addMatrix4D() {
		Matrix4f m1 = this.randomMatrix4f();
		Matrix4f m2 = this.randomMatrix4f();
		Matrix4f m3 = m2.clone();
		
		m2.set(m2.m00+m1.m00, m2.m01+m1.m01, m2.m02+m1.m02, m2.m03+m1.m03, m2.m10+m1.m10, m2.m11+m1.m11, m2.m12+m1.m12, m2.m13+m1.m13, m2.m20+m1.m20, m2.m21+m1.m21, m2.m22+m1.m22, m2.m23+m1.m23, m2.m30+m1.m30, m2.m31+m1.m31, m2.m32+m1.m32, m2.m33+m1.m33);
		
		m3.add(m1);
		
		assertTrue(m2.equals(m3));
	}
	
	@Test
	public void subMatrix4DMatrix4D() {
		Matrix4f m1 = this.randomMatrix4f();
		Matrix4f m2 = this.randomMatrix4f();
		Matrix4f m3 = m2.clone();
		
		m2.set(m2.m00-m1.m00, m2.m01-m1.m01, m2.m02-m1.m02, m2.m03-m1.m03, m2.m10-m1.m10, m2.m11-m1.m11, m2.m12-m1.m12, m2.m13-m1.m13, m2.m20-m1.m20, m2.m21-m1.m21, m2.m22-m1.m22, m2.m23-m1.m23, m2.m30-m1.m30, m2.m31-m1.m31, m2.m32-m1.m32, m2.m33-m1.m33);
		
		m3.sub(m3,m1);
		
		assertTrue(m2.equals(m3));
	}
	
	@Test
	public void subMatrix4D() {
		Matrix4f m1 = this.randomMatrix4f();
		Matrix4f m2 = this.randomMatrix4f();
		Matrix4f m3 = m2.clone();
		
		m2.set(m2.m00-m1.m00, m2.m01-m1.m01, m2.m02-m1.m02, m2.m03-m1.m03, m2.m10-m1.m10, m2.m11-m1.m11, m2.m12-m1.m12, m2.m13-m1.m13, m2.m20-m1.m20, m2.m21-m1.m21, m2.m22-m1.m22, m2.m23-m1.m23, m2.m30-m1.m30, m2.m31-m1.m31, m2.m32-m1.m32, m2.m33-m1.m33);
		
		m3.sub(m1);
		
		assertTrue(m2.equals(m3));
	}
	
	@Test
	public void transpose() {
		Matrix4f m1 = this.randomMatrix4f();
		Matrix4f transpose = new Matrix4f();
		
		double [] v = new double[4];
		
		m1.getRow(0, v);
		transpose.setColumn(0, v);
		
		m1.getRow(1, v);
		transpose.setColumn(1, v);
		
		m1.getRow(2, v);
		transpose.setColumn(2, v);
		
		m1.getRow(3, v);
		transpose.setColumn(3, v);
		
		m1.transpose();
		
		assertTrue(transpose.equals(m1));
	}
	
	@Test
	public void transposeMatrix4D() {
		Matrix4f m1 = this.randomMatrix4f();
		Matrix4f transpose = new Matrix4f();
		
		double [] v = new double[4];
		
		m1.getRow(0, v);
		transpose.setColumn(0, v);
		
		m1.getRow(1, v);
		transpose.setColumn(1, v);
		
		m1.getRow(2, v);
		transpose.setColumn(2, v);
		
		m1.getRow(3, v);
		transpose.setColumn(3, v);
		
		m1.transpose(m1);
		
		assertTrue(transpose.equals(m1));
	}
	
	@Test
	public void determinant() {
		double a = this.random.nextDouble()*50;
		double b = this.random.nextDouble()*50;
		double c = this.random.nextDouble()*50;
		double d = this.random.nextDouble()*50;
		double e = this.random.nextDouble()*50;
		double f = this.random.nextDouble()*50;
		double g = this.random.nextDouble()*50;
		double h = this.random.nextDouble()*50;
		double i = this.random.nextDouble()*50;
		double j = this.random.nextDouble()*50;
		double k = this.random.nextDouble()*50;
		double l = this.random.nextDouble()*50;
		double m = this.random.nextDouble()*50;
		double n = this.random.nextDouble()*50;
		double o = this.random.nextDouble()*50;
		double p = this.random.nextDouble()*50;
		Matrix4f m1 = new Matrix4f(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p);
		
		
		double determinant = a *(l* (g* n-f* o)+k *(f* p-h* n)+j *(h* o-g* p))+i *(b *(g *p-h *o)+c *(h* n-f* p)+d *(f* o-g *n))+b *(l *(e *o-g* m)+k* (h* m-e* p))+j *(c* e *p-c *h *m+d *(g* m-e *o))+l *(c *f* m-c* e* n)+d* k* (e *n-f* m);
	
		assertEpsilonEquals(determinant,m1.determinant());
	}
	
	@Test
	public void mulDouble() {
		double a = this.random.nextDouble()*50;
		double b = this.random.nextDouble()*50;
		double c = this.random.nextDouble()*50;
		double d = this.random.nextDouble()*50;
		double e = this.random.nextDouble()*50;
		double f = this.random.nextDouble()*50;
		double g = this.random.nextDouble()*50;
		double h = this.random.nextDouble()*50;
		double i = this.random.nextDouble()*50;
		double j = this.random.nextDouble()*50;
		double k = this.random.nextDouble()*50;
		double l = this.random.nextDouble()*50;
		double m = this.random.nextDouble()*50;
		double n = this.random.nextDouble()*50;
		double o = this.random.nextDouble()*50;
		double p = this.random.nextDouble()*50;
		Matrix4f m1 = new Matrix4f(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p);
		
		double s = this.random.nextDouble();
		Matrix4f m2 = new Matrix4f(a*s,b*s,c*s,d*s,e*s,f*s,g*s,h*s,i*s,j*s,k*s,l*s,m*s,n*s,o*s,p*s);
		
		m1.mul(s);
		assertTrue(m1.equals(m2));
	}
	
	@Test
	public void mulDoubleMatrix4D() {
		double a = this.random.nextDouble()*50;
		double b = this.random.nextDouble()*50;
		double c = this.random.nextDouble()*50;
		double d = this.random.nextDouble()*50;
		double e = this.random.nextDouble()*50;
		double f = this.random.nextDouble()*50;
		double g = this.random.nextDouble()*50;
		double h = this.random.nextDouble()*50;
		double i = this.random.nextDouble()*50;
		double j = this.random.nextDouble()*50;
		double k = this.random.nextDouble()*50;
		double l = this.random.nextDouble()*50;
		double m = this.random.nextDouble()*50;
		double n = this.random.nextDouble()*50;
		double o = this.random.nextDouble()*50;
		double p = this.random.nextDouble()*50;
		Matrix4f m1 = new Matrix4f(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p);
		
		double s = this.random.nextDouble();
		Matrix4f m2 = new Matrix4f(a*s,b*s,c*s,d*s,e*s,f*s,g*s,h*s,i*s,j*s,k*s,l*s,m*s,n*s,o*s,p*s);
		
		m1.mul(s,m1);
		assertTrue(m1.equals(m2));
	}
	
	@Test
	public void mulMatrix4D() {
		double a = this.random.nextDouble()*50;
		double b = this.random.nextDouble()*50;
		double c = this.random.nextDouble()*50;
		double d = this.random.nextDouble()*50;
		double e = this.random.nextDouble()*50;
		double f = this.random.nextDouble()*50;
		double g = this.random.nextDouble()*50;
		double h = this.random.nextDouble()*50;
		double i = this.random.nextDouble()*50;
		double j = this.random.nextDouble()*50;
		double k = this.random.nextDouble()*50;
		double l = this.random.nextDouble()*50;
		double m = this.random.nextDouble()*50;
		double n = this.random.nextDouble()*50;
		double o = this.random.nextDouble()*50;
		double p = this.random.nextDouble()*50;
		Matrix4f matrix1 = new Matrix4f(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p);
		
		double a1 = this.random.nextDouble()*50;
		double b1 = this.random.nextDouble()*50;
		double c1 = this.random.nextDouble()*50;
		double d1 = this.random.nextDouble()*50;
		double e1 = this.random.nextDouble()*50;
		double f1 = this.random.nextDouble()*50;
		double g1 = this.random.nextDouble()*50;
		double h1 = this.random.nextDouble()*50;
		double i1 = this.random.nextDouble()*50;
		double j1 = this.random.nextDouble()*50;
		double k1 = this.random.nextDouble()*50;
		double l1 = this.random.nextDouble()*50;
		double m1 = this.random.nextDouble()*50;
		double n1 = this.random.nextDouble()*50;
		double o1 = this.random.nextDouble()*50;
		double p1 = this.random.nextDouble()*50;
		Matrix4f matrix2 = new Matrix4f(a1,b1,c1,d1,e1,f1,g1,h1,i1,j1,k1,l1,m1,n1,o1,p1);
		
		
		Matrix4f prod = new Matrix4f(
				a*a1+b*e1+c*i1+d*m1,
				a*b1+b*f1+c*j1+d*n1,
				a*c1+b*g1+c*k1+d*o1,
				a*d1+b*h1+c*l1+d*p1,
				
				e*a1+f*e1+g*i1+h*m1,
				e*b1+f*f1+g*j1+h*n1,
				e*c1+f*g1+g*k1+h*o1,
				e*d1+f*h1+g*l1+h*p1,
				
				i*a1+j*e1+k*i1+l*m1,
				i*b1+j*f1+k*j1+l*n1,
				i*c1+j*g1+k*k1+l*o1,
				i*d1+j*h1+k*l1+l*p1,
				
				m*a1+n*e1+o*i1+p*m1,
				m*b1+n*f1+o*j1+p*n1,
				m*c1+n*g1+o*k1+p*o1,
				m*d1+n*h1+o*l1+p*p1);
		
		matrix1.mul(matrix2);
		
		assertTrue(matrix1.equals(prod));
	}
	
	@Test
	public void mulMatrix4DMatrix4D() {
		double a = this.random.nextDouble()*50;
		double b = this.random.nextDouble()*50;
		double c = this.random.nextDouble()*50;
		double d = this.random.nextDouble()*50;
		double e = this.random.nextDouble()*50;
		double f = this.random.nextDouble()*50;
		double g = this.random.nextDouble()*50;
		double h = this.random.nextDouble()*50;
		double i = this.random.nextDouble()*50;
		double j = this.random.nextDouble()*50;
		double k = this.random.nextDouble()*50;
		double l = this.random.nextDouble()*50;
		double m = this.random.nextDouble()*50;
		double n = this.random.nextDouble()*50;
		double o = this.random.nextDouble()*50;
		double p = this.random.nextDouble()*50;
		Matrix4f matrix1 = new Matrix4f(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p);
		
		double a1 = this.random.nextDouble()*50;
		double b1 = this.random.nextDouble()*50;
		double c1 = this.random.nextDouble()*50;
		double d1 = this.random.nextDouble()*50;
		double e1 = this.random.nextDouble()*50;
		double f1 = this.random.nextDouble()*50;
		double g1 = this.random.nextDouble()*50;
		double h1 = this.random.nextDouble()*50;
		double i1 = this.random.nextDouble()*50;
		double j1 = this.random.nextDouble()*50;
		double k1 = this.random.nextDouble()*50;
		double l1 = this.random.nextDouble()*50;
		double m1 = this.random.nextDouble()*50;
		double n1 = this.random.nextDouble()*50;
		double o1 = this.random.nextDouble()*50;
		double p1 = this.random.nextDouble()*50;
		Matrix4f matrix2 = new Matrix4f(a1,b1,c1,d1,e1,f1,g1,h1,i1,j1,k1,l1,m1,n1,o1,p1);
		
		
		Matrix4f prod = new Matrix4f(
				a*a1+b*e1+c*i1+d*m1,
				a*b1+b*f1+c*j1+d*n1,
				a*c1+b*g1+c*k1+d*o1,
				a*d1+b*h1+c*l1+d*p1,
				
				e*a1+f*e1+g*i1+h*m1,
				e*b1+f*f1+g*j1+h*n1,
				e*c1+f*g1+g*k1+h*o1,
				e*d1+f*h1+g*l1+h*p1,
				
				i*a1+j*e1+k*i1+l*m1,
				i*b1+j*f1+k*j1+l*n1,
				i*c1+j*g1+k*k1+l*o1,
				i*d1+j*h1+k*l1+l*p1,
				
				m*a1+n*e1+o*i1+p*m1,
				m*b1+n*f1+o*j1+p*n1,
				m*c1+n*g1+o*k1+p*o1,
				m*d1+n*h1+o*l1+p*p1);
		
		matrix1.mul(matrix1,matrix2);
		
		assertTrue(matrix1.equals(prod));
	}
	
	@Test
	public void equals() {
		Matrix4f m1 = this.randomMatrix4f();
		Matrix4f m2 = new Matrix4f(m1);
		
		assertTrue(m1.equals(m2));
	}
	
	@Test
	public void setZero() {
		Matrix4f m1 = this.randomMatrix4f();
		m1.setZero();
		
		assertTrue(m1.equals(new Matrix4f(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)));
	}
	
	@Test
	public void setDiagonalDoubleDoubleDoubleDouble() {
		Matrix4f m1 = this.randomMatrix4f();
		double a = this.random.nextDouble();
		double b = this.random.nextDouble();
		double c = this.random.nextDouble();
		double d = this.random.nextDouble();
				
		m1.setDiagonal(a,b,c,d);
		
		assertTrue(m1.equals(new Matrix4f(a,0,0,0,0,b,0,0,0,0,c,0,0,0,0,d)));
	}
	
	@Test
	public void negate() {
		Matrix4f m1 = this.randomMatrix4f();
		Matrix4f m2 = m1.clone();
		
		m2.negate();
		m2.add(m1, m2);
		m1.setZero();
		
		assertTrue(m2.equals(m1));
		
	}
	
	@Test
	public void negateMatrix4D() {
		Matrix4f m1 = this.randomMatrix4f();
		Matrix4f m2 = m1.clone();
		
		m2.negate(m2);
		m2.add(m1, m2);
		m1.setZero();
		
		assertTrue(m2.equals(m1));
	}
	
	@Test
	public void testClone() {
		Matrix4f m1 = this.randomMatrix4f();
		Matrix4f m2 = m1.clone();
		Matrix4f m3 = new Matrix4f(m1);
		
		assertTrue(m2.equals(m1));
		assertTrue(m3.equals(m1));
		assertTrue(m2.equals(m3));
		
		
	}
	
	@Test
	public void isSymmetric() {
		double a = this.random.nextDouble();
		double b = this.random.nextDouble();
		double c = this.random.nextDouble();
		double d = this.random.nextDouble();
		double e = this.random.nextDouble();
		double f = this.random.nextDouble();
		double g = this.random.nextDouble();
		double h = this.random.nextDouble();
		double i = this.random.nextDouble();
		double j = this.random.nextDouble();
		
		Matrix4f m1 = new Matrix4f(a,b,c,d,b,e,f,g,c,f,h,i,d,g,i,j);
		
		assertTrue(m1.isSymmetric());
		
		m1.setM01(j);
		
		assertFalse(m1.isSymmetric());
	}
	
	@Test
	public void isIdentity() {
		Matrix4f m1 = this.randomMatrix4f();
		
		m1.setIdentity();
		assertTrue(m1.isIdentity());
		
		m1.setZero();
		assertFalse(m1.isIdentity());
	}
	
}