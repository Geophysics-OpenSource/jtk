/****************************************************************************
Copyright (c) 2004, Colorado School of Mines and others. All rights reserved.
This program and accompanying materials are made available under the terms of
the Common Public License - v1.0, which accompanies this distribution, and is 
available at http://www.eclipse.org/legal/cpl-v10.html
****************************************************************************/
package edu.mines.jtk.sgl;

import java.util.*;

/**
 * A set of OpenGL states. State sets can be associated with nodes in
 * a scene graph. During the draw process for a node, these states are 
 * applied in the method {@link Node#drawBegin(DrawContext)}, before
 * the method {@link Node#draw(DrawContext)} is called.
 * <p>
 * If two states of the same class with the same attributes are added
 * to a state set, then the order in which those states are applied
 * is undefined. Generally, 
 * Nothing prevents two states of the same class from being added to
 * the same state set. Howev
 * @author Dave Hale, Colorado School of Mines
 * @version 2005.05.31
 */
public class StateSet implements State {

  /**
   * Adds the specified state to this set.
   * @param state the state.
   */
  public void add(State state) {
    _states.add(state);
  }

  /**
   * Removes the specified state from this set.
   * @param state the state.
   */
  public void remove(State state) {
    _states.remove(state);
  }

  /**
   * Determines whether this set contains a state of the specified class.
   * @param stateClass the state class.
   * @return true; if this state contains such a state; false, otherwise.
   */
  public boolean contains(Class<?> stateClass) {
    return find(stateClass)!=null;
  }

  /**
   * Finds a state in this set of the specified class.
   * @param stateClass the state class.
   * @return the state; null, if the set contains no such state.
   */
  public State find(Class<?> stateClass) {
    for (State s : _states) {
      if (s.getClass().equals(stateClass))
        return s;
    }
    return null;
  }

  /**
   * Gets an iterator for all states in this set.
   * @return the iterator.
   */
  public Iterator<State> getStates() {
    return _states.iterator();
  }

  /**
   * Applies all states in this set.
   */
  public void apply() {
    for (State s : _states)
      s.apply();
  }

  /**
   * Gets the combined attribute bits for all states in this set.
   * @return the attribute bits.
   */
  public int getAttributeBits() {
    int attributeBits = 0;
    for (State state : _states)
      attributeBits |= state.getAttributeBits();
    return attributeBits;
  }

  ///////////////////////////////////////////////////////////////////////////
  // private

  private Set<State> _states = new HashSet<State>();
}
