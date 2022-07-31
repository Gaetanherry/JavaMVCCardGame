package fr.utt.sit.lo02.trophy;

import java.util.LinkedList;

import fr.utt.sit.lo02.game.*;
/**
 * <p>L'interface Trophy correspond à la valeur trophée situé sur la carte
 * Un joueur peut remplir les conditions d'un trophée selon son jest
 * Il se verra alors attribuer le trophée correspondant </p>
 * Les classes TrophyBestJest,Trophy HighLow,TrophyJoker et TrophyMajority héritent de Trophy
 *@see TrophyBestJest
 *@see TrophyHighLow
 *@see TrophyJoker
 *@see TrophyMajority
 */
public interface Trophy {
	public Player attribute(LinkedList<Player> l);
}
