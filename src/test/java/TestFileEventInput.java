import com.plugtree.spotplug.impl.FileEventInput;
import com.plugtree.spotplug.impl.GenericEvent;

import junit.framework.Assert;
import junit.framework.TestCase;


public class TestFileEventInput extends TestCase {
	
	protected FileEventInput fileEventInput;

	public TestFileEventInput(String name){
		super(name);
	}
	
	protected void setUp() {
		fileEventInput = new FileEventInput();
	}
	
	public void testCreateEvent() {
		
		String line = "Bazinga,1000";
		
		GenericEvent event = (GenericEvent)fileEventInput.createEvent(line);
		
		Assert.assertEquals("Bazinga", event.getUserId());
		Assert.assertEquals(1000, event.getAmount());
	}
}
