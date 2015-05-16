package com.rohan.meso;

import com.google.common.collect.Lists;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ElevatorControlSystemTest {

  private ElevatorControlSystemImpl elevatorControlSystem;
  @Before
  public void setUp() {
    elevatorControlSystem = new ElevatorControlSystemImpl(3,10);
  }

  @After
  public void tearDown() {
    elevatorControlSystem.elevatorList = Lists.newArrayList();
  }

  @Test
  public void testSimple() throws Exception {
    elevatorControlSystem.pickup(10, ElevatorState.GOING_DOWN);
    stepN(10);
    Assert.assertEquals(elevatorControlSystem.status(0).floor, 10);
  }

  @Test
  public void testTwoRequests() throws Exception {
    elevatorControlSystem.pickup(10, ElevatorState.GOING_DOWN);
    elevatorControlSystem.pickup(8, ElevatorState.GOING_DOWN);
    stepN(10);
    Assert.assertEquals(elevatorControlSystem.status(0).floor, 10);
  }

  @Test
  public void testTwoElevators() throws Exception {
    elevatorControlSystem.pickup(10, ElevatorState.GOING_DOWN);
    stepN(7);
    elevatorControlSystem.pickup(4, ElevatorState.GOING_DOWN);
    stepN(4);
    Assert.assertEquals(elevatorControlSystem.status(0).floor, 10);
    Assert.assertEquals(elevatorControlSystem.status(0).direction,
                        ElevatorState.STILL);
    Assert.assertEquals(elevatorControlSystem.status(1).floor, 4);
    Assert.assertEquals(elevatorControlSystem.status(1).direction, ElevatorState.STILL);
  }

  @Test
  public void testElevatorBothDirections() throws Exception {
    elevatorControlSystem.pickup(5, ElevatorState.GOING_UP);
    stepN(5);
    elevatorControlSystem.update(0, 7); // e1 going to 7th floor
    elevatorControlSystem.pickup(3, ElevatorState.GOING_DOWN);
    stepN(3);
    Assert.assertEquals(elevatorControlSystem.status(0).floor, 7);
    Assert.assertEquals(elevatorControlSystem.status(0).direction, ElevatorState.STILL);
    Assert.assertEquals(elevatorControlSystem.status(1).floor, 3);
    Assert.assertEquals(elevatorControlSystem.status(1).direction, ElevatorState.STILL);
  }

  private void stepN(int n) {
    for (int i = 0; i < n; i++) {
      elevatorControlSystem.step();
    }
  }
}
