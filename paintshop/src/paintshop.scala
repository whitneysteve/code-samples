import io.Source
import util.matching.Regex

/**
 * The premise is this:
 * - Assume all orders are for gloss;
 * - Set a happiness for each customer to be the number of gloss colors they want;
 * - If they want only matte, add them to unsatisfied customers and keep track of the colors that may need to be upgraded to matte;
 * - While there are unsatisfied customers upgrade the color that will please the most of the unsatisfied customers;
 * - If the color cannot be upgraded because it will upset a previously satisfied customer, skip it's upgrade;
 * - If there are unsatisfied customers after applying all possible upgrades, there is no solution.
 */
object paintshop {

  object NoSolution extends Exception

  case class Order( color: Int, paint: String )

  case class Customer( line: Int, var happyCount: Int = 0 )

  /**
   * Class to track the happiness of customers for a certain color. If we need to upgrade a color to matte, we only
   * really care if the customer with the lowest happiness can be upgraded, this we only track the customer with the
   * lowest satisfaction
   * @param c the color to track satisfaction for
   */
  class SatisfactionWithColor( c: Int ) {

    val color: Int = c
    val customers = collection.mutable.ArrayBuffer.empty[ Customer ]
    var lowestSatisfactionWithColor: Int = 999
    var lowestSatisfiedCustomer: Customer = null.asInstanceOf[ Customer ]

    def addSatisfiedCustomer( customer: Customer ) {

      if( customer.happyCount < lowestSatisfactionWithColor ) {

        lowestSatisfactionWithColor = customer.happyCount
        lowestSatisfiedCustomer = customer

      }
      // Can't use insert sort because a customer satisfaction may increase or decrease independent of satisfaction
      // with this color
      customers += customer

    }

    def increaseSatisfactionForCustomers( satisfiedCustomers: collection.mutable.MutableList[ Customer ] ) {

      if( satisfiedCustomers.contains( lowestSatisfiedCustomer ) ) {

        lowestSatisfactionWithColor += 1
        //Recalculate lowest satisfaction with color in case the above increment made the unhappiest customer happier
        //than another
        val sortedCustomerList = customers.sortBy( _.happyCount )
        lowestSatisfiedCustomer = sortedCustomerList( 0 )
        lowestSatisfactionWithColor = lowestSatisfiedCustomer.happyCount

      }

    }
  }

  val orderPattern = new Regex( "(\\d) ([G|M])" )
  val happyWithGlossPattern = new Regex( "G" )
  val MATTE = "M"
  val GLOSS = "G"

  val matteColors = collection.mutable.ArrayBuffer.empty[ Int ]
  val unsatisfiedCustomers = collection.mutable.Set.empty[ Customer ]
  val customersHappyWithGlossByColor = collection.mutable.Map.empty[ Int, SatisfactionWithColor ]
  val numberOfCustomersMadeHappierByUpgradeByColor = collection.mutable.Map.empty[ Int, Int ]
  val customersWhoWouldLikeUpgradeToMatteByColor = collection.mutable.Map.empty[ Int, collection.mutable.MutableList[ Customer ] ]

