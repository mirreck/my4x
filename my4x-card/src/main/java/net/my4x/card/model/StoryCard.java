package net.my4x.card.model;

import net.my4x.common.model.Element;

public class StoryCard extends Card {

	public StoryCard(final Element element) {
		super(element);
	}

	@Override
	public CardType getType() {
		return CardType.STORY;
	}

}
