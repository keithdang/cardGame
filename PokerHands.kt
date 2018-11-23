object PokerHands{
    fun checkIfStraight(hand: MutableList<Cards>,indices: MutableList<String>):Boolean{
        for(i in 0..(indices.size-2)){
            var firstIndex=indices[i].toInt()-1
            var secondIndex=indices[i+1].toInt()-1
            if(hand[firstIndex].getPerceivedValue()!=(hand[secondIndex].getPerceivedValue()-1)){
                return false
            }
        }
        println("Hello World")
        return true
    }
    fun isFullHouse(hand: MutableList<Cards>,indices: MutableList<String>):Boolean{
        var tempHand = Util.sortedSelectedList(hand,Util.formatIndices(indices))
        if(tempHand[0].getPerceivedValue()==tempHand[1].getPerceivedValue()){
            if(tempHand[0].getPerceivedValue()==tempHand[2].getPerceivedValue()){
                if(tempHand[3].getPerceivedValue()==tempHand[4].getPerceivedValue()){
                    return true
                }
            }
            if(tempHand[2].getPerceivedValue()==tempHand[3].getPerceivedValue()){
                if(tempHand[2].getPerceivedValue()==tempHand[4].getPerceivedValue()){
                    return true
                }
            }
        }
        return false
    }
    fun isFlush(hand: MutableList<Cards>,indices: MutableList<Int>):Boolean{
        var currentSuit:Suits=hand[indices[0]].getSuit()
        for(index in indices){
            if(hand[index].getSuit()!=currentSuit){
                return false
            }
        }
        return true
    }
    fun tripleAndDoubleInFullHouse(fullHouse:MutableList<Cards>):Pair<Int,Int>{
        fullHouse.sortBy { it.getPerceivedValue() }
        //first 3 are triples
        if(fullHouse[0].getPerceivedValue()==fullHouse[1].getPerceivedValue() && fullHouse[0].getPerceivedValue()==fullHouse[2].getPerceivedValue())
        {
            return Pair(0,3)
        }
        //last 3 are triples
        else if(fullHouse[2].getPerceivedValue()==fullHouse[3].getPerceivedValue() && fullHouse[2].getPerceivedValue()==fullHouse[4].getPerceivedValue())
        {
            return Pair(2,0)
        }
        else return Pair(-1,-1)
    }
}