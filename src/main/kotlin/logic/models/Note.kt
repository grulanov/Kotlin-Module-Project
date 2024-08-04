package logic.models

import java.util.Date
import java.util.UUID

data class Note(
        val id: UUID = UUID.randomUUID(),
        val title: String,
        val text: String,
        val creationDate: Date
)
