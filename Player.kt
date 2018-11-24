public class Player(private val hand:MutableList<Cards>){
    fun getHand():MutableList<Cards> = hand
    private var doublesInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var firstCardOfDoublesInHand:MutableList<Cards> = mutableListOf()
    private var triplesInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var firstCardOfTriplesInHand:MutableList<Cards> = mutableListOf()
    private var quadsInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var firstCardOfQuadsInHand:MutableList<Cards> = mutableListOf()
    private var straightsInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var fullHouseInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var flushInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var straightFlushInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var royalFlush:MutableList<MutableList<Cards>> = mutableListOf()

    fun initializePlayer(){
        clearVar()
        initializeIdenticals()
        fiveCardCombos()
    }
    fun clearVar(){
        doublesInHand.clear()
        firstCardOfDoublesInHand.clear()
        triplesInHand.clear()
        firstCardOfTriplesInHand.clear()
        quadsInHand.clear()
        firstCardOfTriplesInHand.clear()
        fullHouseInHand.clear()
        straightsInHand.clear()
        flushInHand.clear()
        straightFlushInHand.clear()
        royalFlush.clear()
    }
    private fun fiveCardCombos(){
        initialStraights(hand,straightsInHand)
        initialFullHouse()
        initializeFlush()
        initializeStraightFlush()
        initializeRoyalFlush()
    }
    private fun initialStraights(hand: MutableList<Cards>,straightsInHand:MutableList<MutableList<Cards>>,royal:Boolean=false){
        var straightHand:MutableList<Cards> = mutableListOf()
        var i:Int=0
        straightHand.add(hand[i])
        while(i<hand.size-1){
            i++
            if(straightHand.last().getPerceivedValue()==hand[i].getPerceivedValue()){
                continue
            }else if((straightHand.last().getPerceivedValue()+1)==hand[i].getPerceivedValue()){
                straightHand.add(hand[i])
            }else{
                addStraightIntoList(straightHand,straightsInHand,royal)
                straightHand.clear()
                straightHand.add(hand[i])
            }
        }
        addStraightIntoList(straightHand,straightsInHand,royal)
    }
    private fun addStraightIntoList(straightHand:MutableList<Cards>, straightsInHand: MutableList<MutableList<Cards>>, royal: Boolean=false){
        if(straightHand.size>=5){
            if(royal){
                if(straightHand.last().getCardName().equals("2")){
                    var royal=straightHand.toMutableList()
                    while(royal.size>5){
                        royal.removeAt(0)
                    }
                    straightsInHand.add(royal)
                }
            }else{
                straightsInHand.add(straightHand.toMutableList())
            }
        }
    }
    private fun getConsecutiveNumber(index:Int,hand: MutableList<Cards>):Int{
        val num=hand[index].getPerceivedValue()
        var count=index+1
        var proceedingIndex:Int=-1
        while(count<hand.size){
            if((hand[num].getPerceivedValue()+1)==hand[count].getPerceivedValue()){
                proceedingIndex=count
            }else if((hand[num].getPerceivedValue())==hand[count].getPerceivedValue()){
                count++
            }else{
                break
            }
        }
        return proceedingIndex
    }
    private fun initialFullHouse(){
        if(doublesInHand.size>0 && triplesInHand.size>0){
            for(i in triplesInHand){
                for(j in doublesInHand){
                    if(i[0].getPerceivedValue()!=j[0].getPerceivedValue()){
                        var tempHand:MutableList<Cards> = i.toMutableList()
                        tempHand.addAll(j)
                        fullHouseInHand.add(tempHand)
                    }
                }
            }
        }
    }
    private fun initializeFlush(){
        for(suit in Suits.values()){
            flushInHand.add(hand.filter { it.getSuit() == suit }.toMutableList())
        }
    }
    private fun initializeStraightFlush(){
        for(flush in flushInHand){
            if(flush.size>=5){
                initialStraights(flush,straightFlushInHand)
            }
        }
    }
    private fun initializeRoyalFlush(){
        for(flush in flushInHand){
            if(flush.size>=5){
                initialStraights(flush,royalFlush,true)
            }
        }
    }
    private fun initializeIdenticals(){
        for(i in 0..hand.size-2){
            //pairs
            if(hand[i].getPerceivedValue()==hand[i+1].getPerceivedValue())
            {
                doublesInHand.add(mutableListOf(hand[i],hand[i+1]))
                firstCardOfDoublesInHand.add(hand[i])
                //triples
                if(i+2<hand.size && hand[i].getPerceivedValue()==hand[i+2].getPerceivedValue())
                {
                    triplesInHand.add(mutableListOf(hand[i],hand[i+1],hand[i+2]))
                    firstCardOfTriplesInHand.add(hand[i])
                    //quads
                    if(i+3<hand.size && hand[i].getPerceivedValue()==hand[i+3].getPerceivedValue())
                    {
                        quadsInHand.add(mutableListOf(hand[i],hand[i+1],hand[i+2]))
                        firstCardOfQuadsInHand.add(hand[i])
                    }
                }
            }
        }
    }
    fun getFirstOfDoubles():MutableList<Cards> = firstCardOfDoublesInHand
    fun getFirstOfTriples():MutableList<Cards> = firstCardOfTriplesInHand
    fun getFirstOfQuads():MutableList<Cards> = firstCardOfTriplesInHand
    fun getFullHouse():MutableList<MutableList<Cards>> = fullHouseInHand
    fun getStraights():MutableList<MutableList<Cards>> = straightsInHand
    fun getFlush():MutableList<MutableList<Cards>> = flushInHand
    fun getStraightFlush():MutableList<MutableList<Cards>> = straightFlushInHand
    fun getRoyalFlush():MutableList<MutableList<Cards>>  = royalFlush
    fun printDoublesInHand(){
        for(i in doublesInHand){
            Util.printCardsInLine(i)
        }
    }
    fun printTriplesInHand(){
        for(i in triplesInHand){
            Util.printCardsInLine(i)
        }
    }


}