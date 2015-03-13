package net.my4x.card.model;

import net.my4x.common.model.Element;

public class ItemCard extends Card {

	protected int quality;

	public ItemCard(final Element element) {
		super(element);
	}

	@Override
	public CardType getType() {
		return CardType.ITEM;
	}

}
