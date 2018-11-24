public class President{
    enum class fiveCardCombos{
        NONE,STRAIGHT,FULLHOUSE,FLUSH
    }
    private val Players:MutableList<Player> = mutableListOf()
    private val activeCards:MutableList<Cards> = mutableListOf()
    private var activeFiveCardState:fiveCardCombos=fiveCardCombos.NONE
    private val presValue:String.()->Int={
        presConvert(this)
    }
    private var turnCount=1
    init{
        val deck=Deck(presValue)
//        deck.shuffleDeck()
//        deck.sortDeckFullHouseForEachPlayer()
//        deck.sortDifferentStraightsForEachPlayer()
        deck.sortFlushes()
        for(i in 1..4){
            Players.add(Player(deck.getDeck().subList((i-1)*13,i*13).toMutableList()))
            Util.sortHand(Players[i-1].getHand())
            Util.printCardsInLine(Players[i-1].getHand())
            Players[i-1].initializePlayer()
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
        print("Player: $num\n")
        val hand=Players[num-1].getHand()
        Util.printCardsInLine(hand)
        var goAgain=false
        if(turnCount==4) activeCards.clear()
        val indices:List<Int> = getIndex(num)

        if(indices.isNotEmpty() && indices[0]!=-1){
            Util.printIndicesOfHand(indices,hand)
            if(activeCards.size>0 && activeCards[0].getPerceivedValue()==hand[indices[0]].getPerceivedValue()){
                println("BURN")
                goAgain=true
                activeCards.clear()
            }else{
                addEntriesIntoActiveCards(indices,hand)
            }
            Util.removeAllIndicesFromHand(indices,hand)
            if(goAgain) playerTurn(num)
            turnCount=1
        }else{
            println("Player ${num} couldn't go")
            turnCount++
        }
    }
    private fun getIndex(playerNum:Int):MutableList<Int>{
        var indices:MutableList<Int> = mutableListOf()
        val player=Players[playerNum-1]
        player.initializePlayer()
        val hand:MutableList<Cards> =  player.getHand()
        if(activeCards.size==0 || hand[hand.size-1].getPerceivedValue() >= activeCards[0].getPerceivedValue()){
            if(playerNum==1){
                indices=inputCard(hand)
            }else{
                when(activeCards.size){
                    0->indices.add(0)
                    1->{
                        val searchVal = activeCards[0].getPerceivedValue()
                        indices.add(Util.searchCard(hand,0,hand.size-1,searchVal))
                    }
                    2->handleIdenticalCards(player,hand,indices,2)
                    3->handleIdenticalCards(player,hand,indices,3)
                    4->handleIdenticalCards(player,hand,indices,4)
                    5->computerDetectFiveCardCombos(player,hand,indices)
                }
            }
        }
        return indices
    }
    private fun inputCard(hand:MutableList<Cards>):MutableList<Int>{
        print("Select Card (s for skip):\n")
        var numList:MutableList<Int> = mutableListOf()
        val input=readLine()!!
        val inputList=input.split(",").toMutableList()
        when(inputList.size){
            1->{
                when{
                    inputList[0].equals("s")->numList.add(-1)
                    activeCards.size>1->numList=inputCard(hand)
                    inputList[0].toIntOrNull() != null-> {
                        when {
                            checkValidEntry(input,hand)->numList.add(input.toInt()-1)
                            else->numList=inputCard(hand)
                        }
                    }
                    else->numList=inputCard(hand)
                }
            }
            2,3,4->{
                when{
                    inputList.all { checkValidEntry(it,hand) } && checkIfIndicesHaveSameCardPerceivedValue(inputList,hand)->numList=inputList.map{it.toInt()-1}.toMutableList()
                    else->numList=inputCard(hand)
                }
            }
            5->{
                var numIndices=inputList.map { it.toInt()-1 }.toMutableList()
                numIndices.sortBy { it }
                when{
                    handleFiveCardCombinations(hand,numIndices)->numList=inputList.map{it.toInt()-1}.toMutableList()
                    else->numList=inputCard(hand)
                }
            }
            else->numList=inputCard(hand)
        }
        //if user is able to select a new card combination thats not length 5 and it wasn't a skip
        if(numList.size<5  && numList[0]!=-1){
            activeFiveCardState=fiveCardCombos.NONE
        }
        return numList
    }
    private fun checkValidEntry(input:String,hand: MutableList<Cards>):Boolean = input.toInt() in 1..hand.size && (activeCards.size == 0 || hand[input.toInt() - 1].getPerceivedValue() >= activeCards[0].getPerceivedValue())
    private fun checkIfIndicesHaveSameCardPerceivedValue(indices: MutableList<String>, hand: MutableList<Cards>):Boolean{
        val cardVal=hand[indices[0].toInt()-1].getPerceivedValue()
        for(i in indices){
            if(hand[i.toInt()-1].getPerceivedValue() != cardVal){
                return false
            }
        }
        return true
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
    private fun addEntriesIntoActiveCards(indices:List<Int>, hand: MutableList<Cards>){
        activeCards.clear()
        for(i in indices){
            activeCards.add(hand[i])
        }
    }
    private fun computerDetectFiveCardCombos(player: Player,hand: MutableList<Cards>,indices: MutableList<Int>){
        when(activeFiveCardState){
            fiveCardCombos.FULLHOUSE->handleFullHouse(player,hand,indices)
            fiveCardCombos.STRAIGHT->handleStraights(player,hand,indices)
            fiveCardCombos.FLUSH->handleFlush(player,hand,indices)
            fiveCardCombos.NONE->println("ERROR: NONE FOUND")
        }
    }
    private fun handleFullHouse(player: Player,hand: MutableList<Cards>,indices: MutableList<Int>){
        if(player.getFullHouse().size>0){
            for(fullHouse in player.getFullHouse()){
                var acIndices=PokerHands.tripleAndDoubleInFullHouse(activeCards)
                if(acIndices.first!=-1 && fullHouse[0].getPerceivedValue() > activeCards[acIndices.first].getPerceivedValue()){
                    var tripleEntryOfHand=Util.searchCard(hand,0,hand.size-1,fullHouse[0].getPerceivedValue())
                    if(tripleEntryOfHand!=-1){
                        var doubleEntryOfHand=Util.searchCard(hand,0,hand.size-1,fullHouse[3].getPerceivedValue())
                        if(doubleEntryOfHand!=-1){
                            indices.addAll(mutableListOf(tripleEntryOfHand,tripleEntryOfHand+1,tripleEntryOfHand+2,doubleEntryOfHand,doubleEntryOfHand+1))
                            return
                        }
                    }
                }
            }
        }
    }
    private fun handleStraights(player: Player,hand: MutableList<Cards>,indices: MutableList<Int>){
        if(player.getStraights().size>0){
            for(straight in player.getStraights()){
                if(straight.last().getPerceivedValue()>=activeCards.last().getPerceivedValue()){
                    var index=0
                    while((straight.size-(index+1))>=5){
                        if(straight[index].getPerceivedValue()<activeCards[0].getPerceivedValue()){
                            index++
                        }else{
                            break
                        }
                    }
                    var entry:Int=Util.searchCard(hand,0,hand.size-1,straight[index].getPerceivedValue())
                    var straightHand:MutableList<Int> = mutableListOf()
                    var i:Int=entry
                    straightHand.add(i)
                    while(i<hand.size-2 && straightHand.size<5){
                        i++
                        if(hand[straightHand.last()].getPerceivedValue()==hand[i].getPerceivedValue()){
                            continue
                        }else if((hand[straightHand.last()].getPerceivedValue()+1)==hand[i].getPerceivedValue()){
                            straightHand.add(i)
                        }else{
                            break
                        }

                    }
                    if(straightHand.size==5){
                        indices.addAll(straightHand)
                        return
                    }
                }
            }
        }
    }
    private fun handleFlush(player: Player,hand: MutableList<Cards>,indices: MutableList<Int>){
        var activeSuit=activeCards.last().getSuit()
        var flush:MutableList<Cards>? = player.getFlush()[activeSuit]
        var flushLastVal = if (flush!!.size>=5) flush.last().getPerceivedValue() else return
        if(activeCards.last().getPerceivedValue() < flushLastVal){
            var index=flush.lastIndex
            while(index > 4){
                if(activeCards.last().getPerceivedValue() < flush[index].getPerceivedValue()){
                    index--
                }
                break
            }
            var tempList:MutableList<Int> = mutableListOf()
            for(i in (index-4)..index){
                tempList.add(Util.searchExactCard(hand,0,hand.size-1,flush[i]))
            }
            indices.addAll(tempList)
            println("FLUSH")
        }
    }


    private fun handleFiveCardCombinations(hand: MutableList<Cards>,indices: MutableList<Int>):Boolean{
        if(PokerHands.checkIfStraight(hand,indices)){
            activeFiveCardState=fiveCardCombos.STRAIGHT
            return true
        }
        if(PokerHands.isFullHouse(hand,indices)){
            activeFiveCardState=fiveCardCombos.FULLHOUSE
            return true
        }
        if(PokerHands.isFlush(hand,indices)){
            activeFiveCardState=fiveCardCombos.FLUSH
            return true
        }
        return false
    }
    private fun handleIdenticalCards(player: Player,hand: MutableList<Cards>,indices: MutableList<Int>,numIdentical:Int){
        var searchVal = activeCards[0].getPerceivedValue()
        var firstEntriesOfIdenticalGroups:MutableList<Cards> = mutableListOf()
        when(numIdentical){
            2->firstEntriesOfIdenticalGroups=player.getFirstOfDoubles()
            3->firstEntriesOfIdenticalGroups=player.getFirstOfTriples()
            4->firstEntriesOfIdenticalGroups=player.getFirstOfQuads()
        }
        val firstEntry=Util.searchCard(firstEntriesOfIdenticalGroups,0,firstEntriesOfIdenticalGroups.size-1,searchVal)
        if(firstEntry!=-1){
            searchVal =  firstEntriesOfIdenticalGroups[firstEntry].getPerceivedValue()
            var indexFound = Util.searchCard(hand,0,hand.size-1,searchVal)
            for(i in 0..(numIdentical-1)){
                indices.add(indexFound+i)
            }
        }
    }

}