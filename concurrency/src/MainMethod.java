import java.util.concurrent.*;

public class MainMethod {
  public static void main (String argv[]) {

    // Initialize the semaphores/variables needed for thread synchronization
    // The constructor of the Semaphore class accepts two parameters. The first is an integer parameter that
    // specifies the initial number of permits available. The second is a boolean parameter that will ensure
    // that permits are granted on a FIFO basis if set to true.
    Synch.mutex = new Semaphore(1, true);
    Synch.WriterSem = new Semaphore(1, true);
    Synch.ReaderSem = new Semaphore(1, true);

    // Now create several instances of Reader and Writer.
    Reader R;  // R can hold an instance of class Reader
    Writer W;  // W can hold an instance of class Writer

    for (int i=1; i<=8; i++) {
      W = new Writer(i);
            W.start();
      R = new Reader(i);
            R.start();
    }

    System.out.println("This is main speaking");
  }  // end of "main"
}  // end of "MainMethod"

