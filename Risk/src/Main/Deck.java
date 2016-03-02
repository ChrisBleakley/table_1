package Main;

import Game.Country;

import java.util.ArrayList;

public interface Deck {
	public void setCountryList(ArrayList<Country> countrylist);
	public Country getCountryCard();
	public boolean isEmpty();
}
