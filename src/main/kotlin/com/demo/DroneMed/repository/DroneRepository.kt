package com.demo.DroneMed.repository

import com.demo.DroneMed.model.Drone
import org.springframework.data.jpa.repository.JpaRepository

interface DroneRepository : JpaRepository<Drone, Int> {
}