public class DiskDrive {
	
	public static int oldTrack; // for the track position
	public static final int DISKSIZE = 1024;

	private int moveTime; // time it takes to move arm
	private static final int READTIME = 1;		
	public DiskDrive() {} 
	
	// synchronized locks 'disk usage' from other instances
	public synchronized void diskUse(int newTrack) {
		moveTime = Math.abs(newTrack - oldTrack);	// calculate move time		
		System.out.println("Disk is at track # " + oldTrack + ", it will move  " + moveTime + " tracks in " + (moveTime + 1) + "  ms.");	
		// update track arm location		
		oldTrack = newTrack;
		try {Thread.sleep(moveTime + READTIME);} catch(Exception e) {};
	}
}
