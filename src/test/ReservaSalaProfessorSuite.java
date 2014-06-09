package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.control.ManageReserveRoomTeacherTest;
import test.model.TeacherReserveRoomTest;
import test.persistence.RoomTeacherReservationDAOTest;

@RunWith(Suite.class)

@SuiteClasses({TeacherReserveRoomTest.class, RoomTeacherReservationDAOTest.class, ManageReserveRoomTeacherTest.class})

public class ReservaSalaProfessorSuite {

}
