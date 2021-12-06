package application;

import java.util.*;

import application.Controller.Order;
import application.Controller.Stock;

public class Application {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String[] res = new String[1];
		System.out.println( "Please enter file path of Inventory of Stock CSV: " );
		Scanner in = new Scanner(System.in);
		res[0]=in.nextLine();
		Application app = new Application();
		app.startMarket(res);
	}
	
	private void startMarket(String[] args) throws Exception  {
		StringBuilder str = new StringBuilder();
		if(args.length == 0) {
			System.exit(0);
		}
		for(String arg:args) {	
			if(!str.isEmpty())
				str.append(" ");
			str.append(arg);
		}
		Stock stockctr = new Stock(str.toString());
		stockctr.create();
		while(true){
			String path = handleOrder();
			if(path.equals(""))
				break;
			result(path);
		}
		System.out.println("Bye !");
	}
	
	@SuppressWarnings("resource")
	private String handleOrder(){
		System.out.println("Please enter the csv file path of order or press enter to exit: ");
		String res = null;
		try {
			Scanner in = new Scanner(System.in);
			res=in.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private void result(String path) throws Exception{
		Order orderctr = new Order(path);
		if(orderctr.orderOrigin()){
			if(orderctr.orderVerify()) {
				orderctr.cost();
				orderctr.settle();
				System.out.println("Total Amount $" + orderctr.getValue());
			} else {
				System.out.println("Please check the generated error file.");
				orderctr.output();
			}
		} else {
			System.out.println("Please enter the correct order file or file path");
		}
	}
}
