package github.ouchadam.common

import android.app.Application
import github.ouchadam.modules.Modules

abstract class BonzoBaseApplication : Application() {

    abstract var modules: Modules

}
