package application.Model;

public class Card {
	private String creditCardNumber;
	public Card( String creditCardNumber ) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
}
