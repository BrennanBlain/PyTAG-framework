package games.coltexpress;

import core.AbstractGameState;
import core.ForwardModel;
import core.actions.IAction;
import core.components.PartialObservableDeck;
import games.coltexpress.cards.ColtExpressCard;
import games.coltexpress.components.Train;
import games.coltexpress.ColtExpressParameters.CharacterType;

import java.util.*;

import static utilities.CoreConstants.PARTIAL_OBSERVABLE;

public class ColtExpressForwardModel extends ForwardModel {

    @Override
    public void setup(AbstractGameState firstState) {
        ColtExpressGameState cegs = (ColtExpressGameState) firstState;
        ColtExpressParameters cep = (ColtExpressParameters) firstState.getGameParameters();

        cegs.train = new Train(cegs.getNPlayers());
        cegs.playerCharacters = new HashMap<>();

        HashSet<CharacterType> characters = new HashSet<>();
        Collections.addAll(characters, CharacterType.values());

        cegs.playerDecks = new ArrayList<>(cegs.getNPlayers());
        for (int playerIndex = 0; playerIndex < cegs.getNPlayers(); playerIndex++) {
            cegs.playerCharacters.put(playerIndex, pickRandomCharacterType(characters));

            boolean[] visibility = new boolean[cegs.getNPlayers()];
            Arrays.fill(visibility, !PARTIAL_OBSERVABLE);
            visibility[playerIndex] = true;

            PartialObservableDeck<ColtExpressCard> playerCards =
                    new PartialObservableDeck<>("playerCards" + playerIndex, visibility);
            for (ColtExpressCard.CardType type : cep.cardCounts.keySet()){
                for (int j = 0; j < cep.cardCounts.get(type); j++) {
                    playerCards.add(new ColtExpressCard(type));
                }
            }
            cegs.playerDecks.add(playerCards);
        }
    }

    @Override
    public void next(AbstractGameState gameState, IAction action) {
        if (action != null) {
            System.out.println(action.toString());
            action.execute(gameState);
        }
    }

    public CharacterType pickRandomCharacterType(HashSet<CharacterType> characters){
        int size = characters.size();
        int item = rnd.nextInt(size);
        int i = 0;
        for(CharacterType obj : characters) {
            if (i == item){
                characters.remove(obj);
                return obj;
            }
            i++;
        }
        return null;
    }

    public void pickCharacterType(CharacterType characterType, HashSet<CharacterType> characters){
        characters.remove(characterType);
    }

}
