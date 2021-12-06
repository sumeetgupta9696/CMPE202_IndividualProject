package application.Handler;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Error implements Output{
	private ArrayList<String> content;
	@Override
	public void writeToFile(ArrayList<String> content){
		this.content = content;
	}
	@Override
	public void save(Path path) throws IOException{
		FileWriter errorFile = new FileWriter(path.getParent().toString() + "/error_" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) +  ".txt");
		for(String line : content)
			errorFile.write(line + "\n");
		errorFile.close();
	}
}
