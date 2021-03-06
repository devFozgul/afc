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

package org.arakhne.afc.math.geometry.d2.ifx;

import static org.junit.Assert.*;

import org.arakhne.afc.math.geometry.PathElementType;
import org.arakhne.afc.math.geometry.PathWindingRule;
import org.arakhne.afc.math.geometry.d2.ai.AbstractPath2aiTest;
import org.arakhne.afc.math.geometry.d2.ai.TestShapeFactory;
import org.junit.Test;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;

@SuppressWarnings("all")
public class Path2ifxTest extends AbstractPath2aiTest<Path2ifx, Rectangle2ifx> {

	@Override
	protected TestShapeFactory2ifx createFactory() {
		return TestShapeFactory2ifx.SINGLETON;
	}

	@Test
	@Override
	public void testClone() {
		super.testClone();
		Path2ifx clone = this.shape.clone();
		for (int i = 0; i < this.shape.size() * 2; ++i) {
			assertEquals(this.shape.getCoordAt(i), clone.getCoordAt(i));
		}
		for (int i = 0; i < this.shape.getPathElementCount(); ++i) {
			assertEquals(this.shape.getPathElementTypeAt(i), clone.getPathElementTypeAt(i));
		}
	}

	@Test
	public void boundingBoxProperty() {
		ObjectProperty<Rectangle2ifx> property = this.shape.boundingBoxProperty();
		assertNotNull(property);
		Rectangle2ifx box = property.get();
		assertNotNull(box);
		assertEquals(0, box.getMinX());
		assertEquals(-5, box.getMinY());
		assertEquals(7, box.getMaxX());
		assertEquals(3, box.getMaxY());
	}
	
	@Test
	public void controlPointBoundingBoxProperty() {
		ObjectProperty<Rectangle2ifx> property = this.shape.controlPointBoundingBoxProperty();
		assertNotNull(property);
		Rectangle2ifx box = property.get();
		assertNotNull(box);
		assertEquals(0, box.getMinX());
		assertEquals(-5, box.getMinY());
		assertEquals(7, box.getMaxX());
		assertEquals(5, box.getMaxY());
	}
	
	@Test
	public void coordinatesProperty() {
		ReadOnlyListProperty<Integer> property = this.shape.coordinatesProperty();
		assertNotNull(property);
		assertEquals(14, property.size());
		assertEquals(0, property.get(0).intValue());
		assertEquals(0, property.get(1).intValue());
		assertEquals(2, property.get(2).intValue());
		assertEquals(2, property.get(3).intValue());
		assertEquals(3, property.get(4).intValue());
		assertEquals(0, property.get(5).intValue());
		assertEquals(4, property.get(6).intValue());
		assertEquals(3, property.get(7).intValue());
		assertEquals(5, property.get(8).intValue());
		assertEquals(-1, property.get(9).intValue());
		assertEquals(6, property.get(10).intValue());
		assertEquals(5, property.get(11).intValue());
		assertEquals(7, property.get(12).intValue());
		assertEquals(-5, property.get(13).intValue());
	}
	
	@Test
	public void isCurvedProperty() {
		BooleanProperty property = this.shape.isCurvedProperty();
		assertNotNull(property);
		assertTrue(property.get());
	}
	
	@Test
	public void isEmptyProperty() {
		BooleanProperty property = this.shape.isEmptyProperty();
		assertNotNull(property);
		assertFalse(property.get());
	}
	
	@Test
	public void isMultiPartsProperty() {
		BooleanProperty property = this.shape.isMultiPartsProperty();
		assertNotNull(property);
		assertFalse(property.get());
	}
	
	@Test
	public void isPolygonProperty() {
		BooleanProperty property = this.shape.isPolygonProperty();
		assertNotNull(property);
		assertTrue(property.get());
	}
	
	@Test
	public void isPolylineProperty() {
		BooleanProperty property = this.shape.isPolylineProperty();
		assertNotNull(property);
		assertFalse(property.get());
	}
	
	@Test
	public void typesProperty() {
		ReadOnlyListProperty<PathElementType> property = this.shape.typesProperty();
		assertNotNull(property);
		assertEquals(5, property.size());
		assertSame(PathElementType.MOVE_TO, property.get(0));
		assertSame(PathElementType.LINE_TO, property.get(1));
		assertSame(PathElementType.QUAD_TO, property.get(2));
		assertSame(PathElementType.CURVE_TO, property.get(3));
		assertSame(PathElementType.CLOSE, property.get(4));
	}
	
	@Test
	public void windingRuleProperty() {
		ObjectProperty<PathWindingRule> property = this.shape.windingRuleProperty();
		assertNotNull(property);
		assertSame(PathWindingRule.NON_ZERO, property.get());
		this.shape.setWindingRule(PathWindingRule.EVEN_ODD);
		assertSame(PathWindingRule.EVEN_ODD, property.get());
		property.set(PathWindingRule.NON_ZERO);
		assertSame(PathWindingRule.NON_ZERO, property.get());
	}

}