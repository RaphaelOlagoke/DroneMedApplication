package com.demo.DroneMed.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator


@Entity
@Table(name = "deliveries")
class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    var id: Int? = null
    var location: String? = null
    var medId: Int = 0
    var quantity: Int = 0
    var deliveryStatus: String = "Pending"
    var deliveryType: String? = null

    constructor()

    constructor(
        location : String,
        medId : Int,
        quantity : Int,
        deliveryType : String
    ){
        this.location = location
        this.medId = medId
        this.quantity = quantity
        this.deliveryType  = deliveryType
    }

}