// This file defines class "Synch".  This class contains all the semaphores
// and variables needed to coordinate the instances of the Reader and Writer
// classes.  

import java.util.concurrent.*;

public class Synch {

  public static Semaphore mutex;
  public static Semaphore wrt;
  public static int readcount = 0;
  public static Semaphore readerSem;
  public static Semaphore writerSem;
  public static int ReadersWaiting, WritersWaiting, ReadersActive, WritersActive;

}  // end of class "Synch"

