package logic.repositories

import logic.models.Note
import logic.storages.NotesStorage
import java.util.UUID

interface NotesRepository {
    fun addNote(note: Note): Result<Unit>
    fun getNoteById(id: UUID): Note?
}

sealed class NotesRepositoryError(val message: String) {
    data class AlreadyExists(val id: UUID): NotesRepositoryError("Архив с таким же id $id уже существует")
}


class NotesRepositoryException(val error: NotesRepositoryError) : Exception(error.message)

class NotesRepositoryImpl(private val notesStorage: NotesStorage): NotesRepository {
    override fun addNote(note: Note): Result<Unit> {
        val isNoteWithSameIdAlreadyExists = notesStorage.getNoteById(note.id) != null
        if (isNoteWithSameIdAlreadyExists) {
            return Result.failure(NotesRepositoryException(NotesRepositoryError.AlreadyExists(note.id)))
        }
        notesStorage.addOrUpdateNote(note)
        return Result.success(Unit)
    }

    override fun getNoteById(id: UUID): Note? {
        return notesStorage.getNoteById(id)
    }
}