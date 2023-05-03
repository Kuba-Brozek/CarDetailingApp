package kamil.degree.cardetailingapp.model

data class User(
    var id: String,
    var email: String,
    var username: String,
    var birthDate: String, // format dd.MM.yyyy
    var hasBusiness: Boolean
)
