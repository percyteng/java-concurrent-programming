
public class DiskDrive {
	private static int track;
	static int IOTIME = 1;
	private static int jobCount = 0;
	public DiskDrive(){
	}
	public  void useTheDisk(int trackNum){
		while (jobCount > 0){
			try {  wait(); } catch (Exception e) {};
		}
		jobCount++;
		System.out.println("The current track is " + track + ". It is going to " + trackNum + ". And it will need to move " + Math.abs((trackNum-track)));
		startUser(trackNum);

			
		  
	}
	public synchronized void startUser (int userTrack) {
		try {
			Thread.sleep(IOTIME*(Math.abs(userTrack-track)));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    track = userTrack;
	    jobCount--;
	    this.notify();
	  }  // end of "startRead" method
}
