package com.demo.DroneMed.controller

import com.demo.DroneMed.model.Delivery
import com.demo.DroneMed.service.DeliveryService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/delivery")
class DeliveryController(deliveryService: DeliveryService) {

    var deliveryService = deliveryService

    @GetMapping("{deliveryId}")
    fun getDeliveryDetails(@PathVariable("deliveryId") deliveryId : Int) : Delivery {
        return deliveryService.getDelivery(deliveryId)
    }

    @GetMapping
    fun getAllDeliveries(): List<Delivery> {
        return deliveryService.getAllDeliveries()
    }

    @PostMapping
    fun createDelivery(@RequestBody delivery: Delivery) : String{
        return deliveryService.createDelivery(delivery)
    }

    @PutMapping("{deliveryId}")
    fun updateDeliveryDetails(@RequestBody delivery: Delivery, @PathVariable("deliveryId") deliveryId: Int) : String{
        return deliveryService.updateDelivery(delivery, deliveryId)
    }

    @DeleteMapping("{deliveryId}")
    fun cancelDelivery(@PathVariable("deliveryId") deliveryId: Int) : String{
        return deliveryService.deleteDelivery(deliveryId)
    }
}