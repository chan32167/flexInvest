package one.tinkin.flexInvest.main.repositories

import android.content.Context
import one.tinkin.flexInvest.COMPANY_TABLE_NAME
import one.tinkin.flexInvest.database
import one.tinkin.flexInvest.main.repositories.model.Company
import org.jetbrains.anko.db.*

class CompanyRepository(val context: Context) {

  fun findAll(): ArrayList<Company> = context.database.use {
    val companies = ArrayList<Company>()

    select(COMPANY_TABLE_NAME, "id", "name", "description", "image")
        .parseList(object : MapRowParser<List<Company>> {
          override fun parseRow(columns: Map<String, Any?>): List<Company> {
            val id = columns.getValue("id")
            val name = columns.getValue("name")
            val description = columns.getValue("description")
            val image = columns.getValue("image")

            val company =
                Company(id.toString().toLong(), name.toString(), description.toString(), image.toString())

            companies.add(company)

            return companies
          }
        })

    companies
  }

  fun deleteAndcreateAll(companies: List<Company>) = context.database.use {
    transaction {
      delete(COMPANY_TABLE_NAME)
      companies.forEach {
        insert(
            COMPANY_TABLE_NAME,
            "id" to it.id,
            "name" to it.name,
            "description" to it.description,
            "image" to it.image
        )
      }
    }
  }

}