package presentation.screens.noteCreation

import di.AppContainer
import presentation.core.BaseBuilder
import presentation.core.Screen
import presentation.core.ScreenEventsHandler
import java.util.UUID

interface NoteCreationBuilder {
    fun build(archiveId: UUID, eventsHandler: ScreenEventsHandler?): Screen
}

class NoteCreationBuilderImpl(
        appContainer: AppContainer
): BaseBuilder(appContainer), NoteCreationBuilder {
    override fun build(archiveId: UUID, eventsHandler: ScreenEventsHandler?): Screen {
        return NoteCreationScreen(
                archiveId,
                eventsHandler,
                appContainer.archivedNotesUseCase,
                appContainer.scannerProvider
        )
    }
}