package com.demo.DroneMed.model

import com.demo.DroneMed.enum.DeliveryStatus
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime


@Entity
@Table(name = "deliveries")
class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    var id: Int? = null
    var location: String? = null

    @ManyToMany
    @JoinTable(
        name = "delivery_medication",
        joinColumns = [JoinColumn(name = "delivery_id")],
        inverseJoinColumns = [JoinColumn(name = "medication_id")]
    )
    var medications: List<Medication> = mutableListOf()
    var deliveryStatus: String = DeliveryStatus.PENDING.toString()
    var deliveryType: String? = null
    var outForDeliveryTime: LocalDateTime? = null
    var droneId : Int? = null

    constructor()

    constructor(
        location : String?,
        medications : List<Medication>,
        deliveryType : String?
    ){
        this.location = location
        this.medications = medications
        this.deliveryType  = deliveryType
    }

}