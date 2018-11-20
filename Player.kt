public class Player(private val hand:MutableList<Cards>){
    fun getHand():MutableList<Cards> = hand
    private var doublesInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var firstCardOfDoublesInHand:MutableList<Cards> = mutableListOf()
    private var triplesInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var firstCardOfTriplesInHand:MutableList<Cards> = mutableListOf()
    private var quadsInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var firstCardOfQuadsInHand:MutableList<Cards> = mutableListOf()

    fun initializePlayer(){
        clearVar()
        initializeIdenticals()
    }
    fun clearVar(){
        doublesInHand.clear()
        firstCardOfDoublesInHand.clear()
        triplesInHand.clear()
        firstCardOfTriplesInHand.clear()
        quadsInHand.clear()
        firstCardOfTriplesInHand.clear()
    }
    private fun initializeIdenticals(){
        for(i in 0..hand.size-2){
            if(hand[i].getPerceivedValue()==hand[i+1].getPerceivedValue()){
                doublesInHand.add(mutableListOf(hand[i],hand[i+1]))
                firstCardOfDoublesInHand.add(hand[i])
                if(i+2<hand.size && hand[i].getPerceivedValue()==hand[i+2].getPerceivedValue()) {
                    triplesInHand.add(mutableListOf(hand[i],hand[i+1],hand[i+2]))
                    firstCardOfTriplesInHand.add(hand[i])
                    if(i+3<hand.size && hand[i].getPerceivedValue()==hand[i+3].getPerceivedValue()) {
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