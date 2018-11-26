package models

case class Product(productId: Int,
                   productType: String,
                   colour: String,
                   availability: Boolean,
                   size: String)

object Product {
  def listProducts(): List[Product] = List(
    Product(1111, "shirt", "white", true, "S"),
    Product(1112, "trouser", "white", true, "S"),
    Product(1113, "shirt", "black", false, "M"),
    Product(1114, "trouser", "black", true, "M"),
    Product(1115, "shirt", "blue", false, "L"))
}
