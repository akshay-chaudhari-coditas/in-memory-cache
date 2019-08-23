# Internal Memory Cache (InMemoryCache)

InMemoryCache is a cache which is been used to avoid frequent datastore operations by storing the most frequently accessed object in the cache.
## Syntax

```bash
ConcurrentMap<K,V> cache= new ConcurrentMap<>(size);
K = Key for the cached object (String)
V = The object to be stored into the cache (CacheObject)
size = No of Objects to be stored into the cache
```

## Functionalities

```
1 Add
2 Load
3 Get
4 Remove
5 Flush
```

## 1. Add
 To add in the cache we need to create <b>CacheObject</b> which contains the expiry time, last modified time and the object to be stored into the cache. Whenever we want to the object into the cache the method checks whether there is space in the cache or not if yes then CacheObject is been created and stored into the cache. If the cache is full then it checks for the least used cache and removes that object from the cache and then adds the new object.
 
```
cacheFunctionalityService.add("suzuki",new Car("Maruti Suzuki", 1000.00, "Hatchback",5.45,6.30, 5, "White"));
cacheFunctionalityService.add("ford",new Car("Ford", 3198.00, "SUV", 40.00, 42.50, 7, "Black"));
cacheFunctionalityService.add("enfield",new Bike("Royal Enfield", 350.00, "Cruise", 1.49, 1.82, "Desert Storm"));
```
## 2.Load
The load is responsible to get the object from the datastore and add it into the cache.Loan internally calls the add method.

## 3.Get
To retrieve the object from the cache using the key passed to the get() function.

```
Car ford = (Car)cacheFunctionalityService.get("ford");
```
## 4.Remove
Remove the existing object from the cache by the key passed to the method.

```
cacheFunctionalityService.remove("kia");
```
## 5. Flush
Clear the whole cache.

##Execution
The following two steps will help you to execute the InMemoryCache applicaton and the test cases will also be carried out in it.

```
1.Creating JAR file
	mvn clean package
	
2. Execution of the JAR
	java -cp target/inmemorycache-1.0-SNAPSHOT.jar com.tavisca.App
```