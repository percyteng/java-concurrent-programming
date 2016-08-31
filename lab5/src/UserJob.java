import java.util.Random;

public class UserJob extends Thread {
  int myName;  // The name of this thread, passed as a parameter by
               // the process creating this thread
  SharedDataStruct sharedData;  // The shared data.
  final int SLEEPTIME = 1000;
  int CPUTIME = 10;
  int IOTIME = 300;
  int CPUcount = 1;
  private String boundType;
  // This is the constructor for class Reader.
  public UserJob(int name, SharedDataStruct SD, String boundType) {
	this.boundType = boundType;
    myName = name;    // copy the parameter value to local variable "MyName"
    sharedData = SD;  // sharedData refers to the SharedDataStruct object
                      // that the readers and writers are using.
  }  // end of the constructor for class "Reader"

 
  public void run () {
    int val;  // The value read from the database

    for (int I = 0;  I < 5; I++) {
		if (boundType.equals("CPU")){
		      System.out.println("UserJob " + myName + 
		         " is starting to use CPU" + CPUcount);
		      CPUcount++;
		      try {
				Thread.sleep(CPUTIME);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
	    else{
		      int track;
			  track = 1 + (int)(Math.random() * ((1024 - 1) + 1));
		      System.out.println("UserJob " + myName + " is requesting access to disk sector " + track);
			  DiskDrive startUser = new DiskDrive();
		      startUser.useTheDisk(track);
		      System.out.println("UserJob " + myName + " has finished accessing disk sector " + track);
			}
	   
      // Simulate "doing something else" by delaying for a while.
      try { sleep((int)(500.0*Math.random()+1)); } catch(Exception e) {break;}
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Reader"