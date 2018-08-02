package net.my4x.card.model.story;

import net.my4x.card.model.Card;
import net.my4x.card.model.CardType;
import net.my4x.common.model.Element;

public class StoryCard extends Card {

	protected StoryCardModel model;

	public StoryCard(final Element element) {
		super(element);
	}

	@Override
	public CardType getType() {
		return CardType.STORY;
	}

}
