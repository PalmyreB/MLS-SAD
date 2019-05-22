package sad.detection.generators.Azadeh;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import au.com.bytecode.opencsv.CSVReader;

/**
 * @author Zeinab(Azadeh) Kermansaravi
 * @since  2015/05/10
**/

public final class MarkovModelAzadehMylyn {
	private String Newligne = System.getProperty("line.separator");
	private	Writer output = null;
	public static String [][] Ver1 = new String [35][10000];
	public static String [][] SVer1 = new String [35][10000];
	public static String [][] Ver2 = new String [35][10000];
	public static String [][] SVer2 = new String [35][10000];
	public static String [][] Ver3 = new String [35][10000];
	public static String [][] SVer3 = new String [35][10000];
	public static String [][] Ver4 = new String [35][10000];
	public static String [][] SVer4 = new String [35][10000];
	public static String [][] Ver5 = new String [35][10000];
	public static String [][] SVer5 = new String [35][10000];
	public static String [][] Ver6 = new String [35][10000];
	public static String [][] SVer6 = new String [35][10000];
	public static String [][] Ver7 = new String [35][10000];
	public static String [][] SVer7 = new String [35][10000];
	public static String [][] Ver8 = new String [35][10000];
	public static String [][] SVer8 = new String [35][10000];
	public static String [][] Ver9 = new String [35][10000];
	public static String [][] SVer9 = new String [35][10000];
	
	public static int lineNumberVer1;
	public static int lineNumberVer2;
	public static int linecount1;
	public static int linecount2;
	public static int lineNumberVer3;
	public static int lineNumberVer4;
	public static int linecount3;
	public static int linecount4;
	public static int lineNumberVer5;
	public static int lineNumberVer6;
	public static int linecount5;
	public static int linecount6;
	public static int lineNumberVer7;
	public static int lineNumberVer8;
	public static int linecount7;
	public static int linecount8;
	public static int lineNumberVer9;
	public static int linecount9;		
	
	private int[][] version1= new int [34][34];
	private int[][] version2= new int [34][34];
	private int[][] version3= new int [34][34];
	private int[][] version4= new int [34][34];
	private int[][] version5= new int [34][34];
	private int[][] version6= new int [34][34];
	private int[][] version7= new int [34][34];
	private int[][] version8= new int [34][34];
	private int[][] versionall = new int [34][34];
	
	private int[] Total= new int [34];

	private int[] Total1= new int [34];
	private int[] Total2= new int [34];
	private int[] Total3= new int [34];
	private int[] Total4= new int [34];
	private int[] Total5= new int [34];
	private int[] Total6= new int [34];
	private int[] Total7= new int [34];
	private int[] Total8= new int [34];
	private int[] Totalall= new int [34];
	
	
	private String[][] Pr1= new String [34][34];
	private String[][] Pr2= new String [34][34];
	private String[][] Pr3= new String [34][34];
	private String[][] Pr4= new String [34][34];
	private String[][] Pr5= new String [34][34];
	private String[][] Pr6= new String [34][34];
	private String[][] Pr7= new String [34][34];
	private String[][] Pr8= new String [34][34];
	private double[][] PrAll= new double [34][34];
 
