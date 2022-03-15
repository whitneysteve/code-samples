import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class HashtableTest extends AnyFunSuite with Matchers {
  val Key1 = "Key1"
  val Key2 = "Key2"
  val Value1 = "Value1"
  val Value2 = "Value2"

  test("it should add elements") {
    val hash = new Hashtable[String, String](10)
    hash.put(Key1, Value1)
    hash.put(Key2, Value2)
    hash.get(Key1) should be(Some(Value1))
    hash.get(Key2) should be(Some(Value2))
    hash.get("key").isEmpty should be(true)
  }

  test("it should overwrite elements") {
    val hash = new Hashtable[String, String](10)
    hash.put(Key1, Value1)
    hash.put(Key1, Value2)
    hash.get(Key1) should be(Some(Value2))
  }

  test("it should calculate size") {
    val hash = new Hashtable[String, String](10)
    hash.put(Key1, Value1)
    hash.put(Key2, Value2)
    hash.size should be(2)
  }

  test("it should get keys") {
    val hash = new Hashtable[String, String](10)
    hash.put(Key1, Value1)
    hash.put(Key2, Value2)
    hash.keys.toSeq.sorted should be(Seq(Key1, Key2))
  }

  test("it should get values") {
    val hash = new Hashtable[String, String](10)
    hash.put(Key1, Value1)
    hash.put(Key2, Value2)
    hash.values.toSeq.sorted should be(Seq(Value1, Value2))
  }

  test("it should remove key") {
    val hash = new Hashtable[String, String](10)
    hash.put(Key1, Value1)
    hash.get(Key1) should be(Some(Value1))
    hash.remove(Key1)
    hash.get(Key1) should be(None)
  }
}
