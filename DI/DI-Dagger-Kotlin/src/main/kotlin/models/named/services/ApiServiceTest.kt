package models.named.services

class ApiServiceTest : ApiService() {
    override val data: String
        get() = "I am test Api Service"
}
