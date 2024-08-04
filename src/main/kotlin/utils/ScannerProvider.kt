package utils

import java.util.Scanner

interface ScannerProvider {
    val scanner: Scanner
    fun closeCurrentScanner()
}

class ScannerProviderImpl: ScannerProvider {
    override val scanner: Scanner
        get() {
            if (_scanner == null) {
                _scanner = Scanner(System.`in`)
            }
            return _scanner!!
        }

    private var _scanner: Scanner? = null

    override fun closeCurrentScanner() {
        _scanner?.close()
        _scanner = null
    }
}