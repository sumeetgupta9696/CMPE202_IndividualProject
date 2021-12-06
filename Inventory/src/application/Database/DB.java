package application.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import application.Model.Item;
import application.Model.OrderModel;

public class DB{
	private static DB instance;
	private HashMap<String, Integer> categoryCap = new HashMap<>();
	private HashMap<String, Item> items = new HashMap<>();
	private HashSet<String> cards = new HashSet<>();
	private ArrayList<OrderModel> orders = new ArrayList<>();
	private DB(){
		categoryCap.put("Luxury", 3);
		categoryCap.put("Essential", 5);
		categoryCap.put("Misc", 6);
	}
	
	public static DB getInstance(){
		if(instance == null)
			instance = new DB();
		return instance;
	}

	public HashMap<String, Integer> getCategoryCap(){
		return categoryCap;
	}
	
	public HashMap<String, Item> getItems(){
		return items;
	}

	public ArrayList<OrderModel> getOrders(){
		return orders;
	}
	
	public HashSet<String> getCreditCards(){
		return cards;
	}
}