package net.my4x.card.service;

import net.my4x.card.model.Card;
import net.my4x.card.model.Pack;
import net.my4x.card.model.PackType;

public interface CardService {
	Card create();

	Pack generatePack(final PackType type);
}
