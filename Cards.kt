public class Cards(private val num:Int, private val suit:Suits){
    fun printCard(){
        println("${num} of ${suit}")
    }
}