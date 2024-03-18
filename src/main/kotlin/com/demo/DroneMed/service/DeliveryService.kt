package com.demo.DroneMed.service

import com.demo.DroneMed.model.Delivery

interface DeliveryService {
    fun createDelivery(delivery: Delivery) : String
    fun updateDelivery(delivery: Delivery, deliveryId: Int) : String
    fun deleteDelivery(deliveryId: Int) : String
    fun getDelivery(deliveryId: Int) : Delivery
    fun getAllDeliveries() : List<Delivery>
}