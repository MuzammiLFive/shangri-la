package com.coursework.iGSE.controller

import com.coursework.iGSE.service.PropertyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/property")
class PropertyController(private val propertyService: PropertyService) {

    @GetMapping("/propertycount")
    fun propertyCount(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(propertyService.getPropertyCount())
    }
}
