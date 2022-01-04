import org.scalatest.{FunSuite, Matchers}

class WordDistanceTest extends FunSuite with Matchers {

  test("should find minimum word distance") {
    val sentence = "the quick brown dog jumped over the lazy dog"
    val distance = new WordDistance(sentence)
    distance.apply("the", "lazy") should be(1)
    distance.apply("quick", "lazy") should be(6)
    distance.apply("dog", "lazy") should be(1)
    distance.apply("fox", "lazy") should be(-1)
    distance.apply("fox", "leaped") should be(-1)
    distance.apply("dog", "dog") should be(0)
  }

  test("should work for no words") {
    val sentence = ""
    val distance = new WordDistance(sentence)
    distance.apply("the", "lazy") should be(-1)
  }

  test("should work for equal distance") {
    val sentence = "the bees knees bees the"
    val distance = new WordDistance(sentence)
    distance.apply("the", "knees") should be(2)
  }
}