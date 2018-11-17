enum class Suits{
    SPADES,CLUBS,DIAMONDS,HEARTS
}
public class Cards(private val num:Int, private val suit:Suits,val styleValue:String.()->Int){
    private val util=Util()
    private val perceivedValue=util.genCardName(num).styleValue()
    fun printCardLn(){
        println("${num} of ${suit}")
    }
    fun printCardTab(){
        print("${num} of ${suit},\t")
    }
    fun getPerceivedValue():Int=perceivedValue
}