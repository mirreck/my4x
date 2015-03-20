package net.my4x.card.model.story;

import java.util.List;

import net.my4x.card.model.CardModel;

public class StoryCardModel extends CardModel {
	private List<StoryCondition> conditions;
	private List<StoryAction> actions;

	public List<StoryCondition> getConditions() {
		return conditions;
	}

	public void setConditions(final List<StoryCondition> conditions) {
		this.conditions = conditions;
	}

	public List<StoryAction> getActions() {
		return actions;
	}

	public void setActions(final List<StoryAction> actions) {
		this.actions = actions;
	}
}
