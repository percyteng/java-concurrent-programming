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

  public void run () {
    for (int I = 0;  I < 5; I++) {

      // Get permission to write
       System.out.println("Writer " + myName + " wants to write");
      try{
        Synch.mutex.acquire();
      }
      catch(Exception e){}
      if (Synch.mode !=0){
        Synch.wwcount++;
        Synch.mutex.release();
        try{
         Synch.WriterSem.acquire();
        }
        catch(Exception e){}
        try{
         Synch.mutex.acquire();
        }
        catch(Exception e){}
        Synch.wwcount--;
      }
      System.out.println("Writer " + myName + " is now writing");
      Synch.wacount++;
      Synch.mode = 2;
      Synch.mutex.release();
      rSleep.doSleep(1, 200);
            System.out.println("Writer " + myName + " is finished writing");

      try{
        Synch.mutex.acquire();
      }

      catch(Exception e){}
      Synch.wacount--;
      Synch.mutex.release();
      try{
        Synch.mutex.acquire();
        }
        catch (Exception e) {}
      if (Synch.wwcount > 0){

        Synch.WriterSem.release();
      }
      else if (Synch.wwcount == 0 && Synch.rwcount > 0){
        for (int rw = 0; rw < Synch.rwcount; rw++){
          Synch.ReaderSem.release();
          Synch.mode = 1;
        }
      }
     else{
        Synch.mode = 0;
      }
      Synch.mutex.release();
      // Simulate "doing something else"
      rSleep.doSleep(1, 1000);
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Writer"
