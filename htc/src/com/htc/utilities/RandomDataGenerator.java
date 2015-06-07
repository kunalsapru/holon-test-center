package com.htc.utilities;

import java.util.Random;

import org.apache.commons.lang.xwork.RandomStringUtils;
import org.apache.log4j.Logger;

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
}
