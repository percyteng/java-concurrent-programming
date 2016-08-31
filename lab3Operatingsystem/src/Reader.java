// This file defines class "Reader".

// This code uses
//      class Semaphore, from the java.util.concurrent package in Java 5.0 which defines the behaviour of a 
//                           semaphore, including acquire and release operations.
//      class Synch, which defines the semaphores and variables 
//                   needed for synchronizing the readers and writers.
//      class RandomSleep, which defines the doSleep method.


public class Reader extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor for class Reader.
  RandomSleep rSleep;  // rSleep can hold an instance of class RandomSleep.



  // This is the constructor for class Reader.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Reader(int name) {
    myName = name;  // copy the parameter value to local variable "MyName"
    rSleep = new RandomSleep();  // Create an instance of RandomSleep.
  }  // end of the constructor for class "Reader"
	public void startReader(){
	    System.out.println("Reader " + myName + " wants to read.  ");
		try {
			Synch.mutex.acquire();
		} catch (InterruptedException e) {}
		if (Synch.WritersWaiting > 0 || Synch.WritersActive > 0){
			Synch.ReadersWaiting++;
			Synch.mutex.release();
			try {
				Synch.readerSem.acquire();
			} catch (InterruptedException e) {}
		}
		else{
		    System.out.println("Reader " + myName + " is now reading.");
			Synch.ReadersActive++;
			Synch.mutex.release();
			endReader();
		}
	}

	public void endReader(){
		try {
			Synch.mutex.acquire();
		} catch (InterruptedException e) {}
		Synch.ReadersActive--;
		if (Synch.ReadersActive == 0 && Synch.WritersWaiting > 0){
			Synch.writerSem.release();
			Synch.WritersWaiting--;
			Synch.WritersActive++;
		    System.out.println("Reader " + myName + " is finished reading.  " );
		}
		Synch.mutex.release();

	}

  public void run () {
    for (int I = 0;  I < 5; I++) {

      startReader();
      // Now we have permission to start reading.  
      // Print a message and release mutex.

      Synch.mutex.release();

      // Simulate the time taken for reading
      rSleep.doSleep(1, 200);   

      // We're finished reading.  Decrement readcount.  If we are the last
      // reader, then signal "wrt".  The signal to "wrt" will either wake up
      // a waiting writer, or set the semaphore value to 1, so that a future
      // writer or reader can go without waiting.



      // Simulate "doing something else".
      rSleep.doSleep(1, 1000);
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Reader"

