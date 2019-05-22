package sad.detection.generators.Azadeh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.*;


/**
 * @author Zeinab(Azadeh) Kermansaravi
 * @since  2015/04/27
**/

public final class SampleModelAzadeh {
	private String Newligne = System.getProperty("line.separator");
	private	Writer output = null;
	private int[][] version1= new int [12][13];
	private int[][] version2= new int [12][13];
	private int[] version3= new int [12];
	private int[] version4= new int [12];
	private int[][] Total1= new int [12][13];
	private int[][] Total2= new int [12][13];
	private int[] Total3= new int [12];
	private int[] Total4= new int [12];
	private int mutatedFault;
	private int mutatedClean;
	private int NonmutatedFault;
	private int NonmutatedClean;
	
	public static void main(String[] args) {
		SampleModelAzadeh AModel = new SampleModelAzadeh();
		String[] items = new String[14];
		items[0]=	"Antisingleton";
		items[1]=	"Blob";
		items[2]=	"ClassDataShouldBePrivate";
		items[3]=	"ComplexClass";
		items[4]=	"LargeClass";
		items[5]=	"LazyClass";
		items[6]=	"LongMethod";
		items[7]=	"LongParameterList";
	    items[8]=	"MessageChains";
		items[9]=	"RefusedParentRequest";
		items[10]=	"SpeculativeGenerality";
		items[11]=	"SwissArmyKnife";
//		items[12]=	"CHANGES";
//		items[13]=	"BUGS";
		AModel.mutatedFault=0;
		AModel.mutatedClean=0;
		AModel.NonmutatedFault=0;
	    AModel.NonmutatedClean=0;
		
		String[] ClassExistant1 = new String[10000];
		int class1 =0;
		String[] ClassExistant2 = new String[10000];
		int class2 =0;
		String[] ClassExistant3 = new String[10000];
		int class3 =0;
		String[] ClassExistant4 = new String[10000];
		int class4 =0;
		String[] ClassExistant5 = new String[10000];
		int class5 =0;
		String[] ClassExistant6 = new String[10000];
		int class6 =0;
		String[] ClassExistant7 = new String[10000];
		int class7 =0;
		String[] ClassExistant8 = new String[10000];
		int class8 =0;
		String[] ClassExistant9 = new String[10000];
		int class9 =0;
		
		  try {
			  			   
				String Infile = "C:/Users/azks.GIGL/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/Anti-Pattern Detection/ArgoUML/CSV/EvolutinResults.txt";
				File file = new File(Infile);
				AModel.output = new BufferedWriter(new FileWriter(file));
				
				
//		        InputStream ips = new FileInputStream("C:/Users/azks.GIGL/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/Anti-Pattern Detection/ArgoUML/CSV/initial.txt");
//			    InputStreamReader ipsr = new InputStreamReader(ips);
//			    BufferedReader br = new BufferedReader(ipsr);
			  
//			    System.out.println(br.readLine());			    
			    for (int ppo=0; ppo<12; ppo++)
			    	AModel.output.write(items[ppo].substring( 0, 4)+ "  ");
			    
			String ligne = null;
			int version = 0;
		
//			System.out.println(br.readLine());
			InputStream ips = new FileInputStream("C:/Users/azks.GIGL/Azadeh/Azadeh Papers/Journal Papers/2015/EMSE/Results/Anti-Pattern Detection/ArgoUML/CSV/ArgoUMLv0.10.1.csv");
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			
			System.out.println(br.readLine());
			while ((ligne = br.readLine()) != null) {
				System.out.println(br.readLine());
				if (ligne.contains("ClassName,AntiSingleton,")) 
				{
					version++; 
					System.out.println( "Version: " + version ); 
					System.out.println(br.readLine());
				}
				
			switch(version){
			case 1: ClassExistant1 [class1]=ligne; class1++;
					break;
			case 2: ClassExistant2 [class2]=ligne; class2++;
					break;
			case 3: ClassExistant3 [class3]=ligne; class3++;
					break;
			case 4: ClassExistant4 [class4]=ligne; class4++;
					break;
			case 5: ClassExistant5 [class5]=ligne; class5++;
					break;
			case 6: ClassExistant6 [class6]=ligne; class6++;
					break;
			case 7: ClassExistant7 [class7]=ligne; class7++;
					break;
			case 8: ClassExistant8 [class8]=ligne; class8++;
					break;
			case 9: ClassExistant9 [class9]=ligne; class9++;
					break;
			default: break;

			}
			System.out.println( "Version: " + version ); 
				
			
			for (int pp=0; pp<12; pp++) {
				for (int pp1=0; pp1<13; pp1++) {
					AModel.Total1 [pp][pp1]=0;
					
				}
			}
			
			for (int tt=0; tt<12; tt++) {
				for (int tt1=0; tt1<13; tt1++) {
					AModel.Total2 [tt][tt1]=0;
					
				}
			}
			
				for (int tp1=0; tp1<12; tp1++) {
					AModel.Total3 [tp1]=0;
					
				}
			
			
			
				for (int pt1=0; pt1<12; pt1++) {
					AModel.Total4 [pt1]=0;
					
				}
			
		
			AModel.intitialisation ();
			AModel.EvolutionAnalysis(ClassExistant1, class1, ClassExistant2, class2);
			AModel.Affichage ();
			AModel.intitialisation ();
			AModel.EvolutionAnalysis(ClassExistant2, class2, ClassExistant3, class3);
			AModel.Affichage ();
			AModel.intitialisation ();
			AModel.EvolutionAnalysis(ClassExistant3, class3, ClassExistant4, class4);
			AModel.Affichage ();
			AModel.intitialisation ();
			AModel.EvolutionAnalysis(ClassExistant4, class4, ClassExistant5, class5);
			AModel.Affichage ();
			AModel.intitialisation ();
			AModel.EvolutionAnalysis(ClassExistant5, class5, ClassExistant6, class6);
			AModel.Affichage ();
			AModel.intitialisation ();
			AModel.EvolutionAnalysis(ClassExistant6, class6, ClassExistant7, class7);
			AModel.Affichage ();
			AModel.intitialisation ();
			AModel.EvolutionAnalysis(ClassExistant7, class7, ClassExistant8, class8);
			AModel.Affichage ();
			AModel.intitialisation ();
			AModel.EvolutionAnalysis(ClassExistant8, class8, ClassExistant9, class9);
			AModel.Affichage ();
			
			AModel.output.write(AModel.Newligne);
			AModel.output.write("mutated and faulty:  "+AModel.mutatedFault);
			AModel.output.write(AModel.Newligne);
			AModel.output.write("mutated and clean:  "+AModel.mutatedClean);
			AModel.output.write(AModel.Newligne);
			AModel.output.write("No mutated and faulty:  "+AModel.NonmutatedFault);
			AModel.output.write(AModel.Newligne);
			AModel.output.write("No mutated and clean:  "+AModel.NonmutatedClean);
			
			AModel.output.write(AModel.Newligne);
			AModel.output.write(AModel.Newligne);
			AModel.output.write(" Markov Chains for all versions: ");
			AModel.output.write(AModel.Newligne);
			for (int i=0; i<12; i++) {
				
				AModel.output.write(AModel.Newligne);
				for (int j=0; j<13; j++) {
					
					AModel.output.write(Integer.toString(AModel.Total1 [i][j]));
					int s= Integer.toString(AModel.Total1 [i][j]).length();
					for (int jj=s; jj<6; jj++) {
						
						AModel.output.write(" ");
					}
						
				}AModel.output.write(Integer.toString(AModel.Total3 [i]));
			}
			AModel.output.write(AModel.Newligne);
			AModel.output.write(AModel.Newligne);
			AModel.output.write(" Markov Chains with probabilities for the all versions: ");
			AModel.output.write(AModel.Newligne);
			for (int ii=0; ii<12; ii++) {
				System.out.println("");
				AModel.output.write(AModel.Newligne);
				for (int jj=0; jj<13; jj++) {
					if ((AModel.Total3 [ii]!=0)&& (AModel.Total1 [ii][jj]!=0))
					{
						
					String valeur= String.valueOf((double)AModel.Total1 [ii][jj]/(double)AModel.Total3 [ii]);
					if (valeur.length()>5)
					{valeur=valeur.substring(0, 5);
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
						
					}
					
					else if (AModel.Total1 [ii][jj]==0)
					{AModel.output.write("0.000");
					AModel.output.write("  ");}
					else 
					{AModel.output.write("Not ap");
					AModel.output.write(" ");}
}
			}
			
			
		
			AModel.output.write(AModel.Newligne);
			AModel.output.write(" Bugs FOR ALL VERSIONS:");
			AModel.output.write(AModel.Newligne);
			AModel.output.write(AModel.Newligne);
			
			
			for (int i1=0; i1<12; i1++) {
				System.out.println("");
				AModel.output.write(AModel.Newligne);
				for (int j1=0; j1<12; j1++) {
					
					AModel.output.write(Integer.toString(AModel.Total2 [i1][j1]));
					int s= Integer.toString(AModel.Total2 [i1][j1]).length();
					for (int jj2=s; jj2<6; jj2++) {
						System.out.print(" ");
						AModel.output.write(" ");
					}
				}AModel.output.write(Integer.toString(AModel.Total4 [i1]));
			}
			AModel.output.write(AModel.Newligne);
			AModel.output.write(AModel.Newligne);
			AModel.output.write(" Markov Chains for bugs with probabilities for All releases: ");
		    AModel.output.write(AModel.Newligne);
			for (int i2=0; i2<12; i2++) {
				
				AModel.output.write(AModel.Newligne);
				for (int j2=0; j2<12; j2++) {
					if ((AModel.Total4 [i2]!=0)&& (AModel.Total2 [i2][j2]!=0))
					{
						String valeur= String.valueOf((double)AModel.Total2 [i2][j2]/(double)AModel.Total4 [i2]);
						if (valeur.length()>5)
						{valeur=valeur.substring(0, 5);
						if(valeur.substring(0, 1).equals("0"))
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
							for (int ccp=valeur.length(); ccp<7; ccp++)
							AModel.output.write(" ");}
							
						}
					
					else if (AModel.version2 [i2][j2]==0)
					{AModel.output.write("0.000");
					AModel.output.write("  ");}
					else 
					{AModel.output.write("Not ap");
					AModel.output.write(" ");}
				}}
			
			
			br.close(); AModel.output.close();
				
			}
		  }
				
				catch (IOException ioe)		{ 
		System.out.println( "IO error:" +ioe ); 
      }  


}
	// Initializing Matrixes version1 to 4
	public void intitialisation () 
	 {
			for (int pp=0; pp<12; pp++) {
				for (int pp1=0; pp1<13; pp1++) {
					this.version1 [pp][pp1]=0;
				}
			}
			
			for (int tt=0; tt<12; tt++) {
				for (int tt1=0; tt1<13; tt1++) {
					this.version2 [tt][tt1]=0;
				}
			}

			for (int tp1=0; tp1<12; tp1++) {
					this.version3 [tp1]=0;
				}

			for (int pt1=0; pt1<12; pt1++) {
					this.version4 [pt1]=0;
				}
		  }
	
	public void EvolutionAnalysis(String ClassExistanta[], int classa, String ClassExistantb[], int classb) {
			
		String CurrentClass; String[] tab1 = new String[12]; String[] tab2 = new String[12];
				
		for (int i=0; i<classa; i++)
			 {
					StringTokenizer val = new StringTokenizer(ClassExistanta [i], ","); 
					CurrentClass=val.nextToken();
					int trouve=0;			
					tab1[0]=val.nextToken();
					tab1[1]=val.nextToken();
					tab1[2]=val.nextToken();
					tab1[3]=val.nextToken();
					tab1[4]=val.nextToken();
					tab1[5]=val.nextToken();
					tab1[6]=val.nextToken();
					tab1[7]=val.nextToken();
				    tab1[8]=val.nextToken();
					tab1[9]=val.nextToken();
					tab1[10]=val.nextToken();
					tab1[11]=val.nextToken();
//					val.nextToken();val.nextToken();
//					tab1[12]=val.nextToken();
//					val.nextToken();
//					tab1[13]=val.nextToken();
					
					for (int j=0; j<classb; j++)
					{
						if (ClassExistantb [j].contains(CurrentClass+","))
						{
							StringTokenizer val2 = new StringTokenizer(ClassExistantb [j], ","); 
							
							CurrentClass=val2.nextToken();
										
							tab2[0]=val2.nextToken();
							tab2[1]=val2.nextToken();
							tab2[2]=val2.nextToken();
							tab2[3]=val2.nextToken();
							tab2[4]=val2.nextToken();
							tab2[5]=val2.nextToken();
							tab2[6]=val2.nextToken();
							tab2[7]=val2.nextToken();
						    tab2[8]=val2.nextToken();
							tab2[9]=val2.nextToken();
							tab2[10]=val2.nextToken();
							tab2[11]=val2.nextToken();
//							val2.nextToken();val2.nextToken();
//							tab2[12]=val2.nextToken();
//							val2.nextToken();
//							tab2[13]=val2.nextToken();
						}
					}
					for (int k=0; k<12; k++)
					{
					if (tab1[k].equals("1"))
					{  
						for (int l=0; l<12; l++)
						{
							if (tab2[l].equals("1"))
							{
								this.version1[k][l]++;// to count mutation
								this.Total1[k][l]++;
								trouve=1; 
								this.version2[k][l]+=Integer.parseInt(tab2[13]);// to count bugs
								this.Total2[k][l]+=Integer.parseInt(tab2[13]);
								if ((Integer.parseInt(tab2[13])==1)&&(k!=l)) mutatedFault++;
								else if ((Integer.parseInt(tab2[13])==1)&&(k==l)) NonmutatedFault++;
								else mutatedClean++;
							}
						}
					if (trouve==0) 
					{
						this.version1[k][12]++;this.Total1[k][12]++;
						if (Integer.parseInt(tab2[13])==1) NonmutatedFault++;
						else NonmutatedClean++;
					}
					}
					}// for
			 }// for
			
			
			}
			
	//Displaying the results
	public void Affichage(){
				try {
						this.output.write(this.Newligne);
						System.out.println(" Markov Chains ");
						this.output.write(this.Newligne);
						this.output.write(" Markov Chains for version: ");
						this.output.write(this.Newligne);
						for (int i=0; i<12; i++) {
							System.out.println("");
							this.output.write(this.Newligne);
							for (int j=0; j<13; j++) {
								System.out.print(this.version1 [i][j]);
								this.version3 [i]+=this.version1 [i][j];
								this.Total3 [i]+=this.version1 [i][j];
								this.output.write(Integer.toString(this.version1 [i][j]));
								int s= Integer.toString(this.version1 [i][j]).length();
								for (int jj=s; jj<6; jj++) {
									System.out.print(" ");
									this.output.write(" ");
								}		
							}this.output.write(Integer.toString(this.version3 [i]));
						}
						this.output.write(this.Newligne);
						this.output.write(this.Newligne);
						this.output.write(" Markov Chains with probabilities: ");
						this.output.write(this.Newligne);
						for (int ii=0; ii<12; ii++) {
							System.out.println("");
							this.output.write(this.Newligne);
							for (int jj=0; jj<13; jj++) {
								if ((version3 [ii]!=0)&& (this.version1 [ii][jj]!=0))
								{					
									String valeur= String.valueOf((double)this.version1 [ii][jj]/(double)this.version3 [ii]);
									if (valeur.length()>5)
									{valeur=valeur.substring(0, 5);
									if (valeur.substring(0, 1).equals("0"))
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
						this.output.write(this.Newligne);
						System.out.println(" Bugs ");
						this.output.write(this.Newligne);
						this.output.write(this.Newligne);
						
						this.output.write(" Bugs:");
						this.output.write(this.Newligne);
						for (int i1=0; i1<12; i1++) {
							System.out.println("");
							this.output.write(this.Newligne);
							for (int j1=0; j1<12; j1++) {
								System.out.print(this.version2 [i1][j1]);
								this.version4 [i1]+=this.version2 [i1][j1];
								this.Total4 [i1]+=this.version2 [i1][j1];
								this.output.write(Integer.toString(this.version2 [i1][j1]));
								int s= Integer.toString(this.version2 [i1][j1]).length();
								for (int jj2=s; jj2<6; jj2++) {
									System.out.print(" ");
									this.output.write(" ");
								}
							}this.output.write(Integer.toString(this.version4 [i1]));
						}
						this.output.write(this.Newligne);
						this.output.write(this.Newligne);
						this.output.write(" Markov Chains for bugs with probabilities: ");
						this.output.write(this.Newligne);
						for (int i2=0; i2<12; i2++) {
							System.out.println("");
							this.output.write(this.Newligne);
							for (int j2=0; j2<12; j2++) {
								if ((version4 [i2]!=0)&& (this.version2 [i2][j2]!=0))
								{
									String valeur= String.valueOf((double)this.version2 [i2][j2]/(double)this.version4 [i2]);
									if (valeur.length()>5)
									{valeur=valeur.substring(0, 5);
									if(valeur.substring(0, 1).equals("0"))
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
										for (int ccp=valeur.length(); ccp<7; ccp++)
											this.output.write(" ");}
							
									}
					
								else if (this.version2 [i2][j2]==0)
								{this.output.write("0.000");
								this.output.write("  ");}
								else 
								{this.output.write("Not ap");
								this.output.write(" ");}
						}
					}
				}
				 catch (Exception ioe)		{ 
						ioe.printStackTrace();
				 }
			}

}