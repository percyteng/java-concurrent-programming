// This file contains the main method for a traffic simulation.

// This code uses
//      class Car, which defines the behaviour of a car.
//      class Synch, which defines the semaphores and variables
//                   needed for synchronizing the cars.

// --- Use of Synch.timeSim ---
// Synch.timeSim is code provided by Prof. Blostein to allow your simulation
// to keep accurate track of simulated time.  (As discussed in class, the standard
// java "thread.sleep()" is quite inaccurate: if one thread starts "sleep(10)" at the
// same time that another thread starts "sleep(20)", the inaccurate timing can easily
// cause the "sleep(20)" thread to wake up first.)
//
// For the timeSim code to work properly, timeSim needs to know how many threads there
// are.  When all the threads have finished their computation, then timeSim can advance
// the simulated time.
// As you look at the code for Car.java, notice that the constructor for Car calls 
// Synch.timeSim.threadStart().  Also, the end of the run() method calls Synch.timeSim.threadEnd().
// If you create other kinds of threads (e.g. to operate the traffic light), those threads should 
// make similar calls to timeSim, in the constructor and at the end of the run() method.



public class MainMethod {
  public static void main (String argv[]) {

    Synch.timeSim = new TimeSim();  // timeSim provides accurate "sleep"
          // timing (in contrast to thread.sleep, which is highly variable)
    Synch.debug=0;  // We don't want debug output from TimeSim.Java

    // Initialize the semaphores and variables that are needed for thread
    // synchronization.
    // *** No semaphores are used in the car code, yet.  You will add some.
    // *** This declaration of semaphore "mutex" is here to remind you how
    // *** to declare semaphores.
    Synch.westSem = new Semaphore(0,true);
    Synch.eastSem = new Semaphore(0,true);
    Synch.mutex = new Semaphore(1, true);
    Synch.westcount = 0;
    Synch.eastcount = 0;
    // Create several instances of Car threads
    Car C;  // C can hold an instance of class Car

    for (int i=1; i<=8; i++) {
      C = new Car(i);
      C.start();
    }

    System.out.println("This is main speaking");
    
    // This part of the code is a new thread representing the function of traffic lights.
    // The time Q we chose is 300
    //It starts at red light and turns to green light for westbound and release west Semaphore for waiting cars to acquire in car class.
    // It turns back to red light after time 300 and does the same thing for eastbound traffic.
    Synch.timeSim.threadStart();
    while(Synch.carsActive > 0){
	System.out.println("The light is now red");
	Synch.timeSim.doSleep(100);
	System.out.println("Lights to westbound is green now, " + Synch.westcount + " cars can go");
	for (int j=0; j < Synch.westcount; j++){
	    Synch.westSem.release();
	}
	Synch.timeSim.doSleep(300);
	System.out.println("The light is now red");
	Synch.timeSim.doSleep(100);
	System.out.println("Lights to eastbound is green now, " + Synch.eastcount + " cars can go");
	for (int k=0; k < Synch.eastcount; k++){
	    Synch.eastSem.release();
	}
	Synch.timeSim.doSleep(300);
    }
    
    Synch.timeSim.threadEnd();

  }  // end of "main"
}  // end of "MainMethod"


