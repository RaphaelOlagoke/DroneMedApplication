package com.demo.DroneMed.service.impl

import com.demo.DroneMed.model.Delivery
import com.demo.DroneMed.model.Drone
import com.demo.DroneMed.repository.DeliveryRepository
import com.demo.DroneMed.repository.DroneRepository
import com.demo.DroneMed.repository.MedicationRepository
import com.demo.DroneMed.service.DeliveryService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DeliveryServiceImpl(deliveryRepository: DeliveryRepository, medicationRepository: MedicationRepository, droneRepository: DroneRepository) : DeliveryService {

    var deliveryRepository = deliveryRepository
    val medicationRepository = medicationRepository
    val droneRepository = droneRepository

    val lightWeightDroneCapacityInGram = 1000
    val mediumWeightDroneCapacityInGram = 1500
    val heavyWeightDroneCapacityInGram = 2000
    override fun createDelivery(delivery: Delivery): String {
        var deliveryType = delivery.deliveryType
        var medicationToBeDelivery = medicationRepository.findById(delivery.medId).get()
        var deliveryWeight = medicationToBeDelivery.weightGram?.times(delivery.quantity)

        // GOLD DELIVERY TYPE
        if (deliveryWeight != null && deliveryType?.uppercase() == DeliveryType.GOLD.toString()) {
            assignDroneForGoldDelivery(deliveryWeight, delivery)
        }

        return "Your order has been placed Successfully"
    }

    override fun updateDelivery(delivery: Delivery, deliveryId: Int): String {
        deliveryRepository.deleteById(deliveryId)
        deliveryRepository.save(delivery)
        return "Your order has been updated Successfully"
    }

    override fun deleteDelivery(deliveryId: Int): String {
        deliveryRepository.deleteById(deliveryId)
        return "Your order has been Canceled"
    }

    override fun getDelivery(deliveryId: Int): Delivery {
        return deliveryRepository.findById(deliveryId).get()
    }

    override fun getAllDeliveries(): List<Delivery> {
        return deliveryRepository.findAll()
    }

    fun assignDroneForGoldDelivery(deliveryWeight : Int, delivery: Delivery){
        var allDrones = droneRepository.findAll()
        if (deliveryWeight < lightWeightDroneCapacityInGram){
            assignDrone(allDrones, delivery, DroneType.LIGHTWEIGHT.toString())
        }
        else if (deliveryWeight > lightWeightDroneCapacityInGram && deliveryWeight < mediumWeightDroneCapacityInGram){
            assignDrone(allDrones, delivery, DroneType.MEDIUM_WEIGHT.toString())
        }
        else{
            assignDrone(allDrones, delivery, DroneType.HEAVYWEIGHT.toString())
        }

    }

    fun assignDrone(allDrones : List<Drone>, delivery: Delivery, droneType : String){
        for (drone in allDrones){
            if (drone.isAvailable == "TRUE" && drone.type == droneType){
                delivery.deliveryStatus = DeliveryStatus.OUT_FOR_DELIVERY.toString()
                delivery.outForDeliveryTime = LocalDateTime.now()
                val savedDelivery = deliveryRepository.save(delivery)
                var assignedDrone = Drone(drone.id,drone.type,savedDelivery.id.toString(), "FALSE")
                droneRepository.save(assignedDrone)
                break
            }
            else{
                deliveryRepository.save(delivery)
            }
        }
    }

    @Scheduled(fixedDelay = 30000) // Run every minute
    fun simulateDroneDeliveries() {
        val oneMinuteAgo = LocalDateTime.now().minusMinutes(1)
        val fiveMinutesAgo = LocalDateTime.now().minusMinutes(5)
        val allDeliveries = deliveryRepository.findAll()
        val outForDeliveryDeliveries = mutableListOf<Delivery>()

        for (delivery in allDeliveries){
            if (delivery.outForDeliveryTime?.isBefore(fiveMinutesAgo) == true && delivery.deliveryStatus == DeliveryStatus.OUT_FOR_DELIVERY.toString()){
                println(delivery.outForDeliveryTime)
                outForDeliveryDeliveries.add(delivery)
            }
        }
        for (delivery in outForDeliveryDeliveries) {
            delivery.deliveryStatus = DeliveryStatus.DELIVERED.toString()
            deliveryRepository.save(delivery)
        }

    }

    @Scheduled(fixedDelay = 30000) // Run every minute
    fun checkAndUpdateDroneStatus() {
        val allDrones = droneRepository.findAll()
        for (drone in allDrones){
            var amountOfDeliveriesCompleted = 0
            var droneDeliveries = drone.deliveryIds?.split(",")!!
            for (delivery in droneDeliveries){
                if (delivery != ""){
                    if(deliveryRepository.findById(delivery.toInt()).get().deliveryStatus == DeliveryStatus.DELIVERED.toString()){
                        amountOfDeliveriesCompleted++
                    }
                }
            }
            if (amountOfDeliveriesCompleted == droneDeliveries.size){
                drone.isAvailable = "TRUE"
                drone.deliveryIds = ""
                droneRepository.save(drone)
            }
        }
    }

    @Scheduled(fixedDelay = 30000) // Run every minute
    fun checkAndAssignPendingGoldDeliveries(){
        val allDrones = droneRepository.findAll()
        val allDelivery = deliveryRepository.findAll()
        val pendingGoldDeliveries = mutableListOf<Delivery>()

        for (delivery in allDelivery){
            if (delivery.deliveryType == DeliveryType.GOLD.toString() && delivery.deliveryStatus == DeliveryStatus.PENDING.toString()){
                var medicationToBeDelivery = medicationRepository.findById(delivery.medId).get()
                var deliveryWeight = medicationToBeDelivery.weightGram?.times(delivery.quantity)
                if (deliveryWeight != null) {
                    assignDroneForGoldDelivery(deliveryWeight, delivery)
                }
            }
        }
    }

}

enum class DroneType{
    LIGHTWEIGHT,
    MEDIUM_WEIGHT,
    HEAVYWEIGHT
}

enum class DeliveryType{
    REGULAR,
    GOLD
}

enum class DeliveryStatus{
    PENDING,
    OUT_FOR_DELIVERY,
    DELIVERED
}