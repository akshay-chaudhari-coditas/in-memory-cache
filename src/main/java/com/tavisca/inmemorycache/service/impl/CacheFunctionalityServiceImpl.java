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

	public CacheFunctionalityServiceImpl() {
	}

	@Override
	public void add(String key, Object data) {
		cache.entrySet().removeIf(entry ->
		entry.getValue().getExpiryTime() < System.currentTimeMillis()
				);
		if(key == null || key.isEmpty()){
			log.info("Key is null");
			return;
		}
		if(data == null || data.toString().isEmpty()){
			cache.remove(key);
			log.info("Data is null so the existing object in the cache for the key is been removed");
		}else{
			if(cache.size() < Constants.CACHE_SIZE) {
				CacheObject cacheObject = Optional.ofNullable(cache.get(key)).map(cacheObjectSoftReference ->
				cacheObjectSoftReference).orElse(null);
				if(cacheObject != null) {
					log.info("Object updation");
					cacheObject.setExpiryTime(System.currentTimeMillis() + Constants.MAX_LIFETIME_OF_OBJECT_IN_CACHE);
					cacheObject.setLastModifiedTime(System.currentTimeMillis());
					cacheObject.setData(data);
					cache.put(key,cacheObject);
				}else {
					long expiryTime = System.currentTimeMillis() + Constants.MAX_LIFETIME_OF_OBJECT_IN_CACHE;
					cache.put(key, new CacheObject(expiryTime,System.currentTimeMillis(),data));
					log.info("object added in cache");
				}
			}else{
				log.info("Replacement Algorithm");
				//Removing the least used object with the new one instead of not adding it in the cache
				cache.entrySet().forEach(entry->{
					if((System.currentTimeMillis() - entry.getValue().getLastModifiedTime()) > higheshMilli) {
						higheshMilli = entry.getValue().getLastModifiedTime();
						keyToBeReplaced = entry.getKey();
					}
				});
				remove(keyToBeReplaced);
				long expiryTime = System.currentTimeMillis() + Constants.MAX_LIFETIME_OF_OBJECT_IN_CACHE;
				cache.put(key, new CacheObject(expiryTime,System.currentTimeMillis(),data));
			}
		}

	}

	@Override
	public Object get(String key) {

		boolean isObjectExpired =  cache.get(key).isObjectExpired();
		log.debug(""+isObjectExpired);
		CacheObject cachedObject = isObjectExpired ? null : cache.get(key);

		if(cachedObject != null){
			cachedObject.setExpiryTime(System.currentTimeMillis() + Constants.MAX_LIFETIME_OF_OBJECT_IN_CACHE);
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
}
