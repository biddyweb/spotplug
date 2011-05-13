import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;


import com.plugtree.spotplug.model.GenericEvent;
import junit.framework.Assert;

import org.drools.time.SessionPseudoClock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.plugtree.spotplug.impl.EventLog;
import com.plugtree.spotplug.impl.FusionEngine;
import com.plugtree.spotplug.actuator.LogActuator;

public class RulesTestPseudoClock{

	private static FusionEngine engine;
	private static LinkedList<EventLog> eventLogList;
	private static SessionPseudoClock clock;
	//KnowledgeRuntimeLogger logger;
	
	private void printEventLog() {
		
		System.out.println("Log size: " + eventLogList.size());
		System.out.println("Fraud patterns:");
		
		for (EventLog event : eventLogList) {
			System.out.println(event.getFraudPattern());
		}
	}
	
	@Before
	public void setUp() {
		
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/testPseudoClock.xml"});
		
		engine = (FusionEngine)context.getBean("Engine");
		eventLogList = ((LogActuator)context.getBean("Actuator")).getEventLogList();
		engine.configure();
		//logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger(engine.getSession());		
		clock = engine.getSession().getSessionClock();

	}
	
	@After
	public void after() {
		//logger.close();
		engine.getSession().dispose();
	    
	}
	
	@Test
	public void strangeVolumnTransaction() {
		
		GenericEvent event1 = new GenericEvent("Hera", 5000, new Date(clock.getCurrentTime()), 20000, 0, 1, 105);
		engine.processEvent(event1);
		
		clock.advanceTime(1, TimeUnit.HOURS);
		GenericEvent event2 = new GenericEvent("Hera", 6000, new Date(clock.getCurrentTime()), 20000, 1, 1, 105);
		engine.processEvent(event2);
		
		clock.advanceTime(25, TimeUnit.HOURS);
		GenericEvent event3 = new GenericEvent("Hera", 7000, new Date(clock.getCurrentTime()), 20000, 2, 1, 105);
		engine.processEvent(event3);
		
		clock.advanceTime(1, TimeUnit.HOURS);		
		GenericEvent event4 = new GenericEvent("Hera", 7000, new Date(clock.getCurrentTime()), 20000, 3, 1, 105);
		engine.processEvent(event4);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Hera");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Strange volume in transaction");
	}
	
	@Test
	public void exactSameTimeSameUser() {
		
		GenericEvent event1 = new GenericEvent("Hera", 5000, new Date(clock.getCurrentTime()), 20000, 0, 1, 105);
		engine.processEvent(event1);
		//Assert.assertTrue(eventLogList.isEmpty());


		GenericEvent event2 = new GenericEvent("Hera", 6000, new Date(clock.getCurrentTime()), 20000, 1, 1, 105);
		engine.processEvent(event2);
		//Assert.assertTrue(eventLogList.isEmpty());

		clock.advanceTime(1, TimeUnit.HOURS);
		GenericEvent event3 = new GenericEvent("Hera", 7000, new Date(clock.getCurrentTime()), 20000, 0, 1, 105);
		engine.processEvent(event3);
		
		//Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Hera");
		Assert.assertEquals(eventLogList.size(),1);
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(),"Exact coincidence of 2 events at Start Time Stamp , same User");		
	}
	
	@Test
	public void sameTransactionSameTimeDifferentDay() {
	
		//Calendar calendar = Calendar.getInstance();
		
		
		//calendar.set(2010, 9, 22, 10, 30, 00);
		GenericEvent event1 = new GenericEvent("Hera", 6000, new Date(clock.getCurrentTime()), 20000, 0, 1, 105);
		engine.processEvent(event1);
		Assert.assertTrue(eventLogList.isEmpty());

		//calendar.set(2010, 9, 22, 11, 30, 00);
		clock.advanceTime(1,TimeUnit.HOURS);
		GenericEvent event2 = new GenericEvent("Zeus", 7000, new Date(clock.getCurrentTime()), 20000, 1, 1, 105);
		engine.processEvent(event2);
		Assert.assertTrue(eventLogList.isEmpty());

		//calendar.set(2010, 9, 23, 10, 30, 00);
		clock.advanceTime(23,TimeUnit.HOURS);
		GenericEvent event3 = new GenericEvent("Hera", 6000, new Date(clock.getCurrentTime()), 20000, 0, 1, 105);
		engine.processEvent(event3);
		Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Hera");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(),"Same transaction, same time, everyday");
	}
	
	@Test
	public void incorrectMessageOrder() {
		
		//Calendar calendar = Calendar.getInstance();
		
		
		//calendar.set(2010, 9, 22, 11, 30, 01);
		GenericEvent event1 = new GenericEvent("Hera", 6000, new Date(clock.getCurrentTime()), 20000, 0, 1, 105);
		engine.processEvent(event1);
		Assert.assertTrue(eventLogList.isEmpty());

		//calendar.set(2010, 9, 22, 11, 30, 02);
		clock.advanceTime(1, TimeUnit.SECONDS);
		GenericEvent event2 = new GenericEvent("Hera", 7000, new Date(clock.getCurrentTime()), 20000, 2, 1, 105);
		engine.processEvent(event2);
		Assert.assertTrue(eventLogList.isEmpty());
		
		//calendar.set(2010, 9, 22, 11, 30, 03);
		clock.advanceTime(1, TimeUnit.SECONDS);
		GenericEvent event3 = new GenericEvent("Hera", 5000, new Date(clock.getCurrentTime()), 20000, 1, 1, 105);
		engine.processEvent(event3);
		Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Hera");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Incorrect Message Order");
	}
			
	@Test
	public void excesiveAmountPattern() {
		
		//Calendar cal1 = Calendar.getInstance();
		//cal1.set(2010,9,22,12,30,01);
		//Calendar cal2 = Calendar.getInstance();
		//cal2.set(2010,9,23,13,30,01);
		//Calendar cal3 = Calendar.getInstance();
		//cal3.set(2010,9,24,14,30,01);
		
		
		
		GenericEvent event1 = new GenericEvent("Zeus", 6000, new Date(clock.getCurrentTime()), 20000, 0, 1, 105);
		engine.processEvent(event1);
		Assert.assertTrue(eventLogList.isEmpty());
		
		clock.advanceTime(25, TimeUnit.HOURS);
		GenericEvent event2 = new GenericEvent("Zeus", 5000, new Date(clock.getCurrentTime()), 20000, 0, 2, 105);
		engine.processEvent(event2);	
		Assert.assertTrue(eventLogList.isEmpty());
		
		clock.advanceTime(25, TimeUnit.HOURS);
		GenericEvent event3 = new GenericEvent("Zeus", 10001, new Date(clock.getCurrentTime()), 20000, 0, 3, 105);
		engine.processEvent(event3);
		Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Zeus");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Excesive amount");		
	}
	
	@Test
	public void manyEventsShortPeriod() {
		
		
		//Calendar cal1 = Calendar.getInstance();
		//cal1.set(2010,9,23,10,30,00);
		//Calendar cal2 = Calendar.getInstance();
		//cal2.set(2010,9,23,10,30,05);
		//Calendar cal3 = Calendar.getInstance();
		//cal3.set(2010,9,23,10,30,10);
		//Calendar cal4 = Calendar.getInstance();
		//cal4.set(2010,9,23,10,30,20);
			
		GenericEvent event1 = new GenericEvent("Thor", 5000, new Date(clock.getCurrentTime()), 20000, 0, 1, 105);
		engine.processEvent(event1);
		Assert.assertTrue(eventLogList.isEmpty());
		
		clock.advanceTime(5, TimeUnit.SECONDS);
		GenericEvent event2 = new GenericEvent("Thor", 6000, new Date(clock.getCurrentTime()), 20000, 1, 1, 105);
		engine.processEvent(event2);
		Assert.assertTrue(eventLogList.isEmpty());
		
		clock.advanceTime(5, TimeUnit.SECONDS);
		GenericEvent event3 = new GenericEvent("Thor", 7000, new Date(clock.getCurrentTime()), 20000, 2, 1, 105);
		engine.processEvent(event3);
     	Assert.assertTrue(eventLogList.isEmpty());
		
     	clock.advanceTime(5, TimeUnit.SECONDS);
     	GenericEvent event4 = new GenericEvent("Thor", 8000, new Date(clock.getCurrentTime()), 20000, 3, 1, 105);
		engine.processEvent(event4);
		Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Thor");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Too many events in a short period of time");
	}
	
	@Test
	public void identicalTransactions() {
		
		//Calendar cal1 = Calendar.getInstance();
		//cal1.set(2010, 10, 8, 16, 0, 1);
		//Calendar cal2 = Calendar.getInstance();
		//cal2.setTimeInMillis(System.currentTimeMillis());
		//Calendar cal3 = Calendar.getInstance();
		//cal3.setTimeInMillis(System.currentTimeMillis());
		//Calendar cal4 = Calendar.getInstance();
		//cal4.set(2010, 10, 8, 16, 0, 30);
	
						
		
		GenericEvent event1 = new GenericEvent("Mike", 6000, new Date(clock.getCurrentTime()), 20000, 1, 1, 105);
		engine.processEvent(event1);
		Assert.assertTrue(eventLogList.isEmpty());
		
		clock.advanceTime(1, TimeUnit.SECONDS);
		GenericEvent event2 = new GenericEvent("John", 6000, new Date(clock.getCurrentTime()), 20000, 1, 7, 105);
		engine.processEvent(event2);
		Assert.assertTrue(eventLogList.isEmpty());
		
		clock.advanceTime(1, TimeUnit.SECONDS);
		GenericEvent event3 = new GenericEvent("Mike", 7000, new Date(clock.getCurrentTime()), 20000, 2, 5, 105);
		engine.processEvent(event3);
		Assert.assertTrue(eventLogList.isEmpty());
		
		clock.advanceTime(28, TimeUnit.SECONDS);
		GenericEvent event4 = new GenericEvent("Mike", 6000, new Date(clock.getCurrentTime()), 20000, 3, 4, 105);
		engine.processEvent(event4);
		Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Mike");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Identical transactions");
	}
	
	@Test
	public void unusualHoursTransactions(){
		
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2010,9,23,2,30,00);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(System.currentTimeMillis());
		Calendar cal3 = Calendar.getInstance();
		cal3.set(2010,9,23,4,30,01);
		Calendar cal4 = Calendar.getInstance();
		cal4.setTimeInMillis(System.currentTimeMillis());
		
		GenericEvent event1 = new GenericEvent("Hera", 6000, cal1.getTime(), 20000, 0, 1, 105);
		GenericEvent event2 = new GenericEvent("Hulk", 6000, cal2.getTime(), 20000, 1, 2, 105);
		GenericEvent event3 = new GenericEvent("Hera", 7000, cal3.getTime(), 20000, 1, 2, 105);
		GenericEvent event4 = new GenericEvent("Hera", 5000, cal4.getTime(), 20000, 1, 3, 105);
		
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
		
		//Calendar cal1 = Calendar.getInstance();
		//cal1.set(2010,10,12,9,30,00);
		//Calendar cal2 = Calendar.getInstance();
		//cal2.setTimeInMillis(System.currentTimeMillis());
		//Calendar cal3 = Calendar.getInstance();
		//cal3.set(2010,10,12,10,31,20);
		//Calendar cal4 = Calendar.getInstance();
		//cal4.set(2010,10,12,11,32,00);
				
		
		
		
		
		GenericEvent event1 = new GenericEvent("Hera", 6000, new Date(clock.getCurrentTime()), 20000, 1, 1, 105);
		engine.processEvent(event1);
		Assert.assertEquals(eventLogList.size(),0);
		
		GenericEvent event2 = new GenericEvent("Hulk", 6000, new Date(clock.getCurrentTime()), 20000, 1, 2,105);
		engine.processEvent(event2);
		Assert.assertEquals(eventLogList.size(),0);
		
		clock.advanceTime(61,TimeUnit.MINUTES);
		GenericEvent event3 = new GenericEvent("Hera", 7000, new Date(clock.getCurrentTime()), 20000, 2, 1, 105);
		engine.processEvent(event3);
		Assert.assertEquals(eventLogList.size(),0);
		
		clock.advanceTime(61,TimeUnit.MINUTES);
		GenericEvent event4 = new GenericEvent("Hera", 5000, new Date(clock.getCurrentTime()), 20000, 3, 1, 105);
		engine.processEvent(event4);
		Assert.assertEquals(eventLogList.size(),1);
		
	}
}
