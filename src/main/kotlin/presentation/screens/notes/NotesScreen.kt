package presentation.screens.notes

import logic.usecases.ArchivedNotesUseCase
import presentation.common.ScreenListManager
import presentation.core.BaseScreen
import presentation.core.ScreenEventsHandler
import presentation.screens.note.NoteBuilder
import presentation.screens.noteCreation.NoteCreationBuilder
import java.util.UUID

class NotesScreen(
        private val archiveId: UUID,
        eventsHandler: ScreenEventsHandler?,
        private val screenListManager: ScreenListManager,
        private val archivedNotesUseCase: ArchivedNotesUseCase,
        private val noteCreationBuilder: NoteCreationBuilder,
        private val noteBuilder: NoteBuilder
): BaseScreen(eventsHandler), ScreenListManager.EventsHandler<UUID> {
    init {
        screenListManager.newItemTitle = "Создать заметку"
        screenListManager.exitTitle = "Назад"
    }

    override fun start() {
        val notesByArchiveId = archivedNotesUseCase.getNotesByArchiveId(archiveId)
        if (notesByArchiveId == null) {
            println("Архива с id `$archiveId` не найдено")
            eventsHandler?.onFinish()
            return
        }

        val notesItemList = notesByArchiveId.map { ScreenListManager.ListItem(it.id, it.title) }
        screenListManager.start("Список заметок:", notesItemList, this)
    }

    override fun onNewItem() {
        val noteCreationScreen = noteCreationBuilder.build(archiveId, object : ScreenEventsHandler {
            override fun onFinish() {
                start()
            }
        })
        noteCreationScreen.start()
    }

    override fun onExit() {
        eventsHandler?.onFinish()
    }

    override fun onItemSelection(id: UUID) {
        val noteScreen = noteBuilder.build(id, object : ScreenEventsHandler {
            override fun onFinish() {
                start()
            }
        })
        noteScreen.start()
    }
}