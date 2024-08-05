package presentation.core

interface ScreenEventsHandler {
    fun onFinish()
}

abstract class BaseScreen(
        protected val eventsHandler: ScreenEventsHandler?
): Screen