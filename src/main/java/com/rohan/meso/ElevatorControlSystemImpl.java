package com.rohan.meso;

import com.rohan.meso.Elevator.ElevatorStatus;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Queue;

public class ElevatorControlSystemImpl implements ElevatorControlSystem {

  public static List<Elevator> elevatorList = Lists.newArrayList();
  public static Queue<Pickup> pickups = Lists.newLinkedList();
  private int numElevators;
  private int numFloors;

  public ElevatorControlSystemImpl(int numElevators, int numFloors) {
    this.numElevators = numElevators;
    this.numFloors = numFloors;
    initElevators(numElevators);
  }

  private void initElevators(int numElevators) {
    for (int i = 0; i < numElevators; i++){
      elevatorList.add(new Elevator(i, numFloors));
    }
  }

  /**
   * Querying the state of the elevators (what floor are they on and where they
   * are going),
   */
  @Override
  public ElevatorStatus status(int id) {
    return elevatorList.get(id).getStatus();
  }

  @Override
  public void update(int elevatorId, int destination) {
    Elevator elevator = elevatorList.get(elevatorId);
    elevator.addRequest(destination);
  }

  @Override
  public void pickup(int floor, ElevatorState direction) {
    pickups.add(new Pickup(floor, direction));
  }

  /**
   * A unit of time in the Elevator Control System.
   */
  @Override
  public void step() {

    for (Elevator e : elevatorList) {
      ElevatorState state = e.getDirection();
      switch (state) {
        case GOING_UP:
          e.moveUp();
          e.processFloor();
          attemptPickup(e);
          break;
        case GOING_DOWN:
          e.moveDown();
          e.processFloor();
          attemptPickup(e);
          break;
        case STILL:
          attemptPickup(e);
          break;
      }
    }
  }

  /**
   * Take requests from waiting passengers
   */
  private void attemptPickup(Elevator e) {
    if (!pickups.isEmpty() && e.addDestination(pickups.peek())) {
      pickups.remove();
    }
  }
}

  class Pickup {
    public final int floor;
    public final ElevatorState direction;

    public Pickup(int floor, ElevatorState direction) {
      this.floor = floor;
      this.direction = direction;
    }
  }
