public class President{
    fun start(){
        println("President")
        val util=Util()
        val deck=Deck(presValue)
        deck.shuffleDeck()
        val playerOne=Player(deck.getDeck().subList(0,13).toMutableList())
        util.printCardsInLine(playerOne.hand)
        util.sortHand(playerOne.hand)
        util.printCardsInLine(playerOne.hand)
    }
    fun presConvert(card:String):Int{
        val newVal:Int
        when(card){
            "Jack"->newVal=11
            "Queen"->newVal=12
            "King"->newVal=13
            "Ace"->newVal=14
            "2"->newVal=15
            else->newVal=card.toInt()
        }
        return newVal
    }
    val presValue:String.()->Int={
        presConvert(this)
    }
}