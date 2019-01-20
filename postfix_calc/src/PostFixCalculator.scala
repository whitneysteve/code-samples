import scala.collection.mutable

/**
  * A post fix calculator using command line arguments. For example:
  *
  * scala PostFixCalculator "2 3 *"
  * => prints 6
  * scala PostFixCalculator "6 2 /"
  * => prints 3
  * scala PostFixCalculator "1 7 3 - 2 * + 3 /"
  * => prints 3
  * scala PostFixCalculator "1 2 5 7 + 6 / - +"
  * => prints 1
  */
object PostFixCalculator {
  val OperandPattern = """(\d+)""".r
  val OperatorPattern = "([+-/*])".r

  val operands = mutable.Stack[Int]()

  def main(args: Array[String]): Unit = {
    evaluateExpression(args.head.split(" "))
    printResult()
  }

  def evaluateExpression(exps: Seq[String]): Unit = {
    exps foreach {
      case OperandPattern(operand) => operands push operand.toInt
      case OperatorPattern(operator) =>
        operands push evaluate(operator, operands pop(), operands pop())
      case invalidCharacter: String => throw new IllegalArgumentException(invalidCharacter + " is not valid syntax")
    }
  }

  private def evaluate(operator: String, rhs: Int, lhs: Int): Int = {
    operator match {
      case "+" => lhs + rhs
      case "-" => lhs - rhs
      case "*" => lhs * rhs
      case "/" => lhs / rhs
    }
  }

  private def printResult(): Unit = {
    if (operands.size > 1) {
      println("Invalid equation")
    } else {
      println(operands pop())
    }
  }
}
