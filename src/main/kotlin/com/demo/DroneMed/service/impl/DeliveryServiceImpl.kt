package com.demo.DroneMed.service.impl

import com.demo.DroneMed.model.Delivery
import com.demo.DroneMed.repository.DeliveryRepository
import com.demo.DroneMed.service.DeliveryService
import org.springframework.stereotype.Service

@Service
class DeliveryServiceImpl(deliveryRepository: DeliveryRepository) : DeliveryService {

    var deliveryRepository = deliveryRepository
    override fun createDelivery(delivery: Delivery): String {
        deliveryRepository.save(delivery)
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

}