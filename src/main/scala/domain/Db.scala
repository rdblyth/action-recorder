package domain

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

trait DbConfiguration {
  lazy val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("db")
}

trait Db {
  val dbConfig: DatabaseConfig[JdbcProfile]
  val db: JdbcProfile#Backend#Database = dbConfig.db
}
