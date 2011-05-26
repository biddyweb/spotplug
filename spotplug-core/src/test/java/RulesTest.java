import java.util.Calendar;
import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.plugtree.spotplug.actuator.LogActuator;
import com.plugtree.spotplug.impl.EventLog;
import com.plugtree.spotplug.impl.FusionEngine;
import com.plugtree.spotplug.model.GenericEvent;

public class RulesTest {

	private static FusionEngine engine;
	private static LinkedList<EventLog> eventLogList;

	@Before
	public void setUp() {
		
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/test.xml"});
		
		engine = (FusionEngine)context.getBean("Engine");
		eventLogList = ((LogActuator)context.getBean("Actuator")).getEventLogList();
		engine.configure();
	}
	
	@After
	public void after() {
		engine.getSession().dispose();	
	}
	
	@Test
	public void strangeVolumnTransaction() {

		int DURATION = 20000;
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(2010, 9, 22, 10, 30, 00);
		GenericEvent event1 = new GenericEvent("bank-event", calendar.getTime(), DURATION);
		event1.addAttribute("amount", "5000");
		event1.addAttribute("userId", "Hera");
		event1.addAttribute("sequentialID", "0");
		event1.addAttribute("transactionID", "1");
		event1.addAttribute("opCode", "105");
		
		calendar.set(2010, 9, 22, 11, 30, 00);
		GenericEvent event2 = new GenericEvent("bank-event", calendar.getTime(), DURATION);
		event2.addAttribute("amount", "6000");
		event2.addAttribute("userId", "Hera");
		event2.addAttribute("sequentialID", "1");
		event2.addAttribute("transactionID", "1");
		event2.addAttribute("opCode", "105");
		
		calendar.set(2010, 9, 23, 12, 30, 00);
		GenericEvent event3 = new GenericEvent("bank-event", calendar.getTime(), DURATION);
		event3.addAttribute("amount", "6000");
		event3.addAttribute("userId", "Hera");
		event3.addAttribute("sequentialID", "2");
		event3.addAttribute("transactionID", "1");
		event3.addAttribute("opCode", "105");
		
		calendar.set(2010, 9, 23, 15, 30, 00);
		GenericEvent event4 = new GenericEvent("bank-event", calendar.getTime(), DURATION);
		event4.addAttribute("amount", "7000");
		event4.addAttribute("userId", "Hera");
		event4.addAttribute("sequentialID", "3");
		event4.addAttribute("transactionID", "1");
		event4.addAttribute("opCode", "105");

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
		
		int DURATION = 20000;
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(2010, 9, 22, 10, 30, 00);
		GenericEvent event1 = new GenericEvent("bank-event", calendar.getTime(), DURATION);
		event1.addAttribute("amount", "5000");
		event1.addAttribute("userId", "Hera");
		event1.addAttribute("sequentialID", "0");
		event1.addAttribute("transactionID", "1");
		event1.addAttribute("opCode", "105");
		
		GenericEvent event3 = new GenericEvent("bank-event", calendar.getTime(), DURATION);
		event3.addAttribute("amount", "7000");
		event3.addAttribute("userId", "Hera");
		event3.addAttribute("sequentialID", "0");
		event3.addAttribute("transactionID", "1");
		event3.addAttribute("opCode", "105");
		
		calendar.set(2010, 10, 22, 10, 30, 00);
		GenericEvent event2 = new GenericEvent("bank-event", calendar.getTime(), DURATION);
		event2.addAttribute("amount", "6000");
		event2.addAttribute("userId", "Hera");
		event2.addAttribute("sequentialID", "1");
		event2.addAttribute("transactionID", "1");
		event2.addAttribute("opCode", "105");

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
	
		int DURATION = 20000;
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(2010, 9, 23, 10, 30, 00);
		GenericEvent event1 = new GenericEvent("bank-event", calendar.getTime(), DURATION);
		event1.addAttribute("amount", "6000");
		event1.addAttribute("userId", "Hera");
		event1.addAttribute("sequentialID", "0");
		event1.addAttribute("transactionID", "1");
		event1.addAttribute("opCode", "105");
		
		calendar.set(2010, 9, 22, 11, 30, 00);
		GenericEvent event2 = new GenericEvent("bank-event", calendar.getTime(), DURATION);
		event2.addAttribute("amount", "7000");
		event2.addAttribute("userId", "Zeus");
		event2.addAttribute("sequentialID", "1");
		event2.addAttribute("transactionID", "1");
		event2.addAttribute("opCode", "105");
		
		calendar.set(2010, 9, 22, 10, 30, 00);
		GenericEvent event3 = new GenericEvent("bank-event", calendar.getTime(), DURATION);
		event3.addAttribute("amount", "6000");
		event3.addAttribute("userId", "Hera");
		event3.addAttribute("sequentialID", "0");
		event3.addAttribute("transactionID", "1");
		event3.addAttribute("opCode", "105");

		engine.processEvent(event1);
		Assert.assertTrue(eventLogList.isEmpty());

		engine.processEvent(event2);
		Assert.assertTrue(eventLogList.isEmpty());

		engine.processEvent(event3);
		Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Hera");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Same BankEvent, same time, everyday");
	}
	
	@Test
	public void incorrectMessageOrder() {
		
		int DURATION = 20000;
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(2010, 9, 22, 11, 30, 01);
		GenericEvent event1 = new GenericEvent("bank-event", calendar.getTime(), DURATION);
		event1.addAttribute("amount", "6000");
		event1.addAttribute("userId", "Hera");
		event1.addAttribute("sequentialID", "0");
		event1.addAttribute("transactionID", "1");
		event1.addAttribute("opCode", "105");
		
		calendar.set(2010, 9, 22, 11, 30, 02);
		GenericEvent event2 = new GenericEvent("bank-event", calendar.getTime(), DURATION);
		event2.addAttribute("amount", "7000");
		event2.addAttribute("userId", "Hera");
		event2.addAttribute("sequentialID", "2");
		event2.addAttribute("transactionID", "1");
		event2.addAttribute("opCode", "105");
		
		calendar.set(2010, 9, 22, 11, 30, 03);
		GenericEvent event3 = new GenericEvent("bank-event", calendar.getTime(), DURATION);
		event3.addAttribute("amount", "5000");
		event3.addAttribute("userId", "Hera");
		event3.addAttribute("sequentialID", "1");
		event3.addAttribute("transactionID", "1");
		event3.addAttribute("opCode", "105");
		
		engine.processEvent(event1);
		Assert.assertTrue(eventLogList.isEmpty());

		engine.processEvent(event2);
		Assert.assertTrue(eventLogList.isEmpty());
		
		engine.processEvent(event3);
		Assert.assertEquals(eventLogList.size(), 1);
		Assert.assertEquals(eventLogList.getLast().getUserId(), "Hera");
		Assert.assertEquals(eventLogList.getLast().getFraudPattern(), "Incorrect Message Order");
	}
			
	@Test
	public void excesiveAmountPattern() {
		
		int DURATION = 20000;
		
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2010,9,22,12,30,01);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2010,9,23,13,30,01);
		Calendar cal3 = Calendar.getInstance();
		cal3.set(2010,9,24,14,30,01);
		
		
		GenericEvent event1 = new GenericEvent("bank-event", cal1.getTime(), DURATION);
		event1.addAttribute("amount", "6000");
		event1.addAttribute("userId", "Zeus");
		event1.addAttribute("sequentialID", "0");
		event1.addAttribute("transactionID", "1");
		event1.addAttribute("opCode", "105");
		
		GenericEvent event2 = new GenericEvent("bank-event", cal2.getTime(), DURATION);
		event2.addAttribute("amount", "5000");
		event2.addAttribute("userId", "Zeus");
		event2.addAttribute("sequentialID", "0");
		event2.addAttribute("transactionID", "2");
		event2.addAttribute("opCode", "105");
		
		GenericEvent event3 = new GenericEvent("bank-event", cal3.getTime(), DURATION);
		event3.addAttribute("amount", "10001");
		event3.addAttribute("userId", "Zeus");
		event3.addAttribute("sequentialID", "0");
		event3.addAttribute("transactionID", "3");
		event3.addAttribute("opCode", "105");
		
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
		
		int DURATION = 20000;
		
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2010,9,23,10,30,0);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2010,9,23,10,30,5);
		Calendar cal3 = Calendar.getInstance();
		cal3.set(2010,9,23,10,30,10);
		Calendar cal4 = Calendar.getInstance();
		cal4.set(2010,9,23,10,30,15);
		
		GenericEvent event1 = new GenericEvent("bank-event", cal1.getTime(), DURATION);
		event1.addAttribute("amount", "5000");
		event1.addAttribute("userId", "Thor");
		event1.addAttribute("sequentialID", "0");
		event1.addAttribute("transactionID", "1");
		event1.addAttribute("opCode", "105");
		
		GenericEvent event2 = new GenericEvent("bank-event", cal2.getTime(), DURATION);
		event2.addAttribute("amount", "6000");
		event2.addAttribute("userId", "Thor");
		event2.addAttribute("sequentialID", "1");
		event2.addAttribute("transactionID", "1");
		event2.addAttribute("opCode", "105");
		
		GenericEvent event3 = new GenericEvent("bank-event", cal3.getTime(), DURATION);
		event3.addAttribute("amount", "7000");
		event3.addAttribute("userId", "Thor");
		event3.addAttribute("sequentialID", "2");
		event3.addAttribute("transactionID", "1");
		event3.addAttribute("opCode", "105");
		
		GenericEvent event4 = new GenericEvent("bank-event", cal4.getTime(), DURATION);
		event4.addAttribute("amount", "8000");
		event4.addAttribute("userId", "Thor");
		event4.addAttribute("sequentialID", "3");
		event4.addAttribute("transactionID", "1");
		event4.addAttribute("opCode", "105");
				
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
		
		int DURATION = 20000;
		
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2010, 10, 8, 16, 0, 1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(System.currentTimeMillis());
		Calendar cal3 = Calendar.getInstance();
		cal3.setTimeInMillis(System.currentTimeMillis());
		Calendar cal4 = Calendar.getInstance();
		cal4.set(2010, 10, 8, 16, 0, 30);
		
		GenericEvent event1 = new GenericEvent("bank-event", cal1.getTime(), DURATION);
		event1.addAttribute("amount", "6000");
		event1.addAttribute("userId", "Mike");
		event1.addAttribute("sequentialID", "1");
		event1.addAttribute("transactionID", "1");
		event1.addAttribute("opCode", "105");
		
		GenericEvent event2 = new GenericEvent("bank-event", cal2.getTime(), DURATION);
		event2.addAttribute("amount", "6000");
		event2.addAttribute("userId", "John");
		event2.addAttribute("sequentialID", "1");
		event2.addAttribute("transactionID", "7");
		event2.addAttribute("opCode", "105");
		
		GenericEvent event3 = new GenericEvent("bank-event", cal3.getTime(), DURATION);
		event3.addAttribute("amount", "7000");
		event3.addAttribute("userId", "Mike");
		event3.addAttribute("sequentialID", "2");
		event3.addAttribute("transactionID", "5");
		event3.addAttribute("opCode", "105");

		GenericEvent event4 = new GenericEvent("bank-event", cal4.getTime(), DURATION);
		event4.addAttribute("amount", "6000");
		event4.addAttribute("userId", "Mike");
		event4.addAttribute("sequentialID", "3");
		event4.addAttribute("transactionID", "4");
		event4.addAttribute("opCode", "105");
		
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
		
		int DURATION = 20000;
		
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2010,9,23,2,30,00);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(System.currentTimeMillis());
		Calendar cal3 = Calendar.getInstance();
		cal3.set(2010,9,23,4,30,01);
		Calendar cal4 = Calendar.getInstance();
		cal4.setTimeInMillis(System.currentTimeMillis());
		
		
		GenericEvent event1 = new GenericEvent("bank-event", cal1.getTime(), DURATION);
		event1.addAttribute("amount", "6000");
		event1.addAttribute("userId", "Hera");
		event1.addAttribute("sequentialID", "0");
		event1.addAttribute("transactionID", "1");
		event1.addAttribute("opCode", "105");
		
		GenericEvent event2 = new GenericEvent("bank-event", cal2.getTime(), DURATION);
		event2.addAttribute("amount", "6000");
		event2.addAttribute("userId", "Hulk");
		event2.addAttribute("sequentialID", "1");
		event2.addAttribute("transactionID", "2");
		event2.addAttribute("opCode", "105");
		
		GenericEvent event3 = new GenericEvent("bank-event", cal3.getTime(), DURATION);
		event3.addAttribute("amount", "7000");
		event3.addAttribute("userId", "Hera");
		event3.addAttribute("sequentialID", "1");
		event3.addAttribute("transactionID", "2");
		event3.addAttribute("opCode", "105");
		
		GenericEvent event4 = new GenericEvent("bank-event", cal4.getTime(), DURATION);
		event4.addAttribute("amount", "5000");
		event4.addAttribute("userId", "Hera");
		event4.addAttribute("sequentialID", "1");
		event4.addAttribute("transactionID", "3");
		event4.addAttribute("opCode", "105");
		
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
		
		int DURATION = 20000;
		
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2010,10,12,9,30,00);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(System.currentTimeMillis());
		Calendar cal3 = Calendar.getInstance();
		cal3.set(2010,10,12,10,31,20);
		Calendar cal4 = Calendar.getInstance();
		cal4.set(2010,10,12,11,32,00);

		GenericEvent event1 = new GenericEvent("bank-event", cal1.getTime(), DURATION);
		event1.addAttribute("amount", "6000");
		event1.addAttribute("userId", "Hera");
		event1.addAttribute("sequentialID", "1");
		event1.addAttribute("transactionID", "1");
		event1.addAttribute("opCode", "105");
		
		GenericEvent event2 = new GenericEvent("bank-event", cal2.getTime(), DURATION);
		event2.addAttribute("amount", "6000");
		event2.addAttribute("userId", "Hulk");
		event2.addAttribute("sequentialID", "1");
		event2.addAttribute("transactionID", "2");
		event2.addAttribute("opCode", "105");
		
		GenericEvent event3 = new GenericEvent("bank-event", cal3.getTime(), DURATION);
		event3.addAttribute("amount", "7000");
		event3.addAttribute("userId", "Hera");
		event3.addAttribute("sequentialID", "2");
		event3.addAttribute("transactionID", "1");
		event3.addAttribute("opCode", "105");
		
		GenericEvent event4 = new GenericEvent("bank-event", cal4.getTime(), DURATION);
		event4.addAttribute("amount", "5000");
		event4.addAttribute("userId", "Hera");
		event4.addAttribute("sequentialID", "3");
		event4.addAttribute("transactionID", "1");
		event4.addAttribute("opCode", "105");
		
		engine.processEvent(event1);
		Assert.assertEquals(eventLogList.size(),0);
		
		engine.processEvent(event2);
		Assert.assertEquals(eventLogList.size(),0);
				
		engine.processEvent(event3);
		Assert.assertEquals(eventLogList.size(),0);
				
		engine.processEvent(event4);
		Assert.assertEquals(eventLogList.size(),1);
		
	}
}
