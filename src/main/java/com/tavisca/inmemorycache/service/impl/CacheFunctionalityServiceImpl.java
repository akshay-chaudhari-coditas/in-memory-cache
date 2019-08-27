package com.tavisca.inmemorycache.service.impl;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tavisca.Constants;
import com.tavisca.inmemorycache.CacheObject;
import com.tavisca.inmemorycache.service.CacheFunctionalityService;

public class CacheFunctionalityServiceImpl implements CacheFunctionalityService{

	private static final Logger log = LoggerFactory.getLogger(CacheFunctionalityServiceImpl.class);

	private ConcurrentMap<String, CacheObject>  cache = new ConcurrentHashMap<>(Constants.CACHE_SIZE);

	private long higheshMilli = 0;
	private String keyToBeReplaced = null;

	@Override
	public void add(String key, Object data) {
		removeExpiredObjectsFromCache();
		if(validateTheCacheObjectInput(key, data) ) {
			addDataIntoTheCache(key, data);
		}
	}

	@Override
	public Object get(String key) {

		boolean isObjectExpired =  cache.containsKey(key) ? cache.get(key).isObjectExpired() : false;
		CacheObject cachedObject = isObjectExpired ? null : cache.get(key);

		if(cachedObject != null){
			add(key, cachedObject.getData());
		}
		return cachedObject==null?null:cachedObject.getData();
	}

	@Override
	public void remove(String key) {
		cache.entrySet().removeIf(entry -> entry.getKey().equals(key));
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public long size() {
		return cache.size();
	}

	@Override
	public void load(long id) {
		/*
		 * Code to retrieve the object from data store and it into the cache. This
		 * method can be used when you want the supportive objects the database to be
		 * stored into the cache
		 */
	}

	/**
	 * Before adding the data into the cache this methods checks whether any 
	 * existing objects are expired or not if expired then it removes it from the cache
	 * then we proceed to add an new object 
	 */
	private void removeExpiredObjectsFromCache() {
		cache.entrySet().removeIf(entry ->
		entry.getValue().getExpiryTime() < System.currentTimeMillis());
	}

	/**
	 * This method validates the key and data to be stored into the cache.
	 *
	 * @param key - Key for the object in the cache. If null then the object is not been added into the cache.
	 * @param data - If data is null and then does not allow to add data into the cache and if the key is present into
	 *              the cache then deletes the entry of that key because the object is null.
	 * @return true if key and data are validated successfully else false.
	 */
	private Boolean validateTheCacheObjectInput(String key, Object data) {

		if(key == null || key.isEmpty()) {
			return false;
		}else if(data == null || data.toString().isEmpty()) {
			remove(key);
			return false;
		}
		return true;
	}

	/**
	 * This is the core logic to add the data into the cache if their is place into the cache to add then 
	 * the object is been added or updated else by the use of replacement algorithm the object is added into the cache. 
	 * @param key - To be added into the cache
	 * @param data - To be added into the cache.
	 */
	private void addDataIntoTheCache(String key,Object data) {
		CacheObject cacheObject = Optional.ofNullable(cache.get(key)).map(cacheObjectSoftReference ->
		cacheObjectSoftReference).orElse(null);
		if(cacheObject != null) {
//			log.info("Object updation");
			cacheObject.setExpiryTime(System.currentTimeMillis() + Constants.MAX_LIFETIME_OF_OBJECT_IN_CACHE);
			cacheObject.setLastModifiedTime(System.currentTimeMillis());
			cacheObject.setData(data);
			cache.put(key,cacheObject);
		}else if(cache.size() < Constants.CACHE_SIZE){
			long expiryTime = System.currentTimeMillis() + Constants.MAX_LIFETIME_OF_OBJECT_IN_CACHE;
			cache.put(key, new CacheObject(expiryTime,System.currentTimeMillis(),data));
//			log.info("object added in cache");
		}else {
			//Replacement Algorithm
			replacementAlgorithm(key, data);
		}
	}

	/**
	 * Replacement Algorithm Instead of not allowing to add the object into the cache this algorithm 
	 * check which is the least accessed object by using the lastModifiedTime and replaces the new object 
	 * with the old one.
	 * @param key - Key to be replaced.
	 * @param data - Data to be replaced.
	 */
	private void replacementAlgorithm(String key,Object data) {
//		log.info("Replacement Algorithm");
		//Removing the least used object with the new one instead of not adding it in the cache
		long currentMilli = System.currentTimeMillis();
		cache.entrySet().forEach(entry->{
			if((currentMilli - entry.getValue().getLastModifiedTime()) > higheshMilli) {
				higheshMilli = entry.getValue().getLastModifiedTime();
				keyToBeReplaced = entry.getKey();
			}
		});
		remove(keyToBeReplaced);
		long expiryTime = System.currentTimeMillis() + Constants.MAX_LIFETIME_OF_OBJECT_IN_CACHE;
		cache.put(key, new CacheObject(expiryTime,System.currentTimeMillis(),data));
	}

}
