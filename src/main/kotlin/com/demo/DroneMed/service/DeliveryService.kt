package com.demo.DroneMed.service

import com.demo.DroneMed.dto.DeliveryDTO
import com.demo.DroneMed.model.Delivery

interface DeliveryService {
    fun createDelivery(delivery: DeliveryDTO) : String
    fun updateDelivery(delivery: DeliveryDTO, deliveryId: Int) : String
    fun deleteDelivery(deliveryId: Int) : String
    fun getDelivery(deliveryId: Int) : DeliveryDTO
    fun getAllDeliveries() : List<DeliveryDTO>
}