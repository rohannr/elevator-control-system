# elevator-control-system

Design and implement an elevator control system. What data structures, interfaces and algorithms will you need? Your elevator control system should be able to handle a few elevators — up to 16.

You can use the language of your choice to implement an elevator control system. In the end, your control system should provide an interface for:

    Querying the state of the elevators (what floor are they on and where they are going),
    receiving an update about the status of an elevator,
    receiving a pickup request,
    time-stepping the simulation.


<h1>Design</h1>
Updated interface
```java
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
   * @param directionhan/meso/ElevatorControlSystem.java" 32L, 720C
   */
  void pickup(int floor, ElevatorState direction);

  /**
   * Unit of elevator operation.
   */
  void step();
```

<h2> Scheduling </h2>

The naive FCFS algorithm for handling elevator requests would be unfair and inefficient. Instead we use a modified version of a First Come - First Server approach with the initial movement in the direction of the pickup request. As additional pickup requests arrive, requests are serviced only in the current direction of the elevator until the it reaches either the topmost or ground floor. When this happens, the direction of the arm reverses, and the requests that were remaining in the opposite direction are serviced. For the implementation, a MinMaxPriorityQueue is used to facilitate easy lookup of the closest destination along the direction of movement. The priority order is reversed on reversing direction.


<h2> Improvements </h2>

Bounds checking, error handling.
