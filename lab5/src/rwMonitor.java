// This file defines class "rwMonitor", a monitor for the reader/writer
// problem.  It defines methods startRead, endRead, startWrite, endWrite.
//
// The synchronization (readers have priority) is the same as was used
// in lab 3.  Where lab 3 used semaphores, a monitor is used here.
//
// startRead, endRead, startWrite, and endWrite are declared to be 
// "synchronized" methods.  The "synchronized" keyword tells the Java 
// compiler to provide mutually exclusive access.  At any given time, at most
// one thread can be executing any of the code inside these synchronized
// methods.  For example, if thread R1 is executing startRead, then any
// other threads that call startRead, endRead, startWrite, or endWrite
// have to wait.  The compiler generates code to do this, both to make the
// calling threads wait, and to arrange that when R1 finishes execution of
// StartRead, one of the waiting threads is allowed to continue executing.

public class rwMonitor {
  private int readcount;   // A count of the number of readers who are
                           // currently reading.
  private int writecount;  // A count of the number of writers who are
                           // currently writing (0 or 1).

  // This is the constructor for class rwMonitor.
  public rwMonitor() {
    readcount = 0;
    writecount = 0;
  }  // end of the constructor for class "rwMonitor"
  public synchronized void startRead () {
    while (writecount > 0) {
       try {  wait(); } catch (Exception e) {};  // wait for "notify()"
    }  // end of the while loop
    readcount++;
  }  // end of "startRead" method

  public synchronized void endRead () {
    readcount--;
    if (readcount == 0) {
        notifyAll(); 
    }  // if readcount = 0
  }  // end of "endRead" method


  public synchronized void startWrite () {
    while (readcount > 0 || writecount > 0) {
       try {  wait(); } catch (Exception e) {};  // wait for notify()
    }  // end of the while loop
    writecount++;
  }  // end of "startWrite" method

  public synchronized void endWrite () {
    writecount--;
    notifyAll();  // notify all processes that are waiting.  This will
                  // start all waiting readers, or one waiting writer.
  }  // end of "endWrite" method

}  // end of class "rwMonitor"

