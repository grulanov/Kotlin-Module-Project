package di

import logic.repositories.ArchivesRepository
import logic.repositories.ArchivesRepositoryImpl
import logic.repositories.NotesRepository
import logic.repositories.NotesRepositoryImpl
import logic.storages.ArchivesStorage
import logic.storages.ArchivesStorageImpl
import logic.storages.NotesStorage
import logic.storages.NotesStorageImpl
import logic.usecases.ArchivedNotesUseCase
import logic.usecases.ArchivedNotesUseCaseImpl
import presentation.common.ScreenListManager
import presentation.common.ScreenListManagerImpl
import presentation.screens.archiveCreation.ArchiveCreationBuilder
import presentation.screens.archiveCreation.ArchiveCreationBuilderImpl
import presentation.screens.archives.ArchivesBuilder
import presentation.screens.archives.ArchivesBuilderImpl
import presentation.screens.note.NoteBuilder
import presentation.screens.note.NoteBuilderImpl
import presentation.screens.noteCreation.NoteCreationBuilder
import presentation.screens.noteCreation.NoteCreationBuilderImpl
import presentation.screens.notes.NotesBuilder
import presentation.screens.notes.NotesBuilderImpl
import utils.ScannerProvider
import utils.ScannerProviderImpl

class AppContainer {

    class Screens(private val appContainer: AppContainer) {
        val archivesBuilder: ArchivesBuilder
            get() = ArchivesBuilderImpl(appContainer)

        val notesBuilder: NotesBuilder
            get() = NotesBuilderImpl(appContainer)

        val archiveCreationBuilder: ArchiveCreationBuilder
            get() = ArchiveCreationBuilderImpl(appContainer)

        val noteCreationBuilder: NoteCreationBuilder
            get() = NoteCreationBuilderImpl(appContainer)

        val noteBuilder: NoteBuilder
            get() = NoteBuilderImpl(appContainer)
    }

    val screens: Screens = Screens(this)

    val scannerProvider: ScannerProvider = ScannerProviderImpl()

    private val archivesStorage: ArchivesStorage = ArchivesStorageImpl()
    private val notesStorage: NotesStorage = NotesStorageImpl()

    val archivesRepository: ArchivesRepository
        get() = ArchivesRepositoryImpl(archivesStorage)

    val notesRepository: NotesRepository
        get() = NotesRepositoryImpl(notesStorage)

    val archivedNotesUseCase: ArchivedNotesUseCase
        get() = ArchivedNotesUseCaseImpl(archivesRepository, notesRepository)

    val screenListManager: ScreenListManager
        get() = ScreenListManagerImpl(scannerProvider)

}