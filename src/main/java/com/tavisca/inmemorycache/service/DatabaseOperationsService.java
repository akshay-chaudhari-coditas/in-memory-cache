package com.tavisca.inmemorycache.service;

import org.apache.log4j.Logger;

import com.tavisca.inmemorycache.model.UUIDGenerator;

public abstract class DatabaseOperationsService<T extends UUIDGenerator, UUID extends String> {


	public final static Logger log = Logger.getLogger(DatabaseOperationsService.class);
	
    CacheFunctionalityService<T, UUID> cacheFunctionalityService;
    
    public DatabaseOperationsService(CacheFunctionalityService<T, UUID> cacheFunctionalityService) {
    	this.cacheFunctionalityService = cacheFunctionalityService; 
    }
    
	public void deleteByUid(String uUID) {
		//delete the object from DB
		if(cacheFunctionalityService.get(uUID) == null)
     		cacheFunctionalityService.remove(uUID);
	}

	
	public void deleteAll() {
		cacheFunctionalityService.clear();
	}

	
	public Object save(T data,boolean addInCache) {
		//Object saved in datastore
		 if(addInCache) {
         	if(cacheFunctionalityService.get(data.getUuid()) == null)
         		cacheFunctionalityService.add(data);
         	else
         		log.info("Object already Exist in Cache");
         }
		
		return null;
	}

	/**
	 * This method is used to add Update the object in the database if the object is in the cache then it will
	 *  Firstly be updated in the data store and then updated in the cache.  
	 * @param data - Object to be updated in the data store.
	 * @return - nothing
	 */
	public void put(T data) {
		//update the object and then update it in the cache
		if(cacheFunctionalityService.get(data.getUuid()) == null)
    		cacheFunctionalityService.add(data);
		
	}

	


}
