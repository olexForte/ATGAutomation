package com.fortegrp.at.content.modules.common

import com.fortegrp.at.content.modules.login.NavBarModule
import geb.Module
import groovy.util.logging.Slf4j

@Slf4j
class ModalDialogModule extends Module {
    static MODAL_DIALOG_LOCATOR = 'div.modal-dialog'
    static MODAL_DIALOG_TITLE_LOCATOR = '.modal-title'

    static content = {
        dialogTitle { $(MODAL_DIALOG_TITLE_LOCATOR) }
        closeIcon { $("button.close") }
    }

}
