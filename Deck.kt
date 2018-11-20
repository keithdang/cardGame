public class Deck(styleValue:String.()->Int){
    private val cards:MutableList<Cards> = mutableListOf()
    init{
        for(suit in Suits.values()){
            for(i in 1..13){
                cards.add(Cards(i,suit,styleValue))
            }
        }
    }
    fun shuffleDeck(){
        cards.shuffle()
    }
    fun sortDeck(){
        cards.sortBy { it.getPerceivedValue() }
    }
    fun getDeck():MutableList<Cards> = cards
    fun printDeck(){
        Util.printCardsInLine(cards)
        println()
    }
}