public class Util{
    fun printCards(cards:MutableList<Cards>){
        for(card in cards){
            card.printCardLn()
        }
    }
    fun printCardsInLine(cards:MutableList<Cards>){
        for(card in cards){
            card.printCardTab()
        }
        println()
    }
    fun sortHand(cards: MutableList<Cards>){
        cards.sortBy { it.getPerceivedValue() }
    }
    fun genCardName(num:Int):String{
        val cardName:String
        when(num){
            11->cardName="Jack"
            12->cardName="Queen"
            13->cardName="King"
            1->cardName="Ace"
            else->cardName=num.toString()
        }
        return cardName
    }
}