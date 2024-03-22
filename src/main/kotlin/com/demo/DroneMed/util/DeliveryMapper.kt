package com.demo.DroneMed.util

import com.demo.DroneMed.dto.DeliveryDTO
import com.demo.DroneMed.model.Delivery

object DeliveryMapper {

    fun mapDeliveryToDTO(delivery: Delivery) : DeliveryDTO {
        return DeliveryDTO(
            location = delivery.location,
            medications = delivery.medications,
            deliveryType = delivery.deliveryType,
            deliveryStatus = delivery.deliveryStatus
        )
    }

    fun mapDTOToDelivery(deliveryDTO: DeliveryDTO) : Delivery {
        return Delivery(
            location = deliveryDTO.location,
            medications = deliveryDTO.medications,
            deliveryType = deliveryDTO.deliveryType,
        )
    }
}