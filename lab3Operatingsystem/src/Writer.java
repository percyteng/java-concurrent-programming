// This file defines class "writer".

// This code uses
//      class Semaphore, from the java.util.concurrent package in Java 5.0 which defines the behaviour of a 
//                           semaphore, including acquire and release operations.
//      class Synch, which defines the semaphores and variables
//                   needed for synchronizing the readers and writers.
//      class RandomSleep, which defines the doSleep method.


public class Writer extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor for class Reader.

  RandomSleep rSleep;  // rSleep can hold an instance of class RandomSleep.



  // This is the constructor for class Writer.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Writer(int name) {
    myName = name;  // copy the parameter value to local variable "MyName"
    rSleep = new RandomSleep();   // Create and instance of RandomSleep.
  }  // end of the constructor for class "Writer"
  public void startWriter(){
      System.out.println("Writer " + myName + " wants to write");
	  try {
		Synch.mutex.acquire();
	} catch (InterruptedException e) {}
	  if (Synch.ReadersActive > 0 || Synch.WritersActive >0){
		  Synch.WritersWaiting++;
		  Synch.mutex.release();
		  try {
			Synch.writerSem.acquire();
		} catch (InterruptedException e) {}
	  }
	  else{
	      System.out.println("Writer " + myName + " is now writing");
		  Synch.WritersActive++;
		  Synch.mutex.release();
	      endWriter();
	  }
	  
  }
  public void endWriter(){
	  try {
		Synch.mutex.acquire();
	} catch (InterruptedException e) {}
	  Synch.WritersActive--;
	  if(Synch.WritersWaiting > 0){
		  Synch.writerSem.release();
		  Synch.WritersWaiting--;
		  Synch.WritersActive++;
	  }
	  else if(Synch.ReadersWaiting > 0){
		  for (int i = 1; i <= Synch.ReadersWaiting; i++){
			  Synch.readerSem.release();
		  }
		  Synch.ReadersActive= Synch.ReadersWaiting;
		  Synch.ReadersWaiting = 0;
	  }
	  Synch.mutex.release();
      System.out.println("Writer " + myName + " is finished writing");
  }

  public void run () {
    for (int I = 0;  I < 5; I++) {

      // Get permission to write
      startWriter();

      rSleep.doSleep(1, 200);

      // We're done writing.  Signal the "wrt" semaphore.  If a Reader thread
      // was waiting on "wrt", that reader starts, and allows other waiting
      // readers (who are waiting on "mutex") to start as well.  If a Writer
      // thread was waiting on "wrt", then that writer goes next.  If no one
      // was waiting on wrt, then wrt has the value 1, so the next future
      // reader or writer can go without waiting.



      // Simulate "doing something else"
      rSleep.doSleep(1, 1000);
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Writer"

