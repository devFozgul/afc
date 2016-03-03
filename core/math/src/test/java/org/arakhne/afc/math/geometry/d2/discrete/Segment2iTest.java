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
package org.arakhne.afc.math.geometry.d2.discrete;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.arakhne.afc.math.MathConstants;
import org.arakhne.afc.math.geometry.PathElementType;
import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.continuous.Transform2D;
import org.arakhne.afc.math.geometry.d2.discrete.PathIterator2i;
import org.arakhne.afc.math.geometry.d2.discrete.Point2i;
import org.arakhne.afc.math.geometry.d2.discrete.Rectangle2i;
import org.arakhne.afc.math.geometry.d2.discrete.Segment2i;
import org.junit.Test;

/**
 * @author $Author: galland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("static-method")
public class Segment2iTest extends AbstractShape2iTestCase<Segment2i> implements MathConstants {
	
/*	private static void generateSegmentImages(int... coords) {
		int minx, maxx, miny, maxy;
		minx = miny = Integer.MAX_VALUE;
		maxx = maxy=  Integer.MIN_VALUE;
		for(int i=0; i<coords.length;) {
			int x = coords[i++];
			int y = coords[i++];
			if (x<minx) minx = x;
			if (x>maxx) maxx = x;
			if (y<miny) miny = y;
			if (y>maxy) maxy = y;
		}
		Color[] colors = new Color[] {
			Color.BLACK,
			Color.RED,
			Color.GREEN,
			Color.BLUE,
			Color.CYAN,
			Color.LIGHT_GRAY,
			Color.MAGENTA,
			Color.ORANGE,
			Color.PINK,
			Color.YELLOW,
		};
		int width = (maxx-minx+1) * 10;
		int height = (maxy-miny+1) * 10;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)img.getGraphics();
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, width, height);
		
		for(int i=0, j=0; i<coords.length; ++j) {
			int x1 = coords[i++];
			int y1 = coords[i++];
			int x2 = coords[i++];
			int y2 = coords[i++];
			g.setColor(new Color(colors[j].getRed(), colors[j].getGreen(), colors[j].getBlue(), 128));
			Segment2i segment = new Segment2i(x1, y1, x2, y2);
			Iterator<Point2i> iterator = segment.getPointIterator();
			while (iterator.hasNext()) {
				Point2i p = iterator.next();
				g.fillRect(p.x()*10, p.y()*10, 10, 10);
			}
		}
		
		g.dispose();
		
		File imgFile = new File("/home/sgalland/gen.png");
		try {
			ImageIO.write(img, "png", imgFile);
		}
		catch (IOException e) {
			throw new IOError(e);
		}
	}
*/
	
	@Override
	protected Segment2i createShape() {
		return new Segment2i(0, 0, 10, 5);
	}
	
	@Test
	@Override
	public void isEmpty() {
		assertFalse(this.r.isEmpty());
		this.r.clear();
		assertTrue(this.r.isEmpty());
	}

	@Test
	@Override
	public void clear() {
		this.r.clear();
		assertEquals(0, this.r.getX1());
		assertEquals(0, this.r.getY1());
		assertEquals(0, this.r.getX2());
		assertEquals(0, this.r.getY2());
	}

	@Test
	@Override
	public void testClone() {
		Segment2i b = this.r.clone();

		assertNotSame(b, this.r);
		assertEpsilonEquals(this.r.getX1(), b.getX1());
		assertEpsilonEquals(this.r.getY1(), b.getY1());
		assertEpsilonEquals(this.r.getX2(), b.getX2());
		assertEpsilonEquals(this.r.getY2(), b.getY2());
		
		b.set(this.r.getX1()+1, this.r.getY1()+1,
				this.r.getX2()+1, this.r.getY2()+1);

		assertNotEquals(this.r.getX1(), b.getX1());
		assertNotEquals(this.r.getY1(), b.getY1());
		assertNotEquals(this.r.getX2(), b.getX2());
		assertNotEquals(this.r.getY2(), b.getY2());
	}

	@Test
	@Override
	public void distancePoint2D() {
		assertEpsilonEquals(0f, this.r.distance(new Point2i(0, 0)));
		assertEpsilonEquals(1f, this.r.distance(new Point2i(1, 1)));
		
		assertEpsilonEquals(2.828427125f, this.r.distance(new Point2i(2, 4)));

		assertEpsilonEquals(1f, this.r.distance(new Point2i(2, 2)));

		assertEpsilonEquals(7.071067812f, this.r.distance(new Point2i(-5, 5)));
	}

	@Test
	@Override
	public void distanceSquaredPoint2D() {
		assertEpsilonEquals(0f, this.r.distanceSquared(new Point2i(0, 0)));
		assertEpsilonEquals(1f, this.r.distanceSquared(new Point2i(1, 1)));
		
		assertEpsilonEquals(8f, this.r.distanceSquared(new Point2i(2, 4)));

		assertEpsilonEquals(1f, this.r.distanceSquared(new Point2i(2, 2)));

		assertEpsilonEquals(50f, this.r.distanceSquared(new Point2i(-5, 5)));
	}

	@Test
	@Override
	public void distanceL1Point2D() {
		assertEpsilonEquals(0f, this.r.distanceL1(new Point2i(0, 0)));
		assertEpsilonEquals(1f, this.r.distanceL1(new Point2i(1, 1)));
		
		assertEpsilonEquals(4f, this.r.distanceL1(new Point2i(2, 4)));

		assertEpsilonEquals(1f, this.r.distanceL1(new Point2i(2, 2)));

		assertEpsilonEquals(10f, this.r.distanceL1(new Point2i(-5, 5)));
	}

	@Test
	@Override
	public void distanceLinfPoint2D() {
		assertEpsilonEquals(0f, this.r.distanceLinf(new Point2i(0, 0)));
		assertEpsilonEquals(1f, this.r.distanceLinf(new Point2i(1, 1)));
		
		assertEpsilonEquals(2f, this.r.distanceLinf(new Point2i(2, 4)));

		assertEpsilonEquals(1f, this.r.distanceLinf(new Point2i(2, 2)));

		assertEpsilonEquals(5f, this.r.distanceLinf(new Point2i(-5, 5)));
	}

	@Test
	@Override
	public void translateIntInt() {
		this.r.translate(3,  4);
		assertEquals(3, this.r.getX1());
		assertEquals(4, this.r.getY1());
		assertEquals(13, this.r.getX2());
		assertEquals(9, this.r.getY2());
	}

	/**
	 */
	@Test
	public void setIntIntIntInt() {
		this.r.set(3,  4, 5, 6);
		assertEquals(3, this.r.getX1());
		assertEquals(4, this.r.getY1());
		assertEquals(5, this.r.getX2());
		assertEquals(6, this.r.getY2());
	}

	/**
	 */
	@Test
	public void setPoint2DPoint2D() {
		this.r.set(new Point2i(3.4f,  4.5f), new Point2i(5.6f, 6.7f));
		assertEpsilonEquals(3, this.r.getX1());
		assertEpsilonEquals(4, this.r.getY1());
		assertEpsilonEquals(5, this.r.getX2());
		assertEpsilonEquals(6, this.r.getY2());
	}

	@Test
	@Override
	public void toBoundingBox() {
		Rectangle2i bb = this.r.toBoundingBox();
		assertEpsilonEquals(0, bb.getMinX());
		assertEpsilonEquals(0, bb.getMinY());
		assertEpsilonEquals(10, bb.getMaxX());
		assertEpsilonEquals(5, bb.getMaxY());
	}

	/**
	 */
	@Test
	public void containsPoint2D() {
		assertTrue(this.r.contains(new Point2i(0, 0)));
		assertTrue(this.r.contains(new Point2i(10, 5)));
		
		assertFalse(this.r.contains(new Point2i(1, 1)));
		assertFalse(this.r.contains(new Point2i(2, 4)));

		assertFalse(this.r.contains(new Point2i(2, 2)));

		assertTrue(this.r.contains(new Point2i(1, 0)));

		assertFalse(this.r.contains(new Point2i(5, 3)));
		assertTrue(this.r.contains(new Point2i(5, 2)));
	}

	/**
	 */
	@Test
	public void containsIntInt() {
		assertTrue(this.r.contains(0, 0));
		assertTrue(this.r.contains(10, 5));
		
		assertFalse(this.r.contains(1, 1));
		assertFalse(this.r.contains(2, 4));

		assertFalse(this.r.contains(2, 2));

		assertTrue(this.r.contains(1, 0));

		assertFalse(this.r.contains(5, 3));
		assertTrue(this.r.contains(5, 2));
	}

	/**
	 */
	@Test
	public void getClosestPointTo() {
		Point2D p;
		
		p = this.r.getClosestPointTo(new Point2i(0,0));
		assertEquals(0, p.ix());
		assertEquals(0, p.iy());

		p = this.r.getClosestPointTo(new Point2i(1,1));
		assertEquals(2, p.ix());
		assertEquals(1, p.iy());

		p = this.r.getClosestPointTo(new Point2i(2,2));
		assertEquals(2, p.ix());
		assertEquals(1, p.iy());

		p = this.r.getClosestPointTo(new Point2i(-2,2));
		assertEquals(0, p.ix());
		assertEquals(0, p.iy());

		p = this.r.getClosestPointTo(new Point2i(0f,1f));
		assertEquals(0, p.ix());
		assertEquals(0, p.iy());

		p = this.r.getClosestPointTo(new Point2i(10f,-1f));
		assertEquals(7, p.ix());
		assertEquals(3, p.iy());

		p = this.r.getClosestPointTo(new Point2i(2,4));
		assertEquals(4, p.ix());
		assertEquals(2, p.iy());
	}

	/**
	 */
	@Test
	public void getFarthestPointTo() {
		Point2D p;
		
		p = this.r.getFarthestPointTo(new Point2i(0,0));
		assertEquals(10, p.ix());
		assertEquals(5, p.iy());

		p = this.r.getFarthestPointTo(new Point2i(1,1));
		assertEquals(10, p.ix());
		assertEquals(5, p.iy());

		p = this.r.getFarthestPointTo(new Point2i(2,2));
		assertEquals(10, p.ix());
		assertEquals(5, p.iy());

		p = this.r.getFarthestPointTo(new Point2i(-2,2));
		assertEquals(10, p.ix());
		assertEquals(5, p.iy());

		p = this.r.getFarthestPointTo(new Point2i(0f,1f));
		assertEquals(10, p.ix());
		assertEquals(5, p.iy());

		p = this.r.getFarthestPointTo(new Point2i(10f,-1f));
		assertEquals(0, p.ix());
		assertEquals(0, p.iy());

		p = this.r.getFarthestPointTo(new Point2i(2,4));
		assertEquals(10, p.ix());
		assertEquals(5, p.iy());
	}

	/**
	 */
	@Test
	public void getPathIteratorVoid() {
		PathIterator2i pi = this.r.getPathIteratorDiscrete();
		assertElement(pi, PathElementType.MOVE_TO, 0,0);
		assertElement(pi, PathElementType.LINE_TO, 10,5);
		assertNoElement(pi);
	}

	/**
	 */
	@Test
	public void getPathIteratorTransform2D() {
		Transform2D tr;
		PathIterator2i pi;
		
		tr = new Transform2D();
		pi = this.r.getPathIteratorDiscrete(tr);
		assertElement(pi, PathElementType.MOVE_TO, 0,0);
		assertElement(pi, PathElementType.LINE_TO, 10,5);
		assertNoElement(pi);

		tr = new Transform2D();
		tr.makeTranslationMatrix(3.4f, 4.5f);
		pi = this.r.getPathIteratorDiscrete(tr);
		assertElement(pi, PathElementType.MOVE_TO, 3, 4);
		assertElement(pi, PathElementType.LINE_TO, 13, 9);
		assertNoElement(pi);

		tr = new Transform2D();
		tr.makeRotationMatrix(MathConstants.QUARTER_PI);
		pi = this.r.getPathIteratorDiscrete(tr); 
		assertElement(pi, PathElementType.MOVE_TO, 0, 0);
		assertElement(pi, PathElementType.LINE_TO, 3, 10);
		assertNoElement(pi);
	}

	/**
	 */
	@Test
    public void createTransformedPathTransform2D() {
    	Segment2i s;
    	Transform2D tr;
    	
    	tr = new Transform2D();    	
    	s = (Segment2i)this.r.createTransformedShape(tr);
		assertEquals(0, s.getX1());
		assertEquals(0, s.getY1());
		assertEquals(10, s.getX2());
		assertEquals(5, s.getY2());

    	tr = new Transform2D();
    	tr.setTranslation(3.4f, 4.5f);
    	s = (Segment2i)this.r.createTransformedShape(tr);
		assertEquals(3, s.getX1());
		assertEquals(4, s.getY1());
		assertEquals(13, s.getX2());
		assertEquals(9, s.getY2());

    	tr = new Transform2D();
    	tr.setRotation(MathConstants.PI);
    	s = (Segment2i)this.r.createTransformedShape(tr);
		assertEquals(0, s.getX1());
		assertEquals(0, s.getY1());
		assertEquals(-10, s.getX2());
		assertEquals(-5, s.getY2());

    	tr = new Transform2D();
    	tr.setRotation(MathConstants.QUARTER_PI);
    	s = (Segment2i)this.r.createTransformedShape(tr);
		assertEquals(0, s.getX1());
		assertEquals(0, s.getY1());
		assertEquals(3, s.getX2());
		assertEquals(10, s.getY2());
    }

	/**
	 */
	@Test
    public void transformTransform2D() {
    	Segment2i s;
    	Transform2D tr;
    	
    	tr = new Transform2D();
    	s = this.r.clone();
    	s.transform(tr);
		assertEquals(0, s.getX1());
		assertEquals(0, s.getY1());
		assertEquals(10, s.getX2());
		assertEquals(5, s.getY2());

    	tr = new Transform2D();
    	tr.setTranslation(3.4f, 4.5f);
    	s = this.r.clone();
    	s.transform(tr);
    	assertEquals(3, s.getX1());
    	assertEquals(4, s.getY1());
    	assertEquals(13, s.getX2());
    	assertEquals(9, s.getY2());

    	tr = new Transform2D();
    	tr.setRotation(MathConstants.PI);
    	s = this.r.clone();
    	s.transform(tr);
    	assertEquals(0, s.getX1());
    	assertEquals(0, s.getY1());
    	assertEquals(-10, s.getX2());
    	assertEquals(-5, s.getY2());

    	tr = new Transform2D();
    	tr.setRotation(MathConstants.QUARTER_PI);
    	s = this.r.clone();
    	s.transform(tr);
    	assertEquals(0, s.getX1());
    	assertEquals(0, s.getY1());
    	assertEquals(3, s.getX2());
    	assertEquals(10, s.getY2());
    }
    
    /**
     */
	@Test
    public void getPointIterator() {
    	Point2i p;
    	Iterator<Point2i> iterator = this.r.getPointIterator();

    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertNotNull(p);
    	assertEquals(0, p.ix());
    	assertEquals(0, p.iy());

    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertNotNull(p);
    	assertEquals(1, p.ix());
    	assertEquals(0, p.iy());

    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertNotNull(p);
    	assertEquals(2, p.ix());
    	assertEquals(1, p.iy());

    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertNotNull(p);
    	assertEquals(3, p.ix());
    	assertEquals(1, p.iy());

    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertNotNull(p);
    	assertEquals(4, p.ix());
    	assertEquals(2, p.iy());

    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertNotNull(p);
    	assertEquals(5, p.ix());
    	assertEquals(2, p.iy());

    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertNotNull(p);
    	assertEquals(6, p.ix());
    	assertEquals(3, p.iy());

    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertNotNull(p);
    	assertEquals(7, p.ix());
    	assertEquals(3, p.iy());

    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertNotNull(p);
    	assertEquals(8, p.ix());
    	assertEquals(4, p.iy());

    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertNotNull(p);
    	assertEquals(9, p.ix());
    	assertEquals(4, p.iy());

    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertNotNull(p);
    	assertEquals(10, p.ix());
    	assertEquals(5, p.iy());

    	assertFalse(iterator.hasNext());
    }
    
    /**
     */
	@Test
    public void intersectsSegmentSegment() {
    	assertTrue(Segment2i.intersectsSegmentSegment(0, 0, 10, 5, 0, 0, 10, 5));
    	assertTrue(Segment2i.intersectsSegmentSegment(0, 0, 10, 5, 0, 0, 5, 2));
    	assertFalse(Segment2i.intersectsSegmentSegment(0, 0, 10, 5, 0, 1, 5, 3));
    	assertFalse(Segment2i.intersectsSegmentSegment(0, 0, 10, 5, 0, 2, 5, 4));
    	assertTrue(Segment2i.intersectsSegmentSegment(0, 0, 10, 5, 5, 0, 4, 3));
    	assertFalse(Segment2i.intersectsSegmentSegment(0, 0, 10, 5, -1, 5, -1, 0));
    	assertTrue(Segment2i.intersectsSegmentSegment(5, 3, 7, 5, 6, 2, 6, 5));
    	assertTrue(Segment2i.intersectsSegmentSegment(5, 3, 7, 5, 9, 4, 6, 6));
    	assertFalse(Segment2i.intersectsSegmentSegment(5, 3, 7, 5, 9, 4, 6, 7));
    	assertTrue(Segment2i.intersectsSegmentSegment(5, 3, 7, 5, 6, 4, 6, 8));
    }
    
    /**
     */
	@Test
    public void computeCrossingsFromPoint() {
    	assertEquals(0, Segment2i.computeCrossingsFromPoint(0, 5, 3, -1, -1, 0, 0));
    	assertEquals(0, Segment2i.computeCrossingsFromPoint(0, 5, 3, 4, -2, 4, 10));
    	assertEquals(2, Segment2i.computeCrossingsFromPoint(0, 5, 3, 6, -2, 6, 10));
    	assertEquals(SHAPE_INTERSECTS, Segment2i.computeCrossingsFromPoint(0, 5, 3, 5, -2, 5, 10));
    	assertEquals(-2, Segment2i.computeCrossingsFromPoint(0, 5, 3, 6, 10, 6, -2));
    	assertEquals(SHAPE_INTERSECTS, Segment2i.computeCrossingsFromPoint(0, 5, 3, 5, 10, 5, -2));
    	assertEquals(2, Segment2i.computeCrossingsFromPoint(0, 5, 3, 10, -5, 127, 345));
    	assertEquals(-2, Segment2i.computeCrossingsFromPoint(0, 5, 3, 127, 345, 10, -5));
    	assertEquals(1, Segment2i.computeCrossingsFromPoint(0, 5, 3, 127, 3, 200, 345));
    	assertEquals(-1, Segment2i.computeCrossingsFromPoint(0, 5, 3, 127, 345, 200, 3));
    	assertEquals(1, Segment2i.computeCrossingsFromPoint(0, 5, 3, 10, 1, 12, 3));
    	assertEquals(-1, Segment2i.computeCrossingsFromPoint(0, 5, 3, 12, 3, 10, 1));
    	assertEquals(1, Segment2i.computeCrossingsFromPoint(0, 5, 3, 10, 3, 12, 5));
    	assertEquals(-1, Segment2i.computeCrossingsFromPoint(0, 5, 3, 12, 5, 10, 3));
    	assertEquals(0, Segment2i.computeCrossingsFromPoint(0, 4, -1, 7, -5, 0, 0));
    }

    /**
     */
	@Test
    public void computeCrossingsFromRect() {
    	int[] data = new int[] {
    			-1, -1, 0, 0, 				0,
    			-1, 0, 10, 0,				0,
    			10, -2, 12, 4,				1,
    			12, 4, 50, 10,				1,
    			10, -2, 50, 10,				2,
    			10, 3, 12, 4,				0,
    			12, 4, 50, 5,				0,
    			12, 3, 50, 5,				0,
    			12, 5, 50, 5,				0,
    			12, 4, 50, 10,				1,
    			12, 3, 50, 10,				1,
    			12, 5, 50, 10,				1,
    			0, 5, 3, 7,					0,
    			6, 2, 6, 4,					SHAPE_INTERSECTS,
    			6, 4, 6, 8,					SHAPE_INTERSECTS,
    			7, 4, 7, 8,					SHAPE_INTERSECTS,
    			5, 4, 5, 8,					SHAPE_INTERSECTS,
    			4, 4, 6, 6,					SHAPE_INTERSECTS,
    			6, 6, 8, 4,					SHAPE_INTERSECTS,
    			5, 4, 7, 4,					SHAPE_INTERSECTS,
    			0, 4, 7, 4,					SHAPE_INTERSECTS,
    			6, 6, 12, 8,				0,
    			6, 2, 12, -3,				0,
    	};
    	
    	String label;
    	for(int i=0; i<data.length;) {
    		int x1 = data[i++];
    		int y1 = data[i++];
    		int x2 = data[i++];
    		int y2 = data[i++];
    		int crossing = data[i++];
    		    		
    		label = x1+";"+y1+";"+x2+";"+y2;  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
    		assertEquals(label, crossing, Segment2i.computeCrossingsFromRect(0, 5, 3, 7, 5, x1, y1, x2, y2));

    		label = x2+";"+y2+";"+x1+";"+y1;  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
    		if (crossing!=MathConstants.SHAPE_INTERSECTS) {
    			crossing = -crossing;
    		}
    		assertEquals(label, crossing, Segment2i.computeCrossingsFromRect(0, 5, 3, 7, 5, x2, y2, x1, y1));
    	}
    }
    /**
     */
	@Test
    public void computeCrossingsFromSegment() {
    	assertEquals(0, Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ -1, -1, 0, 0));
    	assertEquals(0, Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ -1, 0, 10, 0));
    	assertEquals(1, Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 10, -2, 12, 4));
    	assertEquals(1, Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 12, 4, 50, 10));
    	assertEquals(2, Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 10, -2, 50, 10));
    	assertEquals(0, Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 10, 3, 12, 4));
    	assertEquals(0, Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 12, 4, 50, 5));
    	assertEquals(0, Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 12, 3, 50, 5));
    	assertEquals(1,
    			Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 6, 2, 6, 3));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 6, 2, 6, 5));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 6, 2, 6, 4));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 6, 4, 6, 8));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 6, 3, 6, 8));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 7, 4, 7, 8));
    	assertEquals(0,
    			Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 5, 4, 5, 8));
    	assertEquals(0,
    			Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 4, 4, 6, 6));
    	assertEquals(0,
    			Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 4, 3, 6, 5));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 8, 4, 6, 6));
    	assertEquals(1,
    			Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 10, 4, 6, 8));
    	assertEquals(-1,
    			Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 6, 8, 10, 4));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromSegment(0, /**/ 5, 3, 7, 5, /**/ 5, 4, 100, 6));
    }

    /**
     */
	@Test
    public void computeCrossingsFromCircle() {
    	assertEquals(0, Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ -1, -1, 0, 0));
    	assertEquals(0, Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ -1, 0, 10, 0));
    	assertEquals(1, Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 10, -2, 12, 4));
    	assertEquals(1, Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 12, 4, 50, 10));
    	assertEquals(2, Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 10, -2, 50, 10));
    	assertEquals(0, Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 10, 3, 12, 4));
    	assertEquals(0, Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 12, 4, 50, 5));
    	assertEquals(0, Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 12, 3, 50, 5));
    	assertEquals(1,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 6, 2, 6, 3));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 6, 2, 6, 5));
    	assertEquals(1,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 7, 2, 7, 4));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 6, 2, 6, 4));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 6, 4, 6, 8));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 6, 3, 6, 8));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 7, 4, 7, 8));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 5, 4, 5, 8));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 4, 4, 6, 6));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 4, 3, 6, 5));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 8, 4, 6, 6));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 10, 4, 6, 8));
    	assertEquals(MathConstants.SHAPE_INTERSECTS,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 6, 8, 10, 4));
    	assertEquals(1,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 7, 8, 10, 12));
    	assertEquals(-1,
    			Segment2i.computeCrossingsFromCircle(0, /**/ 4, 6, 3, /**/ 10, 12, 7, 8));
    }

	/**
	 */
	@Test
	public void setShape2i() {
		this.r.set(new Rectangle2i(10, 12, 14, 16));
		assertEquals(10, this.r.getX1());
		assertEquals(12, this.r.getY1());
		assertEquals(24, this.r.getX2());
		assertEquals(28, this.r.getY2());
	}

}