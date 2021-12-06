package application.Handler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HandleInput {
	Path path;
	Output outputFile;
	private ArrayList<String> fileContent = new ArrayList<>();
	public HandleInput(String filePath){
		this.path = Paths.get(filePath);
	}

	public void readFile(boolean ignoreFirstLine) throws IOException {
		if (Files.notExists(path)){
			new IOException();
        }
		BufferedReader bufferedReader = new BufferedReader( new FileReader( path.toFile() ) );
		String line = "";
		while (( line = bufferedReader.readLine())!=null){
			if(ignoreFirstLine){ignoreFirstLine = false; continue;}
			fileContent.add(line);
		}
	}
	
	public ArrayList<String> getFileContent() {
		return fileContent;
	}
	
	public void writeOutput(ArrayList<String> message, boolean isError) throws IOException {
		if(isError){
			outputFile = new Error();
		} else {
			outputFile = new Result();
		}
		outputFile.writeToFile(message); 
		outputFile.save(path);
	}
}