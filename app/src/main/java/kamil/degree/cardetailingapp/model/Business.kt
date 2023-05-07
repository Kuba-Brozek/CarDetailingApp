package kamil.degree.cardetailingapp.model

data class Business(
    var name: String?= null,
    var services: MutableList<Service> = mutableListOf(),
    var description: String? = null,
    var address: String? = null
)
