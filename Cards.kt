enum class Suits{
    SPADES,CLUBS,DIAMONDS,HEARTS
}
public class Cards(private val num:Int, private val suit:Suits,val styleValue:String.()->Int){
    private val util=Util()
    private val cardName=util.genCardName(num)
    private val perceivedValue=cardName.styleValue()

    fun printCardLn(){
        println("${cardName} of ${suit}")
    }
    fun printCardLabel(num:Int){
        print("${num}:${cardName} of ${suit},\t")
    }
    fun printCardTab(num:Int){
        print("${num}:${cardName} of ${suit},\t")
    }
    fun printCardTabNolabel(){
        print("${cardName} of ${suit},\t")
    }
    fun getPerceivedValue():Int=perceivedValue
}