package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.control.ManageReserveRoomTeacherTest;
import test.model.TeacherReserveRoomTest;
import test.persistence.ResSalaProfessorDAOTest;

@RunWith(Suite.class)

@SuiteClasses({TeacherReserveRoomTest.class, ResSalaProfessorDAOTest.class, ManageReserveRoomTeacherTest.class})

public class ReservaSalaProfessorSuite {

}
