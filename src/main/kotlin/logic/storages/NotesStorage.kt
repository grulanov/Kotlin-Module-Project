package logic.storages

import logic.models.Note
import java.util.UUID

interface NotesStorage {
    fun addOrUpdateNote(note: Note)
    fun getNoteById(id: UUID): Note?

}

class NotesStorageImpl: NotesStorage {
    private var notes: MutableMap<UUID, Note> = mutableMapOf()

    override fun addOrUpdateNote(note: Note) {
        notes[note.id] = note
    }

    override fun getNoteById(id: UUID): Note? {
        return notes[id]
    }
}