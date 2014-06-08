package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.control.SupportRoomTest;
import test.model.SalaTest;
import test.persistence.RoomDAOTest;

@RunWith(Suite.class)
@SuiteClasses({SalaTest.class, RoomDAOTest.class, SupportRoomTest.class })

public class SalaSuite {

}
