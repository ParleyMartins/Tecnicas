package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.control.ManageReserveRoomStudentTest;
import test.model.StudentReserveRoomTest;
import test.persistence.RoomStudentReservationDAOTest;

@RunWith(Suite.class)
@SuiteClasses({StudentReserveRoomTest.class, RoomStudentReservationDAOTest.class, ManageReserveRoomStudentTest.class})
public class ReservaSalaAlunoSuite {

}
