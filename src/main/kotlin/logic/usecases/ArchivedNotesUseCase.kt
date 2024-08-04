package logic.usecases

import logic.models.Note
import logic.repositories.ArchivesRepository
import logic.repositories.ArchivesRepositoryError
import logic.repositories.ArchivesRepositoryException
import logic.repositories.NotesRepository
import java.util.UUID

interface ArchivedNotesUseCase {
    fun getNotesByArchiveId(id: UUID): List<Note>?
    fun addNoteToArchiveWithId(note: Note, archiveId: UUID)
}

sealed class ArchivedNotesUseCaseError(val message: String) {
    data class ArchiveNotFound(val id: UUID): ArchivedNotesUseCaseError("Archive with id $id not found.")
    object UnknownError: ArchivedNotesUseCaseError("Unknown error")
}


class ArchivedNotesUseCaseException(val error: ArchivedNotesUseCaseError) : Exception(error.message)

class ArchivedNotesUseCaseImpl(
        private val archivesRepository: ArchivesRepository,
        private val notesRepository: NotesRepository
): ArchivedNotesUseCase {
    override fun getNotesByArchiveId(id: UUID): List<Note>? {
        val archive = archivesRepository.getArchiveById(id) ?: return null
        return archive.notes.mapNotNull { notesRepository.getNoteById(it) }
    }

    override fun addNoteToArchiveWithId(note: Note, archiveId: UUID) {
        try {
            archivesRepository.updateArchive(archiveId) {
                val notesIds = it.notes.toMutableList()
                notesIds.add(note.id)
                return@updateArchive it.copy(
                        id = it.id,
                        name = it.name,
                        notes = notesIds
                )
            }
            notesRepository.addNote(note)
        } catch (e: ArchivesRepositoryException) {
            when (e.error) {
                is ArchivesRepositoryError.NotFound -> throw ArchivedNotesUseCaseException(ArchivedNotesUseCaseError.ArchiveNotFound(archiveId))
                else -> throw ArchivedNotesUseCaseException(ArchivedNotesUseCaseError.UnknownError)
            }
        }
    }
}