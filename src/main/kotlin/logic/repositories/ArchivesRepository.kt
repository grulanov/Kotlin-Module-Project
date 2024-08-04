package logic.repositories

import logic.models.Archive
import logic.storages.ArchivesStorage
import java.util.UUID

interface ArchivesRepository {
    fun addArchive(archive: Archive)
    fun updateArchive(id: UUID, updateBlock: (Archive) -> Archive)
    fun getArchiveById(id: UUID): Archive?
    fun getArchives(): List<Archive>
}

sealed class ArchivesRepositoryError(val message: String) {
    data class NameConflict(val name: String): ArchivesRepositoryError("Archive with the name '$name' already exists.")
    data class AlreadyExists(val id: UUID): ArchivesRepositoryError("Archive with the same id $id already exists.")
    data class NotFound(val id: UUID): ArchivesRepositoryError("Archive with id $id not found.")
}


class ArchivesRepositoryException(val error: ArchivesRepositoryError) : Exception(error.message)

class ArchivesRepositoryImpl(private val archivesStorage: ArchivesStorage): ArchivesRepository {
    override fun addArchive(archive: Archive) {
        val isArchiveWithSameIdAlreadyExists = archivesStorage.getArchiveById(archive.id) != null
        if (isArchiveWithSameIdAlreadyExists) {
            throw ArchivesRepositoryException(ArchivesRepositoryError.AlreadyExists(archive.id))
        }

        val isArchiveWithSameNameAlreadyExists = archivesStorage.getArchiveByName(archive.name) != null
        if (isArchiveWithSameNameAlreadyExists) {
            throw ArchivesRepositoryException(ArchivesRepositoryError.NameConflict(archive.name))
        }
        archivesStorage.addOrUpdateArchive(archive)
    }

    override fun updateArchive(id: UUID, updateBlock: (Archive) -> Archive) {
        val archive = archivesStorage.getArchiveById(id) ?: throw ArchivesRepositoryException(ArchivesRepositoryError.NotFound(id))
        val newArchive = updateBlock(archive)
        archivesStorage.addOrUpdateArchive(archive)
    }

    override fun getArchiveById(id: UUID): Archive? {
        return archivesStorage.getArchiveById(id)
    }

    override fun getArchives(): List<Archive> {
        return archivesStorage.getArchives()
    }
}