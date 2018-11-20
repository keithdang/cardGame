object Util{
    fun printCards(cards:MutableList<Cards>){
        for(card in cards){
            card.printCardLn()
        }
    }
    fun printCardsInLine(cards:MutableList<Cards>){
        var i=1
        for(card in cards){
            card.printCardTab(i)
            i++
        }
        println()
    }
    fun printIndicesOfHand(indices:List<Int>,cards: MutableList<Cards>){
        for(i in indices){
            cards[i].printCardTabNolabel()
        }
        println()
    }
    fun printCardsInLineLabel(cards:MutableList<Cards>){
        var i=1
        for(card in cards){
            card.printCardLabel(i)
            i++
        }
        println()
    }
    fun removeAllIndicesFromHand(indices: List<Int>, cards: MutableList<Cards>){
        var count=0
        for(i in indices){
            cards.removeAt(i-count)
            count++
        }
    }
    fun sortHand(cards: MutableList<Cards>){
        cards.sortBy { it.getPerceivedValue() }
    }
    fun genCardName(num:Int):String{
        val cardName:String
        when(num){
            11->cardName="Jack"
            12->cardName="Queen"
            13->cardName="King"
            1->cardName="Ace"
            else->cardName=num.toString()
        }
        return cardName
    }
    fun searchCard(hand:MutableList<Cards>,left:Int,right:Int,x:Int):Int{
        if(left==right){
            if(hand[left].getPerceivedValue()<x)return -1
            return originalOrBetterValue(hand,left,x)
        }else if(right-left==1){
            when{
                hand[left].getPerceivedValue()>=x -> return originalOrBetterValue(hand,left,x)
                hand[right].getPerceivedValue()>=x -> return right
                else->return -1
            }
        } else if(right>0 && left<right){
            val mid=(right+left)/2
            val midVal=hand[mid].getPerceivedValue()
            if(midVal==x){
                return originalOrBetterValue(hand,mid,x)
            }else if(x<midVal){
                return searchCard(hand,left,mid,x)
            }else{
                return searchCard(hand,mid,right,x)
            }
        }
        return -1
    }
    fun originalOrBetterValue(hand: MutableList<Cards>,originalIndex:Int, num:Int):Int{
        if(originalIndex!=0 && hand[originalIndex-1].getPerceivedValue()>=num){
            return originalIndex-1
        }
        return  originalIndex
    }
}