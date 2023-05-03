package kamil.degree.cardetailingapp.model

data class User(
    var id: String? = null,
    var email: String? = null,
    var username: String? = null,
    var birthDate: String? = null, // format dd.MM.yyyy
    var hasBusiness: Boolean? = null
)
