package presentation.screens.archiveCreation

import di.AppContainer
import presentation.core.BaseBuilder
import presentation.core.Screen
import presentation.core.ScreenEventsHandler

interface ArchiveCreationBuilder {
    fun build(eventsHandler: ScreenEventsHandler?): Screen
}

class ArchiveCreationBuilderImpl(
        appContainer: AppContainer
): BaseBuilder(appContainer), ArchiveCreationBuilder {
    override fun build(eventsHandler: ScreenEventsHandler?): Screen {
        return ArchiveCreationScreen(
                eventsHandler,
                appContainer.archivesRepository,
                appContainer.scannerProvider
        )
    }
}