public class Player(private val hand:MutableList<Cards>){
    fun getHand():MutableList<Cards> = hand
    val util=Util()
    private var doublesInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var firstCardOfDoublesInHand:MutableList<Cards> = mutableListOf()
    private fun DoublesInHand(){
        var count=0;
        for(i in 0..hand.size-2){
            if(hand[i].getPerceivedValue()==hand[i+1].getPerceivedValue()){
                count++
                doublesInHand.add(mutableListOf(hand[i],hand[i+1]))
                firstCardOfDoublesInHand.add(hand[i])
            }
        }
        println("Pairs found: $count")
    }
    fun printDoublesInHand(){
        for(i in doublesInHand){
            util.printCardsInLine(i)
        }
    }
    fun getFirstOfDoubles():MutableList<Cards> = firstCardOfDoublesInHand
    fun initializePlayer(){
        doublesInHand.clear()
        firstCardOfDoublesInHand.clear()
        DoublesInHand()
    }
}