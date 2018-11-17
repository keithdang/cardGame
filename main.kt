enum class Suits{
    SPADES,CLUBS,DIAMONDS,HEARTS
}
fun main(args:Array<String>){
    val util=Util()
    println("Card Game")
    val deck=Deck()
    deck.shuffleDeck()
    val playerOne=Player(deck.getDeck().subList(0,13).toMutableList())
    util.printCardsInLine(playerOne.hand)
}