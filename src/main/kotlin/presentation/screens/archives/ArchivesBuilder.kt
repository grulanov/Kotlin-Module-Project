package presentation.screens.archives

import di.AppContainer
import presentation.common.BaseBuilder
import presentation.common.Screen
import presentation.common.ScreenEventsHandler

interface ArchivesBuilder {
    fun build(eventsHandler: ScreenEventsHandler?): Screen
}

class ArchivesBuilderImpl(
        appContainer: AppContainer
): BaseBuilder(appContainer), ArchivesBuilder {
    override fun build(eventsHandler: ScreenEventsHandler?): Screen {
        val archivesScreen = ArchivesScreen(
                eventsHandler,
                appContainer.scannerProvider,
                appContainer.archivesRepository
        )
        return archivesScreen
    }
}