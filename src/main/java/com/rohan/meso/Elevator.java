package com.rohan.meso;

import com.google.common.collect.Lists;
import com.google.common.collect.MinMaxPriorityQueue;

import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

public class Elevator {

  static final Comparator<Integer> ascend = new Comparator<Integer>() {
    @Override
    public int compare(Integer a, Integer b) {
      return a.compareTo(b);
    }
  };
  static final Comparator<Integer> descend = Collections.reverseOrder(ascend);

  private final int id;
  private int currentFloor;
  private ElevatorState direction;
  private int numFloors;

  public MinMaxPriorityQueue<Integer> getDestinations() {
    return destinations;
  }

  private MinMaxPriorityQueue<Integer> destinations;
  private Queue<Integer> requests;

  public Elevator(int id, int numFloors) {
    this.id = id;
    this.currentFloor = 1;
    this.numFloors = numFloors;
    this.direction = ElevatorState.STILL;
    this.destinations = MinMaxPriorityQueue.create();
    this.requests = Lists.newLinkedList();
  }

  public void moveUp() {
    this.currentFloor++;
  }

  public void moveDown() {
    this.currentFloor--;
  }

  public ElevatorStatus getStatus() {
    return new ElevatorStatus(id, currentFloor, direction);
  }

  public ElevatorState getDirection() {
    return direction;
  }

  public void setDirection(ElevatorState direction) {
    this.direction = direction;
  }

  public void addRequest(int floor) {
    direction = floor > currentFloor ? ElevatorState.GOING_UP : ElevatorState.GOING_DOWN;
    destinations.add(floor);
  }

  /**
   * Add a destination only if elevator is idle or if destination is
   * along the way to the ultimate destination (topmost or ground floor depending
   * on direction)
   * @return
   */
  public boolean addDestination(Pickup pickup) {
    int dest  = pickup.floor;
    ElevatorState targetDirection = pickup.direction;
    if (destinations.isEmpty()) {
      if (currentFloor < dest) {
        direction = ElevatorState.GOING_UP;
        destinations = MinMaxPriorityQueue.create();
      } else {
        direction = ElevatorState.GOING_DOWN;
        destinations = MinMaxPriorityQueue.orderedBy(descend).create();
      }
      destinations.add(dest);
      return true;
    } else if (direction == ElevatorState.GOING_UP) {
      if (dest > currentFloor && dest < numFloors &&
          direction == targetDirection) {
        destinations.add(dest);
        return true;
      }
    } else if (direction == ElevatorState.GOING_DOWN) {
      if (dest < currentFloor && dest >= 1) {
        destinations.add(dest);
        return true;
      }
    }
    return false;
  }

  /**
   * Open doors to accept or drop off passengers.
   */
  public void processFloor() {
    if (destinations.isEmpty()) {
      return;
    }
    // if any of the destinations are on the current floor
    while (destinations.peek() != null && destinations.peek() == currentFloor) {
      System.out.println("Stopping at floor" + destinations.pollFirst());
    }
    direction = destinations.isEmpty() ? ElevatorState.STILL : direction;
  }

  public static class ElevatorStatus {

    public int id;
    public int floor;
    public ElevatorState direction;

    public ElevatorStatus(int id, int floor, ElevatorState direction) {
      this.id = id;
      this.floor = floor;
      this.direction = direction;
    }
  }
}
