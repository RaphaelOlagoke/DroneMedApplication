package com.demo.DroneMed.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name ="drones")
class Drone {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
//    @GenericGenerator(name = "native", strategy = "native")
    var id: Int? = null
    var type : String? = null
    var deliveryIds : String? = null
    var isAvailable : String? = null

    constructor()

//    constructor(
//        type : String,
//        deliveryIds : String,
//        isAvailable: String
//    ){
//        this.type = type
//        this.deliveryIds = deliveryIds
//        this.isAvailable = isAvailable
//    }

    constructor(
        id : Int?,
        type : String?,
        deliveryIds : String,
        isAvailable: String
    ){
        this.id = id
        this.type = type
        this.deliveryIds = deliveryIds
        this.isAvailable = isAvailable
    }
}