package presentation.common

import utils.ScannerProvider

interface ScreenListManager {
    interface EventsHandler<ID> {
        fun onNewItem()
        fun onExit()
        fun onItemSelection(id: ID)
    }
    data class ListItem<ID>(val id: ID, val title: String)

    var newItemTitle: String
    var exitTitle: String

    fun <ID> start(
            listTitle: String,
            items: List<ListItem<ID>>,
            eventsHandler: EventsHandler<ID>
    )
}

class ScreenListManagerImpl(private val scannerProvider: ScannerProvider): ScreenListManager {
    override var newItemTitle: String = "Создать"
    override var exitTitle: String = "Выход"

    override fun <ID> start(
            listTitle: String,
            items: List<ScreenListManager.ListItem<ID>>,
            eventsHandler: ScreenListManager.EventsHandler<ID>
    ) {
        val fullListItems: List<String> = listOf(newItemTitle) + items.map { it.title } + listOf(exitTitle)

        do {
            println(listTitle)
            fullListItems.forEachIndexed { index, title ->
                println("$index. $title")
            }

            val userInput = scannerProvider.scanner.nextLine().toIntOrNull()
            if (userInput == null) {
                println("Введите число, чтобы выбрать один из пунктов меню")
                continue
            }
            if (userInput < 0 || userInput > (fullListItems.count() - 1)) {
                println("Введенное число не соответствует ни одному из пунктов меню")
                continue
            }

            when (userInput) {
                0 -> eventsHandler.onNewItem()
                (fullListItems.count() - 1) -> eventsHandler.onExit()
                else -> {
                    val selectedItem = items[userInput - 1]
                    eventsHandler.onItemSelection(selectedItem.id)
                }
            }
            return
        } while (true)
    }
}