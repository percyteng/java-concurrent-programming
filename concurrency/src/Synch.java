// This file defines class "Synch".  This class contains all the semaphores
// and variables needed to coordinate the instances of the Reader and Writer
// classes.

import java.util.concurrent.*;

public class Synch {

  public static Semaphore mutex;
  public static Semaphore ReaderSem;
  public static Semaphore WriterSem;
  public static int racount = 0;
  public static int wacount = 0;
  public static int rwcount = 0;
  public static int wwcount = 0;
  public static int mode = 0;

}  // end of class "Synch"
