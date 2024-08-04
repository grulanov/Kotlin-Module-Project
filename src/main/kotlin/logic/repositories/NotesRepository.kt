package logic.repositories

import logic.models.Note
import logic.storages.NotesStorage
import java.util.UUID

interface NotesRepository {
    fun addNote(note: Note)
    fun getNoteById(id: UUID): Note?
}

sealed class NotesRepositoryError(val message: String) {
    data class AlreadyExists(val id: UUID): NotesRepositoryError("Archive with the same id $id already exists.")
}


class NotesRepositoryException(val error: NotesRepositoryError) : Exception(error.message)

class NotesRepositoryImpl(private val notesStorage: NotesStorage): NotesRepository {
    override fun addNote(note: Note) {
        val isNoteWithSameIdAlreadyExists = notesStorage.getNoteById(note.id) != null
        if (isNoteWithSameIdAlreadyExists) {
            throw NotesRepositoryException(NotesRepositoryError.AlreadyExists(note.id))
        }
        notesStorage.addOrUpdateNote(note)
    }

    override fun getNoteById(id: UUID): Note? {
        return notesStorage.getNoteById(id)
    }
}