public class President{
    val Players:MutableList<Player> = mutableListOf()
    val util=Util()
    val activeCards:MutableList<Cards> = mutableListOf()
    val presValue:String.()->Int={
        presConvert(this)
    }
    init{
        val deck=Deck(presValue)
        deck.shuffleDeck()
        for(i in 1..4){
            Players.add(Player(deck.getDeck().subList((i-1)*13,i*13).toMutableList()))
            util.sortHand(Players.get(i-1).getHand())
            util.printCardsInLine(Players.get(i-1).getHand())
        }
    }
    fun start(){
        println("President")
        for(i in 1..Players.size){
            playerTurn(i)
        }
    }
    fun playerTurn(num:Int){
        val hand=Players.get(num-1).getHand()
        val searchIndex:Int
        if(num==1){
            searchIndex= readLine()!!.toInt()
        }else{
            searchIndex = activeCards.get(0).getPerceivedValue()
        }
        val foundIndex=util.searchCard(hand,0,hand.size,searchIndex)
        activeCards.clear()
        activeCards.add(hand.get(foundIndex))
        hand.removeAt(foundIndex)
        util.printCardsInLine(activeCards)
        util.printCardsInLine(hand)
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