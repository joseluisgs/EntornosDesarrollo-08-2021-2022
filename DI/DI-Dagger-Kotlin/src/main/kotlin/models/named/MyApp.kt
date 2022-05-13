package models.named

import java.util.*
import javax.inject.Inject
import javax.inject.Named


class MyApp
@Inject constructor(
    private val collaborator: IAppCollaborator,
    @Named("Production") private val apiService: IApiService
) {
    private val id = UUID.randomUUID().toString()


    fun myCollaborator(): String {
        return collaborator.getName()
    }

    fun apiService(): String {
        return apiService.data
    }

    override fun toString(): String {
        return "MyApp{" +
                "id='" + id + '\'' +
                ", collaborator=" + collaborator +
                ", apiService=" + apiService +
                '}'
    }
}
