package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.control.ManterResSalaProfessorTest;
import test.model.TeacherReserveRoomTest;
import test.persistence.ResSalaProfessorDAOTest;

@RunWith(Suite.class)
@SuiteClasses({TeacherReserveRoomTest.class, ResSalaProfessorDAOTest.class, ManterResSalaProfessorTest.class})
public class ReservaSalaProfessorSuite {

}
