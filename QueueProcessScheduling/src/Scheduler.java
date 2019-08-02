import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Scheduler {

	private static class Process {
		private String name;
		private int timeToCompletion;
		private int startTime;

		/**
		 * Creates new process with specified
		 * start time, execution duration, and name.
		 * 
		 * @param startTime Non-negative simulation time.
		 * @param executionTime Positive number of time units
		 * 						that process requires on processor.
		 * @param name Name that identifies process in output file.
		 */
		public Process(int startTime, int executionTime, String name) {
			if(executionTime <= 0) { // Don't allow non-positive execution durations
				throw new IllegalArgumentException("Execution time must be positive: " + executionTime);
			}
			if(startTime < 0) { // Don't allow negative start times
				throw new IllegalArgumentException("Start times cannot be negative: " + startTime);
			}
			this.name = name;
			this.timeToCompletion = executionTime;
			this.startTime = startTime;
		}

		/**
		 * When the process should appear in the ready queue in terms of
		 * simulation time (starts from 0 and goes up from there)
		 * @return Integer start time of process.
		 */
		public int getStartTime() {
			return startTime;
		}

		/**
		 * Get name identifying the process
		 * @return String name of process
		 */
		public String getName(){
			return name;
		}

		/**
		 * Returns remaining number of time units that the
		 * process must spend on the processor in order to complete.
		 * @return Remaining execution time
		 */
		public int getTimeRemaining(){
			return timeToCompletion;
		}

		/**
		 * Executes the process for a given number of time units, which
		 * simply means subtracting the given time from the remaining 
		 * timeToCompletion. The provided time must be positive and
		 * cannot exceed the timeToCompletion without causing an exception.
		 * 
		 * @param processorTime Time to run process for. Must be positive and
		 *                      less than or equal to timeToCompletion.
		 */
		public void executeForTime(int processorTime) {
			if(processorTime <= 0) { // Cannot run for a non-positive amount of time
				throw new IllegalArgumentException("Time spent on processor must be positive: " + processorTime);
			} else if(processorTime > timeToCompletion) { // Cannot run more than the process has left to run
				throw new IllegalArgumentException("Cannot execute for more time than is remaining: " + processorTime + " > " + timeToCompletion);
			}
			timeToCompletion -= processorTime;
		}
	}

	/**
	 * 
	 * Creates a Scanner for a specified input file name and an output stream for
	 * a specified output file name, and runs the processor simulation with a
	 * given timeout period using the two files. All resources are closed
	 * at the completion of the method.
	 * 
	 * @param inputFile Name of file containing correctly formatted input data (see below)
	 * @param outputFile Name of file that will contain output data
	 * @param timeout How long a process can be on the processor before being kicked off
	 * @throws FileNotFoundException If either the input or output files are not found/created
	 */
	public static void simulateProcessor(String inputFile, String outputFile, int timeout) throws FileNotFoundException {
		Scanner processList = new Scanner(new File(inputFile));
		PrintStream processHistory = new PrintStream(new File(outputFile));

		simulateProcessor(processList, processHistory, timeout);

		processList.close();
		processHistory.close();
	}

	/**
	 * Takes input file Scanner and PrintStream to the output file from the simulateProcessor
	 * method above, along with the timeout, and actually runs the simulation. The input file
	 * contains one line per process to be loaded, and three columns. The first column is the
	 * start time, the second column is the execution duration, and all remaining text makes
	 * up the process name. Note that process names may consist of multiple string tokens
	 * separated by whitespace. Note that the start times of sequential processes in the file
	 * must be non-decreasing (times increase, but ties are allowed).
	 * 
	 * @param processList Scanner that reads the input file
	 * @param processHistory PrintStream to the output file
	 * @param timeout How long a process can be on the processor before being kicked off
	 */
	private static void simulateProcessor(Scanner processList, PrintStream processHistory, int timeout) {
		//For processes that are not ready to run at the current simulationTime
		Queue<Process> startQueue = new Queue<>();
		//For processes that are ready to run
		Queue<Process> readyQueue = new Queue<>();
		//Read in each process line by line from the scanner
		while(processList.hasNextLine()) {
			int startTime = processList.nextInt(); //Get the start time
			int runTime = processList.nextInt(); //Get the run time
			String processName = processList.nextLine().trim(); //Get the name of the process + remove leading and trailing whitespace
			Process p = new Process(startTime, runTime, processName); //Create the process and enqueue it into the startQueue
			startQueue.enqueue(p);
		}
		//Declare and initialize int simulationTime that will hold the current time of the scheduler
		int simulationTime = 0;
		//While there are processes in startQueue or in readyQueue, feed in processes with a startTime less than or equal to the simulationTime,
		//execute the next process, feed in processes if they had a start time during the execution of the current process, and enqueue the current process
		//if it has time left
		while(!startQueue.isEmpty() || !readyQueue.isEmpty()) { 			
			//Put all processes eligible to run at the current simulationTime in the readyQueue
			processFeeder(startQueue, readyQueue, simulationTime);
			//If there is a process present in readyQueue, execute the process, feed new processes into readyQueue, and place
			//the current process back into the queue in the event it is not finished executing
			if(!readyQueue.isEmpty()) {
				Process temp = readyQueue.dequeue();
				//Execute the process for the min of timeout and timeRemaining 
				int timeToExecute = Math.min(timeout, temp.getTimeRemaining());
				temp.executeForTime(timeToExecute);
				processHistory.println("At time "+ simulationTime+ ": run \""+ temp.getName() + "\" for "+timeToExecute+ " time units");
				//Increase simulationTime by the time elapsed executing the current process
				simulationTime += timeToExecute;
				//If the process has time left, feed in processes that have a start time during the execution of the current process
				//and have them execute before the current process in the next cycle. Then queue the current process
				if(temp.getTimeRemaining()>0){
					//Peek in front of start queue, if anything has start time past current time, move it to the ready queue
					processFeeder(startQueue, readyQueue, simulationTime);
					//Place the current process with time left back into the queue
					readyQueue.enqueue(temp);
				}
				//Else, the process is finished executing and is not pushed back onto the queue
			}
			//In the case the readyQueue is empty and startQueue has more processes not ready to run at the current simulation time,
			//run for idle time
			else {
				//Get the start time of the next process and calculate the time until that process will execute
				int idleTime = startQueue.getFront().getStartTime() - simulationTime;
				//Advance simulationTime past all of the idle period and print appropriate message
				processHistory.println("CPU idle from time "+simulationTime+" until time "+ (simulationTime+ idleTime));
				//Increase simulationTime by the time elapsed idling
				simulationTime += idleTime;
			}


		}

	}
	/**
	 * processFeeder is a helper method that moves processes that are eligible to be executed based on their start time
	 * from startQueue into readyQueue. It works utilizing a while loop that runs on the condition it will cycle 
	 * until either the startQueue is empty or the next process in startQueue has a start time exceeding the 
	 * current simulationTime
	 * @param startQueue Queue holding processes set to be executed at a certain start time
	 * @param readyQueue Queue holding processes that are eligible to be executed at the current simulation time
	 * @param simulationTime the current time elapsed in the program dictating when certain processes are eligible to 
	 * 		  be executed 
	 */
	private static void processFeeder(Queue<Process> startQueue, Queue<Process> readyQueue, int simulationTime) {
		//If a process is present and it has a startTime less than the current simulationTime, dequeue it from
		//startQueue and enqueue it into readyQueue
		while(!startQueue.isEmpty() && startQueue.getFront().getStartTime() <= simulationTime) {
			readyQueue.enqueue(startQueue.dequeue());
		} 		
	}
}
