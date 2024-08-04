package presentation.common

import utils.ScannerProvider

interface ScreenEventsHandler {
    fun onFinish()
}

abstract class BaseScreen(
        protected val eventsHandler: ScreenEventsHandler?,
        protected val scannerProvider: ScannerProvider
): Screen