package logic.repositories

import logic.models.Archive
import logic.storages.ArchivesStorage
import java.util.UUID

interface ArchivesRepository {
    fun addArchive(archive: Archive): Result<Unit>
    fun updateArchive(id: UUID, updateBlock: (Archive) -> Archive): Result<Unit>
    fun getArchiveById(id: UUID): Archive?
    fun getArchives(): List<Archive>
}

sealed class ArchivesRepositoryError(val message: String) {
    data class NameConflict(val name: String): ArchivesRepositoryError("Архив с именем '$name' уже существует")
    data class AlreadyExists(val id: UUID): ArchivesRepositoryError("Архив с таким же id $id уже существует")
    data class NotFound(val id: UUID): ArchivesRepositoryError("Архив с таким id $id не найден")
}


class ArchivesRepositoryException(val error: ArchivesRepositoryError) : Exception(error.message)

class ArchivesRepositoryImpl(private val archivesStorage: ArchivesStorage): ArchivesRepository {
    override fun addArchive(archive: Archive): Result<Unit> {
        val isArchiveWithSameIdAlreadyExists = archivesStorage.getArchiveById(archive.id) != null
        if (isArchiveWithSameIdAlreadyExists) {
            return Result.failure(ArchivesRepositoryException(ArchivesRepositoryError.AlreadyExists(archive.id)))
        }

        val isArchiveWithSameNameAlreadyExists = archivesStorage.getArchiveByName(archive.name) != null
        if (isArchiveWithSameNameAlreadyExists) {
            return Result.failure(ArchivesRepositoryException(ArchivesRepositoryError.NameConflict(archive.name)))
        }
        archivesStorage.addOrUpdateArchive(archive)
        return Result.success(Unit)
    }

    override fun updateArchive(id: UUID, updateBlock: (Archive) -> Archive): Result<Unit> {
        val archive = archivesStorage.getArchiveById(id)
                ?: return Result.failure(ArchivesRepositoryException(ArchivesRepositoryError.NotFound(id)))

        val newArchive = updateBlock(archive)
        archivesStorage.addOrUpdateArchive(newArchive)
        return Result.success(Unit)
    }

    override fun getArchiveById(id: UUID): Archive? {
        return archivesStorage.getArchiveById(id)
    }

    override fun getArchives(): List<Archive> {
        return archivesStorage.getArchives()
    }
}