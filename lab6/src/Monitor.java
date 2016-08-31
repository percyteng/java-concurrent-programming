import java.util.ArrayList;
import java.util.*;

public class Monitor {
	// CPUs array shared among instances of Monitor
	public static int[] CPUs = new int[0];	
	public static final int CPUS = 3;
	
	public Monitor() {
		init(CPUS);		
	}
	// initialize a CPU by making PID = 0, 
//we also wake up UserJobs if there are any free CPUs
	public synchronized void endCPU(int pid) {
		System.out.println("UserJob " + pid + " is freeing CPU " + PIDindex(pid));
		swap(pid, 0);	// 0 = free	
		if (!full())
			notifyAll(); // notify other process when CPUs are free 		
	}

	// when we don¡¯t have CPUs we set the array to 0
	private void init(int NumCPU) {
		if (CPUs.length == 0) {
			CPUs = new int[NumCPU];
			for (int i = 0; i < NumCPU; i++) CPUs[i] = 0;
		}
	}

	// method to swap values in the CPU array
	private void swap(int target, int newValue) {
		for (int i = 0; i < CPUs.length; i++) {
			if (CPUs[i] == target) {
				CPUs[i] = newValue;
				return;
			}
		}
	}
	
	// when CPUs are all busy we wait, otherwise use the available CPUs
	// when finished wake up waiting UserJobs in case a CPU is free
	public synchronized void start(int pid) {
		while (full()) try { wait(); } catch(Exception e) {};
		swap(0, pid);
		System.out.println("UserJob " + pid + " is executing on CPU " + PIDindex(pid));
		printCPUs();
	}

	
	// check if there are any free CPUs 
	public static boolean full() { 
		for (int i = 0; i < CPUs.length; i++) {
			if (CPUs[i] == 0) return false;
		}
		return true;
	}


// find a free CPU
	public int PIDindex(int pid){
		for (int i = 0; i < CPUs.length; i++) {
			if (CPUs[i] == pid) return i;
		}
		return -1;
	}
	// prints out cpu usage 
	private void printCPUs() {
		for (int i = 0; i < CPUs.length; i++) System.out.println("CPU " + i + " executing PID " + CPUs[i]);
	}
}
