package logic.models

import java.util.UUID

data class Archive(val id: UUID = UUID.randomUUID(), val name: String, val notes: List<UUID>) {
    override fun hashCode(): Int {
        return id.hashCode()
    }
}
