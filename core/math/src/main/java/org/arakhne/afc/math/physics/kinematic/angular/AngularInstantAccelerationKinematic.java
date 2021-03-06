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

package org.arakhne.afc.math.physics.kinematic.angular;

import org.arakhne.afc.math.physics.AngularUnit;

/**
 * This interface describes an object that is able to
 * provide angular instant speed, velocity, and acceleration.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public interface AngularInstantAccelerationKinematic extends AngularInstantVelocityKinematic {

	/**
	 * Returns the angular acceleration of this object in r/s^2.
	 *
	 * <p>The sign of the acceleration indicates if the object
	 * is accelerating (positive) or decelerating (negative).
	 *
	 * <p>The replied value is in <code>[-d;a]</code> where:<ul>
	 * <li><code>d</code> is the max deceleration value, replied
	 * by <code>getMaxAngularDeceleration</code>.</li>
	 * <li><code>a</code> is the max acceleration value, replied
	 * by <code>getMaxAngularAcceleration</code>.</li>
	 * </ul>
	 *
	 * @return the angular acceleration of this object in r/s^2.
	 */
	double getAngularAcceleration();

	/**
	 * Returns the angular acceleration of this object in the acceleration corresponding to the given speed unit
	 * e.g if the speed unit is m/s the acceleration will be given in m/s^2
	 *
	 * <p>The sign of the acceleration indicates if the object
	 * is accelerating (positive) or decelerating (negative).
	 *
	 * <p>The replied value is in <code>[-d;a]</code> where:<ul>
	 * <li><code>d</code> is the max deceleration value, replied
	 * by <code>getMaxAngularDeceleration</code>.</li>
	 * <li><code>a</code> is the max acceleration value, replied
	 * by <code>getMaxAngularAcceleration</code>.</li>
	 * </ul>
	 *
	 * @param unit the unit in which the speed will be given.
	 * @return the angular acceleration of this object in the given unit.
	 */
	double getAngularAcceleration(AngularUnit unit);

}
