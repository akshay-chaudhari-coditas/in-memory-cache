package com.tavisca;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tavisca.inmemorycache.model.Bike;
import com.tavisca.inmemorycache.model.Car;
import com.tavisca.inmemorycache.service.CacheFunctionalityService;
import com.tavisca.inmemorycache.service.impl.CacheFunctionalityServiceImpl;


public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
    	BasicConfigurator.configure();

        Logger logger = LoggerFactory.getLogger(App.class.getName());

        //1.Creating Object of the Interface
        CacheFunctionalityService cacheFunctionalityService = new CacheFunctionalityServiceImpl();

        //2.Add data into the Map(cache)
        cacheFunctionalityService.add("suzuki",new Car("Maruti Suzuki", 1000.00, "Hatchback",5.45,6.30, 5, "White"));
        cacheFunctionalityService.add("ford",new Car("Ford", 3198.00, "SUV", 40.00, 42.50, 7, "Black"));
        cacheFunctionalityService.add("enfield",new Bike("Royal Enfield", 350.00, "Cruise", 1.49, 1.82, "Desert Storm"));
        cacheFunctionalityService.add("kia",new Car("Kia", 1500.00, "Seltos", 12.15, 18.20, 5, "Red"));
        cacheFunctionalityService.add("jawa",new Bike("Jawa", 298.00, "Cruise", 1.25, 1.48, "Black"));
        cacheFunctionalityService.add("mahindra",new Car("Mahindra", 2179.00, "SUV", 14.25, 17.30, 7, "Black"));
        cacheFunctionalityService.add("triumph",new Bike("Triumph", 800.00, "Cruise", 15.20, 17.40, "Blue"));
        
        //4 Get data from the cache by any key.
        Car ford = (Car)cacheFunctionalityService.get("kia");

        logger.info("Ford Data", ford.toString());

        logger.info("Cache size",cacheFunctionalityService.size());

        //5 Update the existing data.
        cacheFunctionalityService.add("suzuki",new Car("Maruti Suzuki", 1000.00, "Hatchback",5.45,6.30, 5, "White"));
        
        //6 Update existing object to null
        cacheFunctionalityService.add("suzuki",null);
        
        //7 Size after trying to add null object.
        logger.info("Cache size",cacheFunctionalityService.size());

        //8 Sleep the thread to see the working on the Cache
        Thread.currentThread().sleep(8000);

        
        logger.info("Size of cache after Thread wakeup",cacheFunctionalityService.size());

        //9 Again try adding object into the cache
        cacheFunctionalityService.add("suzuki_1",new Car("Maruti Suzuki", 1500.00, "Hatchback",5.45,6.30, 5, "White"));

        //10 Remove existing object from the cache. 
        cacheFunctionalityService.remove("kia");
        
        //11 Flush the whole cache
        cacheFunctionalityService.clear();
        
    }
}
