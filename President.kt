public class President{
    val Players:MutableList<Player> = mutableListOf()
    val util=Util()
    val presValue:String.()->Int={
        presConvert(this)
    }
    init{
        val deck=Deck(presValue)
        deck.shuffleDeck()
        for(i in 1..4){
            Players.add(Player(deck.getDeck().subList((i-1)*13,i*13).toMutableList()))
            util.sortHand(Players.get(i-1).hand)
            util.printCardsInLine(Players.get(i-1).hand)
        }
    }
    fun start(){
        println("President")
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
}