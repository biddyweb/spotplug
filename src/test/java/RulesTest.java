import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.plugtree.spotplug.impl.FusionEngine;
import com.plugtree.spotplug.impl.GenericEvent;

import junit.framework.TestCase;


public class RulesTest extends TestCase {

	protected FusionEngine engine;

	public RulesTest(String name){
		super(name);
	}
	
	protected void setUp() {
		
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/spotplug.xml"});
		
		engine = (FusionEngine) context.getBean("Engine");
		
		engine.configure();
	}
	
	public void testExcesiveAmountPattern() {
		
		GenericEvent event1 = new GenericEvent("Zeus", 6000, new Date(), 20000, 0, 1);
		GenericEvent event2 = new GenericEvent("Zeus", 5000, new Date(), 20000, 1, 2);
		GenericEvent event3 = new GenericEvent("Zeus", 10001, new Date(), 20000, 3, 3);
		
		engine.processEvent(event1);
		//No se activa
		engine.processEvent(event2);
		//No se activa
		engine.processEvent(event3);
		//Se activa	
	}
	
	public void testManyEventsShortPeriod() {
		GenericEvent event1 = new GenericEvent("Thor", 6000, new Date(), 20000, 0, 1);
		GenericEvent event2 = new GenericEvent("Thor", 5000, new Date(), 20000, 1, 2);
		GenericEvent event3 = new GenericEvent("Thor", 10001, new Date(), 20000, 2, 3);
		GenericEvent event4 = new GenericEvent("Thor", 10001, new Date(), 20000, 3, 4);
		
		engine.processEvent(event1);
		//No se activa
		engine.processEvent(event2);
		//No se activa
		engine.processEvent(event3);
		//No se activa
		engine.processEvent(event4);
		//Se activa
	}
	
	public void testIdenticalTransactions() {
		GenericEvent event1 = new GenericEvent("Hera", 6000, new Date(), 20000, 0, 1);
		GenericEvent event2 = new GenericEvent("Hulk", 6000, new Date(), 20000, 1, 2);
		GenericEvent event3 = new GenericEvent("Hera", 7000, new Date(), 20000, 2, 3);
		GenericEvent event4 = new GenericEvent("Hera", 6000, new Date(), 20000, 3, 4);
		
		engine.processEvent(event1);
		//No se activa
		
		engine.processEvent(event2);
		//No se activa
		
		engine.processEvent(event3);
		//No Se activa
		
		engine.processEvent(event4);
		//Se activa
	}
	
}
