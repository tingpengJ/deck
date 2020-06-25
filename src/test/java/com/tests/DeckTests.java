package com.tests;


import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import java.util.Random;
import com.objects.Deck;

public class DeckTests {
	
	Deck testDeck;

	public JsonPath drawRequest(int cards_draw_count) {
		return given().
					pathParam("deck_id",  testDeck.deck_id).
					queryParam("count", cards_draw_count).
					log().all().
				when().
					get("https://deckofcardsapi.com/api/deck/{deck_id}/draw/").
				then().
					log().all().
					statusCode(200).
					extract().body().jsonPath();
	}

	@BeforeTest
	public void beforeAll() {
		JsonPath jsonPath = given().
					log().all().
				when().
					get("https://deckofcardsapi.com/api/deck/new").
				then().
					log().all().
					statusCode(200).
					extract().body().jsonPath();
		testDeck = jsonPath.getObject("$", Deck.class);		
	}

	@Test(priority=1)
	public void newDeck() {
		testDeck.checkNewDeck(52);
		Assert.assertEquals(testDeck.success, true);
	}
    @Test(priority=2)
	public void drawOneOrMoreFromDeck() {
		int cards_draw_count = new Random().nextInt(testDeck.remaining) + 1;
		JsonPath jsonPath = drawRequest(cards_draw_count);
		testDeck = jsonPath.getObject("$", Deck.class);
		testDeck.checkCards();
		testDeck.checkRemainingCount(testDeck.remaining);
		Assert.assertEquals(testDeck.success, true);
	}
    
    @Test(priority=3)
	public void drawTooManyCardsFromDeck() {
		int cards_draw_count = testDeck.remaining + 1;
		JsonPath jsonPath = drawRequest(cards_draw_count);
		testDeck = jsonPath.getObject("$", Deck.class);
		testDeck.checkCards();
		testDeck.checkRemainingCount(testDeck.remaining);
		Assert.assertEquals(testDeck.success, false);
		Assert.assertEquals(jsonPath.getString("error"), String.format("Not enough cards remaining to draw %d additional", cards_draw_count));
	}
    
	@Test(priority=4)
	public void newDeckWithJokers() {
		JsonPath jsonPath = given().
					queryParam("jokers_enabled", true).
					log().all().
				when().
					get("https://deckofcardsapi.com/api/deck/new").
				then().
					log().all().
					statusCode(200).
					extract().body().jsonPath();
		Deck testDeckWithJokers = jsonPath.getObject("$", Deck.class);
		testDeckWithJokers.checkNewDeck(54);
	}
}
