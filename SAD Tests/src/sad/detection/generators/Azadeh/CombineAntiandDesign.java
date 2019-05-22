package sad.detection.generators.Azadeh;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import au.com.bytecode.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class CombineAntiandDesign{
	public static int lineNumberAnti;
	public static int lineNumberDesg;
	public static int linecount1;
	public static int linecount2;
	public static String [][] Ver1 = new String [14][10000];
	public static String [][] SVer1 = new String [14][10000];
	public static String [][] Ver2 = new String [24][10000];
	public static String [][] SVer2 = new String [24][10000];
	public static String [][] CombineM = new String [38][10000];
	public static String [][] SCombineM = new String [38][10000];
	public static int[][] CombineValues= new int [34][10000];
	
	
	public static void main(String[] args) {
		try{
		String strFileAnti = "C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/Anti-Pattern Detection/ArgoUML/CSV/ArgoUMLv0.10.1.csv";
	    String strFileDesg = "C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/Design-Pattern Detection/CSV/AzadehModelArgoUMLv0.10.1.csv";
	    	
	    CSVReader readerAnti = new CSVReader (new FileReader(strFileAnti));
	    CSVReader readerDesg= new CSVReader (new FileReader(strFileDesg));
	    	
	    String[] nextLineAnti;
	    String[] nextLineDesg;
	    	
	    lineNumberAnti = 0;
	    lineNumberDesg = 0;
	    	
	    linecount1 = 0;
	    linecount2 = 0;

	    	//Writing First File contains in VER1 matrix
	    while((nextLineAnti = readerAnti.readNext()) != null){    		
	    		//System.out.println("Linename: " + nextLineAnti);
	    		for (int col1=0; col1<14; col1++)
	    		{
	    			Ver1[col1][lineNumberAnti] = nextLineAnti[col1];
	    			//System.out.println("VER1 Contains:( " + col1 + " and " + lineNumberAnti + ")" + Ver1[col1][lineNumberAnti]);
	    		}
	    		lineNumberAnti ++;
	    		//System.out.println(lineNumberVer1);
	   	    }
	    	linecount1 = lineNumberAnti;
	    	//System.out.println("line =" + linecount1);
	    		    	
	    	for (int row1=0; row1 < linecount1; row1 ++) 
	    		for (int col1=0; col1<14; col1++)
	    			SVer1[col1][row1] = Ver1[col1][row1];
	    	
	    	//Writing Second File contains in VER2 matrix
	    	while((nextLineDesg = readerDesg.readNext()) != null){	    		
	    		for (int col2=0; col2<23; col2++)
	    		{
	    			//System.out.println(nextLine[i]);
	    			Ver2[col2][lineNumberDesg] = nextLineDesg[col2];
	    			//System.out.println("VER2 Contains:( " + col2 + " and " + lineNumberDesg + ")" + Ver2[col2][lineNumberDesg]);
	    		}
	    		lineNumberDesg ++;
	   	    }	
	    	linecount2 = lineNumberDesg;
	    	//System.out.println("line =" + linecount2);
	    	
	    	for (int row2=0; row2 < linecount2; row2 ++) 
	    			for (int col2=0; col2<23; col2++)
	    				//System.out.println("Second Input =" + Ver2[col2][row2]);
	    				SVer2[col2][row2] = Ver2[col2][row2];

		
		for (int CombineRow=0; CombineRow<(lineNumberAnti+lineNumberDesg); CombineRow++)
			for (int CombineCol=0; CombineCol < 34; CombineCol++)
			{
				CombineValues[CombineCol][CombineRow]= 0;
			}
		
		for (int Ccol=0; Ccol<13; Ccol++)
		{
			CombineM[Ccol][0]= SVer1[Ccol][0];
			//System.out.println(CombineM[Ccol][0]);
		}
		for (int Ccol=13; Ccol<35; Ccol++)
		{
			CombineM[Ccol][0]= SVer2[Ccol-12][0];
			//System.out.println(CombineM[Ccol][0]);
		}
		
		for (int Crow=1; Crow<lineNumberAnti; Crow++)
			
		{
			CombineM[0][Crow]= SVer1[0][Crow];
		}
		

		for (int CombineRow=1; CombineRow<(lineNumberAnti); CombineRow++)
			for (int CombineCol=1; CombineCol < 13; CombineCol++)
			{
				if (SVer1[CombineCol][CombineRow].equals("1"))
				{
					CombineValues[CombineCol][CombineRow]= 1;
				}
				else if (SVer1[CombineCol][CombineRow].equals("0"))
				{
					CombineValues[CombineCol][CombineRow]= 0;
				}
					
			}
		for (int CombineRow=lineNumberAnti; CombineRow<(lineNumberAnti+lineNumberDesg-1); CombineRow++)
			for (int CombineCol=13; CombineCol < 34; CombineCol++)
			{
				/*if (SVer2[0][CombineRow-lineNumberAnti+1] == SVer1[0][CombineRow-lineNumberAnti+1])
				{
					System.out.println("OK");
					System.out.println(SVer2[0][CombineRow-lineNumberAnti+1]);
					System.out.println(SVer1[0][CombineRow-lineNumberAnti+1]);*/
					if ((Integer.parseInt(SVer2[CombineCol-12][CombineRow-lineNumberAnti+1]) != 0))
					{
						CombineValues[CombineCol][CombineRow]= Integer.parseInt(SVer2[CombineCol-12][CombineRow-lineNumberAnti+1]);
						//System.out.println(Integer.parseInt(SVer2[CombineCol-12][CombineRow-lineNumberAnti+1]));
					}
					else if (SVer2[CombineCol-12][CombineRow-lineNumberAnti+1].equals("0"))
					{
						CombineValues[CombineCol][CombineRow]= 0;
					}
				}	
			//}
	
		/*for (int CombineRow=lineNumberAnti; CombineRow<(lineNumberAnti+lineNumberDesg); CombineRow++)
			for (int CombineCol=13; CombineCol < 34; CombineCol++)
			{
				System.out.println(Integer.parseInt(SVer2[CombineCol-12][CombineRow-lineNumberAnti+1]));
			}*/
				
		for (int CombineRow=1; CombineRow<(lineNumberAnti+lineNumberDesg-1); CombineRow++)
			for (int CombineCol=1; CombineCol < 34; CombineCol++)
			{
				System.out.println("Col: " + CombineCol + ", Row: " + CombineRow+ ", Value: " + CombineValues[CombineCol][CombineRow]);
			}
			
		}
		catch (IOException ioe)		
		{ 
		System.out.println("IO error:" +ioe ); 
		} 
		
		final String output = "C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/Design-Pattern Detection/CSV/Combine/";
		final File outputDir = new File(output);	
		
		// Writing as a csv File
		try{
			
			FileWriter outFileStream = new FileWriter("C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/Design-Pattern Detection/CSV/Combine/TestArgoUML.csv");
			final String Fheader = "ClassName,AntiSingleton,Blob,ClassDataShouldBePrivate,ComplexClass,LargeClass,LazyClass,LongMethod,LongParameterList,MessageChains,RefusedParentBequest,SpeculativeGenerality,SwissArmyKnife,Command.invoker,Command.command,Composite.leaf,Decorator.decorator,FactoryMethod.creator,Command.client,FactoryMethod.concreteProduct,Command.receiver,Composite.composite,FactoryMethod.concreteCreator,Decorator.concretecomponent,Composite.component,Observer1.concreteObserver,Observer1.observer,Command.concretecommand,FactoryMethod.product,Decorator.concretedecorator,Observer1.subject,Decorator.component,Prototype.prototype,Observer1.concreteSubject";
			outFileStream.append(Fheader.toString());
			

		}catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
		}				
	}
}
