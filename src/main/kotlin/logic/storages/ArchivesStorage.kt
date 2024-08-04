package logic.storages

import logic.models.Archive
import java.util.UUID

interface ArchivesStorage {
    fun addOrUpdateArchive(archive: Archive)
    fun getArchives(): List<Archive>
    fun getArchiveById(id: UUID): Archive?
    fun getArchiveByName(name: String): Archive?

}

class ArchivesStorageImpl: ArchivesStorage {
    private var archives: MutableMap<UUID, Archive> = mutableMapOf()

    override fun addOrUpdateArchive(archive: Archive) {
        archives.put(archive.id, archive)
    }

    override fun getArchives(): List<Archive> {
        return archives.values.toList()
    }

    override fun getArchiveById(id: UUID): Archive? {
        return archives.get(id)
    }

    override fun getArchiveByName(name: String): Archive? {
        return archives.values.firstOrNull { it.name == name }
    }
}