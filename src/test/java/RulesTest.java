import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import junit.framework.Assert;

import org.drools.runtime.rule.FactHandle;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.plugtree.spotplug.impl.EventLog;
import com.plugtree.spotplug.impl.FusionEngine;
import com.plugtree.spotplug.impl.GenericEvent;
import com.plugtree.spotplug.impl.LogActuator;

public class RulesTest{

	private static FusionEngine engine;
	private static LinkedList<EventLog> eventLogList;
	
	@BeforeClass
	public static void setUp() {
		
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/test.xml"});
		
		engine = (FusionEngine)context.getBean("Engine");
		eventLogList = ((LogActuator)context.getBean("Actuator")).getEventLogList();
		
		engine.configure();
	}
	
	@After 
	public void clear() {
		
		eventLogList.clear();
		
		Collection<FactHandle> factHandles = engine.getSession().getFactHandles(null);

		for(FactHandle factHandle : factHandles) {
			engine.getSession().retract(factHandle);		
		}
	}
	
	@Test
	public void strangeVolumnTransaction() {
		
		GenericEvent event1 = new GenericEvent("Hera", 5000, new Date(2010,9,22,10,30,00), 20000, 0, 1, 105);
		GenericEvent event2 = new GenericEvent("Hera", 6000, new Date(2010,9,22,11,30,00), 20000, 1, 1, 105);
		GenericEvent event3 = new GenericEvent("Hera", 7000, new Date(2010,9,23,12,30,00), 20000, 2, 1, 105);
		GenericEvent event4 = new GenericEvent("Hera", 7000, new Date(2010,9,23,15,30,00), 20000, 3, 1, 105);
		
		engine.processEvent(event1);
		Assert.assertTrue(eventLogList.isEmpty());

		engine.processEvent(event2);
		Assert.assertTrue(eventLogList.isEmpty());
		
		engine.processEvent(event3);
		Assert.assertTrue(eventLogList.isEmpty());
		
		engine.processEvent(event4);
		Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Hera");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Strange volume in transaction");
	}
	
	@Test
	public void exactSameTimeSameUser() {
		
		GenericEvent event1 = new GenericEvent("Hera", 5000, new Date(2010,9,22,10,30,00), 20000, 0, 1, 105);
		GenericEvent event2 = new GenericEvent("Hera", 6000, new Date(2010,9,22,11,30,00), 20000, 1, 1, 105);
		GenericEvent event3 = new GenericEvent("Hera", 7000, new Date(2010,9,22,10,30,00), 20000, 0, 1, 105);
		
		engine.processEvent(event1);
		Assert.assertTrue(eventLogList.isEmpty());

		engine.processEvent(event2);
		Assert.assertTrue(eventLogList.isEmpty());

		engine.processEvent(event3);
		Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Hera");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Exact coincidence of 2 events at Start Time Stamp , same User");		
	}
	
	@Test
	public void sameTransactionSameTimeDifferentDay() {
	
		GenericEvent event1 = new GenericEvent("Hera", 6000, new Date(2010,9,23,10,30,00), 20000, 0, 1, 105);
		GenericEvent event2 = new GenericEvent("Zeus", 7000, new Date(2010,9,22,11,30,00), 20000, 1, 1, 105);
		GenericEvent event3 = new GenericEvent("Hera", 6000, new Date(2010,9,22,10,30,00), 20000, 0, 1, 105);
		
		engine.processEvent(event1);
		Assert.assertTrue(eventLogList.isEmpty());

		engine.processEvent(event2);
		Assert.assertTrue(eventLogList.isEmpty());

		engine.processEvent(event3);
		Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Hera");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Same transaction, same time, everyday");
	}
	
	@Test
	public void excesiveAmountPattern() {
		
		GenericEvent event1 = new GenericEvent("Zeus", 6000, new Date(), 20000, 0, 1, 105);
		GenericEvent event2 = new GenericEvent("Zeus", 5000, new Date(), 20000, 0, 2, 105);
		GenericEvent event3 = new GenericEvent("Zeus", 10001, new Date(), 20000, 0, 3, 105);
		
		engine.processEvent(event1);
		Assert.assertTrue(eventLogList.isEmpty());
		
		engine.processEvent(event2);
		Assert.assertTrue(eventLogList.isEmpty());
		
		engine.processEvent(event3);
		Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Zeus");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Excesive amount");		
	}
	
	@Test
	public void manyEventsShortPeriod() {
		
		GenericEvent event1 = new GenericEvent("Thor", 5000, new Date(), 20000, 0, 1, 105);
		GenericEvent event2 = new GenericEvent("Thor", 6000, new Date(), 20000, 1, 1, 105);
		GenericEvent event3 = new GenericEvent("Thor", 7000, new Date(), 20000, 2, 1, 105);
		GenericEvent event4 = new GenericEvent("Thor", 8000, new Date(), 20000, 3, 1, 105);
		
		engine.processEvent(event1);
		Assert.assertTrue(eventLogList.isEmpty());
		
		engine.processEvent(event2);
		Assert.assertTrue(eventLogList.isEmpty());
		
		engine.processEvent(event3);
		Assert.assertTrue(eventLogList.isEmpty());
		
		engine.processEvent(event4);
		Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Thor");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Too many events in a short period of time");
	}
	
	@Test
	public void identicalTransactions() {

		GenericEvent event1 = new GenericEvent("Mike", 6000, new Date(2010,9,23,10,30,0), 20000, 1, 1, 105);
		GenericEvent event2 = new GenericEvent("John", 6000, new Date(), 20000, 1, 7, 105);
		GenericEvent event3 = new GenericEvent("Mike", 7000, new Date(), 20000, 2, 5, 105);
		GenericEvent event4 = new GenericEvent("Mike", 6000, new Date(2010,9,23,10,30,9), 20000, 3, 4, 105);
		
		engine.processEvent(event1);
		Assert.assertTrue(eventLogList.isEmpty());
		
		engine.processEvent(event2);
		Assert.assertTrue(eventLogList.isEmpty());
		
		engine.processEvent(event3);
		Assert.assertTrue(eventLogList.isEmpty());
		
		engine.processEvent(event4);
		Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Mike");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Identical transactions");
	}
	
	@Test
	public void unusualHoursTransactions(){
		GenericEvent event1 = new GenericEvent("Hera", 6000, new Date(2010,9,23,2,30,00), 20000, 0, 1, 105);
		GenericEvent event2 = new GenericEvent("Hulk", 6000, new Date(), 20000, 1, 2, 105);
		GenericEvent event3 = new GenericEvent("Hera", 7000, new Date(2010,9,23,4,30,00), 20000, 1, 2, 105);
		GenericEvent event4 = new GenericEvent("Hera", 5000, new Date(), 20000, 1, 3, 105);
		
		engine.processEvent(event1);
		Assert.assertEquals(eventLogList.size(),1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Hera");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Transaction at unusual hours");
		
		engine.processEvent(event2);
		Assert.assertEquals(eventLogList.size(), 1);
		
		engine.processEvent(event3);
		Assert.assertEquals(eventLogList.size(), 2);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Hera");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Transaction at unusual hours");
		
		engine.processEvent(event4);
		Assert.assertEquals(eventLogList.size(), 2);
	}
	
	@Test
	public void wideDistanceBetweenMessages(){
		GenericEvent event1 = new GenericEvent("Hera", 6000, new Date(2010,9,23,10,30,00), 20000, 1, 1, 105);
		GenericEvent event2 = new GenericEvent("Hulk", 6000, new Date(), 20000, 1, 2,105);
		GenericEvent event3 = new GenericEvent("Hera", 7000, new Date(2010,9,23,14,30,00), 20000, 1, 3, 105);
		GenericEvent event4 = new GenericEvent("Hera", 5000, new Date(2010,9,23,14,31,00), 20000, 1, 4, 105);
		
		engine.processEvent(event1);
		
		engine.processEvent(event2);
		
		engine.processEvent(event3);
		
		engine.processEvent(event4);
	}
}