  public static void main(String[] args) {
 
 		MarkovModelAzadehMylyn AModel = new MarkovModelAzadehMylyn();

	  //  String[] tab1 = new String[12]; 
	  //  String[] tab2 = new String[12];

	    try{
	    		String Infile = "C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/CombineAnti-Design/Mylyn/MylynEvolutinResults.txt";
				File file = new File(Infile);
				AModel.output = new BufferedWriter(new FileWriter(file));			
				
//		        InputStream ips = new FileInputStream("C:/Users/azks.GIGL/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/Anti-Pattern Detection/ArgoUML/CSV/initial.txt");
//			    InputStreamReader ipsr = new InputStreamReader(ips);
//			    BufferedReader br = new BufferedReader(ipsr);
			  
//			    System.out.println(br.readLine());			    	
	    	
	    		    	
	    	//Reading the First and Second Files
	    	String strFileVer1 = "C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/CombineAnti-Design/Mylyn/mylyn-2.0.0.csv";
	    	String strFileVer2 = "C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/CombineAnti-Design/Mylyn/mylyn-2.1.csv";
	    	String strFileVer3 = "C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/CombineAnti-Design/Mylyn/mylyn-2.2.0.csv";
	    	String strFileVer4 = "C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/CombineAnti-Design/Mylyn/mylyn-2.3.0.csv";
	    	String strFileVer5 = "C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/CombineAnti-Design/Mylyn/mylyn-2.3.1.csv";
	    	String strFileVer6 = "C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/CombineAnti-Design/Mylyn/mylyn-2.3.2.csv";
	    	String strFileVer7 = "C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/CombineAnti-Design/Mylyn/mylyn-3.0.0.csv";
	    	String strFileVer8 = "C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/CombineAnti-Design/Mylyn/mylyn-3.0.1.csv";
	    	String strFileVer9 = "C:/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/CombineAnti-Design/Mylyn/mylyn-3.0.2.csv";
	    	
	    	CSVReader readerVer1 = new CSVReader (new FileReader(strFileVer1));
	    	CSVReader readerVer2 = new CSVReader (new FileReader(strFileVer2));
	        CSVReader readerVer3 = new CSVReader (new FileReader(strFileVer3));
	    	CSVReader readerVer4 = new CSVReader (new FileReader(strFileVer4));
	    	CSVReader readerVer5 = new CSVReader (new FileReader(strFileVer5));
	    	CSVReader readerVer6 = new CSVReader (new FileReader(strFileVer6));
	    	CSVReader readerVer7 = new CSVReader (new FileReader(strFileVer7));
	    	CSVReader readerVer8 = new CSVReader (new FileReader(strFileVer8));
	    	CSVReader readerVer9 = new CSVReader (new FileReader(strFileVer9));
	    	
	    	String[] nextLineVer1;
	    	String[] nextLineVer2;
	    	String[] nextLineVer3;
	    	String[] nextLineVer4;
	    	String[] nextLineVer5;
	    	String[] nextLineVer6;
	    	String[] nextLineVer7;
	    	String[] nextLineVer8;
	    	String[] nextLineVer9;
	    	
	    	lineNumberVer1 = 0;
	    	lineNumberVer2 = 0;
	    	lineNumberVer3 = 0;
	    	lineNumberVer4 = 0;
	    	lineNumberVer5 = 0;
	    	lineNumberVer6 = 0;
	    	lineNumberVer7 = 0;
	    	lineNumberVer8 = 0;
	    	lineNumberVer9 = 0;
	    	
	    	linecount1 = 0;
	    	linecount2 = 0;
	    	linecount3 = 0;
	    	linecount4 = 0;
	    	linecount5 = 0;
	    	linecount6 = 0;
	    	linecount7 = 0;
	    	linecount8 = 0;
	    	linecount9 = 0;

	    	//Writing First File contains in VER1 matrix
	    	while((nextLineVer1 = readerVer1.readNext()) != null){    		
	    		//System.out.println("ok");	    		
	    		for (int col1=0; col1<34; col1++)
	    		{
	    			//System.out.println(nextLineVer1[col1]);
	    			Ver1[col1][lineNumberVer1] = nextLineVer1[col1];
	    			//System.out.println("nextLineVersion: " + nextLineVer1[col1]);
	    			//System.out.println("VER1 Contains:( " + col1 + " and " + lineNumberVer1 + ")" + Ver1[col1][lineNumberVer1]);
	    		}
	    		lineNumberVer1 ++;
	    		//System.out.println(lineNumberVer1);
	   	    }
	    	linecount1 = lineNumberVer1;
	    	//System.out.println("line =" + linecount1);
	    		    	
	    	for (int row1=0; row1 < linecount1; row1 ++) 
	    		for (int col1=0; col1<34; col1++){
	    			SVer1[col1][row1] = Ver1[col1][row1];
	    			System.out.println("Matrix1 is : " + SVer1[col1][row1]);
	    		}
	    	//Writing Second File contains in VER2 matrix
	    	while((nextLineVer2 = readerVer2.readNext()) != null){	    		
	    		for (int col2=0; col2<34; col2++)
	    		{
	    			//System.out.println(nextLine[i]);
	    			Ver2[col2][lineNumberVer2] = nextLineVer2[col2];
	    			//System.out.println("VER2 Contains: " + Ver2[col2][lineNumberVer2]);
	    		}
	    		lineNumberVer2 ++;
	   	    }	
	    	linecount2 = lineNumberVer2;
	    	//System.out.println("line =" + linecount2);
	    	
	    	for (int row2=0; row2 < linecount2; row2 ++) 
	    			for (int col2=0; col2<34; col2++)
	    				//System.out.println("Second Input =" + Ver2[col2][row2]);
	    				SVer2[col2][row2] = Ver2[col2][row2];
	    	
	    	
	    	//Writing Third File contains in VER3 matrix
	    	while((nextLineVer3 = readerVer3.readNext()) != null){	    		
	    		for (int col3=0; col3<34; col3++)
	    		{
	    			//System.out.println(nextLine[i]);
	    			Ver3[col3][lineNumberVer3] = nextLineVer3[col3];
	    		//	System.out.println("VER3 Contains: " + Ver3[j][lineNumberVer3]);
	    		}
	    		lineNumberVer3 ++;
	   	    }	
	    	linecount3 = lineNumberVer3;
	    	//System.out.println("line =" + linecount3);
	    	
	    	for (int row3=0; row3 < linecount3; row3 ++) 
	    			for (int col3=0; col3<34; col3++)
	    				//System.out.println("Second Input =" + Ver3[col3][row3]);
	    				SVer3[col3][row3] = Ver3[col3][row3];
	    	
	    	
	    	//Writing 4th File contains in VER4 matrix
	    	while((nextLineVer4 = readerVer4.readNext()) != null){	    		
	    		for (int col4=0; col4<34; col4++)
	    		{
	    			//System.out.println(nextLine[i]);
	    			Ver4[col4][lineNumberVer4] = nextLineVer4[col4];
	    		//	System.out.println("VER4 Contains: " + Ver4[j][lineNumberVer4]);
	    		}
	    		lineNumberVer4 ++;
	   	    }	
	    	linecount4 = lineNumberVer4;
	    	//System.out.println("line =" + linecount4);
	    	
	    	for (int row4=0; row4 < linecount4; row4 ++) 
	    			for (int col4=0; col4<34; col4++)
	    				//System.out.println("Second Input =" + Ver4[col4][row4]);
	    				SVer4[col4][row4] = Ver4[col4][row4];
 
	  
	    	//Writing 5th File contains in VER5 matrix
	    	while((nextLineVer5 = readerVer5.readNext()) != null){	    		
	    		for (int col5=0; col5<34; col5++)
	    		{
	    			//System.out.println(nextLine[i]);
	    			Ver5[col5][lineNumberVer5] = nextLineVer5[col5];
	    		//	System.out.println("VER5 Contains: " + Ver5[j][lineNumberVer5]);
	    		}
	    		lineNumberVer5 ++;
	   	    }	
	    	linecount5 = lineNumberVer5;
	    	//System.out.println("line =" + linecount5);
	    	
	    	for (int row5=0; row5 < linecount5; row5 ++) 
	    			for (int col5=0; col5<34; col5++)
	    				//System.out.println("Second Input =" + Ver5[col5][row5]);
	    				SVer5[col5][row5] = Ver5[col5][row5];
	    	
	    	
	    	//Writing 6th File contains in VER6 matrix
	    	while((nextLineVer6 = readerVer6.readNext()) != null){	    		
	    		for (int col6=0; col6<34; col6++)
	    		{
	    			//System.out.println(nextLine[i]);
	    			Ver6[col6][lineNumberVer6] = nextLineVer6[col6];
	    		//	System.out.println("VER6 Contains: " + Ver6[j][lineNumberVer6]);
	    		}
	    		lineNumberVer6 ++;
	   	    }	
	    	linecount6 = lineNumberVer6;
	    	//System.out.println("line =" + linecount6);
	    	
	    	for (int row6=0; row6 < linecount6; row6 ++) 
	    			for (int col6=0; col6<34; col6++)
	    				//System.out.println("Second Input =" + Ver6[col6][row6]);
	    				SVer6[col6][row6] = Ver6[col6][row6];
	    	

	    	//Writing 7th File contains in VER7 matrix
	    	while((nextLineVer7 = readerVer7.readNext()) != null){	    		
	    		for (int col7=0; col7<34; col7++)
	    		{
	    			//System.out.println(nextLine[i]);
	    			Ver7[col7][lineNumberVer7] = nextLineVer7[col7];
	    		//	System.out.println("VER7 Contains: " + Ver7[j][lineNumberVer7]);
	    		}
	    		lineNumberVer7 ++;
	   	    }	
	    	linecount7 = lineNumberVer7;
	    	//System.out.println("line =" + linecount7);
	    	
	    	for (int row7=0; row7 < linecount7; row7 ++) 
	    			for (int col7=0; col7<34; col7++)
	    				//System.out.println("Second Input =" + Ver7[col7][row7]);
	    				SVer7[col7][row7] = Ver7[col7][row7];
	    	
	    
	    	//Writing 8th File contains in VER8 matrix
	    	while((nextLineVer8 = readerVer8.readNext()) != null){	    		
	    		for (int col8=0; col8<34; col8++)
	    		{
	    			//System.out.println(nextLine[i]);
	    			Ver8[col8][lineNumberVer8] = nextLineVer8[col8];
	    		//	System.out.println("VER8 Contains: " + Ver8[j][lineNumberVer8]);
	    		}
	    		lineNumberVer8 ++;
	   	    }	
	    	linecount8 = lineNumberVer8;
	    	//System.out.println("line =" + linecount8);
	    	
	    	for (int row8=0; row8 < linecount8; row8 ++) 
	    			for (int col8=0; col8<34; col8++)
	    				//System.out.println("Second Input =" + Ver8[col8][row8]);
	    				SVer8[col8][row8] = Ver8[col8][row8];
	    	
	    	
	    	//Writing 9th File contains in VER9 matrix
	    	while((nextLineVer9 = readerVer9.readNext()) != null){	    		
	    		for (int col9=0; col9<34; col9++)
	    		{
	    			//System.out.println(nextLine[i]);
	    			Ver9[col9][lineNumberVer9] = nextLineVer9[col9];
	    		//	System.out.println("VER9 Contains: " + Ver9[j][lineNumberVer9]);
	    		}
	    		lineNumberVer9 ++;
	   	    }	
	    	linecount9 = lineNumberVer9;
	    	//System.out.println("line =" + linecount9);
	    	
	    	for (int row9=0; row9 < linecount9; row9 ++) 
	    			for (int col9=0; col9<34; col9++)
	    				//System.out.println("Second Input =" + Ver9[col9][row9]);
	    				SVer9[col9][row9] = Ver9[col9][row9];
	
		for (int pp=0; pp<34; pp++) {
			for (int pp1=0; pp1<34; pp1++) {
					AModel.version1 [pp][pp1]=0;
					AModel.version2 [pp][pp1]=0;
					AModel.version3 [pp][pp1]=0;
					AModel.version4 [pp][pp1]=0;
					AModel.version5 [pp][pp1]=0;
					AModel.version6 [pp][pp1]=0;
					AModel.version7 [pp][pp1]=0;
					AModel.version8 [pp][pp1]=0;
					AModel.versionall[pp][pp]=0;
				}
			} 
		
		for (int tp1=0; tp1<34; tp1++) {
			AModel.Total1 [tp1]=0;
			AModel.Total2 [tp1]=0;
			AModel.Total3 [tp1]=0;
			AModel.Total4 [tp1]=0;
			AModel.Total5 [tp1]=0;
			AModel.Total6 [tp1]=0;
			AModel.Total7 [tp1]=0;
			AModel.Total8 [tp1]=0;			
		}
	    	
	    	
	AModel.intitialisation ();
	AModel.EvolutionAnalysis(SVer1, SVer2, linecount1, linecount2);
	AModel.Affichage ();
	AModel.Total1 = AModel.Total;
	int total1 = 0;
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			total1 = total1 + AModel.version1[a][b];
			
		}
	for (int l=0; l<34; l++)
	{
		AModel.Total1[l] = total1;
	}
	System.out.println("Total1: " + total1);
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			String Pr1= "";
			Pr1= String.valueOf((double)AModel.version1[a][b]/total1);
			if (AModel.version1[a][b] < 30)
			{
				Pr1 = "0.000";
			}
			if (Pr1.length()>5){
				Pr1=Pr1.substring(0, 5);
			}				
			AModel.Pr1[a][b] = Pr1;
			//System.out.println("Version1: " + AModel.version1[a][b]);
			//System.out.println("Pr1: " + AModel.Pr1[a][b]);
		}
	
