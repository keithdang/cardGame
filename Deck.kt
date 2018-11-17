public class Deck{
    val cards:MutableList<Cards> = mutableListOf()
    init{
        for(suit in Suits.values()){
            for(i in 1..13){
                cards.add(Cards(i,suit))
            }
        }
    }
    fun printDeck(){
        for(card in cards){
            card.printCard()
        }
    }
}