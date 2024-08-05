package presentation.screens.notes

import di.AppContainer
import presentation.core.BaseBuilder
import presentation.core.Screen
import presentation.core.ScreenEventsHandler
import java.util.UUID

interface NotesBuilder {
    fun build(archiveId:UUID, eventsHandler: ScreenEventsHandler?): Screen
}

class NotesBuilderImpl(
        appContainer: AppContainer
): BaseBuilder(appContainer), NotesBuilder {
    override fun build(archiveId: UUID, eventsHandler: ScreenEventsHandler?): Screen {
        return NotesScreen(
                archiveId,
                eventsHandler,
                appContainer.screenListManager,
                appContainer.archivedNotesUseCase,
                appContainer.screens.noteCreationBuilder,
                appContainer.screens.noteBuilder
        )
    }
}