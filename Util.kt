public class Util{
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
    fun printCardsInLineLabel(cards:MutableList<Cards>){
        var i=1
        for(card in cards){
            card.printCardLabel(i)
            i++
        }
        println()
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
            if(hand.get(left).getPerceivedValue()<x)return -1
            return left
        }else if(right-left==1){
            if(hand.get(left).getPerceivedValue()>=x)return left
            else if(hand.get(right).getPerceivedValue()>=x)return right
            else return -1
        } else if(right>0 && left<right){
            val mid=(right+left)/2
            val midVal=hand.get(mid).getPerceivedValue()
            if(midVal==x){
                return mid
            }else if(x<midVal){
                return searchCard(hand,left,mid,x)
            }else{
                return searchCard(hand,mid,right,x)
            }
        }
        return -1
    }
}