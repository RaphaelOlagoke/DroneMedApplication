package com.demo.DroneMed.repository

import com.demo.DroneMed.model.Medication
import org.springframework.data.jpa.repository.JpaRepository

interface MedicationRepository : JpaRepository<Medication, Int> {
}