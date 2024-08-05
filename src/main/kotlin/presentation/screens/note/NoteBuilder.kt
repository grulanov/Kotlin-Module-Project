package presentation.screens.note

import di.AppContainer
import presentation.core.BaseBuilder
import presentation.core.Screen
import presentation.core.ScreenEventsHandler
import java.util.UUID

interface NoteBuilder {
    fun build(noteId: UUID, eventsHandler: ScreenEventsHandler?): Screen
}

class NoteBuilderImpl(
        appContainer: AppContainer
): BaseBuilder(appContainer), NoteBuilder {
    override fun build(noteId: UUID, eventsHandler: ScreenEventsHandler?): Screen {
        return NoteScreen(
                noteId,
                eventsHandler,
                appContainer.notesRepository,
                appContainer.scannerProvider
        )
    }
}