	AModel.intitialisation ();
	AModel.EvolutionAnalysis(SVer2, SVer3, linecount2, linecount3);
	//AModel.Affichage ();
	AModel.version2 = AModel.version1;
	AModel.Total2 = AModel.Total;
	//System.out.println(AModel.version2);
	int total2 = 0;
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			total2 = total2 + AModel.version2[a][b];
			//System.out.println(AModel.version1);
		}
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			String Pr2= "";
			Pr2= String.valueOf((double)AModel.version2[a][b]/total2);
			if (AModel.version2[a][b] < 30)
			{
				Pr2 = "0.000";
			}
			if (Pr2.length()>5){
				Pr2=Pr2.substring(0, 5);
			}				
			AModel.Pr2[a][b] = Pr2;
			//System.out.println("Version2: " + AModel.version2[a][b]);
			//System.out.println("Pr2: " + AModel.Pr2[a][b]);
		}
	AModel.intitialisation ();
	AModel.EvolutionAnalysis(SVer3, SVer4, linecount3, linecount4);
	//AModel.Affichage ();
	AModel.version3 = AModel.version1;
	AModel.Total3 = AModel.Total;
	int total3 = 0;
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			total3 = total3 + AModel.version3[a][b];
			//System.out.println(AModel.version1);
		}
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			String Pr3= "";
			Pr3= String.valueOf((double)AModel.version3[a][b]/total3);
			if (AModel.version3[a][b] < 30)
			{
				Pr3 = "0.000";
			}
			if (Pr3.length()>5){
				Pr3=Pr3.substring(0, 5);
			}				
			AModel.Pr3[a][b] = Pr3;
			//System.out.println("Version3: " + AModel.version3[a][b]);
			//System.out.println("Pr3: " + AModel.Pr3[a][b]);
		}	
	AModel.intitialisation ();
	AModel.EvolutionAnalysis(SVer4, SVer5, linecount4, linecount5);
	//AModel.Affichage ();
	AModel.version4 = AModel.version1;
	AModel.Total4 = AModel.Total;
	int total4 = 0;
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			total4 = total4 + AModel.version4[a][b];
			//System.out.println(AModel.version1);
		}
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			String Pr4= "";
			Pr4= String.valueOf((double)AModel.version4[a][b]/total4);
			if (AModel.version4[a][b] < 30)
			{
				Pr4 = "0.000";
			}
			if (Pr4.length()>5){
				Pr4=Pr4.substring(0, 5);
			}				
			AModel.Pr4[a][b] = Pr4;
			//System.out.println("Version4: " + AModel.version4[a][b]);
			//System.out.println("Pr4: " + AModel.Pr4[a][b]);
		}
	AModel.intitialisation ();
	AModel.EvolutionAnalysis(SVer5, SVer6, linecount5, linecount6);
	//AModel.Affichage ();
	AModel.version5 = AModel.version1;
	AModel.Total5 = AModel.Total;
	int total5 = 0;
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			total5 = total5 + AModel.version5[a][b];
			//System.out.println(AModel.version1);
		}
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			String Pr5= "";
			Pr5= String.valueOf((double)AModel.version5[a][b]/total5);
			if (AModel.version5[a][b] < 30)
			{
				Pr5 = "0.000";
			}
			if (Pr5.length()>5){
				Pr5=Pr5.substring(0, 5);
			}				
			AModel.Pr5[a][b] = Pr5;
			//System.out.println("Version5: " + AModel.version5[a][b]);
			//System.out.println("Pr5: " + AModel.Pr5[a][b]);
		}
	AModel.intitialisation ();
	AModel.EvolutionAnalysis(SVer6, SVer7, linecount6, linecount7);
	//AModel.Affichage ();
	AModel.version6 = AModel.version1;
	AModel.Total6 = AModel.Total;
	int total6 = 0;
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			total6 = total6 + AModel.version6[a][b];
			//System.out.println(AModel.version1);
		}
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			String Pr6= "";
			Pr6= String.valueOf((double)AModel.version6[a][b]/total6);
			if (AModel.version6[a][b] < 30)
			{
				Pr6 = "0.000";
			}
			if (Pr6.length()>5){
				Pr6=Pr6.substring(0, 5);
			}				
			AModel.Pr6[a][b] = Pr6;
			//System.out.println("Version6: " + AModel.version6[a][b]);
			//System.out.println("Pr6: " + AModel.Pr6[a][b]);
		}
	AModel.intitialisation ();
	AModel.EvolutionAnalysis(SVer7, SVer8, linecount7, linecount8);
	//AModel.Affichage ();
	AModel.version7 = AModel.version1;
	AModel.Total7 = AModel.Total;
	int total7 = 0;
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			total7 = total7 + AModel.version7[a][b];
			//System.out.println(AModel.version1);
		}
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			String Pr7= "";
			Pr7= String.valueOf((double)AModel.version7[a][b]/total7);
			if (AModel.version7[a][b] < 30)
			{
				Pr7 = "0.000";
			}
			if (Pr7.length()>5){
				Pr7=Pr7.substring(0, 5);
			}				
			AModel.Pr7[a][b] = Pr7;
			//System.out.println("Version7: " + AModel.version7[a][b]);
			//System.out.println("Pr7: " + AModel.Pr7[a][b]);
		}
	AModel.intitialisation ();
	AModel.EvolutionAnalysis(SVer8, SVer9, linecount8, linecount9);
	//AModel.Affichage ();
	AModel.version8 = AModel.version1;
	AModel.Total8 = AModel.Total;
	int total8 = 0;
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			total8 = total8 + AModel.version8[a][b];
			//System.out.println(AModel.version1);
			AModel.versionall[a][b]=AModel.version1[a][b]+AModel.version2[a][b]+AModel.version3[a][b]+AModel.version4[a][b]+AModel.version5[a][b]+AModel.version6[a][b]+AModel.version7[a][b]+AModel.version8[a][b];
		}
	for (int a=0; a<34; a++)
		for(int b=0; b<34; b++)
		{
			String Pr8= "";
			Pr8= String.valueOf((double)AModel.version8[a][b]/total8);
			if (AModel.version8[a][b] < 30)
			{
				Pr8 = "0.000";
			}
			if (Pr8.length()>5){
				Pr8=Pr8.substring(0, 5);
			}				
			AModel.Pr8[a][b] = Pr8;
		}	
	
	int totallall = total1+total2+total3+total4+total5+total6+total7+total8;
	// Computing Total Probability
	for(int tot=0; tot<34; tot++)
		for(int to=0; to<34; to++)
		{
			AModel.PrAll[tot][to] = Double.parseDouble(AModel.Pr1[tot][to])+ Double.parseDouble(AModel.Pr2[tot][to])+ Double.parseDouble(AModel.Pr3[tot][to])+ Double.parseDouble(AModel.Pr4[tot][to])+ Double.parseDouble(AModel.Pr5[tot][to])+ Double.parseDouble(AModel.Pr6[tot][to])+ Double.parseDouble(AModel.Pr7[tot][to])+ Double.parseDouble(AModel.Pr8[tot][to]);
			AModel.PrAll[tot][to] = Math.round(AModel.PrAll[tot][to]*10000.0)/10000.0;
			System.out.println("Probability All: " + AModel.PrAll[tot][to]);
		}
	
