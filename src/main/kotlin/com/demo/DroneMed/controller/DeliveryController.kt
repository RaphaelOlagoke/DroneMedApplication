package com.demo.DroneMed.controller

import com.demo.DroneMed.dto.DeliveryDTO
import com.demo.DroneMed.model.Delivery
import com.demo.DroneMed.service.DeliveryService
import com.demo.DroneMed.util.DeliveryMapper
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
    fun getDeliveryDetails(@PathVariable("deliveryId") deliveryId : Int) : DeliveryDTO {
        return deliveryService.getDelivery(deliveryId)
    }

    @GetMapping
    fun getAllDeliveries(): List<DeliveryDTO> {
        return deliveryService.getAllDeliveries()
    }

    @PostMapping
    fun createDelivery(@RequestBody deliveryDTO: DeliveryDTO) : String{
        return deliveryService.createDelivery(deliveryDTO)
    }

    @PutMapping("{deliveryId}")
    fun updateDeliveryDetails(@RequestBody deliveryDTO: DeliveryDTO, @PathVariable("deliveryId") deliveryId: Int) : String{
        return deliveryService.updateDelivery(deliveryDTO, deliveryId)
    }

    @DeleteMapping("{deliveryId}")
    fun cancelDelivery(@PathVariable("deliveryId") deliveryId: Int) : String{
        return deliveryService.deleteDelivery(deliveryId)
    }
}