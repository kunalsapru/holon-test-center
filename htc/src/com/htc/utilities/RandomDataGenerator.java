package com.htc.utilities;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.xwork.RandomStringUtils;
import org.apache.log4j.Logger;

import com.htc.JavaExceptions.ArrayLengthMismatchException;

/**
 * A class which gives utility methods to generate random values for each primitive type in java and user defined enums.
 * All methods in this class are static as the clients using this class need to create an object of this class to use these
 * utility methods. Just use the class name!
 * @author Achal
 *
 */
public class RandomDataGenerator {
	static Logger log = Logger.getLogger(RandomDataGenerator.class);
	private static  Random randomGenerator=new Random();
	
	
	public  static int generateRandomValueIntForUpperBound(int upperbound){
		//note a single Random object is reused here
	    int randomInt = randomGenerator.nextInt(upperbound);
	    log.info("Generated : " + randomInt);
	    return randomInt;
	}
	
	
	public static  int generateRandomValueIntInRange(int aStart, int aEnd){
	    if (aStart > aEnd) {
	      throw new IllegalArgumentException("Start cannot exceed End.");
	    }
	    //get the range, casting to long to avoid overflow problems
	    long range = (long)aEnd - (long)aStart + 1;
	    // compute a fraction of the range, 0 <= frac < range
	    long fraction = (long)(range * randomGenerator.nextDouble());
	    int randomNumber =  (int)(fraction + aStart);    
//	    log.info("Generated Random Value in Range between "+aStart+ "and" + aEnd +"which is :"+ randomNumber);
	    return randomNumber;
	  }
	
	public static int generateRandomValueInt(int count){//count - the length of random string to create
		return Integer.parseInt(RandomStringUtils.randomNumeric(count));
	}
	
	public static String generateRandomValueString(int count){
		return RandomStringUtils.randomAlphanumeric(count);
	}
	
	public static  <T extends Enum<?>> T getRandomEnum(Class<T> clazz){
        int x = randomGenerator.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
	
	public static boolean generateRandomValueBoolean(){
		return randomGenerator.nextBoolean();
	}
	
	
	/**
	 * This method(algorithm) takes an EnumClass and a array of frequencies for the corresponding enum types in the enum and returns
	 * an "enum type" based on the frequency(that is its probability)
	 * @author Achal
	 */
	public static  <T extends Enum<?>> T getRandomEnumWithProbability(Class<T> clazz,  int freq[]){
		ArrayLengthMismatchException arrayLengthMismatchException=new ArrayLengthMismatchException();
		
		int totalFreqSum=0;
		List<Integer> localExpandedList=new LinkedList<Integer>();	
		int n=clazz.getEnumConstants().length;   
		int enumarray[]=new int[n];
		int j=1;
		for(int i=0;i<enumarray.length;i++){
			enumarray[i]=j;
			j++;
		}
		
		log.info("Printing Contents(indexes) of the enumarray---->");
		for(int i=0;i<enumarray.length;i++){
			log.info(enumarray[i]);
		}
		
		arrayLengthMismatchException.setMessage("enumarray[] and freq[] do not have the same lengths. They should be same!");
		
		// Code to check the length of the arrays and throw exception if not equal	
		
		if(freq.length!=enumarray.length){
			log.info(arrayLengthMismatchException.getMessage());
			throw arrayLengthMismatchException;
		}

		
		 Map<Integer, Integer> items = new LinkedHashMap<Integer, Integer>();//LinkedHashMap ensures insertion order
		 for(int i=0;i<enumarray.length;i++){
				items.put(enumarray[i], freq[i]);
		 }
		 
		 for(int i=0;i<freq.length;i++){
			 totalFreqSum+=freq[i];
		 }
		 log.info("Totalsum of frequency is:"+totalFreqSum);
		 
		 Set<Integer> keyset = items.keySet();
		 for(Integer key : keyset){
	        
			 int value = (int) items.get(key);
			 for(int i=0;i<value;i++){
				 localExpandedList.add(key);
			 }
		 } 
		 
		int requiredIndex=getRandomItemFromList(localExpandedList)-1;
		return clazz.getEnumConstants()[requiredIndex];
    }
	
	
	/**
	 * Takes a list and returns a random item from that list. This method is customized to be a list of integers.
	 * @param list
	 * @return
	 */
	private static int getRandomItemFromList(List<Integer> list) {
	    int index = randomGenerator.nextInt(list.size());
	    log.info("Index :" + index );
	    return list.get(index);
	}
}
