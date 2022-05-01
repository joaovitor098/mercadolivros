package com.mercadolivro.controllers

import com.mercadolivro.dtos.CreateCustomerDTO
import com.mercadolivro.dtos.UpdateCustomerDTO
import com.mercadolivro.models.CustomerModel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.function.Predicate

@RestController
@RequestMapping("customers")
class CustomerController {

    val customers = mutableListOf<CustomerModel>()


    @GetMapping
    fun findAll(@RequestParam name: String?): List<CustomerModel> {

      name?.let {
          return customers.filter { it.name.contains(name, true) }
      }
        return customers
    }

    @GetMapping("/{id}")
    fun findCustomer(@PathVariable id: String): CustomerModel {

        return customers.filter { it.id == id }.first()

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveCustomer(@RequestBody customer: CreateCustomerDTO) {

        val id = if (customers.isEmpty())
            1
        else {
            customers.last().id.toInt() + 1
        }.toString()

        val newCustomer = CustomerModel(id, customer.name, customer.email)
        customers.add(newCustomer)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateCustomer(@RequestBody customer: UpdateCustomerDTO, @PathVariable id: String) {

        customers.filter { it.id == id }.first().let {
            it.email = customer.email
            it.name = customer.name
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id: String) {

         customers.removeIf { it.id == id }

    }
}