package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.control.ManterResSalaProfessorTest;
import test.model.TeacherReserveRoomTest;
import test.persistence.RoomTeacherReservationDAOTest;

@RunWith(Suite.class)
@SuiteClasses({TeacherReserveRoomTest.class, RoomTeacherReservationDAOTest.class, ManterResSalaProfessorTest.class})
public class ReservaSalaProfessorSuite {

}
