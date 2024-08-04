package presentation.core

import utils.ScannerProvider

interface ScreenEventsHandler {
    fun onFinish()
}

abstract class BaseScreen(
        protected val eventsHandler: ScreenEventsHandler?
): Screen