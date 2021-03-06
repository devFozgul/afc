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

package org.arakhne.afc.attrs.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.arakhne.afc.attrs.attr.Attribute;
import org.arakhne.afc.attrs.attr.AttributeImpl;
import org.arakhne.afc.attrs.attr.AttributeValue;
import org.arakhne.afc.attrs.attr.AttributeValueImpl;

@SuppressWarnings("all")
public abstract class AbstractAttributeCollectionTest<T extends AttributeCollection> extends AbstractAttributeProviderTest<T> {

	private Attribute[] newValues;
	
	protected ListenerStub listenerStub;
	
	public AbstractAttributeCollectionTest(String id) {
		super(id);
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		this.newValues = new Attribute[] {
			new AttributeImpl("A",false),	 
			new AttributeImpl("D","34"),	  
			new AttributeImpl("Z",17f),	 
		};
		
		this.listenerStub = new ListenerStub();
		this.testData.addAttributeChangeListener(this.listenerStub);
	}
	
	@Override
	@After
	public void tearDown() throws Exception {
		this.newValues = null;
		this.listenerStub.reset();
		this.listenerStub = null;
		super.tearDown();
	}
	
	private void runSetAttributeValue(Class<?>[] types, Object[] parameters, Attribute attr) throws Exception {
		String name = attr.getName();
		
		boolean attrExists = this.testData.hasAttribute(name);
		AttributeValue oldValue = null;
		if (attrExists) {
			oldValue = this.testData.getAttribute(name);
		}
	
		Method method = this.testData.getClass().getMethod("setAttribute", types); 
		Object o = method.invoke(this.testData, parameters);

		assertTrue(this.id, o instanceof Attribute);
		assertEquals(this.id, attr,o);
		
		assertNotNull(this.id, this.testData.getAttribute(name));
		assertEquals(this.id, attr.getType(),this.testData.getAttribute(name).getType());
		assertEquals(this.id, attr,this.testData.getAttribute(name));
		
		// Test events
		String message = this.id+": set attribute "+name; 
		this.listenerStub.assertNames(message, name);
		this.listenerStub.assertValues(message, attr);
		if (!attrExists) {
			this.listenerStub.assertTypes(message,
					AttributeChangeEvent.Type.ADDITION);
			this.listenerStub.assertOldNames(message, new String[]{null});
			this.listenerStub.assertOldValues(message, new AttributeValue[]{null});
		}
		else {
			this.listenerStub.assertTypes(message,
					AttributeChangeEvent.Type.VALUE_UPDATE);
			this.listenerStub.assertOldNames(message, name);
			this.listenerStub.assertOldValues(message, oldValue);
		}
		
		this.listenerStub.reset();
	}
	
	private void runSetAttributeValue(Class<?> type, Object parameter, Attribute attr) throws Exception {
		runSetAttributeValue(
				new Class<?>[] {String.class, type},
				new Object[] {attr.getName(), parameter}, attr);
	}

	@Test
	public void setAttributeAttribute() throws Exception {
		for (Attribute attr : this.newValues) {
			runSetAttributeValue(
					new Class<?>[] {Attribute.class},
					new Object[] {attr},
					attr);
		}
	}

	@Test
	public void setAttributeStringAttributeValue() throws Exception {
		for (Attribute attr : this.newValues) {
			runSetAttributeValue(
					AttributeValue.class,
					attr,
					attr);
		}
	}

	@Test
	public void setAttributeStringBoolean() throws Exception {
		Attribute attr = new AttributeImpl("A", false); 
		runSetAttributeValue(
				boolean.class,
				attr.getBoolean(),
				attr);
		attr = new AttributeImpl("X", false); 
		runSetAttributeValue(
				boolean.class,
				attr.getBoolean(),
				attr);
	}

	@Test
	public void setAttributeStringInt() throws Exception {
		Attribute attr = new AttributeImpl("E", 34); 
		runSetAttributeValue(
				int.class,
				(int)attr.getInteger(),
				attr);
		attr = new AttributeImpl("X", 34); 
		runSetAttributeValue(
				int.class,
				(int)attr.getInteger(),
				attr);
	}

