public class President{
    private val Players:MutableList<Player> = mutableListOf()
    private val util=Util()
    private val activeCards:MutableList<Cards> = mutableListOf()
    private val presValue:String.()->Int={
        presConvert(this)
    }
    private var turnCount=1
    init{
        val deck=Deck(presValue)
        deck.shuffleDeck()
        for(i in 1..4){
            Players.add(Player(deck.getDeck().subList((i-1)*13,i*13).toMutableList()))
            util.sortHand(Players[i-1].getHand())
            util.printCardsInLine(Players[i-1].getHand())
        }
    }
    fun start(){
        println("President")
        var gameContinue=true
        while(gameContinue){
            for(i in 1..Players.size){
                playerTurn(i)
                if(Players[i-1].getHand().size==0){
                    gameContinue=false
                    println("Player ${i} wins")
                    break
                }
            }
        }
    }
    private fun playerTurn(num:Int){
        val hand=Players[num-1].getHand()
        print("Player: $num\n")
        util.printCardsInLine(hand)
        val foundIndex:Int

        if(turnCount==4) activeCards.clear()

        foundIndex=getIndex(num,hand)

        var goAgain=false
        if(foundIndex!=-1){
            hand[foundIndex].printCardLn()
            if(activeCards.size>0 && activeCards[0].getPerceivedValue()==hand[foundIndex].getPerceivedValue()){
                println("BURN")
                goAgain=true
                activeCards.clear()
            }else{
                activeCards.clear()
                activeCards.add(hand[foundIndex])
            }
            hand.removeAt(foundIndex)
            if(goAgain) playerTurn(num)

            turnCount=1
        }else{
            println("Player ${num} couldn't go")
            turnCount++
        }
    }
    private fun getIndex(playerNum:Int,hand:MutableList<Cards>):Int{
        val foundIndex:Int
        if(playerNum==1){
            foundIndex=inputCard(hand)[0]
        }else{
            if(activeCards.size==0){
                foundIndex=0
            }else{
                val searchVal = activeCards[0].getPerceivedValue()
                foundIndex=util.searchCard(hand,0,hand.size-1,searchVal)
            }
        }
        return foundIndex
    }
    private fun inputCard(hand:MutableList<Cards>):MutableList<Int>{
        print("Select Card (s for skip):\n")
        var numList:MutableList<Int> = mutableListOf()
        val input=readLine()!!
        val inputList=input.split(",").toMutableList()
        when{
            inputList.size==1->{
                when{
                    inputList[0].equals("s")->numList.add(-1)
                    inputList[0].toIntOrNull() != null-> {
                        when {
                            checkValidEntry(input,hand)->numList.add(input.toInt()-1)
                            else->numList=inputCard(hand)
                        }
                    }
                    else->numList=inputCard(hand)
                }
            }
            inputList.size>=1->{
                when{
                    inputList.all { checkValidEntry(it,hand) }->numList=inputList.map{it.toInt()-1}.toMutableList()
                    else->numList=inputCard(hand)
                }
            }
            else->numList=inputCard(hand)
        }
        return numList
    }
    private fun checkValidEntry(input:String,hand: MutableList<Cards>):Boolean = input.toInt() in 1..hand.size && (activeCards.size == 0 || hand[input.toInt() - 1].getPerceivedValue() >= activeCards[0].getPerceivedValue())

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