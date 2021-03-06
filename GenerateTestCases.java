import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestCasesForBookingClass {

	static int numOfParameters = 5;
	static int[] type = {2,2,1,1,2}; //src,dest,date,hour,cartype
	static String[] stringParameters = {"Delhi,Pilani,Jaipur,Noida,Ghaziabad,Sikar,Chandigarh","Delhi,Pilani,Jaipur,Noida,Ghaziabad,Sikar,Chandigarh","","","micro,sedan,suv"};
	static int[] startOfIntParameters = {0,0,2,0,0};
	static int[] endOfIntParameters = {0,0,31,24,0};

	public static void writeDatabaseAndFile(String writeTestCaseToDB)
	{
		String[] sp = writeTestCaseToDB.split(",");

		//DB
		//        Booking testCase = new Booking(sp[0],sp[1], new Timestamp(new Date(2019-1900,2,
		//                                    Integer.parseInt(sp[2]),Integer.parseInt(sp[3]),0,0)),"pending",sp[4]);
		//
		//        dB.getFirestoreInstance().collection("bookings").document(testCase.getId()).set(testCase);

		//File
		if(sp[0].equals("Pilani") || sp[1].equals("Pilani"))	//checking if either src of dest is Pilani
		{
			if(sp[0].equals(sp[1]) == false)	//checking if src and dest aren't same
			{		
				String timestamp = sp[2] + "-3-2019 " + sp[3] + ":00:00";
				writeTestCaseToDB = sp[0] + "," + sp[1] + "," + timestamp + "," + "pending" + "," + sp[4];
				System.out.println(writeTestCaseToDB);
		
				try
				{
					BufferedWriter append = new BufferedWriter(new FileWriter("Test Cases.csv", true));
					append.write(writeTestCaseToDB);
					append.newLine();
					append.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void testCasesForIntegerParameter(int value, int i, int k, int l, int parametersParsed, String writeTestCaseToDB)
	{
		if(parametersParsed == numOfParameters)
		{
			//write test case to db as well as in a file
			writeDatabaseAndFile(writeTestCaseToDB);
			return;
		}

		for(int j = parametersParsed; j < numOfParameters; j++)
		{
			if(j == i)
			{
				writeTestCaseToDB += Integer.toString( value ) + ",";
				parametersParsed++;
				if(parametersParsed == numOfParameters)
				{
					//write test case to db as well as in a file
					writeDatabaseAndFile(writeTestCaseToDB);
					return;
				}
				continue;
			}

			if(type[j] == 1)	//int parameter
			{
				if( l <= endOfIntParameters[j]/2)
				{
					l++;
					testCasesForIntegerParameter(value, i, k, l, parametersParsed, writeTestCaseToDB);
					writeTestCaseToDB += Integer.toString(l-1) + ",";
					parametersParsed++;
					if(parametersParsed == numOfParameters)
					{
						//write test case to db as well as in a file
						writeDatabaseAndFile(writeTestCaseToDB);
						return;
					}
				}
			}
			else	//string parameter
			{
				String[] splitString = stringParameters[j].split(",");

				if( k < splitString.length)
				{
					k++;
					testCasesForIntegerParameter(value,i,k,l,parametersParsed,writeTestCaseToDB);
					writeTestCaseToDB += splitString[k-1] + ",";
					parametersParsed++;
					if(parametersParsed == numOfParameters)
					{
						//write test case to db as well as in a file
						writeDatabaseAndFile(writeTestCaseToDB);
						return;
					}
				}

			}
		}



	}

	public static void testCasesForStringParameter(String value, int i, int k, int parametersParsed, String writeTestCaseToDB)
	{
		if(parametersParsed == numOfParameters)
		{
			writeDatabaseAndFile(writeTestCaseToDB);
			return;
		}

		for(int j = parametersParsed; j < numOfParameters; j++)
		{
			if(j == i)
			{
				writeTestCaseToDB += value + ",";
				parametersParsed++;
				if(parametersParsed == numOfParameters)
				{
					//write test case to db as well as in a file
					writeDatabaseAndFile(writeTestCaseToDB);
					return;
				}
				continue;
			}

			if(type[j] == 1)	//int parameter
			{
				writeTestCaseToDB += Integer.toString( endOfIntParameters[j]/2 ) + ",";	//nominal value
				parametersParsed++;
				if(parametersParsed == numOfParameters)
				{
					//write test case to db as well as in a file
					writeDatabaseAndFile(writeTestCaseToDB);
					return;
				}

			}
			else	//string parameter
			{
				String[] splitString = stringParameters[j].split(",");

				//can go out of bound for splitString length
				if( k < splitString.length)
				{
					k++;
					testCasesForStringParameter(value,i,k,parametersParsed,writeTestCaseToDB);
					writeTestCaseToDB += splitString[k-1] + ",";
					parametersParsed++;
					if(parametersParsed == numOfParameters)
					{
						//write test case to db as well as in a file
						writeDatabaseAndFile(writeTestCaseToDB);
						return;
					}
				}
			}
		}
	}

	public static void main(String[] args) {

		try
		{
			BufferedWriter testCases = new BufferedWriter(new FileWriter("Test Cases.csv"));
			testCases.write("source,destination,timestamp,status,carType");
			testCases.newLine();
			testCases.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		for(int i = 0; i < numOfParameters; i++) 
		{
			//int parameter
			if (type[i] == 1) 
			{
				for(int j = startOfIntParameters[i]; j <= endOfIntParameters[i]; j++)
				{
					testCasesForIntegerParameter(j,i,0,startOfIntParameters[i],0,"");
				}

			}
			else	//string parameter
			{
				String[] splitString = stringParameters[i].split(",");
				for(int j = 0; j < splitString.length; j++)
				{
					testCasesForStringParameter(splitString[j], i,0,0,"");
				}
			}
		}


	}

}
