package sad.detection.generators.Azadeh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


public class DesignPatternResultParserNew {

	public static final String[] DPATTERNS = new String[] {"Command", "Composite", "Decorator", "FactoryMethod", "Observer1", "Prototype"};

		private static ArrayList<String> commandParticipants = new ArrayList<String>(Arrays.asList("invoker", "command", "concretecommand", "receiver", 
				"client"));
		private static List<String> compositeParticipants = new ArrayList<String>(Arrays.asList("leaf", "composite", "component"));
		private static List<String> decoratorParticipants = new ArrayList<String>(Arrays.asList("component", "concretecomponent", "decorator",
				"concretedecorator"));
		private static List<String> factoryMethodParticipants = new ArrayList<String>(Arrays.asList("creator", "concreteCreator", "product", "concreteProduct"));
		private static List<String> observerParticipants = new ArrayList<String>(Arrays.asList("subject", "concreteSubject", "observer", "concreteObserver"));
		private static List<String> prototypeParticipants = new ArrayList<String>(Arrays.asList("prototype"));

		
	private static TreeMap<String, List<String>> designpatterns;
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		designpatterns = new TreeMap<String, List<String>>();

		designpatterns.put("Command", commandParticipants);
		designpatterns.put("Composite", compositeParticipants);
		designpatterns.put("Decorator", decoratorParticipants);
		designpatterns.put("FactoryMethod", factoryMethodParticipants);
		designpatterns.put("Observer1", observerParticipants);
		designpatterns.put("Prototype", prototypeParticipants);
		final String inputDir = "C:/Users/manel/Desktop/TheseManel/Workspace/Ptidej v5/Ptidej Solver Data/Ant/";		
		final File designpatternresultDir = new File(inputDir);
		final String output = "C:/Users/manel/Desktop/TheseManel/Workspace/Ptidej v5/Ptidej Solver Data/Ant/CSV/";
		final File outputDir = new File(output);			
		String[] resultContent = designpatternresultDir.list();

		
		// for each folder in the project
		for (int i = 0; i < resultContent.length; i++)
		{
			String dirName = resultContent[i];		
			ArrayList<String> listAllSmellyClasses = new ArrayList<String>();						
			File[] dirContent = new File(inputDir + dirName).listFiles();
			
			ArrayList<HashMap<String, HashMap<String, Object>>> results = new ArrayList<HashMap<String, HashMap<String, Object>>>();
			
			// For each ".ini" file in the current folder
			for (int j = 0; j < dirContent.length; j++)
			{
				
							
				HashMap<String, HashMap<String, Object>> classResult = new HashMap<String, HashMap<String, Object>>();
				
				String fileName = dirContent[j].getName();
				
				String completeName = inputDir + "/" + dirName + "/" + fileName;				
				String dpatternName = fileName.substring(fileName.indexOf("for") + 4, fileName.indexOf(".ini"));
				
				String participantPrefix = dpatternName + ".";
				
				List<String> participantForThisPattern = designpatterns.get(dpatternName);

				FileReader fileReader;

				try {
					fileReader = new FileReader(completeName);
					BufferedReader bufferreader = new BufferedReader(fileReader);
			
				 
				boolean dpatternInstance = false;				
				int dpatternInstanceID = 0;
				
					for (String aLine = bufferreader.readLine() ; aLine != null ; aLine = bufferreader.readLine()) {
						
						if ((dpatternInstanceID == 0) || (aLine.equals(""))) { // new instance of design pattern
							dpatternInstance = true;
							dpatternInstanceID++;
						}
						else {
							
							Iterator itParticipantForThisPattern = participantForThisPattern.iterator();
							while (itParticipantForThisPattern.hasNext()) {
								String participant = (String) itParticipantForThisPattern.next();
								if (aLine.contains(participant)) {
									String className = aLine.split(" = ")[1];
		
									if (classResult.keySet().contains(className)) {										
										
										HashMap<String, Object> participantResult = classResult.get(className);	
										for (Map.Entry<String, Object> participantEntry : participantResult.entrySet()) {
										    String key = participantEntry.getKey();
										    Object value = participantEntry.getValue();
										    if (key.equals(participantPrefix + participant)) {
										    	participantResult.put(key, ((Integer) participantResult.get(key)) + 1);
										    	break;
										    }
										}
//										classResult.put(className, participantResult);
									}
									else {
										HashMap<String, Object> participantResult = new HashMap<String, Object>();
										
										participantResult.put(participantPrefix + participant, 1);
										
										Iterator itParticipant = participantForThisPattern.iterator();
										while (itParticipant.hasNext()) {
											String aParticipant = (String) itParticipant.next();
											if (!aParticipant.equals(participant)) 
												participantResult.put(participantPrefix + aParticipant, 0);
										}
										classResult.put(className, participantResult);
									}
									break;
								}
							}
						}
	
					 }
					
					
					for (Entry<String, HashMap<String, Object>> classEntry : classResult.entrySet()) {
					
						HashMap<String, Object> participantResult = classEntry.getValue();	
							for (Map.Entry<String, List<String>> patternsAndParticipants : designpatterns.entrySet()) {
								String key = patternsAndParticipants.getKey();
								if (!key.equals(dpatternName)) {
									Iterator itOtherParticipants = designpatterns.get(key).iterator();
									while (itOtherParticipants.hasNext()) {
										String participant = (String)itOtherParticipants.next();
								    	participantResult.put(key + "." + participant, 0);
									}
								}
							}
					}
				  	
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				results.add(classResult);
				
			}
			FileWriter outFileStream;
			
			try {
				// create the output (csv file) for the current folder
				outFileStream = new FileWriter(outputDir + "/" + dirName + ".csv");
				BufferedWriter outputStream = new BufferedWriter(outFileStream);
				outputStream.write("ClassName");

				HashMap<String, HashMap<String, Object>> a = results.get(0);

				for (Entry<String, HashMap<String, Object>> result : a.entrySet()) {
				
					String className = result.getKey();
					
					for (Map.Entry<String, Object> participant : result.getValue().entrySet()) {
						outputStream.write("," + participant.getKey());
					}
					break;
				}
				outputStream.write(",TotalPatterns");
				outputStream.write("\n");
				outputStream.flush();
				
				Iterator itResults = results.iterator();
				
				while (itResults.hasNext()) {
					HashMap<String, HashMap<String, Object>> aClassResult =  (HashMap<String, HashMap<String, Object>>) itResults.next();
					
					for (Entry<String, HashMap<String, Object>> result : aClassResult.entrySet()) {
						int total = 0;
						String className = result.getKey();
						outputStream.write(className);
						for (Map.Entry<String, Object> participant : result.getValue().entrySet()) {
							outputStream.write("," + participant.getValue());
							total = total + (Integer) participant.getValue();
						}
						outputStream.write("," + total);
						outputStream.write("\n");
						outputStream.flush();
					}
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
    } 
}