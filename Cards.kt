public class Cards(private val num:Int, private val suit:Suits){

    fun printCardLn(){
        println("${num} of ${suit}")
    }
    fun printCardTab(){
        print("${num} of ${suit},\t")
    }
}