package com.tavisca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tavisca.inmemorycache.model.Car;
import com.tavisca.inmemorycache.service.CacheFunctionalityService;
import com.tavisca.inmemorycache.service.impl.CacheFunctionalityServiceImpl;

public class CacheFunctionalityServiceImplTest {
	CacheFunctionalityService cacheFunctionalities;
	private final int CACHE_SIZE = Constants.CACHE_SIZE;
	@BeforeEach
	public void instantiateMapWithData() throws InterruptedException{

		cacheFunctionalities = new CacheFunctionalityServiceImpl();
		cacheFunctionalities.add("ford",new Car("Ford", 3198.00, "SUV", 40.00, 42.50, 7, "Black"));
		cacheFunctionalities.add("suzuki",new Car("Maruti Suzuki", 1000.00, "Hatchback",5.45,6.30, 5, "White"));
		cacheFunctionalities.add("kia",new Car("Kia", 1500.00, "Seltos", 12.15, 18.20, 5, "Red"));
		cacheFunctionalities.add("mahindra",new Car("Mahindra", 2179.00, "SUV", 14.25, 17.30, 7, "Black"));
	}
	@Test
	public void add() throws InterruptedException{
		assertEquals(CACHE_SIZE,cacheFunctionalities.size());


		Thread.currentThread();
		Thread.sleep(8000);

		cacheFunctionalities.add("hyundai",new Car("Hyundai", 1800.00, "SubSUV", 14.25, 18.30, 7, "Black"));

		assertNotEquals(CACHE_SIZE,cacheFunctionalities.size());

		Thread.currentThread();
		Thread.sleep(4000);


	}

	@Test
	public void remove(){
		cacheFunctionalities.remove("ford");

		assertEquals(CACHE_SIZE,cacheFunctionalities.size());

		cacheFunctionalities.remove("kia");

		assertNotEquals(CACHE_SIZE,cacheFunctionalities.size());
	}

	@Test
	public void clear(){

		assertEquals(CACHE_SIZE,cacheFunctionalities.size());

		cacheFunctionalities.clear();

		assertNotEquals(CACHE_SIZE,cacheFunctionalities.size());

	}

	@Test
	public void size(){

		long size = cacheFunctionalities.size();

		assertEquals(CACHE_SIZE,size);

	}

	@Test
	public  void get(){

		Object oldObject = cacheFunctionalities.get("suzuki");
		Object updatedObjectWithTime = cacheFunctionalities.get("suzuki");

		assertEquals(((Car)oldObject).getMake(),((Car)updatedObjectWithTime).getMake());
		assertEquals(((Car)oldObject).getEngineCC(),((Car)updatedObjectWithTime).getEngineCC());
		assertEquals(((Car)oldObject).getSeatingCapacity(),((Car)updatedObjectWithTime).getSeatingCapacity());

		cacheFunctionalities.add("hyundai",new Car("Hyundai", 1800.00, "SubSUV", 14.25, 18.30, 7, "Black"));
		Object updatedObjectWithValues = cacheFunctionalities.get("hundai");

		assertEquals(((Car)oldObject).getMake(),((Car)updatedObjectWithTime).getMake());
		assertEquals(((Car)oldObject).getEngineCC(),((Car)updatedObjectWithTime).getEngineCC());
		assertEquals(((Car)oldObject).getSeatingCapacity(),((Car)updatedObjectWithTime).getSeatingCapacity());


	}


}
