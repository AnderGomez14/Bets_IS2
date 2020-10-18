package test;

/**
 * Auxiliary FacadeImplementation class for testing DataAccess
 */

import java.util.Date;

import configuration.ConfigXML;
import domain.Event;
import domain.Team;

public class TestFacadeImplementation {
	TestDataAccess dbManagerTest;

	public TestFacadeImplementation() {

		System.out.println("Creating TestFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();
		dbManagerTest = new TestDataAccess();
		dbManagerTest.close();
	}

	public boolean removeEvent(Event ev) {
		dbManagerTest.open();
		boolean b = dbManagerTest.removeEvent(ev);
		dbManagerTest.close();
		return b;

	}

	public Event addEvent(Team t1, Team t2, Date d) {
		dbManagerTest.open();
		Event o = dbManagerTest.addEvent(t1, t2, d);
		dbManagerTest.close();
		return o;

	}

}