package fr.utt.sit.lo02.game;

/**
 * <p>L'interface JestVisitor permet de visiter les Jest de chaque joueur.
 *Elle est essentielle pour les troph�es � attribuer � chaque joueur
 *et le comptage des points en fin de partie</p>
 * Les classes AcesVisitor,PairVisitor,JokerHeartsVisitor,SuitsVisitor h�ritent de Trophy
 * @see AcesVisitor
 * @see PairVisitor
 * @see JokerHeartsVisitor
 * @see SuitsVisitor
 */
public interface JestVisitor {
	public void visit(Player p);
}
