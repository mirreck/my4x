package net.my4x.card.model;

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
