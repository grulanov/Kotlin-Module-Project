package logic.usecases

import logic.models.Note
import logic.repositories.ArchivesRepository
import logic.repositories.ArchivesRepositoryError
import logic.repositories.ArchivesRepositoryException
import logic.repositories.NotesRepository
import logic.repositories.NotesRepositoryException
import java.util.UUID

interface ArchivedNotesUseCase {
    fun addNoteToArchiveWithId(note: Note, archiveId: UUID): Result<Unit>
    fun getNotesByArchiveId(id: UUID): List<Note>?
}

sealed class ArchivedNotesUseCaseError(val message: String) {
    data class ArchiveNotFound(val id: UUID): ArchivedNotesUseCaseError("Архив с id $id не найден")
    object UnknownError: ArchivedNotesUseCaseError("Неизвестная ошибка")
}


class ArchivedNotesUseCaseException(val error: ArchivedNotesUseCaseError) : Exception(error.message)

class ArchivedNotesUseCaseImpl(
        private val archivesRepository: ArchivesRepository,
        private val notesRepository: NotesRepository
): ArchivedNotesUseCase {
    override fun addNoteToArchiveWithId(note: Note, archiveId: UUID): Result<Unit> {
        try {
            archivesRepository.updateArchive(archiveId) {
                val notesIds = it.notes.toMutableList()
                notesIds.add(note.id)
                return@updateArchive it.copy(
                        id = it.id,
                        name = it.name,
                        notes = notesIds
                )
            }.getOrThrow()
            notesRepository.addNote(note).getOrThrow()
            return Result.success(Unit)
        } catch (error: ArchivesRepositoryException) {
            return when (error.error) {
                is ArchivesRepositoryError.NotFound -> Result.failure(ArchivedNotesUseCaseException(ArchivedNotesUseCaseError.ArchiveNotFound(archiveId)))
                else -> Result.failure(ArchivedNotesUseCaseException(ArchivedNotesUseCaseError.UnknownError))
            }
        } catch (error: NotesRepositoryException) {
            return Result.failure(ArchivedNotesUseCaseException(ArchivedNotesUseCaseError.UnknownError))
        }
    }

    override fun getNotesByArchiveId(id: UUID): List<Note>? {
        val archive = archivesRepository.getArchiveById(id) ?: return null
        return archive.notes.mapNotNull { notesRepository.getNoteById(it) }
    }
}