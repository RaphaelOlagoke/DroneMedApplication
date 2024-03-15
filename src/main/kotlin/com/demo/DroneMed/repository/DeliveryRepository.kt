package com.demo.DroneMed.repository

import com.demo.DroneMed.model.Delivery
import org.springframework.data.jpa.repository.JpaRepository

interface DeliveryRepository : JpaRepository<Delivery, Int> {
}