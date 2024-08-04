package presentation.screens.archives

import di.AppContainer
import presentation.core.BaseBuilder
import presentation.core.Screen
import presentation.core.ScreenEventsHandler

interface ArchivesBuilder {
    fun build(eventsHandler: ScreenEventsHandler?): Screen
}

class ArchivesBuilderImpl(
        appContainer: AppContainer
): BaseBuilder(appContainer), ArchivesBuilder {
    override fun build(eventsHandler: ScreenEventsHandler?): Screen {
        return ArchivesScreen(
                eventsHandler,
                appContainer.screenListManager,
                appContainer.archivesRepository
        )
    }
}