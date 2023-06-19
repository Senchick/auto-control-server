package com.company.autocontrol.service

import com.company.autocontrol.repository.BookingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookingService {
    @Autowired
    private lateinit var bookingRepository: BookingRepository
}
