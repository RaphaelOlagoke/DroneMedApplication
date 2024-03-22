package com.demo.DroneMed.service.impl

import com.demo.DroneMed.dto.DeliveryDTO
import com.demo.DroneMed.enum.DeliveryStatus
import com.demo.DroneMed.enum.DeliveryType
import com.demo.DroneMed.enum.DroneAvailability
import com.demo.DroneMed.enum.DroneType
import com.demo.DroneMed.model.Delivery
import com.demo.DroneMed.model.Drone
import com.demo.DroneMed.repository.DeliveryRepository
import com.demo.DroneMed.repository.DroneRepository
import com.demo.DroneMed.repository.MedicationRepository
import com.demo.DroneMed.service.DeliveryService
import com.demo.DroneMed.util.DeliveryMapper
import com.demo.DroneMed.util.DroneConstants.HEAVY_WEIGHT_CAPACITY_GRAM
import com.demo.DroneMed.util.DroneConstants.LIGHT_WEIGHT_CAPACITY_GRAM
import com.demo.DroneMed.util.DroneConstants.MEDIUM_WEIGHT_CAPACITY_GRAM
import com.demo.DroneMed.util.SchedulerConstants.DELIVERY_SIMULATION_DELAY_MS
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DeliveryServiceImpl(deliveryRepository: DeliveryRepository, medicationRepository: MedicationRepository, droneRepository: DroneRepository) : DeliveryService {

    var deliveryRepository = deliveryRepository
    val medicationRepository = medicationRepository
    val droneRepository = droneRepository

    val lightWeightDroneCapacityInGram = LIGHT_WEIGHT_CAPACITY_GRAM
    val mediumWeightDroneCapacityInGram = MEDIUM_WEIGHT_CAPACITY_GRAM
    val heavyWeightDroneCapacityInGram = HEAVY_WEIGHT_CAPACITY_GRAM
    override fun createDelivery(deliveryDTO: DeliveryDTO): String {
        val delivery = DeliveryMapper.mapDTOToDelivery(deliveryDTO)
        var deliveryType = delivery.deliveryType
        var medicationToBeDelivery = delivery.medications
        var deliveryWeight = 0

        for (medication in medicationToBeDelivery){
            println(medication.id)
            deliveryWeight += medication.weightGram!!
        }

        // GOLD DELIVERY TYPE
        if (deliveryType?.uppercase() == DeliveryType.GOLD.toString()) {
            assignDroneForGoldDelivery(deliveryWeight, delivery)
        }

        return "Your order has been placed Successfully"
    }

    override fun updateDelivery(deliveryDTO: DeliveryDTO, deliveryId: Int): String {
        val delivery = DeliveryMapper.mapDTOToDelivery(deliveryDTO)
        deliveryRepository.deleteById(deliveryId)
        deliveryRepository.save(delivery)
        return "Your order has been updated Successfully"
    }

    override fun deleteDelivery(deliveryId: Int): String {
        deliveryRepository.deleteById(deliveryId)
        return "Your order has been Canceled"
    }

    override fun getDelivery(deliveryId: Int): DeliveryDTO {
        val delivery : Delivery = deliveryRepository.findById(deliveryId).get()
        return DeliveryMapper.mapDeliveryToDTO(delivery)
    }

    override fun getAllDeliveries(): List<DeliveryDTO> {
        val listOfDeliveries = deliveryRepository.findAll()
        val listOfDeliveriesDTO = mutableListOf<DeliveryDTO>()
        for (delivery in listOfDeliveries){
            listOfDeliveriesDTO.add(DeliveryMapper.mapDeliveryToDTO(delivery))
        }
        return listOfDeliveriesDTO
    }

    fun assignDroneForGoldDelivery(deliveryWeight : Int, delivery: Delivery){
        var allDrones = droneRepository.findAll()
        if (deliveryWeight <= lightWeightDroneCapacityInGram){
            assignDrone(allDrones, delivery, DroneType.LIGHTWEIGHT.toString())
        }
        else if (deliveryWeight > lightWeightDroneCapacityInGram && deliveryWeight <= mediumWeightDroneCapacityInGram){
            assignDrone(allDrones, delivery, DroneType.MEDIUM_WEIGHT.toString())
        }
        else if (deliveryWeight > mediumWeightDroneCapacityInGram && deliveryWeight <= heavyWeightDroneCapacityInGram){
            assignDrone(allDrones, delivery, DroneType.HEAVYWEIGHT.toString())
        }
        else{
            delivery.deliveryStatus = DeliveryStatus.CANCELLED.toString()
            deliveryRepository.save(delivery)
        }

    }

    fun assignDrone(allDrones : List<Drone>, delivery: Delivery, droneType : String){
        val availableDrone = droneRepository.findByIsAvailableAndType(DroneAvailability.TRUE.toString(), droneType)
        for (drone in availableDrone){
            delivery.deliveryStatus = DeliveryStatus.OUT_FOR_DELIVERY.toString()
            delivery.outForDeliveryTime = LocalDateTime.now()
            delivery.droneId = drone.id
            deliveryRepository.save(delivery)
            drone.isAvailable = DroneAvailability.FALSE.toString()
            droneRepository.save(drone)
            break
        }
    }

    @Scheduled(fixedDelay = DELIVERY_SIMULATION_DELAY_MS) // Run every minute
    fun simulateDroneDeliveries() {
        val fiveMinutesAgo = LocalDateTime.now().minusMinutes(5)
        val outForDelivery = deliveryRepository.findByDeliveryStatus(DeliveryStatus.OUT_FOR_DELIVERY.toString())

        for (delivery in outForDelivery){
            if(delivery.outForDeliveryTime?.isBefore(fiveMinutesAgo) == true){
                delivery.deliveryStatus = DeliveryStatus.DELIVERED.toString()
                deliveryRepository.save(delivery)
            }
        }
    }

    @Scheduled(fixedDelay = DELIVERY_SIMULATION_DELAY_MS) // Run every minute
    fun checkAndUpdateDroneStatus() {
        val allDrones = droneRepository.findByIsAvailable(DroneAvailability.FALSE.toString())
        for (drone in allDrones){
            var amountOfDeliveriesCompleted = 0
            var deliveriesAssignedToDrone = deliveryRepository.findByDroneId(drone.id)
            for (delivery in deliveriesAssignedToDrone){
                if (delivery.deliveryStatus == DeliveryStatus.DELIVERED.toString()){
                    amountOfDeliveriesCompleted++
                }
            }
            if (amountOfDeliveriesCompleted == deliveriesAssignedToDrone.size){
                drone.isAvailable = DroneAvailability.TRUE.toString()
                droneRepository.save(drone)
            }
        }
    }

    @Scheduled(fixedDelay = DELIVERY_SIMULATION_DELAY_MS) // Run every minute
    fun checkAndAssignPendingGoldDeliveries(){
        val goldDeliveriesWithPendingStatus = deliveryRepository.findByDeliveryStatusAndDeliveryType(DeliveryStatus.PENDING.toString(), DeliveryType.GOLD.toString())

        for (delivery in goldDeliveriesWithPendingStatus){
            var medicationToBeDelivery = delivery.medications
            var deliveryWeight = 0

            for (medication in medicationToBeDelivery){
                deliveryWeight += medication.weightGram!!
            }
            assignDroneForGoldDelivery(deliveryWeight, delivery)
        }
    }

}
