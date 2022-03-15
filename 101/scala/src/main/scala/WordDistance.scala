import scala.collection.mutable.ListBuffer

class WordDistance(sentence: String) {
  val indices = sentence.toLowerCase
    .split(" ")
    .zipWithIndex
    .foldLeft(Map.empty[String, List[Int]]) { (accumulator, nextWordAndIndex) =>
      nextWordAndIndex match {
        case (word, index) =>
          val nextEntry = accumulator.get(word) match {
            case Some(existingIndices) =>
              (word -> (existingIndices :+ index))
            case None =>
              (word -> List(index))
          }
          accumulator + nextEntry
      }
    }

  def apply(wordOne: String, wordTwo: String): Int = {
    val opWordOne = wordOne.toLowerCase
    val opWordTwo = wordTwo.toLowerCase

    if (!indices.contains(opWordOne) || !indices.contains(opWordTwo)) {
      return -1
    }
    if (opWordOne == opWordTwo) {
      return 0
    }

    val firstIndices = indices(wordOne)
    val secondIndices = indices(wordTwo)

    var i, j = 0
    var minDistance = Int.MaxValue
    while (i < firstIndices.length && j < secondIndices.length) {
      val firstVal = firstIndices(i)
      val secondVal = secondIndices(j)

      val distance = Math.abs(firstVal - secondVal)
      if (distance < minDistance) {
        minDistance = distance
      }
      if (firstVal < secondVal) i += 1
      else j += 1
    }
    minDistance
  }
}
