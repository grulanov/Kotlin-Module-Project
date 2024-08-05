package presentation.screens.note

import logic.repositories.NotesRepository
import presentation.core.BaseScreen
import presentation.core.ScreenEventsHandler
import utils.ScannerProvider
import java.util.UUID

class NoteScreen(
        private val noteId: UUID,
        eventsHandler: ScreenEventsHandler?,
        private val notesRepository: NotesRepository,
        private val scannerProvider: ScannerProvider
): BaseScreen(eventsHandler) {
    override fun start() {
        val note = notesRepository.getNoteById(noteId)
        if (note == null) {
            println("Заметка с id `$noteId` не найдена")
            eventsHandler?.onFinish()
            return
        }

        println("\nЗаметка: ${note.title}")
        println(note.text)

        println("\nЧтобы вернуться, введите любой символ")
        scannerProvider.scanner.nextLine()
        eventsHandler?.onFinish()
    }
}