/* 
 * $Id$
 * 
 * Copyright (C) 2005-09 Stephane GALLAND.
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
package org.arakhne.afc.math.geometry.d2.fx;

import org.arakhne.afc.math.MathUtil;
import org.arakhne.afc.math.geometry.PathElementType;
import org.arakhne.afc.math.geometry.d2.afp.PathElement2afp;
import org.eclipse.xtext.xbase.lib.Pure;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

/** An element of the path.
 *
 * @author $Author: sgalland$
 * @author $Author: hjaffali$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public abstract class PathElement2fx implements PathElement2afp {
	
	private static final long serialVersionUID = 1724746568685625149L;

	/** Create an instance of path element.
	 * 
	 * @param type is the type of the new element.
	 * @param lastX is the coordinate of the last point.
	 * @param lastY is the coordinate of the last point.
	 * @param coords are the coordinates.
	 * @return the instance of path element.
	 */
	@Pure
	public static PathElement2fx newInstance(PathElementType type, DoubleProperty lastX, DoubleProperty lastY, DoubleProperty[] coords) {
		assert (type != null) : "Path element type must be not null"; //$NON-NLS-1$
		assert (lastX != null) : "lastX must be not null"; //$NON-NLS-1$
		assert (lastY != null) : "lastY must be not null"; //$NON-NLS-1$
		assert (coords != null) : "Coordinates must be not null"; //$NON-NLS-1$
		assert (coords.length >= 2) : "Coordinates size is too small"; //$NON-NLS-1$
		switch(type) {
		case MOVE_TO:
			return new MovePathElement2fx(coords[0], coords[1]);
		case LINE_TO:
			return new LinePathElement2fx(lastX, lastY, coords[0], coords[1]);
		case QUAD_TO:
			assert (coords.length >= 4) : "Coordinates size is too small"; //$NON-NLS-1$
			return new QuadPathElement2fx(lastX, lastY, coords[0], coords[1], coords[2], coords[3]);
		case CURVE_TO:
			assert (coords.length >= 6) : "Coordinates size is too small"; //$NON-NLS-1$
			return new CurvePathElement2fx(lastX, lastY, coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
		case CLOSE:
			return new ClosePathElement2fx(lastX, lastY, coords[0], coords[1]);
		default:
		}
		throw new IllegalArgumentException();
	}
	
	/** Type of the element.
	 */
	protected final PathElementType type;
	
	/** Target point.
	 */
	protected final DoubleProperty toX;
	
	/** Target point.
	 */
	protected final DoubleProperty toY;
	
	/** Is Empty property.
	 */
	protected ReadOnlyBooleanWrapper isEmpty;

	/**
	 * @param type is the type of the element.
	 * @param tox the x coordinate of the target point.
	 * @param toy the x coordinate of the target point.
	 */
	PathElement2fx(PathElementType type, DoubleProperty tox, DoubleProperty toy) {
		assert (type != null) : "Path element type must be not null"; //$NON-NLS-1$
		assert (tox != null) : "toX must be not null"; //$NON-NLS-1$
		assert (toy != null) : "toY must be not null"; //$NON-NLS-1$
		this.type = type;
		this.toX = tox;
		this.toY = toy;
	}

	@Pure
	@Override
	public abstract boolean equals(Object obj);
	
	@Pure
	@Override
	public abstract int hashCode();

	/** Replies the property that indicates if this patth element is empty.
	 *
	 * @return the isEmpty property.
	 */
	public abstract BooleanProperty isEmptyProperty();
	
	@Override
	public boolean isEmpty() {
		return isEmptyProperty().get();
	}
	
	@Pure
	@Override
	public final double getToX() {
		return this.toX.get();
	}

	@Pure
	@Override
	public final double getToY() {
		return this.toY.get();
	}

	/** Replies the x coordinate of the starting point property.
	 *
	 * @return the x coordinate, or <code>null</code> if the type is {@link PathElementType#MOVE_TO}.
	 */
	@Pure
	public abstract DoubleProperty fromXProperty();

	/** Replies the y coordinate of the starting point property.
	 *
	 * @return the y coordinate, or <code>null</code> if the type is {@link PathElementType#MOVE_TO}.
	 */
	@Pure
	public abstract DoubleProperty fromYProperty();

	/** Replies the x coordinate of the first control point property.
	 *
	 * @return the x coordinate, or <code>null</code> if the type is {@link PathElementType#MOVE_TO},
	 * {@link PathElementType#LINE_TO}, or {@link PathElementType#CLOSE}.
	 */
	@Pure
	public abstract DoubleProperty ctrlX1Property();

	/** Replies the y coordinate of the first control point property.
	 *
	 * @return the y coordinate, or {@link Double#NaN} if the type is {@link PathElementType#MOVE_TO},
	 * {@link PathElementType#LINE_TO}, or {@link PathElementType#CLOSE}.
	 */
	@Pure
	public abstract DoubleProperty ctrlY1Property();

	/** Replies the x coordinate of the second control point property.
	 *
	 * @return the x coordinate, or <code>null</code> if the type is {@link PathElementType#MOVE_TO},
	 * {@link PathElementType#LINE_TO}, {@link PathElementType#QUAD_TO}, or {@link PathElementType#CLOSE}.
	 */
	@Pure
	public abstract DoubleProperty ctrlX2Property();

	/** Replies the y coordinate of the second  control point property.
	 *
	 * @return the y coordinate, or <code>null</code> if the type is {@link PathElementType#MOVE_TO},
	 * {@link PathElementType#LINE_TO}, {@link PathElementType#QUAD_TO}, or {@link PathElementType#CLOSE}.
	 */
	@Pure
	public abstract DoubleProperty ctrlY2Property();

	/** Replies the x coordinate of the target point property.
	 *
	 * @return the x coordinate.
	 */
	@Pure
	public DoubleProperty toXProperty() {
		return this.toX;
	}

	/** Replies the y coordinate of the target point property.
	 *
	 * @return the y coordinate.
	 */
	@Pure
	public DoubleProperty toYProperty() {
		return this.toY;
	}

	@Pure
	@Override
	public final PathElementType getType() {
		return this.type;
	}

	/** Copy the coords into the given array, except the source point.
	 * 
	 * @param array
	 */
	@Pure
	public abstract void toArray(DoubleProperty[] array);

	/** Copy the coords into an array, except the source point.
	 * 
	 * @return the array of the points, except the source point.
	 */
	@Pure
	public abstract DoubleProperty[] toArray();

	/** An element of the path that represents a <code>MOVE_TO</code>.
	 *
	 * @author $Author: sgalland$
	 * @author $Author: hjaffali$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 13.0
	 */
	static class MovePathElement2fx extends PathElement2fx {
		
		private static final long serialVersionUID = 4465791748559255427L;

		/**
		 * @param x
		 * @param y
		 */
		public MovePathElement2fx(DoubleProperty x, DoubleProperty y) {
			super(PathElementType.MOVE_TO, x, y);
		}

		@Pure
		@Override
		public boolean equals(Object obj) {
			try {
				PathElement2afp elt = (PathElement2afp) obj;
				return getType() == elt.getType()
						&& getToX() == elt.getToX()
						&& getToY() == elt.getToY();
			} catch (Throwable exception) {
				//
			}
			return false;
		}

		@Pure
		@Override
		public int hashCode() {
			long bits = 1L;
			bits = 31L * bits + this.type.ordinal();
			bits = 31L * bits + Double.doubleToLongBits(getToX());
			bits = 31L * bits + Double.doubleToLongBits(getToY());
			return (int) (bits ^ (bits >> 32));
		}

		@Pure
		@Override
		public BooleanProperty isEmptyProperty() {
			if (this.isEmpty == null) {
				this.isEmpty = new ReadOnlyBooleanWrapper(this, "isEmpty", true); //$NON-NLS-1$
			}
			return this.isEmpty;
		}

		@Pure
		@Override
		public boolean isDrawable() {
			return false;
		}
		
		@Pure
		@Override
		public void toArray(int[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 2) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.toX.intValue();
			array[1] = this.toY.intValue();
		}
		
		@Pure
		@Override
		public void toArray(double[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 2) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.toX.doubleValue();
			array[1] = this.toY.doubleValue();
		}

		@Pure
		@Override
		public void toArray(DoubleProperty[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 2) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.toX;
			array[1] = this.toY;
		}

		@Pure
		@Override
		public DoubleProperty[] toArray() {
			return new DoubleProperty[] {this.toX, this.toY};
		}

		@Pure
		@Override
		public String toString() {
			return "MOVE("+ //$NON-NLS-1$
					this.toX+"x"+ //$NON-NLS-1$
					this.toY+")"; //$NON-NLS-1$
		}

		@Pure
		@Override
		public double getFromX() {
			return 0.;
		}

		@Pure
		@Override
		public double getFromY() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlX1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlX2() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY2() {
			return 0.;
		}

		@Pure
		@Override
		public DoubleProperty fromXProperty() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty fromYProperty() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX2Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY2Property() {
			return null;
		}

	}
	
	/** An element of the path that represents a <code>LINE_TO</code>.
	 *
	 * @author $Author: sgalland$
	 * @author $Author: hjaffali$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 13.0
	 */
	static class LinePathElement2fx extends PathElement2fx {
		
		private static final long serialVersionUID = -8828290765080530997L;

		private final DoubleProperty fromX;
		
		private final DoubleProperty fromY;
		
		/**
		 * @param fromx
		 * @param fromy
		 * @param tox
		 * @param toy
		 */
		public LinePathElement2fx(DoubleProperty fromx, DoubleProperty fromy, DoubleProperty tox, DoubleProperty toy) {
			super(PathElementType.LINE_TO, tox, toy);
			assert (fromx != null) : "fromX must be not null"; //$NON-NLS-1$
			assert (fromy != null) : "fromY must be not null"; //$NON-NLS-1$
			this.fromX = fromx;
			this.fromY = fromy;
		}

		@Pure
		@Override
		public boolean equals(Object obj) {
			try {
				PathElement2afp elt = (PathElement2afp) obj;
				return getType() == elt.getType()
						&& getToX() == elt.getToX()
						&& getToY() == elt.getToY()
						&& getFromX() == elt.getFromX()
						&& getFromY() == elt.getFromY();
			} catch (Throwable exception) {
				//
			}
			return false;
		}

		@Pure
		@Override
		public int hashCode() {
			long bits = 1L;
			bits = 31L * bits + this.type.ordinal();
			bits = 31L * bits + Double.doubleToLongBits(getToX());
			bits = 31L * bits + Double.doubleToLongBits(getToY());
			bits = 31L * bits + Double.doubleToLongBits(getFromX());
			bits = 31L * bits + Double.doubleToLongBits(getFromY());
			return (int) (bits ^ (bits >> 32));
		}

		@Pure
		@Override
		public BooleanProperty isEmptyProperty() {
			if (this.isEmpty == null) {
				this.isEmpty = new ReadOnlyBooleanWrapper(this, "isEmpty"); //$NON-NLS-1$
				this.isEmpty.bind(Bindings.createBooleanBinding(
						() -> {
							return MathUtil.isEpsilonEqual(fromXProperty().get(), toXProperty().get())
									&& MathUtil.isEpsilonEqual(fromYProperty().get(), toYProperty().get());
						}, fromXProperty(), toXProperty(), fromYProperty(), toYProperty()));
			}
			return this.isEmpty;
		}

		@Pure
		@Override
		public boolean isDrawable() {
			return !isEmpty();
		}

		@Pure
		@Override
		public void toArray(int[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 2) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.toX.intValue();
			array[1] = this.toY.intValue();
		}
		
		@Pure
		@Override
		public void toArray(double[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 2) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.toX.doubleValue();
			array[1] = this.toY.doubleValue();
		}

		@Pure
		@Override
		public void toArray(DoubleProperty[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 2) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.toX;
			array[1] = this.toY;
		}

		@Pure
		@Override
		public DoubleProperty[] toArray() {
			return new DoubleProperty[] {this.toX, this.toY};
		}

		@Pure
		@Override
		public String toString() {
			return "LINE("+ //$NON-NLS-1$
					this.toX+"x"+ //$NON-NLS-1$
					this.toY+")"; //$NON-NLS-1$
		}

		@Pure
		@Override
		public double getFromX() {
			return this.fromX.get();
		}

		@Pure
		@Override
		public double getFromY() {
			return this.fromY.get();
		}

		@Pure
		@Override
		public double getCtrlX1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlX2() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY2() {
			return 0.;
		}

		@Pure
		@Override
		public DoubleProperty fromXProperty() {
			return this.fromX;
		}

		@Pure
		@Override
		public DoubleProperty fromYProperty() {
			return this.fromY;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX2Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY2Property() {
			return null;
		}

	}
	
	/** An element of the path that represents a <code>QUAD_TO</code>.
	 *
	 * @author $Author: sgalland$
	 * @author $Author: hjaffali$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 13.0
	 */
	static class QuadPathElement2fx extends PathElement2fx {
		
		private static final long serialVersionUID = 4782822639304211439L;

		private final DoubleProperty fromX;
		
		private final DoubleProperty fromY;

		private final DoubleProperty ctrlX;
		
		private final DoubleProperty ctrlY;

		/**
		 * @param fromx
		 * @param fromy
		 * @param ctrlx
		 * @param ctrly
		 * @param tox
		 * @param toy
		 */
		public QuadPathElement2fx(DoubleProperty fromx, DoubleProperty fromy, DoubleProperty ctrlx, DoubleProperty ctrly, DoubleProperty tox, DoubleProperty toy) {
			super(PathElementType.QUAD_TO, tox, toy);
			assert (fromx != null) : "fromX must be not null"; //$NON-NLS-1$
			assert (fromy != null) : "fromY must be not null"; //$NON-NLS-1$
			assert (ctrlx != null) : "ctrlX must be not null"; //$NON-NLS-1$
			assert (ctrly != null) : "ctrlY must be not null"; //$NON-NLS-1$
			this.fromX = fromx;
			this.fromY = fromy;
			this.ctrlX = ctrlx;
			this.ctrlY = ctrly;
		}

		@Pure
		@Override
		public boolean equals(Object obj) {
			try {
				PathElement2afp elt = (PathElement2afp) obj;
				return getType() == elt.getType()
						&& getToX() == elt.getToX()
						&& getToY() == elt.getToY()
						&& getCtrlX1() == elt.getCtrlX1()
						&& getCtrlY1() == elt.getCtrlY1()
						&& getFromX() == elt.getFromX()
						&& getFromY() == elt.getFromY();
			} catch (Throwable exception) {
				//
			}
			return false;
		}

		@Pure
		@Override
		public int hashCode() {
			long bits = 1L;
			bits = 31L * bits + this.type.ordinal();
			bits = 31L * bits + Double.doubleToLongBits(getToX());
			bits = 31L * bits + Double.doubleToLongBits(getToY());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlX1());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlY1());
			bits = 31L * bits + Double.doubleToLongBits(getFromX());
			bits = 31L * bits + Double.doubleToLongBits(getFromY());
			return (int) (bits ^ (bits >> 32));
		}

		@Pure
		@Override
		public BooleanProperty isEmptyProperty() {
			if (this.isEmpty == null) {
				this.isEmpty = new ReadOnlyBooleanWrapper(this, "isEmpty"); //$NON-NLS-1$
				this.isEmpty.bind(Bindings.createBooleanBinding(
						() -> {
							return MathUtil.isEpsilonEqual(fromXProperty().get(), toXProperty().get())
									&& MathUtil.isEpsilonEqual(fromYProperty().get(), toYProperty().get())
									&& MathUtil.isEpsilonEqual(ctrlX1Property().get(), toXProperty().get())
									&& MathUtil.isEpsilonEqual(ctrlY1Property().get(), toYProperty().get());
						}, fromXProperty(), toXProperty(), fromYProperty(), toYProperty()));
			}
			return this.isEmpty;
		}

		@Pure
		@Override
		public boolean isDrawable() {
			return !isEmpty();
		}

		@Pure
		@Override
		public void toArray(int[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 4) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.ctrlX.intValue();
			array[1] = this.ctrlY.intValue();
			array[2] = this.toX.intValue();
			array[3] = this.toY.intValue();
		}
		
		@Pure
		@Override
		public void toArray(double[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 4) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.ctrlX.doubleValue();
			array[1] = this.ctrlY.doubleValue();
			array[2] = this.toX.doubleValue();
			array[3] = this.toY.doubleValue();
		}

		@Pure
		@Override
		public void toArray(DoubleProperty[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 4) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.ctrlX;
			array[1] = this.ctrlY;
			array[2] = this.toX;
			array[3] = this.toY;
		}

		@Pure
		@Override
		public DoubleProperty[] toArray() {
			return new DoubleProperty[] {this.ctrlX, this.ctrlY, this.toX, this.toY};
		}

		@Pure
		@Override
		public String toString() {
			return "QUAD("+ //$NON-NLS-1$
					this.ctrlX+"x"+ //$NON-NLS-1$
					this.ctrlY+"|"+ //$NON-NLS-1$
					this.toX+"x"+ //$NON-NLS-1$
					this.toY+")"; //$NON-NLS-1$
		}

		@Pure
		@Override
		public double getFromX() {
			return this.fromX.get();
		}

		@Pure
		@Override
		public double getFromY() {
			return this.fromY.get();
		}

		@Pure
		@Override
		public double getCtrlX1() {
			return this.ctrlX.get();
		}

		@Pure
		@Override
		public double getCtrlY1() {
			return this.ctrlY.get();
		}

		@Pure
		@Override
		public double getCtrlX2() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY2() {
			return 0.;
		}

		@Pure
		@Override
		public DoubleProperty fromXProperty() {
			return this.fromX;
		}

		@Pure
		@Override
		public DoubleProperty fromYProperty() {
			return this.fromY;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX1Property() {
			return this.ctrlX;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY1Property() {
			return this.ctrlY;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX2Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY2Property() {
			return null;
		}

	}

	/** An element of the path that represents a <code>CURVE_TO</code>.
	 *
	 * @author $Author: sgalland$
	 * @author $Author: hjaffali$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 13.0
	 */
	static class CurvePathElement2fx extends PathElement2fx {
		
		private static final long serialVersionUID = -2831895270995173092L;

		private final DoubleProperty fromX;
		
		private final DoubleProperty fromY;

		private final DoubleProperty ctrlX1;
		
		private final DoubleProperty ctrlY1;

		private final DoubleProperty ctrlX2;
		
		private final DoubleProperty ctrlY2;

		/**
		 * @param fromx
		 * @param fromy
		 * @param ctrlx1
		 * @param ctrly1
		 * @param ctrlx2
		 * @param ctrly2
		 * @param tox
		 * @param toy
		 */
		public CurvePathElement2fx(DoubleProperty fromx, DoubleProperty fromy, DoubleProperty ctrlx1, DoubleProperty ctrly1, DoubleProperty ctrlx2, DoubleProperty ctrly2, DoubleProperty tox, DoubleProperty toy) {
			super(PathElementType.CURVE_TO, tox, toy);
			assert (fromx != null) : "fromX must be not null"; //$NON-NLS-1$
			assert (fromy != null) : "fromY must be not null"; //$NON-NLS-1$
			assert (ctrlx1 != null) : "ctrlX1 must be not null"; //$NON-NLS-1$
			assert (ctrly1 != null) : "ctrlY1 must be not null"; //$NON-NLS-1$
			assert (ctrlx2 != null) : "ctrlX2 must be not null"; //$NON-NLS-1$
			assert (ctrly2 != null) : "ctrlY2 must be not null"; //$NON-NLS-1$
			this.fromX = fromx;
			this.fromY = fromy;
			this.ctrlX1 = ctrlx1;
			this.ctrlY1 = ctrly1;
			this.ctrlX2 = ctrlx2;
			this.ctrlY2 = ctrly2;
		}

		@Pure
		@Override
		public boolean equals(Object obj) {
			try {
				PathElement2afp elt = (PathElement2afp) obj;
				return getType() == elt.getType()
						&& getToX() == elt.getToX()
						&& getToY() == elt.getToY()
						&& getCtrlX1() == elt.getCtrlX1()
						&& getCtrlY1() == elt.getCtrlY1()
						&& getCtrlX2() == elt.getCtrlX2()
						&& getCtrlY2() == elt.getCtrlY2()
						&& getFromX() == elt.getFromX()
						&& getFromY() == elt.getFromY();
			} catch (Throwable exception) {
				//
			}
			return false;
		}

		@Pure
		@Override
		public int hashCode() {
			long bits = 1L;
			bits = 31L * bits + this.type.ordinal();
			bits = 31L * bits + Double.doubleToLongBits(getToX());
			bits = 31L * bits + Double.doubleToLongBits(getToY());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlX1());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlY1());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlX2());
			bits = 31L * bits + Double.doubleToLongBits(getCtrlY2());
			bits = 31L * bits + Double.doubleToLongBits(getFromX());
			bits = 31L * bits + Double.doubleToLongBits(getFromY());
			return (int) (bits ^ (bits >> 32));
		}

		@Pure
		@Override
		public BooleanProperty isEmptyProperty() {
			if (this.isEmpty == null) {
				this.isEmpty = new ReadOnlyBooleanWrapper(this, "isEmpty"); //$NON-NLS-1$
				this.isEmpty.bind(Bindings.createBooleanBinding(
						() -> {
							return MathUtil.isEpsilonEqual(fromXProperty().get(), toXProperty().get())
									&& MathUtil.isEpsilonEqual(fromYProperty().get(), toYProperty().get())
									&& MathUtil.isEpsilonEqual(ctrlX1Property().get(), toXProperty().get())
									&& MathUtil.isEpsilonEqual(ctrlY1Property().get(), toYProperty().get())
									&& MathUtil.isEpsilonEqual(ctrlX2Property().get(), toXProperty().get())
									&& MathUtil.isEpsilonEqual(ctrlY2Property().get(), toYProperty().get());
						}, fromXProperty(), toXProperty(), fromYProperty(), toYProperty()));
			}
			return this.isEmpty;
		}

		@Pure
		@Override
		public boolean isDrawable() {
			return !isEmpty();
		}

		@Pure
		@Override
		public void toArray(int[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 6) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.ctrlX1.intValue();
			array[1] = this.ctrlY1.intValue();
			array[2] = this.ctrlX2.intValue();
			array[3] = this.ctrlY2.intValue();
			array[4] = this.toX.intValue();
			array[5] = this.toY.intValue();
		}
		
		@Pure
		@Override
		public void toArray(DoubleProperty[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 6) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.ctrlX1;
			array[1] = this.ctrlY1;
			array[2] = this.ctrlX2;
			array[3] = this.ctrlY2;
			array[4] = this.toX;
			array[5] = this.toY;
		}

		@Pure
		@Override
		public void toArray(double[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 6) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.ctrlX1.doubleValue();
			array[1] = this.ctrlY1.doubleValue();
			array[2] = this.ctrlX2.doubleValue();
			array[3] = this.ctrlY2.doubleValue();
			array[4] = this.toX.doubleValue();
			array[5] = this.toY.doubleValue();
		}

		@Pure
		@Override
		public DoubleProperty[] toArray() {
			return new DoubleProperty[] {this.ctrlX1, this.ctrlY1, this.ctrlX2, this.ctrlY2, this.toX, this.toY};
		}

		@Pure
		@Override
		public String toString() {
			return "CURVE("+ //$NON-NLS-1$
					this.ctrlX1+"x"+ //$NON-NLS-1$
					this.ctrlY1+"|"+ //$NON-NLS-1$
					this.ctrlX2+"x"+ //$NON-NLS-1$
					this.ctrlY2+"|"+ //$NON-NLS-1$
					this.toX+"x"+ //$NON-NLS-1$
					this.toY+")"; //$NON-NLS-1$
		}

		@Pure
		@Override
		public double getFromX() {
			return this.fromX.get();
		}

		@Pure
		@Override
		public double getFromY() {
			return this.fromY.get();
		}

		@Pure
		@Override
		public double getCtrlX1() {
			return this.ctrlX1.get();
		}

		@Pure
		@Override
		public double getCtrlY1() {
			return this.ctrlY1.get();
		}

		@Pure
		@Override
		public double getCtrlX2() {
			return this.ctrlX2.get();
		}

		@Pure
		@Override
		public double getCtrlY2() {
			return this.ctrlY2.get();
		}

		@Pure
		@Override
		public DoubleProperty fromXProperty() {
			return this.fromX;
		}

		@Pure
		@Override
		public DoubleProperty fromYProperty() {
			return this.fromY;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX1Property() {
			return this.ctrlX1;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY1Property() {
			return this.ctrlY1;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX2Property() {
			return this.ctrlX2;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY2Property() {
			return this.ctrlY2;
		}

	}

	/** An element of the path that represents a <code>CLOSE</code>.
	 *
	 * @author $Author: sgalland$
	 * @author $Author: hjaffali$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 13.0
	 */
	static class ClosePathElement2fx extends PathElement2fx {
		
		private static final long serialVersionUID = 5324688417590599323L;

		private final DoubleProperty fromX;
		
		private final DoubleProperty fromY;
		
		/**
		 * @param fromx
		 * @param fromy
		 * @param tox
		 * @param toy
		 */
		public ClosePathElement2fx(DoubleProperty fromx, DoubleProperty fromy, DoubleProperty tox, DoubleProperty toy) {
			super(PathElementType.CLOSE, tox, toy);
			assert (fromx != null) : "fromX must be not null"; //$NON-NLS-1$
			assert (fromy != null) : "fromY must be not null"; //$NON-NLS-1$
			this.fromX = fromx;
			this.fromY = fromy;
		}

		@Pure
		@Override
		public boolean equals(Object obj) {
			try {
				PathElement2afp elt = (PathElement2afp) obj;
				return getType() == elt.getType()
						&& getToX() == elt.getToX()
						&& getToY() == elt.getToY()
						&& getFromX() == elt.getFromX()
						&& getFromY() == elt.getFromY();
			} catch (Throwable exception) {
				//
			}
			return false;
		}

		@Pure
		@Override
		public int hashCode() {
			long bits = 1L;
			bits = 31L * bits + this.type.ordinal();
			bits = 31L * bits + Double.doubleToLongBits(getToX());
			bits = 31L * bits + Double.doubleToLongBits(getToY());
			bits = 31L * bits + Double.doubleToLongBits(getFromX());
			bits = 31L * bits + Double.doubleToLongBits(getFromY());
			return (int) (bits ^ (bits >> 32));
		}

		@Pure
		@Override
		public BooleanProperty isEmptyProperty() {
			if (this.isEmpty == null) {
				this.isEmpty = new ReadOnlyBooleanWrapper(this, "isEmpty"); //$NON-NLS-1$
				this.isEmpty.bind(Bindings.createBooleanBinding(
						() -> {
							return MathUtil.isEpsilonEqual(fromXProperty().get(), toXProperty().get())
									&& MathUtil.isEpsilonEqual(fromYProperty().get(), toYProperty().get());
						}, fromXProperty(), toXProperty(), fromYProperty(), toYProperty()));
			}
			return this.isEmpty;
		}

		@Pure
		@Override
		public boolean isDrawable() {
			return !isEmpty();
		}

		@Pure
		@Override
		public void toArray(int[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 2) : "Array size is too small"; //$NON-NLS-1$
			array[0] = (int) this.toX.get();
			array[1] = (int) this.toY.get();
		}
		
		@Pure
		@Override
		public void toArray(DoubleProperty[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 2) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.toX;
			array[1] = this.toY;
		}

		@Pure
		@Override
		public void toArray(double[] array) {
			assert (array != null) : "Array must be not null"; //$NON-NLS-1$
			assert (array.length >= 2) : "Array size is too small"; //$NON-NLS-1$
			array[0] = this.toX.get();
			array[1] = this.toY.get();
		}

		@Pure
		@Override
		public DoubleProperty[] toArray() {
			return new DoubleProperty[] { this.toX, this.toY };
		}

		@Pure
		@Override
		public String toString() {
			return "CLOSE"; //$NON-NLS-1$
		}

		@Pure
		@Override
		public double getFromX() {
			return this.fromX.get();
		}

		@Pure
		@Override
		public double getFromY() {
			return this.fromY.get();
		}

		@Pure
		@Override
		public double getCtrlX1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY1() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlX2() {
			return 0.;
		}

		@Pure
		@Override
		public double getCtrlY2() {
			return 0.;
		}

		@Pure
		@Override
		public DoubleProperty fromXProperty() {
			return this.fromX;
		}

		@Pure
		@Override
		public DoubleProperty fromYProperty() {
			return this.fromY;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY1Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlX2Property() {
			return null;
		}

		@Pure
		@Override
		public DoubleProperty ctrlY2Property() {
			return null;
		}

	}

}