	@Test
	public void setAttributeStringLong() throws Exception {
		Attribute attr = new AttributeImpl("E", 34); 
		runSetAttributeValue(
				long.class,
				attr.getInteger(),
				attr);
		attr = new AttributeImpl("X", 34); 
		runSetAttributeValue(
				long.class,
				attr.getInteger(),
				attr);
	}

	@Test
	public void setAttributeStringFloat() throws Exception {
		Attribute attr = new AttributeImpl("E", 34f); 
		runSetAttributeValue(
				float.class,
				(float)attr.getReal(),
				attr);
		attr = new AttributeImpl("X", 34f); 
		runSetAttributeValue(
				float.class,
				(float)attr.getReal(),
				attr);
	}

	@Test
	public void setAttributeStringDouble() throws Exception {
		Attribute attr = new AttributeImpl("E", 34.); 
		runSetAttributeValue(
				double.class,
				attr.getReal(),
				attr);
		attr = new AttributeImpl("X", 34.); 
		runSetAttributeValue(
				double.class,
				attr.getReal(),
				attr);
	}
	
	@Test
	public void setAttributeStringString() throws Exception {
		Attribute attr = new AttributeImpl("E", "Toto");  
		runSetAttributeValue(
				String.class,
				attr.getString(),
				attr);
		attr = new AttributeImpl("X", "Titi et Rominet");  
		runSetAttributeValue(
				String.class,
				attr.getString(),
				attr);
	}

	@Test
	public void removeAttributeString() {
		String message;
		
		assertFalse(this.id, this.testData.removeAttribute("Y")); 
		// Testing events
		message = this.id+": removing Y"; 
		this.listenerStub.assertEmpty(message);
		this.listenerStub.reset();
		
		assertTrue(this.id, this.testData.removeAttribute("C")); 
		// Testing events
		message = "removing C"; 
		this.listenerStub.assertTypes(message, AttributeChangeEvent.Type.REMOVAL);
		this.listenerStub.assertNames(message, "C"); 
		this.listenerStub.assertOldNames(message, "C"); 
		this.listenerStub.assertValues(message, new AttributeValueImpl(true));
		this.listenerStub.assertOldValues(message, new AttributeValueImpl(true));
		this.listenerStub.reset();

		assertFalse(this.id, this.testData.removeAttribute("X")); 
		// Testing events
		message = this.id+": removing X"; 
		this.listenerStub.assertEmpty(message);
		this.listenerStub.reset();
		
		assertTrue(this.id, this.testData.hasAttribute("A")); 
		assertFalse(this.id, this.testData.hasAttribute("X")); 
		assertTrue(this.id, this.testData.hasAttribute("B")); 
		assertFalse(this.id, this.testData.hasAttribute("Y")); 
		assertFalse(this.id, this.testData.hasAttribute("C")); 
		assertTrue(this.id, this.testData.hasAttribute("D")); 
		assertFalse(this.id, this.testData.hasAttribute("Z")); 
		assertTrue(this.id, this.testData.hasAttribute("E")); 
		assertTrue(this.id, this.testData.hasAttribute("F")); 
		
	}

