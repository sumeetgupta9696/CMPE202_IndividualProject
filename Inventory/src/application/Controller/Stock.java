package application.Controller;

import java.util.ArrayList;

import application.Database.DB;
import application.Handler.HandleInput;
import application.Model.Item;

public class Stock {
	private DB db = DB.getInstance();
	private HandleInput file;
	public Stock(String filePath){
		file = new HandleInput(filePath);
	}
	
	public void create() throws Exception{
		try {
			file.readFile(true);
			getItems(file.getFileContent());
		}catch(Exception e){
			System.out.println("The stock file or file path is incorrect");
			System.exit(0);
		}
	}
	
	private void getItems(ArrayList<String> fileContent){	
		for(String line : fileContent){
			String[] item = line.split(",");
			db.getItems().put(item[1], new Item(item[0], item[1], Double.parseDouble(item[3]), Integer.parseInt(item[2])));
		}
	}
}