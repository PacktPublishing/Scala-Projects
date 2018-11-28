package models

trait ProductService {
  def findAll(): List[Product] = {
    Product.listProducts()
  }
}

object ProductService extends ProductService
