package net.my4x.card.model;

import java.util.ArrayList;
import java.util.List;

public class Pack {
	private final List<Card> cards = new ArrayList<Card>();

	public void addCard(final Card card) {
		cards.add(card);
	}

	public List<Card> getCards() {
		return cards;
	}

}
