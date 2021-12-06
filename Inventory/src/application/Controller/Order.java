package application.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import application.Database.DB;
import application.Handler.HandleInput;
import application.Model.Item;
import application.Model.OrderModel;

//import application.Model.Order;
import application.Model.OrderItem;

public class Order {
	private DB db = DB.getInstance();
	private OrderModel currentOrder = new OrderModel();
	private HandleInput file;
	private ArrayList<String> outputMessage = new ArrayList<>();
	private ArrayList<OrderItem> items = new ArrayList<>();
	private HashMap<String, Integer> quantityAccumulatedByCategory = new HashMap<>();
	private HashSet<String> creditCards = new HashSet<String>();
	private double totalPrice = 0;
	public Order (String filePath){
		file = new HandleInput(filePath);
	}
	public boolean orderOrigin() throws Exception{	
		try {
			file.readFile(true);
			getItems(file.getFileContent());
		}catch(Exception e){
			return false;
		}
		return true;
	}
	public boolean orderVerify(){	
		categoryLimit();
		itemLimit();
		return outputMessage.isEmpty();
	}
	public void cost(){	
		items.forEach((item)->{
			totalPrice+=item.getQuantity()*item.getPrice();
		});
		currentOrder.setTotalPrice(totalPrice);
	}
	
	public double getValue(){
		return currentOrder.getTotalPrice();
	}
	public void settle(){	
		db.getOrders().add(currentOrder);
		for(OrderItem currentItem:items){
			Item item = db.getItems().get(currentItem.getName());
			item.setQuantityStock(item.getQuantityStock()-currentItem.getQuantity());
 		}
		for(String creditCard : creditCards){	
			if(!db.getCreditCards().contains(creditCard)){
				db.getCreditCards().add(creditCard);
			}
		}
		output();
	}
	public void printMessage(){	
		for (String str : outputMessage){
			System.out.println(str);
		}
	}
	private void getItems(ArrayList<String> fileContent){	
		for(String line:fileContent){
			String[] item = line.split(",");
			if (db.getItems().containsKey(item[0])){
				Item tempItem = db.getItems().get(item[0]);
				int quantity = Integer.parseInt(item[1]);
				items.add(new OrderItem(item[0], quantity, item[2].replaceAll("\\s+", "")));
				addQuantityToCategory(tempItem.getCategory(),quantity);
			} else {
				outputMessage.add("Item " + item[0] + " not found");
			}
		}
		if(!outputMessage.isEmpty()){	
			items.clear();
		}
	}
	private void itemLimit(){	
		StringBuilder message = new StringBuilder();
		for(OrderItem currentItem : items){
			Item item = db.getItems().get(currentItem.getName());
			if (item.getQuantityStock() < currentItem.getQuantity()){
				if (message.length() > 0)
					message.append(", ");
				message.append(currentItem.getName() + ": Quantity on stock (" + item.getQuantityStock() + ")");
			} else {
				currentItem.setPrice(item.getPrice());
				if(!creditCards.contains(currentItem.getCreditCard()))
					creditCards.add(currentItem.getCreditCard());
			}
 		}
		if (message.length() > 0){	
			outputMessage.add("Please correct quantities.");
			outputMessage.add(message.toString());
		}
	}
	private void addQuantityToCategory(String category, int desiredQuantity){	
		int quantity = desiredQuantity;
		if (quantityAccumulatedByCategory.containsKey(category))
			quantity += quantityAccumulatedByCategory.get(category);
		quantityAccumulatedByCategory.put(category,quantity);
	}
	private void categoryLimit(){ 	
		StringBuilder message = new StringBuilder();
		db.getCategoryCap().forEach((category, quantityPermitted)->{
			if (quantityAccumulatedByCategory.containsKey(category)){ 				
				if(quantityAccumulatedByCategory.get(category)>quantityPermitted){
					if (message.length() > 0)
						message.append(", ");
					message.append(category + ": " + quantityPermitted);
				}
			}
		});
		if (message.length() > 0){
			outputMessage.add("Maximum quantity allowed:");
			outputMessage.add(message.toString());
		}
	}
	public void output(){	
		if(outputMessage.isEmpty()){
			outputMessage.add("Amount Paid");
			outputMessage.add(Double.toString(currentOrder.getTotalPrice()));
			try {
				file.writeOutput(outputMessage,false);
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			try{
				file.writeOutput(outputMessage,true);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}