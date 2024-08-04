import di.AppContainer
import presentation.core.ScreenEventsHandler

private val appContainer = AppContainer()

fun main(args: Array<String>) {
    val archivesScreen = appContainer.screens.archivesBuilder.build(object : ScreenEventsHandler {
        override fun onFinish() {
            appContainer.scannerProvider.closeCurrentScanner()
        }
    })
    archivesScreen.start()
}