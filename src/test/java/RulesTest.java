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
		
		GenericEvent event1 = new GenericEvent("leandro", 6000, new Date(), 20000, 0, 1);
		GenericEvent event2 = new GenericEvent("leandro", 5000, new Date(), 20000, 0, 1);
		GenericEvent event3 = new GenericEvent("leandro", 10001, new Date(), 20000, 0, 1);
		
		engine.processEvent(event1);
	}
}
