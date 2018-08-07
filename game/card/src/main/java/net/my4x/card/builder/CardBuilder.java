package net.my4x.card.builder;

import net.my4x.card.model.CardType;

public class CardBuilder {
	public CardBuilder getInstance(final CardType type) {
		return new CardBuilder();
	}
}
