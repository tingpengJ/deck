package com.objects;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.testng.Assert;

public class Deck {
	static final ArrayList<String> Suits = new ArrayList<>(Arrays.asList("SPADES", "DIAMONDS", "CLUBS", "HEARTS"));
	static final ArrayList<String> Values = new ArrayList<>(Arrays.asList("ACE", "2", "3", "4", "5", "6", "7", "8", "9", "10", "JACK", "QUEEN", "KING"));

		public boolean success;
		public String deck_id;
		public int remaining;
		public boolean shuffled;
		public ArrayList<HashMap> cards;

		public void checkNewDeck(int num) {
			Assert.assertEquals(success, true);
			Assert.assertEquals(shuffled, false);
			Assert.assertTrue(deck_id.length() > 0);
			Assert.assertEquals(remaining, num);
		}

		public void checkCards() {
			String suit;
			String value;
			String code;
			String image;
			for(HashMap<String, String> card: cards) {
				suit = card.get("suit");
				value = card.get("value");
				Assert.assertTrue(Suits.contains(suit));
				Assert.assertTrue(Values.contains(value));
				code = card.get("code");
				Assert.assertTrue(this.checkCode(code, suit, value));
				image = card.get("image");
				Assert.assertTrue(this.checkImage(code, image));
			}
		}

		public void checkRemainingCount(int remainingCardCount) {
			Assert.assertTrue(remainingCardCount>=0 && remainingCardCount<=52);
		}
		
		public boolean checkImage(String code, String image) {
			String imageBase = "https://deckofcardsapi.com/static/img/";
			String imageFileType = ".png";
			if(code.equals("AD"))
			return image.equals(imageBase+"aceDiamonds"+imageFileType);
			else
			return image.equals(imageBase+code+imageFileType);
		}
		
		public boolean checkCode(String code, String suit, String value) {
			if(value.equals("10")) { value = "0"; }
			if(value.equals("JOKER")) { 
				value = "X"; 
				suit = (suit.equals("BLACK")) ? "1" : suit.equals("RED") ? "2" : suit;
			} 
			return (value.substring(0, 1) + suit.substring(0, 1)).equals(code);
		}
		
		

}
