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
  public void run () {
    for (int I = 0;  I < 5; I++) {
      System.out.println("Reader " + myName + " wants to read.");

      // Do acquire on the "mutex" semaphore, to get exclusive access to the
      // variable "Synch.readcount".
      try{
        Synch.mutex.acquire();
      }
      catch(Exception e){}
      if (Synch.mode==2 || Synch.wwcount>0){
    	  Synch.rwcount++;
        Synch.mutex.release();
        try{
                Synch.ReaderSem.acquire();
        }
        catch(Exception e){}
        try{
                Synch.mutex.acquire();
        }
        catch(Exception e){}
        Synch.rwcount--;
        }
        System.out.println("Reader " + myName + " is now reading.");

      Synch.mode = 1;
      Synch.racount++;
      Synch.mutex.release();
      // Simulate the time taken for reading
      rSleep.doSleep(1, 200);
  try{
        Synch.mutex.acquire();
      }
      catch(Exception e){}
      Synch.racount--;
      if (Synch.racount == 0){
        if (Synch.wwcount > 0){
          Synch.WriterSem.release();
          Synch.mode = 2;
        }
        else{
          Synch.mode = 0;
        }
      }
      System.out.println("Reader " + myName + " is finished reading.");
      Synch.mutex.release();
      // Simulate "doing something else".
      rSleep.doSleep(1, 1000);
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Reader"
