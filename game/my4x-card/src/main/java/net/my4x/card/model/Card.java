package net.my4x.card.model;

import net.my4x.common.model.Element;

public abstract class Card {
	protected final Element element;
	protected String name;

	public Card(final Element element) {
		this.element = element;
	}

	public Element getElement() {
		return element;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public abstract CardType getType();

}
