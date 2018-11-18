public class President{
    val Players:MutableList<Player> = mutableListOf()
    val util=Util()
    val activeCards:MutableList<Cards> = mutableListOf()
    val presValue:String.()->Int={
        presConvert(this)
    }
    var turnCount=1
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
        var gameContinue=true
        while(gameContinue){
            for(i in 1..Players.size){
                playerTurn(i)
                if(Players.get(i-1).getHand().size==0){
                    gameContinue=false
                    println("Player ${i} wins")
                    break
                }
            }
        }
    }
    private fun playerTurn(num:Int){
        val hand=Players.get(num-1).getHand()
        print("Player: ${num}\n")
        util.printCardsInLine(hand)
        val foundIndex:Int

        if(turnCount==4){
            activeCards.clear()
        }
        foundIndex=getIndex(num,hand)

        var goAgain=false
        if(foundIndex!=-1){
            hand.get(foundIndex).printCardLn()
            if(activeCards.size>0 && activeCards.get(0).getPerceivedValue()==hand.get(foundIndex).getPerceivedValue()){
                println("BURN")
                goAgain=true
                activeCards.clear()
            }else{
                activeCards.clear()
                activeCards.add(hand.get(foundIndex))
            }
            hand.removeAt(foundIndex)
            if(goAgain){
                playerTurn(num)
            }
            turnCount=1
        }else{
            println("Player ${num} couldn't go")
            turnCount++
        }
    }
    private fun getIndex(playerNum:Int,hand:MutableList<Cards>):Int{
        val foundIndex:Int
        if(playerNum==1){
            foundIndex=inputCard(hand)
        }else{
            if(activeCards.size==0){
                foundIndex=0
            }else{
                val searchVal = activeCards.get(0).getPerceivedValue()
                foundIndex=util.searchCard(hand,0,hand.size-1,searchVal)
            }
        }
        return foundIndex
    }
    private fun inputCard(hand:MutableList<Cards>):Int{
        print("Select Card (s for skip):\n")
        val input=readLine()!!
        when{
            input.equals("s")->return -1
            input.toIntOrNull()!=null && input.toInt() in 1..hand.size->return input.toInt() -1
            else->return inputCard(hand)
        }
    }
    private fun presConvert(card:String):Int{
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