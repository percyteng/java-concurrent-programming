// This file defines class "Car".

// This code uses
//      class Synch, which defines the semaphores and variables
//                   needed for synchronizing the cars.

public class Car extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor.
 


  // This is the constructor for class Car.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Car(int name) {
    // copy the parameter value to local variable "MyName"
    myName = name;

    // Call threadStart to let the timeSim scheduler know that another
    // thread is starting.  timeSim needs to know how many threads there
    // are, so that it can accurately judge when all threads have finished
    // their current computation, so that simulated time can be advanced.
    Synch.timeSim.threadStart();
  }  // end of the constructor for class "Car"



  public void run () {
    for (int I=1;  I<= 4; I++) {

      // Simulate driving around Barriefield.
      System.out.println("At time " + Synch.timeSim.curTime() + " Car "
          + myName + " is driving around Barriefield.");
      Synch.timeSim.doSleep(1, 500);

      // Now cross the causeway westbound, into Kingston.
      // this part lets cars wait by acquiring westSem, and the cars will start moving again when the westSem is released in time thread in the main class.
      System.out.println("At time " + Synch.timeSim.curTime() + " Car "
          + myName + " wants to cross westbound.");
      try{
          Synch.mutex.acquire();
        }
      catch(Exception e){}
      Synch.westcount++;
      Synch.mutex.release();
	    try{
	      	Synch.westSem.acquire();
	    }
	    catch(Exception e){}
      try{
        	Synch.mutex.acquire();
        }
      catch(Exception e){}
	  Synch.westcount --;//the amout of cars waiting at west bound decrements one when a car acquires the westSem and starts moving
      
	  
      System.out.println("At time " + Synch.timeSim.curTime() + " Car "
              + myName + " is starting to cross westbound.");
      Synch.mutex.release();

      //cars are getting donuts after crossing westbound
      System.out.println("At time " + Synch.timeSim.curTime() + " Car "
          + myName + " is getting donuts at Tim Horton's.");
      Synch.timeSim.doSleep(1, 500);

      // Now cross the causeway eastbound, back into Barriefield.
      // this part lets cars wait by acquiring eastSem, and the cars will start moving again when the eastSem is released in time thread in the main class.
      System.out.println("At time " + Synch.timeSim.curTime() + " Car "
          + myName + " wants to cross eastbound.");
      try{
          Synch.mutex.acquire();
        }
        catch(Exception e){}
    	Synch.eastcount++;
        Synch.mutex.release();
        try{
          	Synch.eastSem.acquire();
        }
        catch(Exception e){}
      try{
        	Synch.mutex.acquire();
        }
      catch(Exception e){}
	  Synch.eastcount --; //the amout of cars waiting at east bound decrements one when a car acquires the eastSem and starts moving
	  
      System.out.println("At time " + Synch.timeSim.curTime() + " Car "
              + myName + " is starting to cross eastbound.");
      Synch.mutex.release();
      Synch.timeSim.doSleep(100);

    } // end of "for" loop
    System.out.println("At time " + Synch.timeSim.curTime() + " Car "
        + myName + " has finished and disappears.  Poof!");
    try {
    	Synch.mutex.acquire();
        } catch (Exception e) {}
    	
    //decreases the number of cars active at the end with the protection of mutex.
        Synch.carsActive--;

        Synch.mutex.release();
    Synch.timeSim.threadEnd();  // Let timeSim know that this thread
                                // has ended.
  }  // end of "run" method
}  // end of class "Car"

