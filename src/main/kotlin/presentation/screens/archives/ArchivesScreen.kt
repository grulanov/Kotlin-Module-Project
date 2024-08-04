package presentation.screens.archives

import logic.repositories.ArchivesRepository
import presentation.common.ScreenListManager
import presentation.core.BaseScreen
import presentation.core.ScreenEventsHandler
import utils.ScannerProvider
import java.util.UUID

class ArchivesScreen(
        eventsHandler: ScreenEventsHandler?,
        private val screenListManager: ScreenListManager,
        private val archivesRepository: ArchivesRepository
): BaseScreen(eventsHandler), ScreenListManager.EventsHandler<UUID> {
    init {
        screenListManager.newItemTitle = "Создать архив"
    }

    override fun start() {
        val archivesItemList = archivesRepository.getArchives().map { ScreenListManager.ListItem(it.id, it.name) }
        screenListManager.start("Список архивов:", archivesItemList, this)
    }

    override fun onNewItem() {
        TODO("Not yet implemented")
    }

    override fun onExit() {
        eventsHandler?.onFinish()
    }

    override fun onItemSelection(id: UUID) {
        TODO("Not yet implemented")
    }
}