package presentation.screens.archiveCreation

import logic.models.Archive
import logic.repositories.ArchivesRepository
import logic.repositories.ArchivesRepositoryError
import logic.repositories.ArchivesRepositoryException
import presentation.core.BaseScreen
import presentation.core.ScreenEventsHandler
import utils.ScannerProvider

class ArchiveCreationScreen(
        eventsHandler: ScreenEventsHandler?,
        private val archivesRepository: ArchivesRepository,
        private val scannerProvider: ScannerProvider
): BaseScreen(eventsHandler) {
    companion object {
        const val CANCEL_COMMAND = "отменить"
    }

    override fun start() {
        do {
            println("Введите название архива или `Отменить`, чтобы вернуться назад:")
            val archiveName = scannerProvider.scanner.nextLine()
            when {
                archiveName.isEmpty() -> {
                    println("Название не может быть пустым")
                    continue
                }
                archiveName.lowercase() == CANCEL_COMMAND -> {
                    eventsHandler?.onFinish()
                    return
                }
            }

            val archive = Archive(name = archiveName)
            archivesRepository.addArchive(archive)
                    .fold(
                            onSuccess = {
                                eventsHandler?.onFinish()
                                return
                            },
                            onFailure = {
                                when ((it as? ArchivesRepositoryException)?.error) {
                                    is ArchivesRepositoryError.NameConflict -> {
                                        println("Имя уже занято")
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