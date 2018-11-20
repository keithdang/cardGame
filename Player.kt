public class Player(private val hand:MutableList<Cards>){
    fun getHand():MutableList<Cards> = hand
    private var doublesInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var firstCardOfDoublesInHand:MutableList<Cards> = mutableListOf()
    private var triplesInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var firstCardOfTriplesInHand:MutableList<Cards> = mutableListOf()

    private fun DoublesInHand(){
        var count=0;
        for(i in 0..hand.size-2){
            if(hand[i].getPerceivedValue()==hand[i+1].getPerceivedValue()){
                count++
                doublesInHand.add(mutableListOf(hand[i],hand[i+1]))
                firstCardOfDoublesInHand.add(hand[i])
            }
        }
//        println("Pairs found: $count")
    }
    fun printDoublesInHand(){
        for(i in doublesInHand){
            Util.printCardsInLine(i)
        }
    }
    fun getFirstOfDoubles():MutableList<Cards> = firstCardOfDoublesInHand

    private fun TriplesInHand(){
        var count=0;
        for(i in 0..hand.size-3){
            var num=hand[i].getPerceivedValue()
            if(num==hand[i+1].getPerceivedValue() && num==hand[i+1].getPerceivedValue()){
                count++
                triplesInHand.add(mutableListOf(hand[i],hand[i+1],hand[i+2]))
                firstCardOfTriplesInHand.add(hand[i])
            }
        }
    }
    fun printTriplesInHand(){
        for(i in triplesInHand){
            Util.printCardsInLine(i)
        }
    }
    fun getFirstOfTriples():MutableList<Cards> = firstCardOfTriplesInHand

    fun initializePlayer(){
        doublesInHand.clear()
        firstCardOfDoublesInHand.clear()
        DoublesInHand()
    }
}