	@Test
	public void removeAllAttributes() {
		String message;
		
		assertTrue(this.id, this.testData.hasAttribute("A")); 
		assertFalse(this.id, this.testData.hasAttribute("X")); 
		assertTrue(this.id, this.testData.hasAttribute("B")); 
		assertFalse(this.id, this.testData.hasAttribute("Y")); 
		assertTrue(this.id, this.testData.hasAttribute("C")); 
		assertTrue(this.id, this.testData.hasAttribute("D")); 
		assertFalse(this.id, this.testData.hasAttribute("Z")); 
		assertTrue(this.id, this.testData.hasAttribute("E")); 
		assertTrue(this.id, this.testData.hasAttribute("F")); 

		assertTrue(this.id, this.testData.removeAllAttributes());
		message = this.id+": removing all attributes"; 
		this.listenerStub.assertTypes(message, AttributeChangeEvent.Type.REMOVE_ALL);
		this.listenerStub.assertNames(message, new String[]{null});
		this.listenerStub.assertOldNames(message, new String[]{null});
		this.listenerStub.assertValues(message, new AttributeValue[]{null});
		this.listenerStub.assertOldValues(message, new AttributeValue[]{null});
		this.listenerStub.reset();

		assertFalse(this.id, this.testData.hasAttribute("A")); 
		assertFalse(this.id, this.testData.hasAttribute("X")); 
		assertFalse(this.id, this.testData.hasAttribute("B")); 
		assertFalse(this.id, this.testData.hasAttribute("Y")); 
		assertFalse(this.id, this.testData.hasAttribute("C")); 
		assertFalse(this.id, this.testData.hasAttribute("D")); 
		assertFalse(this.id, this.testData.hasAttribute("Z")); 
		assertFalse(this.id, this.testData.hasAttribute("E")); 
		assertFalse(this.id, this.testData.hasAttribute("F")); 

		assertFalse(this.id, this.testData.removeAllAttributes());
		this.listenerStub.assertEmpty(message);
		this.listenerStub.reset();

		assertFalse(this.id, this.testData.hasAttribute("A")); 
		assertFalse(this.id, this.testData.hasAttribute("X")); 
		assertFalse(this.id, this.testData.hasAttribute("B")); 
		assertFalse(this.id, this.testData.hasAttribute("Y")); 
		assertFalse(this.id, this.testData.hasAttribute("C")); 
		assertFalse(this.id, this.testData.hasAttribute("D")); 
		assertFalse(this.id, this.testData.hasAttribute("Z")); 
		assertFalse(this.id, this.testData.hasAttribute("E")); 
		assertFalse(this.id, this.testData.hasAttribute("F")); 
	}
	
	@Test
	public void renameAttribute() {
		String message;
		
		assertTrue(this.id, this.testData.hasAttribute("A")); 
		assertFalse(this.id, this.testData.hasAttribute("X")); 
		assertTrue(this.id, this.testData.hasAttribute("B")); 
		assertFalse(this.id, this.testData.hasAttribute("Y")); 
		assertTrue(this.id, this.testData.hasAttribute("C")); 
		assertTrue(this.id, this.testData.hasAttribute("D")); 
		assertFalse(this.id, this.testData.hasAttribute("Z")); 
		assertTrue(this.id, this.testData.hasAttribute("E")); 
		assertTrue(this.id, this.testData.hasAttribute("F")); 
		
		AttributeValue oldValue = this.testData.getAttribute("B"); 

		assertTrue(this.id, this.testData.renameAttribute("B", "ZZZ", false));  
		// Testing events
		message = this.id+": renaming B to ZZZ"; 
		this.listenerStub.assertTypes(message, AttributeChangeEvent.Type.RENAME);
		this.listenerStub.assertNames(message, "ZZZ"); 
		this.listenerStub.assertOldNames(message, "B"); 
		this.listenerStub.assertValues(message, oldValue);
		this.listenerStub.assertOldValues(message, oldValue);
		this.listenerStub.reset();

		assertTrue(this.id, this.testData.hasAttribute("A")); 
		assertFalse(this.id, this.testData.hasAttribute("X")); 
		assertFalse(this.id, this.testData.hasAttribute("B")); 
		assertTrue(this.id, this.testData.hasAttribute("ZZZ")); 
		assertFalse(this.id, this.testData.hasAttribute("Y")); 
		assertTrue(this.id, this.testData.hasAttribute("C")); 
		assertTrue(this.id, this.testData.hasAttribute("D")); 
		assertFalse(this.id, this.testData.hasAttribute("Z")); 
		assertTrue(this.id, this.testData.hasAttribute("E")); 
		assertTrue(this.id, this.testData.hasAttribute("F")); 
		
		assertEquals(this.id, oldValue, this.testData.getAttribute("ZZZ")); 

		assertFalse(this.id, this.testData.renameAttribute("toto", "XXX", false));  
		// Testing events
		message = this.id+": renaming toto to XXX"; 
		this.listenerStub.assertEmpty(message);
		this.listenerStub.reset();

		assertTrue(this.id, this.testData.hasAttribute("A")); 
		assertFalse(this.id, this.testData.hasAttribute("X")); 
		assertFalse(this.id, this.testData.hasAttribute("B")); 
		assertTrue(this.id, this.testData.hasAttribute("ZZZ")); 
		assertFalse(this.id, this.testData.hasAttribute("Y")); 
		assertTrue(this.id, this.testData.hasAttribute("C")); 
		assertTrue(this.id, this.testData.hasAttribute("D")); 
		assertFalse(this.id, this.testData.hasAttribute("Z")); 
		assertTrue(this.id, this.testData.hasAttribute("E")); 
		assertTrue(this.id, this.testData.hasAttribute("F")); 
		
		oldValue = this.testData.getAttribute("F"); 
		AttributeValue oldValue2 = this.testData.getAttribute("A"); 

		assertFalse(this.id, this.testData.renameAttribute("F", "A", false));  
		// Testing events
		message = this.id+": renaming F to A"; 
		this.listenerStub.assertEmpty(message);
		this.listenerStub.reset();

		assertTrue(this.id, this.testData.hasAttribute("A")); 
		assertFalse(this.id, this.testData.hasAttribute("X")); 
		assertFalse(this.id, this.testData.hasAttribute("B")); 
		assertTrue(this.id, this.testData.hasAttribute("ZZZ")); 
		assertFalse(this.id, this.testData.hasAttribute("Y")); 
		assertTrue(this.id, this.testData.hasAttribute("C")); 
		assertTrue(this.id, this.testData.hasAttribute("D")); 
		assertFalse(this.id, this.testData.hasAttribute("Z")); 
		assertTrue(this.id, this.testData.hasAttribute("E")); 
		assertTrue(this.id, this.testData.hasAttribute("F")); 
		
		assertEquals(this.id, oldValue, this.testData.getAttribute("F")); 
		assertEquals(this.id, oldValue2, this.testData.getAttribute("A")); 
	}
	
