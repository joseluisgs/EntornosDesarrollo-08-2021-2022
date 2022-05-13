package DI.presenter

import dagger.Component
import models.presenter.MyView


@Component
interface GrafoDependencias {
    fun inject(myClass: MyView?)
}
