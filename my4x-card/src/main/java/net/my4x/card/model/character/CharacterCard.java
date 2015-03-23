package net.my4x.card.model.character;

import net.my4x.card.model.Card;
import net.my4x.card.model.CardType;
import net.my4x.common.model.Element;
import net.my4x.common.model.Job;

public class CharacterCard extends Card {

	protected final Job job;

	public CharacterCard(final Element element, final Job job) {
		super(element);
		this.job = job;
	}

	@Override
	public CardType getType() {
		return CardType.CHARACTER;
	}

}