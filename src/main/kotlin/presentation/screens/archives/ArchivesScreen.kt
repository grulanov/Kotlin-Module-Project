package presentation.screens.archives

import logic.repositories.ArchivesRepository
import presentation.common.ScreenListManager
import presentation.core.BaseScreen
import presentation.core.ScreenEventsHandler
import presentation.screens.archiveCreation.ArchiveCreationBuilder
import presentation.screens.notes.NotesBuilder
import java.util.UUID

class ArchivesScreen(
        eventsHandler: ScreenEventsHandler?,
        private val screenListManager: ScreenListManager,
        private val archivesRepository: ArchivesRepository,
        private val notesBuilder: NotesBuilder,
        private val archiveCreationBuilder: ArchiveCreationBuilder
): BaseScreen(eventsHandler), ScreenListManager.EventsHandler<UUID> {
    init {
        screenListManager.newItemTitle = "Создать архив"
    }

    override fun start() {
        val archivesItemList = archivesRepository.getArchives().map { ScreenListManager.ListItem(it.id, it.name) }
        screenListManager.start("Список архивов:", archivesItemList, this)
    }

    override fun onNewItem() {
        val archiveScreen = archiveCreationBuilder.build(object : ScreenEventsHandler {
            override fun onFinish() {
                start()
            }
        })
        archiveScreen.start()
    }

    override fun onExit() {
        eventsHandler?.onFinish()
    }

    override fun onItemSelection(id: UUID) {
        val notesScreen = notesBuilder.build(id, object : ScreenEventsHandler {
            override fun onFinish() {
                start()
            }
        })
        notesScreen.start()
    }
}