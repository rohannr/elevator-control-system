package com.rohan.meso;

import com.rohan.meso.Elevator.ElevatorStatus;

public interface ElevatorControlSystem {

  /**
   * Querying the state of the elevators (what floor are they on and where they are going),
   * @param elevatorId
   * @return
   */
  ElevatorStatus status(int elevatorId);

  /**
   * Equivalent to pushing a button on the pad to set the floor to go to
   * @param elevatorId
   * @param destination
   */
  void update(int elevatorId, int destination);

  /**
   * Request an elevator specifying direction of travel
   * @param floor
   * @param direction
   */
  void pickup(int floor, ElevatorState direction);

  /**
   * Unit of elevator operation.
   */
  void step();
}
