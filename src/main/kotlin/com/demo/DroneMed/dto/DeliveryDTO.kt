package com.demo.DroneMed.dto

import com.demo.DroneMed.enum.DeliveryStatus
import com.demo.DroneMed.model.Delivery
import com.demo.DroneMed.model.Medication
import java.time.LocalDateTime

class DeliveryDTO {
    var location: String? = null
    var medications: List<Medication> = mutableListOf()
    var deliveryStatus: String = DeliveryStatus.PENDING.toString()
    var deliveryType: String? = null

    constructor()

    constructor(
        location : String?,
        medications : List<Medication>,
        deliveryType : String?,
        deliveryStatus: String
    ){
        this.location = location
        this.medications = medications
        this.deliveryType  = deliveryType
        this.deliveryStatus = deliveryStatus
    }

}