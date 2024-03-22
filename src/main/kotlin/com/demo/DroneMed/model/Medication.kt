package com.demo.DroneMed.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name = "medications")
class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    var id : Int? = null
    var name : String? = null
    var weightGram : Int? = null

    constructor()

    constructor(
        name : String,
        weightGram : Int
    ){
        this.name = name
        this.weightGram = weightGram
    }
}