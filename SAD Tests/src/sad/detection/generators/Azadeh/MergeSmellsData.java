package sad.detection.generators.Azadeh;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class MergeSmellsData {

	public static final String[] SMELLS = new String[] { "AntiSingleton",
		"BaseClassKnowsDerivedClass", "BaseClassShouldBeAbstract", "Blob",
		"ClassDataShouldBePrivate", "ComplexClass",
		"FunctionalDecomposition", "LargeClass", "LazyClass", "LongMethod",
		"LongParameterList", "ManyFieldAttributesButNotComplex",
		"MessageChains", "RefusedParentBequest", "SpaghettiCode",
		"SpeculativeGenerality", "SwissArmyKnife", "TraditionBreaker" };
	
	public static void main(String[] args) {
		
		final String dirName = "/home-students/sohzephy/Research/Work/Smell_detection/preliminary/R_2_1_0/intermediate_smells/";
		
		FileWriter outFileStream;
		try {
			outFileStream = new FileWriter("/home-students/sohzephy/Research/Work/Smell_detection/preliminary/R_2_1_0/R_2_1_0.csv");
			BufferedWriter outputStream = new BufferedWriter(outFileStream);
			outputStream.write("ClassName");				
			for (int k = 0 ; k < SMELLS.length; k++) {					
				outputStream.write("," + SMELLS[k]);
			}				
			outputStream.write(",TotalSmells");				
			outputStream.write("\n");
			outputStream.flush();

		File[] listFiles = new File(dirName).listFiles();
		
		// For each ".csv" file in the result folder
		for (int j = 0; j < listFiles.length; j++)
		{
			String fileName = listFiles[j].getAbsolutePath();
			System.out.println("######### File Name: \"" + fileName + "\"");
			
			FileReader fileReader;
			try {
				fileReader = new FileReader(fileName);
				BufferedReader bufferreader = new BufferedReader(fileReader);
				
				for (String aLine = bufferreader.readLine() ; aLine != null ; aLine = bufferreader.readLine()) {
					if (!(aLine.startsWith("ClassName"))) {
						outputStream.write(aLine);
						outputStream.write("\n");
						//System.out.println("######### Line: \"" + aLine + "\"");
					}
				}
			

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
