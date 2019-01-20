import scala.collection.mutable
import scala.io.Source
import scala.util.matching.Regex

/**
  * The premise is this:
  * - Assume all orders are for gloss;
  * - Set a happiness for each customer to be the number of gloss colours they want;
  * - If they want only matte, add them to unsatisfied customers and keep track of the colours that may need to be upgraded to matte;
  * - While there are unsatisfied customers upgrade the colour that will please the most of the unsatisfied customers;
  * - If the colour cannot be upgraded because it will upset a previously satisfied customer, skip it's upgrade;
  * - If there are unsatisfied customers after applying all possible upgrades, there is no solution.
  */
object Paintshop {
  def main(args: Array[String]): Unit = {
    for (inputFile <- args) {
      try {
        new Paintshop().process(inputFile)
      } catch {
        case NoSolution => print("No solution exists")
      }
      println()
    }
  }

  /**
    * Input pattern for orders in input files.
    */
  private val OrderPattern: Regex =
    """(\d) ([G|M])""".r

  /**
    * Marker denoting Matte.
    */
  protected val Matte: String = "M"

  /**
    * Marker denoting Gloss.
    */
  protected val Gloss: String = "G"

  /**
    * A mutable Map that returns a default value on lookup if a key is not in the map.
    */
  private object MutableMapWithDefault {
    def apply[K, V](defaultFactory: K => V): mutable.HashMap[K, V] = {
      new collection.mutable.HashMap[K, V] {
        override def default(key: K): V = {
          val defaultValue = defaultFactory(key)
          this += (key -> defaultValue)
          defaultValue
        }
      }
    }
  }

}

/**
  * The class implementing the solution.
  */
class Paintshop {

  import Paintshop._

  /**
    * Colours to prepare in matte.
    */
  private[this] val matteColours = collection.mutable.MutableList.empty[Int]

  /**
    * This customer has no orders they want.
    */
  private[this] implicit val unsatisfiedCustomers: mutable.Set[Customer] =
    collection.mutable.Set.empty

  /**
    * Colours start as gloss, but these customer would be happier if it was matte.
    */
  private[this] implicit val matteUpgrades
    : mutable.HashMap[Int, mutable.Set[Customer]] =
    MutableMapWithDefault[Int, collection.mutable.Set[Customer]] { _ =>
      collection.mutable.Set[Customer]()
    }

  /**
    * What upgrades will please the most amount of unsatisfied customers.
    */
  private[this] implicit val satisfiedWhenUpgraded: mutable.HashMap[Int, Int] =
    MutableMapWithDefault[Int, Int] { _ =>
      0
    }

  /**
    * In case of upgrade matte, we need to decrement the happiness of customers who want that colour in gloss.
    */
  private[this] implicit val satisfiedWithGloss
    : mutable.HashMap[Int, SatisfactionWithColour] =
    MutableMapWithDefault[Int, SatisfactionWithColour] { key =>
      SatisfactionWithColour(key)
    }

  /**
    * Process a single order file.
    *
    * @param inputFile input file path
    */
  def process(inputFile: String): Unit = {
    val lines = Source.fromFile(inputFile).getLines()
    val numColours = lines.next().toInt

    for (line <- lines) {
      val customer = Customer(line)

      if (!customer.satisfied) {
        unsatisfiedCustomers += customer
      }

      OrderPattern
        .findAllIn(line)
        .matchData map { orderData =>
        Order(orderData.group(1).toInt, orderData.group(2))
      } foreach { order: Order =>
        order.paintType match {
          case Matte =>
            matteUpgrades(order.colour) += customer
            if (!customer.satisfied) {
              val wouldBeSatisfied = satisfiedWhenUpgraded(order.colour)
              satisfiedWhenUpgraded(order.colour) = wouldBeSatisfied + 1
            }
          case Gloss =>
            satisfiedWithGloss(order.colour).addSatisfiedCustomer(customer)
          case _ =>
            throw InvalidInputFile
        }
      }
    }

    // Sort the potential upgrades so the highest number of unsatisfied customer that would be satisfied by upgrading
    val upgradeIterator = satisfiedWhenUpgraded.toList
      .sortBy {
        case (_, fulfilledOrders) => fulfilledOrders
      }(Ordering[Int].reverse)
      .map {
        case (customer, _) => customer
      }
      .toIterator

    // Check potential upgrades to satisfy customer, starting with the potential upgrade that will satisfy the most
    // customers, therefore requiring the least amount of upgrades.
    while (unsatisfiedCustomers.nonEmpty && upgradeIterator.hasNext) {
      val colourToUpgrade = upgradeIterator.next()

      // Everyone will still be OK if we upgrade this to matte. Hopefully this will satisfy as many customers as
      // possible, if not everyone, then we'll move onto the next possible upgrade
      if (!willUpgradeUpsetAnotherCustomer(colourToUpgrade)) {
        performUpgrade(colourToUpgrade, matteUpgrades)
      }
    }

    if (unsatisfiedCustomers.nonEmpty) {
      // After all the upgrades we could apply without unsatisfying a previous order, we still have unsatisfied
      // customers, so ...
      throw NoSolution
    } else {
      for (i <- Range.inclusive(1, numColours)) {
        if (matteColours.contains(i)) {
          print(Matte)
        } else {
          print(Gloss)
        }
        print(' ')
      }
    }
  }

  /**
    * Check will an upgrade to matte upset another customer.
    *
    * @param colourToUpgrade the colour to upgrade to matte.
    * @return true if the upgrade will upset a customer, false if not.
    */
  private def willUpgradeUpsetAnotherCustomer(colourToUpgrade: Int): Boolean = {
    satisfiedWithGloss.contains(colourToUpgrade) &&
    satisfiedWithGloss(colourToUpgrade).lowestSatisfactionWithColour <= 1
  }

  /**
    * Perform a colour upgrade.
    *
    * @param colourToUpgrade the colour to upgrade to matte.
    * @param matteUpgrades eixsting set of customers happier with matte upgrades.
    */
  def performUpgrade(
      colourToUpgrade: Int,
      matteUpgrades: collection.mutable.Map[Int,
                                            collection.mutable.Set[Customer]]
  ): Unit = {
    matteColours += colourToUpgrade
    matteUpgrades.get(colourToUpgrade) foreach { customersNowSatisfied =>
      unsatisfiedCustomers --= customersNowSatisfied
      // We can increase the happiness of the customers who would have preferred matte, it may give us more room to
      // upgrade a colour that they wanted in gloss later.
      satisfiedWithGloss.get(colourToUpgrade) foreach {
        _.increaseSatisfactionForCustomers(customersNowSatisfied)
      }
    }
  }
}
