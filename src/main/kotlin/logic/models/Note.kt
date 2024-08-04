package logic.models

import java.util.UUID

data class Note(val id: UUID = UUID.randomUUID(), val text: String) {
    override fun hashCode(): Int {
        return id.hashCode()
    }
}
