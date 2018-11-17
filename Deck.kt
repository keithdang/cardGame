public class Deck{
    private val cards:MutableList<Cards> = mutableListOf()
    val util=Util()
    init{
        for(suit in Suits.values()){
            for(i in 1..13){
                cards.add(Cards(i,suit))
            }
        }
    }
    fun shuffleDeck(){
        cards.shuffle()
    }
    fun getDeck():MutableList<Cards> = cards
    fun printDeck(){
        util.printCardsInLine(cards)
    }
}