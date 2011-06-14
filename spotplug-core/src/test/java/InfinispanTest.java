import java.io.IOException;

import junit.framework.Assert;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.BeforeClass;
import org.junit.Test;


public class InfinispanTest {
     /*
    private static final String INFINISPAN_CONFIGURATION = "infinitispan-config.xml";
    static DefaultCacheManager cacheManager;

    @BeforeClass
    public static void inicializeServer() {
        try {
            cacheManager = new DefaultCacheManager(INFINISPAN_CONFIGURATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void funcionalityTest() {

        Cache<String, String> cache = cacheManager.getCache();
        
        String oldValue = cache.put("Spotplug", "Rules");
        boolean worked = cache.replace("Spotplug", "Rules", "!!");
        String returnValue = cache.get("Spotplug");

        Assert.assertEquals(worked, true);
        Assert.assertEquals(oldValue, null);
        Assert.assertEquals(returnValue, "!!");
    }
    */
}
