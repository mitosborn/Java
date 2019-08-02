import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class SchedulerTest {

	// Inputs for first test case
	public static final String INPUT_NAME1 = "InputExample1.txt";
	public static final int TIMEOUT1 = 50;
	public static final String OUTPUT_NAME1 = "ExpectedOutput1.txt";

	public static final String INPUT_NAME2 = "InputExample2.txt";
	public static final int TIMEOUT2 = 100;
	public static final String OUTPUT_NAME2 = "ExpectedOutput2.txt";
	//Test with a large timeout value
	public static final String INPUT_NAME3 = "InputExample3.txt";
	public static final int TIMEOUT3 = 1000;
	public static final String OUTPUT_NAME3 = "ExpectedOutput3.txt";
	//Test with a small timeout value
	public static final String INPUT_NAME4 = "InputExample4.txt";
	public static final int TIMEOUT4 = 10;
	public static final String OUTPUT_NAME4 = "ExpectedOutput4.txt";
	//Test with a small timeout value not divisible by 10
	public static final String INPUT_NAME5 = "InputExample5.txt";
	public static final int TIMEOUT5 = 43;
	public static final String OUTPUT_NAME5 = "ExpectedOutput5.txt";
	//Large test with a typical timeout
	public static final String INPUT_NAME6 = "InputExample6.txt";
	public static final int TIMEOUT6 = 100;
	public static final String OUTPUT_NAME6 = "ExpectedOutput6.txt";
	
	//Large test with a large timeout value not divisible by 10
	public static final String INPUT_NAME7 = "InputExample7.txt";
	public static final int TIMEOUT7 = 2043;
	public static final String OUTPUT_NAME7 = "ExpectedOutput7.txt";
	
	//Output files for testing same input but with different timeouts
		public static final String OUTPUT_NAME8 = "ExpectedOutput8.txt";
		public static final String OUTPUT_NAME9 = "ExpectedOutput9.txt";



	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PrintStream ps1 = new PrintStream(new File(INPUT_NAME1));
		ps1.println("0      50   First");
		ps1.println("0      100  Second");
		ps1.println("0      50   Third");
		ps1.close();

		PrintStream ps2 = new PrintStream(new File(INPUT_NAME2));
		ps2.println("0      100  Initialization");
		ps2.println("0      200  Background service");
		ps2.println("10     150  Compiler");
		ps2.println("600    300  Virus scan");
		ps2.println("600    10   Quick process");
		ps2.println("610    150  Browser");
		ps2.println("1630   400  Movie clip");
		ps2.println("1890   350  Email");
		ps2.println("2100   100  Data processing");
		ps2.close();
		//Short process w/ interwoven start times using large timeout (1000)
		PrintStream ps3 = new PrintStream(new File(INPUT_NAME3));
		ps3.println("0      1100   Email");
		ps3.println("55      1700  Compiler");
		ps3.println("105     5000   Movie");
		ps3.println("1255    7804   Formatting");
		ps3.close();
		//Short process with quick timeout (10) and idle periods
		PrintStream ps4 = new PrintStream(new File(INPUT_NAME4));
		ps4.println("0      20 Startup");
		ps4.println("100    30 Chrome");
		ps4.println("105    30 Youtube");
		ps4.println("110    10 Play");
		ps4.println("180    30 JavaRuntime");
		ps4.println("200    30 Minecraft");

		ps4.close();
		//Short process with interwoven start periods, idle periods, and small timeout time not divisible by 10 (43)
		PrintStream ps5 = new PrintStream(new File(INPUT_NAME5));
		ps5.println("0      50   Startup");
		ps5.println("8      235  Virus Check");
		ps5.println("86     90   Initialize");
		ps5.println("400 	100  Cleanup");
		
		ps5.close();
		//Large process with interwoven start periods, idle periods, and a timeout time of 100
		PrintStream ps6 = new PrintStream(new File(INPUT_NAME6));
		ps6.println("0      50    Startup");
		ps6.println("5      100   Update Registry");
		ps6.println("100    500   Virus Scan");
		ps6.println("120    150   Index files");
		ps6.println("220    500   Spotify");
		ps6.println("280    25    AdobePDF");
		ps6.println("300    120   Chrome");
		ps6.println("300    50    Flashplayer");
		ps6.println("310    250   Video");
		ps6.println("1200   50   Facebook");
		ps6.println("1210   350  Render Video");
		ps6.println("1310   10   New tab");
		ps6.println("1400   20   User search");
		ps6.println("1450   450  Video chat");
		ps6.println("2725      50   Segmentation fault");
		ps6.println("2775      50   Recovery");
		ps6.println("2900      50   JavaRuntime");
		ps6.println("2950      250   Minecraft");
		ps6.println("3100      500   Battlefield V");
		ps6.println("3100      500   GPU Scheduler");
		ps6.println("4000      50   Stock manager");
		ps6.println("4100      50   Alarm");
		ps6.println("4150      50   Shutdown");
		ps6.close();
		//Similar set of process names to ps6 however with a large timeout that is not divisible by 10 (2043)
		PrintStream ps7 = new PrintStream(new File(INPUT_NAME7));
		ps7.println("0      1000    Startup");
		ps7.println("5      100   Update Registry");
		ps7.println("100    5000   Virus Scan");
		ps7.println("900    500   Spotify");
		ps7.println("1000   600    AdobePDF");
		ps7.println("1500   120   Chrome");
		ps7.println("1623   1050    Flashplayer");
		ps7.println("1950   1250   Video");
		ps7.println("2000   50   Facebook");
		ps7.println("2500   3050  Render Video");
		ps7.println("2760   100   New tab");
		ps7.println("3000   200   User search");
		ps7.println("3005   2500  Video chat");
		ps7.println("4200   50   Segmentation fault");
		ps7.println("4500   600   Recovery");
		ps7.println("6000   50   JavaRuntime");
		ps7.println("6050   2500  Minecraft");
		ps7.println("6050   200  GPU Scheduler");
		ps7.println("6800   50   Stock manager");
		ps7.println("7000   50   Alarm");
		ps7.println("20000  50   Shutdown");
		ps7.close();
		
		// TODO: Create five more validly formatted input files for testing.
		//       Three can be small, simple cases. The other two must be 
		//       sufficiently larger than my second example above.
		//       Your collection of tests must cover a wide range of 
		//       interesting cases.
	}

	/**
	 * After testing, all input files and output files must be deleted.
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		new File(INPUT_NAME1).delete();
		new File(OUTPUT_NAME1).delete();

		new File(INPUT_NAME2).delete();
		new File(OUTPUT_NAME2).delete();

		new File(INPUT_NAME3).delete();
		new File(OUTPUT_NAME3).delete();

		new File(INPUT_NAME4).delete();
		new File(OUTPUT_NAME4).delete();

		new File(INPUT_NAME5).delete();
		new File(OUTPUT_NAME5).delete();

		new File(INPUT_NAME6).delete();
		new File(OUTPUT_NAME6).delete();

		new File(INPUT_NAME7).delete();
		new File(OUTPUT_NAME7).delete();

	

		// TODO: Delete files created for your other tests.
	}

	/**
	 * Test the simulateProcessor method of the Scheduler class
	 * with each of the test files
	 * 
	 * @throws FileNotFoundException If input or output files
	 *                               are not found/created.
	 */
	@Test
	public void testSimulateProcessor() throws FileNotFoundException {
		// Simulate scheduler and processor
		Scheduler.simulateProcessor(INPUT_NAME1, OUTPUT_NAME1, TIMEOUT1);
		Scheduler.simulateProcessor(INPUT_NAME2, OUTPUT_NAME2, TIMEOUT2);
		Scheduler.simulateProcessor(INPUT_NAME3, OUTPUT_NAME3, TIMEOUT3);
		Scheduler.simulateProcessor(INPUT_NAME4, OUTPUT_NAME4, TIMEOUT4);
		Scheduler.simulateProcessor(INPUT_NAME5, OUTPUT_NAME5, TIMEOUT5);
		Scheduler.simulateProcessor(INPUT_NAME6, OUTPUT_NAME6, TIMEOUT6);
		Scheduler.simulateProcessor(INPUT_NAME7, OUTPUT_NAME7, TIMEOUT7);
		Scheduler.simulateProcessor(INPUT_NAME1, OUTPUT_NAME8, TIMEOUT5);
		Scheduler.simulateProcessor(INPUT_NAME6, OUTPUT_NAME9, TIMEOUT7);


		// Reads the output file and verifies correct contents of each line
		Scanner scan1 = new Scanner(new File(OUTPUT_NAME1));
		assertEquals(scan1.nextLine(), "At time 0: run \"First\" for 50 time units");
		assertEquals(scan1.nextLine(), "At time 50: run \"Second\" for 50 time units");
		assertEquals(scan1.nextLine(), "At time 100: run \"Third\" for 50 time units");
		assertEquals(scan1.nextLine(), "At time 150: run \"Second\" for 50 time units");
		scan1.close(); // close file after reading
		
		//Testing the contents of input 1 with a different timeout (43). Verify that different timeouts provide different results
		scan1 = new Scanner(new File(OUTPUT_NAME8));
		assertEquals(scan1.nextLine(), "At time 0: run \"First\" for 43 time units");
		assertEquals(scan1.nextLine(), "At time 43: run \"Second\" for 43 time units");
		assertEquals(scan1.nextLine(), "At time 86: run \"Third\" for 43 time units");
		assertEquals(scan1.nextLine(), "At time 129: run \"First\" for 7 time units");
		assertEquals(scan1.nextLine(), "At time 136: run \"Second\" for 43 time units");
		assertEquals(scan1.nextLine(), "At time 179: run \"Third\" for 7 time units");
		assertEquals(scan1.nextLine(), "At time 186: run \"Second\" for 14 time units");
		scan1.close();

		Scanner scan2 = new Scanner(new File(OUTPUT_NAME2));
		assertEquals(scan2.nextLine(), "At time 0: run \"Initialization\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 100: run \"Background service\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 200: run \"Compiler\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 300: run \"Background service\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 400: run \"Compiler\" for 50 time units");
		assertEquals(scan2.nextLine(), "CPU idle from time 450 until time 600");
		assertEquals(scan2.nextLine(), "At time 600: run \"Virus scan\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 700: run \"Quick process\" for 10 time units");
		assertEquals(scan2.nextLine(), "At time 710: run \"Browser\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 810: run \"Virus scan\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 910: run \"Browser\" for 50 time units");
		assertEquals(scan2.nextLine(), "At time 960: run \"Virus scan\" for 100 time units");
		assertEquals(scan2.nextLine(), "CPU idle from time 1060 until time 1630");
		assertEquals(scan2.nextLine(), "At time 1630: run \"Movie clip\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 1730: run \"Movie clip\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 1830: run \"Movie clip\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 1930: run \"Email\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 2030: run \"Movie clip\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 2130: run \"Email\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 2230: run \"Data processing\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 2330: run \"Email\" for 100 time units");
		assertEquals(scan2.nextLine(), "At time 2430: run \"Email\" for 50 time units");
		scan2.close(); // close file after reading
		
		Scanner scan3 = new Scanner(new File(OUTPUT_NAME3));
		assertEquals(scan3.nextLine(), "At time 0: run \"Email\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 1000: run \"Compiler\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 2000: run \"Movie\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 3000: run \"Email\" for 100 time units");
		assertEquals(scan3.nextLine(), "At time 3100: run \"Formatting\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 4100: run \"Compiler\" for 700 time units");
		assertEquals(scan3.nextLine(), "At time 4800: run \"Movie\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 5800: run \"Formatting\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 6800: run \"Movie\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 7800: run \"Formatting\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 8800: run \"Movie\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 9800: run \"Formatting\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 10800: run \"Movie\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 11800: run \"Formatting\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 12800: run \"Formatting\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 13800: run \"Formatting\" for 1000 time units");
		assertEquals(scan3.nextLine(), "At time 14800: run \"Formatting\" for 804 time units");

		scan3.close();
		
		Scanner scan4 = new Scanner(new File(OUTPUT_NAME4));
		assertEquals(scan4.nextLine(), "At time 0: run \"Startup\" for 10 time units");
		assertEquals(scan4.nextLine(), "At time 10: run \"Startup\" for 10 time units");
		assertEquals(scan4.nextLine(), "CPU idle from time 20 until time 100");
		assertEquals(scan4.nextLine(), "At time 100: run \"Chrome\" for 10 time units");
		assertEquals(scan4.nextLine(), "At time 110: run \"Youtube\" for 10 time units");
		assertEquals(scan4.nextLine(), "At time 120: run \"Play\" for 10 time units");
		assertEquals(scan4.nextLine(), "At time 130: run \"Chrome\" for 10 time units");
		assertEquals(scan4.nextLine(), "At time 140: run \"Youtube\" for 10 time units");
		assertEquals(scan4.nextLine(), "At time 150: run \"Chrome\" for 10 time units");
		assertEquals(scan4.nextLine(), "At time 160: run \"Youtube\" for 10 time units");
		assertEquals(scan4.nextLine(), "CPU idle from time 170 until time 180");
		assertEquals(scan4.nextLine(), "At time 180: run \"JavaRuntime\" for 10 time units");
		assertEquals(scan4.nextLine(), "At time 190: run \"JavaRuntime\" for 10 time units");
		assertEquals(scan4.nextLine(), "At time 200: run \"Minecraft\" for 10 time units");
		assertEquals(scan4.nextLine(), "At time 210: run \"JavaRuntime\" for 10 time units");
		assertEquals(scan4.nextLine(), "At time 220: run \"Minecraft\" for 10 time units");
		assertEquals(scan4.nextLine(), "At time 230: run \"Minecraft\" for 10 time units");
		scan4.close(); // close file after reading

		Scanner scan5 = new Scanner(new File(OUTPUT_NAME5));
		assertEquals(scan5.nextLine(), "At time 0: run \"Startup\" for 43 time units");
		assertEquals(scan5.nextLine(), "At time 43: run \"Virus Check\" for 43 time units");
		assertEquals(scan5.nextLine(), "At time 86: run \"Startup\" for 7 time units");
		assertEquals(scan5.nextLine(), "At time 93: run \"Initialize\" for 43 time units");
		assertEquals(scan5.nextLine(), "At time 136: run \"Virus Check\" for 43 time units");
		assertEquals(scan5.nextLine(), "At time 179: run \"Initialize\" for 43 time units");
		assertEquals(scan5.nextLine(), "At time 222: run \"Virus Check\" for 43 time units");
		assertEquals(scan5.nextLine(), "At time 265: run \"Initialize\" for 4 time units");
		assertEquals(scan5.nextLine(), "At time 269: run \"Virus Check\" for 43 time units");
		assertEquals(scan5.nextLine(), "At time 312: run \"Virus Check\" for 43 time units");
		assertEquals(scan5.nextLine(), "At time 355: run \"Virus Check\" for 20 time units");
		assertEquals(scan5.nextLine(), "CPU idle from time 375 until time 400");
		assertEquals(scan5.nextLine(), "At time 400: run \"Cleanup\" for 43 time units");
		assertEquals(scan5.nextLine(), "At time 443: run \"Cleanup\" for 43 time units");
		assertEquals(scan5.nextLine(), "At time 486: run \"Cleanup\" for 14 time units");
		scan5.close(); // close file after reading

		Scanner scan6 = new Scanner(new File(OUTPUT_NAME6));
		assertEquals(scan6.nextLine(), "At time 0: run \"Startup\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 50: run \"Update Registry\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 150: run \"Virus Scan\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 250: run \"Index files\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 350: run \"Spotify\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 450: run \"Virus Scan\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 550: run \"AdobePDF\" for 25 time units");
		assertEquals(scan6.nextLine(), "At time 575: run \"Chrome\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 675: run \"Flashplayer\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 725: run \"Video\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 825: run \"Index files\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 875: run \"Spotify\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 975: run \"Virus Scan\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 1075: run \"Chrome\" for 20 time units");
		assertEquals(scan6.nextLine(), "At time 1095: run \"Video\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 1195: run \"Spotify\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 1295: run \"Virus Scan\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 1395: run \"Video\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 1445: run \"Facebook\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 1495: run \"Render Video\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 1595: run \"Spotify\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 1695: run \"New tab\" for 10 time units");
		assertEquals(scan6.nextLine(), "At time 1705: run \"Virus Scan\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 1805: run \"User search\" for 20 time units");
		assertEquals(scan6.nextLine(), "At time 1825: run \"Video chat\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 1925: run \"Render Video\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 2025: run \"Spotify\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 2125: run \"Video chat\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 2225: run \"Render Video\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 2325: run \"Video chat\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 2425: run \"Render Video\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 2475: run \"Video chat\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 2575: run \"Video chat\" for 50 time units");
		assertEquals(scan6.nextLine(), "CPU idle from time 2625 until time 2725");
		assertEquals(scan6.nextLine(), "At time 2725: run \"Segmentation fault\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 2775: run \"Recovery\" for 50 time units");
		assertEquals(scan6.nextLine(), "CPU idle from time 2825 until time 2900");
		assertEquals(scan6.nextLine(), "At time 2900: run \"JavaRuntime\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 2950: run \"Minecraft\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 3050: run \"Minecraft\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 3150: run \"Battlefield V\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 3250: run \"GPU Scheduler\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 3350: run \"Minecraft\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 3400: run \"Battlefield V\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 3500: run \"GPU Scheduler\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 3600: run \"Battlefield V\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 3700: run \"GPU Scheduler\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 3800: run \"Battlefield V\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 3900: run \"GPU Scheduler\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 4000: run \"Battlefield V\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 4100: run \"Stock manager\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 4150: run \"GPU Scheduler\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 4250: run \"Alarm\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 4300: run \"Shutdown\" for 50 time units");
		scan6.close(); 
		//Testing the results of input six with a very large odd number timeout (2043)
		scan6 = new Scanner(new File(OUTPUT_NAME9));
		assertEquals(scan6.nextLine(), "At time 0: run \"Startup\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 50: run \"Update Registry\" for 100 time units");
		assertEquals(scan6.nextLine(), "At time 150: run \"Virus Scan\" for 500 time units");
		assertEquals(scan6.nextLine(), "At time 650: run \"Index files\" for 150 time units");
		assertEquals(scan6.nextLine(), "At time 800: run \"Spotify\" for 500 time units");
		assertEquals(scan6.nextLine(), "At time 1300: run \"AdobePDF\" for 25 time units");
		assertEquals(scan6.nextLine(), "At time 1325: run \"Chrome\" for 120 time units");
		assertEquals(scan6.nextLine(), "At time 1445: run \"Flashplayer\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 1495: run \"Video\" for 250 time units");
		assertEquals(scan6.nextLine(), "At time 1745: run \"Facebook\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 1795: run \"Render Video\" for 350 time units");
		assertEquals(scan6.nextLine(), "At time 2145: run \"New tab\" for 10 time units");
		assertEquals(scan6.nextLine(), "At time 2155: run \"User search\" for 20 time units");
		assertEquals(scan6.nextLine(), "At time 2175: run \"Video chat\" for 450 time units");
		assertEquals(scan6.nextLine(), "CPU idle from time 2625 until time 2725");
		assertEquals(scan6.nextLine(), "At time 2725: run \"Segmentation fault\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 2775: run \"Recovery\" for 50 time units");
		assertEquals(scan6.nextLine(), "CPU idle from time 2825 until time 2900");
		assertEquals(scan6.nextLine(), "At time 2900: run \"JavaRuntime\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 2950: run \"Minecraft\" for 250 time units");
		assertEquals(scan6.nextLine(), "At time 3200: run \"Battlefield V\" for 500 time units");
		assertEquals(scan6.nextLine(), "At time 3700: run \"GPU Scheduler\" for 500 time units");
		assertEquals(scan6.nextLine(), "At time 4200: run \"Stock manager\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 4250: run \"Alarm\" for 50 time units");
		assertEquals(scan6.nextLine(), "At time 4300: run \"Shutdown\" for 50 time units");
		scan6.close();

		
		Scanner scan7 = new Scanner(new File(OUTPUT_NAME7));
		assertEquals(scan7.nextLine(), "At time 0: run \"Startup\" for 1000 time units");
		assertEquals(scan7.nextLine(), "At time 1000: run \"Update Registry\" for 100 time units");
		assertEquals(scan7.nextLine(), "At time 1100: run \"Virus Scan\" for 2043 time units");
		assertEquals(scan7.nextLine(), "At time 3143: run \"Spotify\" for 500 time units");
		assertEquals(scan7.nextLine(), "At time 3643: run \"AdobePDF\" for 600 time units");
		assertEquals(scan7.nextLine(), "At time 4243: run \"Chrome\" for 120 time units");
		assertEquals(scan7.nextLine(), "At time 4363: run \"Flashplayer\" for 1050 time units");
		assertEquals(scan7.nextLine(), "At time 5413: run \"Video\" for 1250 time units");
		assertEquals(scan7.nextLine(), "At time 6663: run \"Facebook\" for 50 time units");
		assertEquals(scan7.nextLine(), "At time 6713: run \"Render Video\" for 2043 time units");
		assertEquals(scan7.nextLine(), "At time 8756: run \"New tab\" for 100 time units");
		assertEquals(scan7.nextLine(), "At time 8856: run \"User search\" for 200 time units");
		assertEquals(scan7.nextLine(), "At time 9056: run \"Video chat\" for 2043 time units");
		assertEquals(scan7.nextLine(), "At time 11099: run \"Virus Scan\" for 2043 time units");
		assertEquals(scan7.nextLine(), "At time 13142: run \"Segmentation fault\" for 50 time units");
		assertEquals(scan7.nextLine(), "At time 13192: run \"Recovery\" for 600 time units");
		assertEquals(scan7.nextLine(), "At time 13792: run \"JavaRuntime\" for 50 time units");
		assertEquals(scan7.nextLine(), "At time 13842: run \"Minecraft\" for 2043 time units");
		assertEquals(scan7.nextLine(), "At time 15885: run \"GPU Scheduler\" for 200 time units");
		assertEquals(scan7.nextLine(), "At time 16085: run \"Stock manager\" for 50 time units");
		assertEquals(scan7.nextLine(), "At time 16135: run \"Alarm\" for 50 time units");
		assertEquals(scan7.nextLine(), "At time 16185: run \"Render Video\" for 1007 time units");
		assertEquals(scan7.nextLine(), "At time 17192: run \"Video chat\" for 457 time units");
		assertEquals(scan7.nextLine(), "At time 17649: run \"Virus Scan\" for 914 time units");
		assertEquals(scan7.nextLine(), "At time 18563: run \"Minecraft\" for 457 time units");
		assertEquals(scan7.nextLine(), "CPU idle from time 19020 until time 20000");
		assertEquals(scan7.nextLine(), "At time 20000: run \"Shutdown\" for 50 time units");
		scan7.close(); 
	}

}