	@Test
	public void renameAttributeOverwrite() {
		String message;
		
		assertTrue(this.id, this.testData.hasAttribute("A")); 
		assertFalse(this.id, this.testData.hasAttribute("X")); 
		assertTrue(this.id, this.testData.hasAttribute("B")); 
		assertFalse(this.id, this.testData.hasAttribute("Y")); 
		assertTrue(this.id, this.testData.hasAttribute("C")); 
		assertTrue(this.id, this.testData.hasAttribute("D")); 
		assertFalse(this.id, this.testData.hasAttribute("Z")); 
		assertTrue(this.id, this.testData.hasAttribute("E")); 
		assertTrue(this.id, this.testData.hasAttribute("F")); 
		
		AttributeValue oldValue = this.testData.getAttribute("B"); 

		assertTrue(this.id, this.testData.renameAttribute("B", "ZZZ", true));  
		// Testing events
		message = this.id+": renaming B to ZZZ"; 
		this.listenerStub.assertTypes(message, AttributeChangeEvent.Type.RENAME);
		this.listenerStub.assertNames(message, "ZZZ"); 
		this.listenerStub.assertOldNames(message, "B"); 
		this.listenerStub.assertValues(message, oldValue);
		this.listenerStub.assertOldValues(message, oldValue);
		this.listenerStub.reset();

		assertTrue(this.id, this.testData.hasAttribute("A")); 
		assertFalse(this.id, this.testData.hasAttribute("X")); 
		assertFalse(this.id, this.testData.hasAttribute("B")); 
		assertTrue(this.id, this.testData.hasAttribute("ZZZ")); 
		assertFalse(this.id, this.testData.hasAttribute("Y")); 
		assertTrue(this.id, this.testData.hasAttribute("C")); 
		assertTrue(this.id, this.testData.hasAttribute("D")); 
		assertFalse(this.id, this.testData.hasAttribute("Z")); 
		assertTrue(this.id, this.testData.hasAttribute("E")); 
		assertTrue(this.id, this.testData.hasAttribute("F")); 
		
		assertEquals(this.id, oldValue, this.testData.getAttribute("ZZZ")); 

		assertFalse(this.id, this.testData.renameAttribute("toto", "XXX", true));  
		// Testing events
		message = this.id+": renaming toto to XXX"; 
		this.listenerStub.assertEmpty(message);
		this.listenerStub.reset();

		assertTrue(this.id, this.testData.hasAttribute("A")); 
		assertFalse(this.id, this.testData.hasAttribute("X")); 
		assertFalse(this.id, this.testData.hasAttribute("B")); 
		assertTrue(this.id, this.testData.hasAttribute("ZZZ")); 
		assertFalse(this.id, this.testData.hasAttribute("Y")); 
		assertTrue(this.id, this.testData.hasAttribute("C")); 
		assertTrue(this.id, this.testData.hasAttribute("D")); 
		assertFalse(this.id, this.testData.hasAttribute("Z")); 
		assertTrue(this.id, this.testData.hasAttribute("E")); 
		assertTrue(this.id, this.testData.hasAttribute("F")); 
		
		oldValue = this.testData.getAttribute("F"); 
		AttributeValue oldValue2 = this.testData.getAttribute("A"); 

		assertTrue(this.id, this.testData.renameAttribute("F", "A", true));  
		// Testing events
		message = this.id+": renaming F to A"; 
		this.listenerStub.assertTypes(message, AttributeChangeEvent.Type.REMOVAL, AttributeChangeEvent.Type.RENAME);
		this.listenerStub.assertNames(message, "A","A");  
		this.listenerStub.assertOldNames(message, "A", "F");  
		this.listenerStub.assertValues(message, oldValue2, oldValue);
		this.listenerStub.assertOldValues(message, oldValue2, oldValue);
		this.listenerStub.reset();

		assertTrue(this.id, this.testData.hasAttribute("A")); 
		assertFalse(this.id, this.testData.hasAttribute("X")); 
		assertFalse(this.id, this.testData.hasAttribute("B")); 
		assertTrue(this.id, this.testData.hasAttribute("ZZZ")); 
		assertFalse(this.id, this.testData.hasAttribute("Y")); 
		assertTrue(this.id, this.testData.hasAttribute("C")); 
		assertTrue(this.id, this.testData.hasAttribute("D")); 
		assertFalse(this.id, this.testData.hasAttribute("Z")); 
		assertTrue(this.id, this.testData.hasAttribute("E")); 
		assertFalse(this.id, this.testData.hasAttribute("F")); 
		
		assertEquals(this.id, oldValue, this.testData.getAttribute("A")); 
	}
	
