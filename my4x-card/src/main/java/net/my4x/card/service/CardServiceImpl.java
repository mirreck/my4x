package net.my4x.card.service;

import net.my4x.card.model.Card;
import net.my4x.card.model.CardModel;
import net.my4x.card.model.Pack;
import net.my4x.card.model.PackType;
import net.my4x.card.model.character.CharacterCard;
import net.my4x.card.repository.CardModelRepository;
import net.my4x.common.model.Element;
import net.my4x.common.model.Job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private CardModelRepository cardModelRepository;

	@Override
	public Card create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pack generatePack(final PackType type) {
		cardModelRepository.count();

		final Pack pack = new Pack();
		for (final CardModel cardModel : cardModelRepository.findAll()) {
			System.out.println("CM = " + cardModel);
			pack.addCard(new CharacterCard(Element.AIR, Job.CHEF));
		}
		return pack;
	}

}
