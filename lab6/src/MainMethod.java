public class MainMethod {
  public static void main (String argv[]) {
  	int num = 5;
    System.out.println("The simulation of the computer system is starting");
    DiskDrive D = new DiskDrive();
    Monitor Mon = new Monitor();
    for (int i = 0; i < num; i++){ 
// run 5 times
      	UserJob job = new UserJob(i,i%2, Mon, D);
      	job.start(); // start user job 
    }



    System.out.println("This is main speaking");
  }  // end of "main"
}  // end of "MainMethod"