	protected class ListenerStub implements AttributeChangeListener {

		private final ArrayList<AttributeChangeEvent> eventList = new ArrayList<AttributeChangeEvent>();

		public void reset() {
			this.eventList.clear();
		}
		
		@Override
		public void onAttributeChangeEvent(AttributeChangeEvent event) {
			this.eventList.add(event);
		}
		
		public void assertEmpty(String message) {
			assertEquals(message, 0,this.eventList.size());
		}

		public void assertTypes(String message, AttributeChangeEvent.Type... desiredTypes) {
			assertEquals(message, desiredTypes.length, this.eventList.size());
			for(int i=0; i<desiredTypes.length; ++i) {
				assertEquals(message+" at index "+i, desiredTypes[i], this.eventList.get(i).getType()); 
			}
		}

		public void assertNames(String message, String... desiredNames) {
			assertEquals(message, desiredNames.length, this.eventList.size());
			for(int i=0; i<desiredNames.length; ++i) {
				assertEquals(message+" at index "+i, desiredNames[i], this.eventList.get(i).getName()); 
			}
		}

		public void assertOldNames(String message, String... desiredNames) {
			assertEquals(message, desiredNames.length, this.eventList.size());
			for(int i=0; i<desiredNames.length; ++i) {
				assertEquals(message+" at index "+i, desiredNames[i], this.eventList.get(i).getOldName()); 
			}
		}

		public void assertValues(String message, AttributeValue... desiredValues) {
			assertEquals(message, desiredValues.length, this.eventList.size());
			for(int i=0; i<desiredValues.length; ++i) {
				assertEquals(message+" at index "+i, desiredValues[i], this.eventList.get(i).getValue()); 
			}
		}

		public void assertOldValues(String message, AttributeValue... desiredValues) {
			assertEquals(message, desiredValues.length, this.eventList.size());
			for(int i=0; i<desiredValues.length; ++i) {
				assertEquals(message+" at index "+i, desiredValues[i], this.eventList.get(i).getOldValue()); 
			}
		}

		public void assertAttributes(String message, Attribute... desiredAttributes) {
			assertEquals(message, desiredAttributes.length, this.eventList.size());
			for(int i=0; i<desiredAttributes.length; ++i) {
				assertEquals(message+" at index "+i, desiredAttributes[i], this.eventList.get(i).getAttribute()); 
			}
		}

	}

}
