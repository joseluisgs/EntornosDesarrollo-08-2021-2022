package models.named.services

import models.named.IApiService
import java.util.*


abstract class ApiService : IApiService {
    private val id = UUID.randomUUID().toString()
    override fun toString(): String {
        return "ApiService{" +
                "id='" + id + '\'' +
                '}'
    }
}
