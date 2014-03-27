import scala.collection.mutable

/**
 * A post fix calculator using command line arguments. For example:
 *
 * scala PostFixCalculator 2 3 * / => 6
 * scala PostFixCalculator 6 2 * / => 3
 * scala PostFixCalculator 1 7 3 - 2 * + 3 / => 3
 * scala PostFixCalculator 1 2 5 7 + 6 / - + => 1
 */
object PostFixCalculator {
  val OperandPattern = """(\d+)""".r
  val OperatorPattern = "([+-/*])".r

  val operands = mutable.Stack[Int]()

  def main(args: Array[String]) {
    evaluateExpression(args)
    printResult()
  }

  def evaluateExpression(exps: Seq[String]) = exps foreach {
    case OperandPattern(operand) => operands push operand.toInt
    case OperatorPattern(operator) => operands push evaluate(operator, operands pop(), operands pop())
    case invalidCharacter: String => throw new IllegalArgumentException(invalidCharacter + " is not valid syntax")
  }

  def evaluate(operator: String, rhs: Int, lhs: Int): Int = operator match {
    case "+" => lhs + rhs
    case "-" => lhs - rhs
    case "*" => lhs * rhs
    case "/" => lhs / rhs
  }

  def printResult() = if(operands.size > 1)
    println("Invalid equation")
  else
    println(operands pop())
}
