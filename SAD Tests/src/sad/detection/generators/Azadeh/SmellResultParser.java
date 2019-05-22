/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package sad.detection.generators.Azadeh;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
/*
 * @author Zéphyrin Soh
 */
public class SmellResultParser {

	public static final String[] SMELLS = new String[] { "AntiSingleton",
		"Blob", "ClassDataShouldBePrivate", "ComplexClass",
		"LargeClass", "LazyClass", "LongMethod",
		"LongParameterList", "MessageChains", "RefusedParentBequest",
		"SpeculativeGenerality", "SwissArmyKnife" };
	
	private static TreeMap<String, List<String>> smells;
	
	public static void main(String[] args) {
		
		TreeMap<String, Integer> aCouple = new TreeMap<String, Integer>();
		
		smells = new TreeMap<String, List<String>>();
		
		ArrayList listClassesForThisSmell = new ArrayList<String>();
		
		for (int k = 0 ; k < SMELLS.length; k++) {	
			smells.put(SMELLS[k], listClassesForThisSmell);
		}
/*		
		smells.put("AntiSingleton", listClassesForThisSmell);		
		smells.put("BaseClassKnowsDerivedClass", listClassesForThisSmell);		
		smells.put("BaseClassShouldBeAbstract", listClassesForThisSmell);		
		smells.put("Blob", listClassesForThisSmell);		
		smells.put("ClassDataShouldBePrivate", listClassesForThisSmell);		
		smells.put("ComplexClass", listClassesForThisSmell);		
		smells.put("FunctionalDecomposition", listClassesForThisSmell);		
		smells.put("LargeClass", listClassesForThisSmell);		
		smells.put("LazyClass", listClassesForThisSmell);		
		smells.put("LongMethod", listClassesForThisSmell);		
		smells.put("LongParameterList", listClassesForThisSmell);		
		smells.put("ManyFieldAttributesButNotComplex", listClassesForThisSmell);		
		smells.put("MessageChains", listClassesForThisSmell);		
		smells.put("RefusedParentBequest", listClassesForThisSmell);		
		smells.put("SpaghettiCode", listClassesForThisSmell);		
		smells.put("SpeculativeGenerality", listClassesForThisSmell);		
		smells.put("SwissArmyKnife", listClassesForThisSmell);		
		smells.put("TraditionBreaker", listClassesForThisSmell);
	*/	
		final String inputDir = "C:/Users/azks.GIGL/Azadeh/Azadeh Papers/Foutse/2015.06.26/Results/ini Results/ArgoUML/";
		
		final File smellresultDir = new File(inputDir);
		
		final File outputDir = new File("C:/Users/azks.GIGL/Azadeh/Ptidej/Ptidej5/ptidej-5/Temp/FoutseCSV/ArgoUML/");
		
		String[] resultContent = smellresultDir.list();
		
		// for each folder in the project
		for (int i = 0; i < resultContent.length; i++)
		{
			String dirName = resultContent[i];		
			ArrayList<String> listAllSmellyClasses = new ArrayList<String>();
			
			System.out.println("######### Folder Name: \"" + inputDir + dirName + "\"");
			
			File[] dirContent = new File(inputDir + dirName).listFiles();
			System.out.println(dirContent.length);
			// For each ".ini" file in the current folder
			for (int j = 0; j < dirContent.length; j++)
			{
				listClassesForThisSmell = new ArrayList<String>();
				String fileName = dirContent[j].getName();
				
				String completeName = inputDir + "/" + dirName + "/" + fileName;				
				System.out.println("\t File Name: " + fileName);				
				String smellName = fileName.substring(fileName.indexOf("for") + 4, fileName.indexOf(".ini"));
				System.out.println("\t Smell Name: " + smellName);	
				FileReader fileReader;
				try {
					fileReader = new FileReader(completeName);
					BufferedReader bufferreader = new BufferedReader(fileReader);
			
				 
				boolean smellInstance = false;				
				int smellInstanceID = 0;
				
					for (String aLine = bufferreader.readLine() ; aLine != null ; aLine = bufferreader.readLine()) {
						
						if (aLine.startsWith("# ------>")) { // new instance of smell
							smellInstance = true;
							smellInstanceID++;
						}
						// it's the first line started with this and not the line containing smell name ...
						if ((aLine.startsWith(smellInstanceID + ".100.")) && (!(aLine.equals(smellInstanceID + ".100.Name = " + smellName))) && (smellInstance)) {
							
							smellInstance = false;
							String className = aLine.substring(aLine.indexOf("= ") + 2);
							
							System.out.println("Smell ###### " + className);
							
							if (!(listClassesForThisSmell.contains(className)))							
								listClassesForThisSmell.add(className);
						}
								
					 }
					
					// Save the list of files for each kind of smell 
					smells.put(smellName, listClassesForThisSmell);		
					
					Iterator it = listClassesForThisSmell.iterator();
					
					// Add the list of file of the current type of smell in the complete list of smelly classes
					// Iterate to avoid duplicate files :-)
					while (it.hasNext()) {						
						String nameOfClass = (String) it.next();							
						if (!(listAllSmellyClasses.contains(nameOfClass)))
						listAllSmellyClasses.add(nameOfClass);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			FileWriter outFileStream;
			
			try {
				// create the output (csv file) for the current folder
				outFileStream = new FileWriter(outputDir + "/" + dirName + ".csv");
				BufferedWriter outputStream = new BufferedWriter(outFileStream);
				
				// Define the header of CSV file
				outputStream.write("ClassName");				
				for (int k = 0 ; k < SMELLS.length; k++) {					
					outputStream.write("," + SMELLS[k]);
				}				
				outputStream.write(",TotalSmells");				
				outputStream.write("\n");
				outputStream.flush();
				
				// Iterate through the complete list of smelly classes
				Iterator it = listAllSmellyClasses.iterator();				
				while (it.hasNext()) {
					
					String aSmellyClass = (String) it.next();					
					outputStream.write(aSmellyClass);	
					int totalSmell = 0;
					
					for (int k = 0 ; k < SMELLS.length; k++) {	
						
						if (smells.get(SMELLS[k]).contains(aSmellyClass))	{					
							outputStream.write("," + 1);
							totalSmell++;
						}
						else 
							outputStream.write("," + 0);
						
					}					
					outputStream.write("," + totalSmell);
					outputStream.write("\n");
					outputStream.flush();
				}

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
				

    } 
	
	}

