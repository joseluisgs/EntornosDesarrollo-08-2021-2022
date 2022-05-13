package models.named.services

class ApiServiceProduction : ApiService() {
    override val data: String
        get() = "I am Production Api Service"
}
