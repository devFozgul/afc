/**
 * 
 * fr.utbm.v3g.core.math.Tuple3d.java
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
package org.arakhne.afc.math.geometry.d3.continuous;


import org.arakhne.afc.math.geometry.d3.FunctionalTuple3D;
import org.arakhne.afc.math.geometry.d3.Tuple3D;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author Olivier LAMOTTE (olivier.lamotte@utbm.fr)
 *
 */
public class Tuple3d<T extends Tuple3D<? super T>> implements FunctionalTuple3D<T> {

	
	private static final long serialVersionUID = -7349241147386340909L;
	
	
	protected DoubleProperty xProperty;
	protected DoubleProperty yProperty;
	protected DoubleProperty zProperty;
	
	public Tuple3d() {
		this(0, 0, 0);
	}
	
	public Tuple3d(Tuple3d<?> t) {
		this(t.getX(), t.getY(), t.getZ());
	}
	
	public Tuple3d(double x, double y, double z) {
		this.xProperty = new SimpleDoubleProperty(x);
		this.yProperty = new SimpleDoubleProperty(y);
		this.zProperty = new SimpleDoubleProperty(z);
	}
	
	public void setX(double x) {
		this.xProperty.set(x);
	}
	public void setY(double y) {
		this.yProperty.set(y);
	}
	public void setZ(double z) {
		this.zProperty.set(z);
	}
	
	public void set(double x, double y, double z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}
	
	public void set(Tuple3d<?> v) {
		this.set(v.getX(), v.getY(), v.getZ());
	}
	
	
	public DoubleProperty xProperty() {
		return this.xProperty;
	}
	public DoubleProperty yProperty() {
		return this.yProperty;
	}
	public DoubleProperty zProperty() {
		return this.zProperty;
	}
	
	public double getX() {
		return this.xProperty.doubleValue();
	}
	public double getY() {
		return this.yProperty.doubleValue();
	}
	public double getZ() {
		return this.zProperty.doubleValue();
	}
	
		

	public String toString() {
		return this.getX()+"  "+this.getY()+"  "+this.getZ();
	}

	/* (non-Javadoc)
	 * @see org.arakhne.afc.math.geometry.d3.Tuple3D#clone()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T clone() {
		try {
			return (T)super.clone();
		}
		catch(CloneNotSupportedException e) {
			throw new Error(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		long bits = 1;
		bits = 31 * bits + Double.doubleToLongBits(this.getX());
		bits = 31 * bits + Double.doubleToLongBits(this.getY());
		bits = 31 * bits + Double.doubleToLongBits(this.getZ());
		int b = (int) bits;
		return b ^ (b >> 32);
	}
}