// Print into output file	
	AModel.output.write(AModel.Newligne);
	AModel.output.write(AModel.Newligne);
	AModel.output.write(" Markov Chains for all versions: ");
	AModel.output.write(AModel.Newligne);
	AModel.output.write(AModel.Newligne);						
	AModel.output.write(" AS   Bl    CS    CC    LC   LaC    LM    LP    MCh    RP   SG    SA    TT   C.i   C.c   C.l   D.d   F.c  C.cl  F.cp   C.r Cp.cp  F.cC  D.cc  Cp.c  O.cO   O.o   C.cc   F.p   D.cd  O.s   D.c   P.p   O.cS   TP");
	AModel.output.write(AModel.Newligne);
	for (int i=0; i<34; i++) {
		AModel.output.write(AModel.Newligne);
		for (int j=0; j<34; j++) {
			AModel.output.write(Integer.toString(AModel.versionall [i][j]));
			int s= Integer.toString(AModel.versionall [i][j]).length();
			for (int jj=s; jj<6; jj++) {						
				AModel.output.write(" ");
			}
						
		}AModel.output.write(Integer.toString(totallall));
	}
	AModel.output.write(AModel.Newligne);
	AModel.output.write(AModel.Newligne);
	AModel.output.write(" Markov Chains with probabilities for the all versions: ");
	AModel.output.write(AModel.Newligne);
	AModel.output.write(AModel.Newligne);						
	AModel.output.write(" AS     Bl     CS     CC      LC     LaC     LM     LP     MCh    RP    SG     SA     TT    C.i    C.c    C.l    D.d     F.c   C.cl   F.cp   C.r   Cp.cp   F.cC   D.cc   Cp.c   O.cO   O.o   C.cc    F.p   D.cd    O.s    D.c    P.p    O.cS");
	AModel.output.write(AModel.Newligne);
	for (int ii=0; ii<34; ii++) {
		System.out.println("");
		AModel.output.write(AModel.Newligne);
		for (int jj=0; jj<34; jj++) {
			if ((AModel.versionall [ii][jj]!=0))
			{	
				//String valeur= String.valueOf((double)AModel.PrAll[ii][jj]);
				String valeur= String.valueOf((double)AModel.versionall[ii][jj]/totallall);
				if (valeur.length()>5){
				valeur=valeur.substring(0, 5);
				if (valeur.substring(0, 1).equals("0"))
						{
						AModel.output.write(valeur);
						AModel.output.write("  ");
						}
				else {
						AModel.output.write("0.000");
						AModel.output.write("  ");}	
				}
				else {
						AModel.output.write(valeur);
						for (int ccc=valeur.length(); ccc<7; ccc++)
						AModel.output.write(" ");}
				///AModel.output.write(PrAll[ii][jj]);						
				}
			else if (AModel.versionall [ii][jj]==0){
				AModel.output.write("0.000");
				AModel.output.write("  ");
				}
				else{
					AModel.output.write("Not ap");
					AModel.output.write(" ");}
				}
			}
			AModel.output.close();
			System.out.println("Total1: " + total1);
			System.out.println("Total All: " + totallall);
		} // end printing	    				
	catch (IOException ioe)		
	{ 
		System.out.println("IO error:" +ioe ); 
	} 
	    
  }
  public void intitialisation () 
	 {
			for (int pp=0; pp<34; pp++) {
				for (int pp1=0; pp1<34; pp1++) {
					this.version1 [pp][pp1]=0;
				}
			}

			for (int tp1=0; tp1<34; tp1++) {
					this.Total [tp1]=0;
				}
		  }
  
  	public void EvolutionAnalysis(String[][] FileContainVer1, String[][] FileContainVer2, int ln1, int ln2) {			
	    String[] tab1 = new String[ln1]; String[] tab2 = new String [ln2];
		int trouve=0; 
		
		//tab1 = FileContainVer1;
		for (int rown1 =1; rown1< ln1; rown1++)
		{
			tab1[rown1]=FileContainVer1[0][rown1];			
		} 
		//tab2 = FileContainVer2;
		for (int rown2 =1; rown2< ln2; rown2++)
		{
			tab2[rown2]=FileContainVer2[0][rown2];
		} 
		
		for (int i=1; i<ln1; i++)
				//if (tab1[i] != null) 
					for (int j=1; j<ln2; j++)
					{
						//System.out.println("tab1[ " + i + "]= " + tab1[i]);
						//System.out.println("tab2[ " + j + "]= " + tab2[j]);
						if (tab1[i].equals(tab2[j]))
						{	
							for (int k=1; k<34; k++)
							{
								if (FileContainVer1[k][i].equals("1"))
								{
//									System.out.println("Contain= " + FileContainVer1[k-1][i-1]);
									for (int l=1; l<34; l++)
									{
										if (FileContainVer2[l][j].equals("1"))
										{
											this.version1[k-1][l-1]++;
											trouve = 1;
										}		
									}
								if (trouve==0) 
								{
									this.version1[k-1][12]++;
								}
								}
							}
							
						}
					}
		for (int m=0; m<34; m++)
			for (int n=0; n<34; n++)
				System.out.println(version1[m][n]);
	}
  	//Displaying the results
	public void Affichage(){
				try {
						this.output.write(this.Newligne);
						System.out.println(" Markov Chains ");
						this.output.write(this.Newligne);
						this.output.write(" Markov model for two first version:");
						this.output.write(this.Newligne);						
						this.output.write(" AS   Bl    CS    CC    LC   LaC    LM    LP    MCh    RP   SG    SA   TT   C.i   C.c   C.l   D.d   F.c  C.cl  F.cp   C.r Cp.cp  F.cC  D.cc  Cp.c  O.cO  O.o   C.cc   F.p   D.cd  O.s   D.c   P.p   O.cS");
						this.output.write(this.Newligne);

						for (int i=0; i<34; i++) {
							System.out.println("");
							this.output.write(this.Newligne);
							for (int j=0; j<34; j++) {
								System.out.print(this.version1 [i][j]);
								this.Total [i]+=this.version1 [i][j];
								this.output.write(Integer.toString(this.version1 [i][j]));
								int s= Integer.toString(this.version1 [i][j]).length();
								for (int jj=s; jj<6; jj++) {
									System.out.print(" ");
									this.output.write(" ");
								}		
							}
						}
						this.output.write(this.Newligne);
						this.output.write(this.Newligne);
						this.output.write(" Markov Chains with probabilities for two first version: ");
						this.output.write(this.Newligne);
						
						this.output.write(this.Newligne);						
						this.output.write(" AS     Bl     CS     CC      LC     LaC     LM     LP     MCh    RP    SG     SA     TT    C.i    C.c    C.l    D.d     F.c   C.cl   F.cp   C.r   Cp.cp   F.cC   D.cc   Cp.c   O.cO   O.o   C.cc    F.p   D.cd    O.s    D.c    P.p    O.cS");
						this.output.write(this.Newligne);
						
						for (int ii=0; ii<34; ii++) {
							System.out.println("");
							this.output.write(this.Newligne);
							for (int jj=0; jj<34; jj++) {
								if ((Total [ii]!=0)&& (this.version1 [ii][jj]!=0))
								{					
									String valeur= String.valueOf((double)this.version1 [ii][jj]/(double)this.Total [ii]);
									if (valeur.length()>5)
									{valeur=valeur.substring(0, 5);
									if (valeur.substring(0, 5).equals("0"))
									{
										this.output.write(valeur);
										this.output.write("  ");
									}
									else {
										this.output.write("0.000");
										this.output.write("  ");}	
									}
									else {
										this.output.write(valeur);
										for (int ccc=valeur.length(); ccc<7; ccc++)
											this.output.write(" ");}
						
									}
					
								else if (this.version1 [ii][jj]==0)
								{this.output.write("0.000");
								this.output.write("  ");}
								else 
								{this.output.write("Not ap");
								this.output.write(" ");}
							}
						}
						System.out.println("");
				}
				 catch (Exception ioe)		{ 
						ioe.printStackTrace();
				 }
			}
}