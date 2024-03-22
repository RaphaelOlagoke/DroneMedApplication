package com.demo.DroneMed.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name ="drones")
class Drone {
    @Id
    var id: Int? = null
    var type : String? = null
    var isAvailable : String? = null

    constructor()

    constructor(
        id : Int?,
        type : String?,
        isAvailable: String
    ){
        this.id = id
        this.type = type
        this.isAvailable = isAvailable
    }
}