  /**
   * Process a single order file.
   * @param inputFile input file path
   */
  def processOrderFile( inputFile: String ) {

    val lines = Source.fromFile( inputFile ).getLines()
    val numColors = lines.next().toInt
    var lineNum = 1

    for( line <- lines ) {

      // All orders a gloss to start, so happiness is the amount of gloss colors the customer wants.
      val customer = Customer( lineNum, happyWithGlossPattern.findAllIn( line ).size )
      lineNum += 1
      if( customer.happyCount < 1 ) {

        // This customer has no orders they want
        unsatisfiedCustomers += customer

      }

      // Check all orders so we can keep track of what colors satisfy what orders.
      for( mattch <- orderPattern.findAllIn(line).matchData ) {

        val order = Order( mattch.group( 1 ).toInt, mattch.group( 2 ) )

        if( MATTE.equals( order.paint ) ) {

          // This color is gloss for now, but this customer would be happier if it was matte.
          if( customersWhoWouldLikeUpgradeToMatteByColor.contains( order.color ) ) {

            customersWhoWouldLikeUpgradeToMatteByColor( order.color ) += customer

          } else {

            customersWhoWouldLikeUpgradeToMatteByColor( order.color ) = collection.mutable.MutableList( customer )

          }

          // Keep track of what upgrades will please the most amount of unsatisfied customers, the upgrade that would
          // please the most unsatisfied customers should be upgraded first, to minimise the number of upgrades
          // required to please all customers.
          if( customer.happyCount < 1 ) {

            if( numberOfCustomersMadeHappierByUpgradeByColor.contains( order.color ) ) {

              numberOfCustomersMadeHappierByUpgradeByColor( order.color ) += 1

            } else {

              numberOfCustomersMadeHappierByUpgradeByColor( order.color ) = 1

            }

          }

        } else {

          // In case we need to upgrade a color to matte, we need to decrement the happiness of customers who want that
          // color in gloss, in case we cannot satisfy their order if we upgrade
          if( customersHappyWithGlossByColor.contains(order.color) ) {

            customersHappyWithGlossByColor( order.color ).addSatisfiedCustomer( customer )

          } else {

            val satisfaction = new SatisfactionWithColor( order.color )
            satisfaction.addSatisfiedCustomer( customer )
            customersHappyWithGlossByColor( order.color ) = satisfaction

          }

        }

      }

    }

    // Sort the potential upgrades in the order of: highest number of unsatisfied orders that would be satisfied by
    // upgrading to lowest
    val upgradeList = ( numberOfCustomersMadeHappierByUpgradeByColor.toList sortBy {
      _._2
    } ).reverse
    val upgradeIterator = upgradeList.toIterator

    // Check potential upgrades to satisfy customer, starting with the potential upgrade that will satisfy the most
    // customers, therefore requiring the least amount of upgrades.
    while( unsatisfiedCustomers.size > 0 && upgradeIterator.hasNext ) {

      val colorToUpgrade = upgradeIterator.next()

      if( !willUpgradeUpsetAnotherCustomer( colorToUpgrade._1 ) ) {
        // Everyone will still be OK if we upgrade this to matte. Hopefully this will satisfy as many customers as
        // possible, if not everyone, then we'll move onto the next possible upgrade
        performUpgrade( colorToUpgrade._1 )

      }

    }

    if( unsatisfiedCustomers.size > 0 ) {

      // After all the upgrades we could apply without unsatisfying a previous order, we still have unsatisfied
      // customers, so ...
      throw NoSolution

    } else {

      // Print the colors
      for( i <- 1 to numColors ) {

        if( matteColors.contains( i ) ) {

          print( MATTE )

        } else {

          print( GLOSS )

        }

        print(' ')

      }

    }

  }

  def willUpgradeUpsetAnotherCustomer( colorToUpgrade: Int ): Boolean = {

    customersHappyWithGlossByColor.contains( colorToUpgrade ) && ( customersHappyWithGlossByColor( colorToUpgrade ).lowestSatisfactionWithColor <= 1 )

  }

  def performUpgrade( colorToUpgrade: Int ) {

    matteColors += colorToUpgrade

    var satisfaction: SatisfactionWithColor = null.asInstanceOf[ SatisfactionWithColor ]

    if( customersHappyWithGlossByColor.contains( colorToUpgrade ) ) {
      // Customers who were happy with gloss, will need their happiness decremented, in case a future upgrade might
      // make them unsatisfied
      satisfaction = customersHappyWithGlossByColor( colorToUpgrade )
      satisfaction.lowestSatisfactionWithColor -= 1
    }

    if( customersWhoWouldLikeUpgradeToMatteByColor.contains( colorToUpgrade ) ) {

      val customersNowSatisfied = customersWhoWouldLikeUpgradeToMatteByColor( colorToUpgrade )
      unsatisfiedCustomers --= customersNowSatisfied

      if( satisfaction != null ) {

        // We can increase the happiness of the customers who would have preferred matte, it may give us more room to
        // upgrade a color that they wanted in gloss later.
        satisfaction.increaseSatisfactionForCustomers( customersNowSatisfied )

      }

    }

  }

  def resetState() {

    matteColors.clear()
    unsatisfiedCustomers.clear()
    customersHappyWithGlossByColor.clear()
    numberOfCustomersMadeHappierByUpgradeByColor.clear()
    customersWhoWouldLikeUpgradeToMatteByColor.clear()

  }

  def main( args: Array[ String ] ) {

    for( inputFile <- args ) {

      resetState()

      try {

        processOrderFile( inputFile )

      } catch {

        case NoSolution => print("No solution exists")

      }

      println()
    }

  }

}