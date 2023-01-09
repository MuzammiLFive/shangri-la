package com.coursework.iGSE.controller

import com.coursework.iGSE.service.PropertyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/property")
class PropertyController(private val propertyService: PropertyService) {

    @GetMapping("/propertycount")
    fun propertyCount(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(propertyService.getPropertyCount())
    }

    @GetMapping("/{propertyType}/{bedCount}")
    fun getStatisticsForPropertyType(
        @PathVariable propertyType: String,
        @PathVariable bedCount: Int,
    ): ResponseEntity<Any> {
        return ResponseEntity.ok().body(propertyService.getStatisticsTypeBedCount(propertyType, bedCount))
    }
}
