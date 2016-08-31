public class UserJob extends Thread {

	// Limits for IO_BOUND, CPU_BOUND
	public static final int CPU_MIN_TIME = 100; 
	public static final int IO_MIN_TIME= 1; 
	public static final int CPU_MAX_TIME= 1000; 
	public static final int IO_MAX_TIME= 200; 
	public static final int MIN_IO_TIME=20;
	
	private DiskDrive DSKDRV; 
	private Monitor CPU; 

	private int sleeptime; // time to run operation
	public int trackNum; // track location
private int jobName; 
	int myJob; // job, cpu or io
	private int jobIterations = 10; 
	
	public UserJob(int name, int job, Monitor Mon, DiskDrive diskMon) { // constructor
		jobName = name;	
		myJob = job;		
		DSKDRV = diskMon;
		CPU = Mon;

	}

	public void run() {
		System.out.println("UserJob " + jobName + " is starting.");	
		for (int i = 1; i <= jobIterations; i++) {
			if (myJob == 0) { // 0 indicates cpu 
				sleeptime = CPU_MIN_TIME + (int)(Math.random() * ((CPU_MAX_TIME- CPU_MIN_TIME) + 1)); // calculate cpu time
				System.out.println("UserJob " + jobName + " starting CPU burst of length " + sleeptime + ".");
			}
			else {
				sleeptime = IO_MIN_TIME+ (int)(Math.random() * ((IO_MAX_TIME- MIN_IO_TIME) + 1)); // calculate io time 
				System.out.println("UserJob " + jobName + " starting IO burst of length CPUtime " + sleeptime + ".");
			}

			CPU.start(jobName);
			try { sleep(sleeptime); } catch(Exception e) {};	// simulate CPU execution
			CPU.endCPU(jobName);

			System.out.println("UserJob " + jobName + " trying to to access disk track " + trackNum);
			trackNum = (int)(Math.random() * ((DiskDrive.DISKSIZE) + 1)); //produce a track #
			DSKDRV.diskUse(trackNum); // run disk
			System.out.println("UserJob " + jobName + " finished reading disk track " + trackNum);			
		}	
	}
}
