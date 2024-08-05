package presentation.screens.noteCreation

import logic.models.Note
import logic.usecases.ArchivedNotesUseCase
import logic.usecases.ArchivedNotesUseCaseError
import logic.usecases.ArchivedNotesUseCaseException
import presentation.core.BaseScreen
import presentation.core.ScreenEventsHandler
import utils.ScannerProvider
import java.util.UUID

class NoteCreationScreen(
        private val archiveId: UUID,
        eventsHandler: ScreenEventsHandler?,
        private val archivedNotesUseCase: ArchivedNotesUseCase,
        private val scannerProvider: ScannerProvider
): BaseScreen(eventsHandler) {

    companion object {
        const val CANCEL_COMMAND = "отменить"
    }

    override fun start() {
        do {
            println("Введите название заметки или `Отменить`, чтобы вернуться назад:")
            val noteTitle = scannerProvider.scanner.nextLine()
            when {
                noteTitle.isEmpty() -> {
                    println("Название не может быть пустым")
                    continue
                }
                noteTitle.lowercase() == CANCEL_COMMAND -> {
                    eventsHandler?.onFinish()
                    return
                }
            }

            println("Введите текст заметки или `Отменить`, чтобы вернуться назад:")
            val noteText = scannerProvider.scanner.nextLine()
            when {
                noteText.isEmpty() -> {
                    println("Название не может быть пустым")
                    continue
                }
                noteText.lowercase() == CANCEL_COMMAND -> {
                    eventsHandler?.onFinish()
                    return
                }
            }

            val note = Note(title = noteTitle, text = noteText)
            archivedNotesUseCase.addNoteToArchiveWithId(note, archiveId)
                    .fold(
                            onSuccess = {
                                eventsHandler?.onFinish()
                                return
                            },
                            onFailure = {
                                when ((it as? ArchivedNotesUseCaseException)?.error) {
                                    is ArchivedNotesUseCaseError.ArchiveNotFound -> {
                                        println("Невозможно создать заметку для архива с id `${archiveId}`")
                                        eventsHandler?.onFinish()
                                        return
                                    }
                                    else -> {
                                        println("Неизвестная ошибка. Попробуйте еще раз")
                                    }
                                }
                            }
                    )
        } while (true)
    }
}