package com.tavisca;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tavisca.Constants;
import com.tavisca.inmemorycache.model.Bike;
import com.tavisca.inmemorycache.service.CacheFunctionalityService;
import com.tavisca.inmemorycache.service.impl.CacheFunctionalityServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CacheFunctionalityServiceImplTest1 {
	private static final Logger log = LoggerFactory.getLogger(CacheFunctionalityServiceImplTest1.class);

	@InjectMocks
	CacheFunctionalityService cacheFunctionalityService = new CacheFunctionalityServiceImpl();

	@Test
	public void testAdd() throws InterruptedException {
		BasicConfigurator.configure();

		//1. Add elements into the cache.
		log.info("TEST ADD:: Objects Added in CACHE");
		cacheFunctionalityService.add("bike_1", new Bike("BMW", 400.00, "Sports", 3.75, 5.0, "White"));
		cacheFunctionalityService.add("bike_2", new Bike("RoyalEnfield", 350.00, "Cruise", 1.49, 1.82, "Desert Storm"));
		cacheFunctionalityService.add("bike_3", new Bike("Jawa", 298.00, "Cruise", 1.25, 1.48, "Black"));
		cacheFunctionalityService.add("bike_4", new Bike("Triumph", 800.00, "Cruise", 15.20, 17.40, "Blue"));
		cacheFunctionalityService.add("bike_5", new Bike("Honda", 110.0, "Scooty", 0.65, 0.71, "Grey"));
		cacheFunctionalityService.add("bike_6", new Bike("Hero", 100.00, "Standard", 0.55, 0.65, "Black"));
		cacheFunctionalityService.add("bike_7", new Bike("Bajaj", 220.00, "Sports", 1.10, 1.25, "Red"));

		log.info("TEST ADD:: Check if the cache is full");
		assertEquals(Constants.CACHE_SIZE, cacheFunctionalityService.size());

		//2. Try updating the existing object by null
		log.info("TEST ADD:: Update existing object to null");
		cacheFunctionalityService.add("bike_3", null);
		assertNotEquals(Constants.CACHE_SIZE, cacheFunctionalityService.size());

		//3. Add new object into the cache to make it full
		log.info("TEST ADD:: Add a new Object and make the cache full");
		cacheFunctionalityService.add("bike_3", new Bike("Jawa", 298.00, "Cruise", 1.25, 1.48, "Black"));
		assertEquals(Constants.CACHE_SIZE, cacheFunctionalityService.size());

		//4. Update existing cache object with proper data.
		log.info("TEST ADD:: Update exsiting cache data with new data");
		cacheFunctionalityService.add("bike_4", new Bike("Kawasaki", 650.00, "Cruise", 5.45, 06.30, "Green"));
		
		Thread.currentThread();
		Thread.sleep(2000);
		
		assertEquals(Constants.CACHE_SIZE, cacheFunctionalityService.size());

		//5. Check for Replacement Algorithm. WE will need to update/use 6 out of 7 cache objects
		log.info("TEST ADD:: Replacement Algorithm");
		cacheFunctionalityService.add("bike_1", new Bike("BMW", 400.00, "Sports", 3.75, 5.0, "White"));
		cacheFunctionalityService.add("bike_5", new Bike("Honda", 110.0, "Scooty", 0.65, 0.71, "Grey"));
		cacheFunctionalityService.add("bike_6", new Bike("Hero", 100.00, "Standard", 0.55, 0.65, "Black"));
		cacheFunctionalityService.add("bike_7", new Bike("Bajaj", 220.00, "Sports", 1.10, 1.25, "Red"));

		// now try adding new one due to which the bike_1 object will be replaced with bike_8
		cacheFunctionalityService.add("bike_8", new Bike("TVS", 220.00, "Sports", 1.10, 1.25, "Red"));
		assertNull(cacheFunctionalityService.get("bike_2"));
		//		assert
	}

	@Test
	public void testGet() throws InterruptedException {
		//1. Add elements into the cache.
		log.info("TEST GET:: Objects Added in CACHE");
		cacheFunctionalityService.add("bike_1", new Bike("BMW", 400.00, "Sports", 3.75, 5.0, "White"));
		cacheFunctionalityService.add("bike_2", new Bike("RoyalEnfield", 350.00, "Cruise", 1.49, 1.82, "Desert Storm"));
		cacheFunctionalityService.add("bike_3", new Bike("Jawa", 298.00, "Cruise", 1.25, 1.48, "Black"));
		cacheFunctionalityService.add("bike_4", new Bike("Triumph", 800.00, "Cruise", 15.20, 17.40, "Blue"));
		cacheFunctionalityService.add("bike_5", new Bike("Honda", 110.0, "Scooty", 0.65, 0.71, "Grey"));
		cacheFunctionalityService.add("bike_6", new Bike("Hero", 100.00, "Standard", 0.55, 0.65, "Black"));
		cacheFunctionalityService.add("bike_7", new Bike("Bajaj", 220.00, "Sports", 1.10, 1.25, "Red"));

		//2. Try to get any of the object by key
		log.info("TEST GET:: Get Any object from CACHE by key");
		assertEquals("RoyalEnfield", ((Bike)cacheFunctionalityService.get("bike_2")).getMake());

		//3. Try to access object by invalid key
		log.info("TEST GET:: Try to get object by invalid key");
		assertNull(cacheFunctionalityService.get("bike_10"));

		//4.Try to access expired objects
		Thread.currentThread();
		Thread.sleep(11000);
		
		log.info("TEST GET:: Try accessing expired objects");
		assertNull(cacheFunctionalityService.get("bike_1"));
	}

	@Test
	public void testRemove() {
		//1. Add elements into the cache.
		log.info("TEST REMOVE:: Objects Added in CACHE");
		cacheFunctionalityService.add("bike_1", new Bike("BMW", 400.00, "Sports", 3.75, 5.0, "White"));
		cacheFunctionalityService.add("bike_2", new Bike("RoyalEnfield", 350.00, "Cruise", 1.49, 1.82, "Desert Storm"));
		cacheFunctionalityService.add("bike_3", new Bike("Jawa", 298.00, "Cruise", 1.25, 1.48, "Black"));
		cacheFunctionalityService.add("bike_4", new Bike("Triumph", 800.00, "Cruise", 15.20, 17.40, "Blue"));
		cacheFunctionalityService.add("bike_5", new Bike("Honda", 110.0, "Scooty", 0.65, 0.71, "Grey"));
		cacheFunctionalityService.add("bike_6", new Bike("Hero", 100.00, "Standard", 0.55, 0.65, "Black"));
		cacheFunctionalityService.add("bike_7", new Bike("Bajaj", 220.00, "Sports", 1.10, 1.25, "Red"));

		//1.Remove existing object
		log.info("TEST REMOVE:: Remove exisiting objects from CACHE by key");
		cacheFunctionalityService.remove("bike_6");
		assertEquals((Constants.CACHE_SIZE - 1), cacheFunctionalityService.size());

		//2. Try removing unknown object size should be same
		log.info("TEST REMOVE:: Try removing invalid objects");
		cacheFunctionalityService.remove("bike_6");
		assertNotEquals(Constants.CACHE_SIZE, cacheFunctionalityService.size());
	}

	@Test
	public void testClear() {
		//1. Add elements into the cache.
		log.info("TEST CLEAR:: Objects Added in CACHE");
		cacheFunctionalityService.add("bike_1", new Bike("BMW", 400.00, "Sports", 3.75, 5.0, "White"));
		cacheFunctionalityService.add("bike_2", new Bike("RoyalEnfield", 350.00, "Cruise", 1.49, 1.82, "Desert Storm"));
		cacheFunctionalityService.add("bike_3", new Bike("Jawa", 298.00, "Cruise", 1.25, 1.48, "Black"));
		cacheFunctionalityService.add("bike_4", new Bike("Triumph", 800.00, "Cruise", 15.20, 17.40, "Blue"));
		cacheFunctionalityService.add("bike_5", new Bike("Honda", 110.0, "Scooty", 0.65, 0.71, "Grey"));
		cacheFunctionalityService.add("bike_6", new Bike("Hero", 100.00, "Standard", 0.55, 0.65, "Black"));
		cacheFunctionalityService.add("bike_7", new Bike("Bajaj", 220.00, "Sports", 1.10, 1.25, "Red"));
		
		assertEquals(Constants.CACHE_SIZE, cacheFunctionalityService.size());
		
		log.info("TEST CLEAR:: Clear the whole CACHE");
		cacheFunctionalityService.clear();
		assertNotEquals(Constants.CACHE_SIZE, cacheFunctionalityService.size());
	}
	
}
