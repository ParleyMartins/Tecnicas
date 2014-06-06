package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.control.ManterSalaTest;
import test.model.SalaTest;
import test.persistence.RoomDAOTest;

@RunWith(Suite.class)
@SuiteClasses({SalaTest.class, RoomDAOTest.class, ManterSalaTest.class })
public class SalaSuite {

}
