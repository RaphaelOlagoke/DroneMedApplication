package com.demo.DroneMed.repository

import com.demo.DroneMed.model.Drone
import org.springframework.data.jpa.repository.JpaRepository

interface DroneRepository : JpaRepository<Drone, Int> {

    fun findByIsAvailableAndType(isAvailable: String, type: String): List<Drone>
    fun findByIsAvailable(isAvailable: String): List<Drone>
}