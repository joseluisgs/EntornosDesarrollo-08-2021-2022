package models.named

import java.util.*
import javax.inject.Inject

class AppCollaborator
@Inject constructor() : IAppCollaborator {
    private val id = UUID.randomUUID().toString()

    override fun getName(): String {
        return "AppCollaborator id: $id"
    }

    override fun toString(): String {
        return "AppCollaborator{" +
                "id='" + id + '\'' +
                '}'
    }
}
