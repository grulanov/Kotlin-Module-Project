package presentation.screens.archives

import logic.repositories.ArchivesRepository
import presentation.common.BaseScreen
import presentation.common.ScreenEventsHandler
import utils.ScannerProvider

class ArchivesScreen(
        eventsHandler: ScreenEventsHandler?,
        scannerProvider: ScannerProvider,
        private val archivesRepository: ArchivesRepository
): BaseScreen(eventsHandler, scannerProvider) {
    override fun start() {
        TODO("Not yet implemented")
    }
}