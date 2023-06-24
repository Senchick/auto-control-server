package com.company.autocontrol.enums

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
enum class BookingType {
    GENERAL,
    MONO,
    CLOSE
}
