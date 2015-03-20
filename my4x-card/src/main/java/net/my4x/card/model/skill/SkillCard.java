package net.my4x.card.model.skill;

import net.my4x.card.model.Card;
import net.my4x.card.model.CardType;
import net.my4x.common.model.Element;

public class SkillCard extends Card {

	public SkillCard(final Element element) {
		super(element);
	}

	@Override
	public CardType getType() {
		return CardType.SKILL;
	}

}
