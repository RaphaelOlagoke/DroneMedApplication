package com.demo.DroneMed.repository

import com.demo.DroneMed.model.Delivery
import org.springframework.data.jpa.repository.JpaRepository

interface DeliveryRepository : JpaRepository<Delivery, Int> {

    fun findByDeliveryStatus(deliveryStatus: String): List<Delivery>

    fun findByDroneId(droneId: Int?): List<Delivery>

    fun findByDeliveryStatusAndDeliveryType(deliveryStatus: String, deliveryType: String): List<Delivery>
}