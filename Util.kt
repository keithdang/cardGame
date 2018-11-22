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
    fun formatIndices(indices: List<String>):List<Int>{
        var list:MutableList<Int> = mutableListOf()
        for(i in indices){
            list.add(i.toInt()-1)
        }
        return list.toList()
    }
    fun sortedSelectedList(hand: MutableList<Cards>,indices: List<Int>):MutableList<Cards>{
        val tempList:MutableList<Cards> = mutableListOf()
        for(i in indices){
            tempList.add(hand[i])
        }
        var copyList = tempList.toMutableList()
        copyList.sortBy { it.getPerceivedValue() }
        return copyList
    }
    fun <T> swap(num1:Int,num2:Int,list: MutableList<T>){
        val temp=list[num1]
        list[num1]=list[num2]
        list[num2]=temp
    }
}