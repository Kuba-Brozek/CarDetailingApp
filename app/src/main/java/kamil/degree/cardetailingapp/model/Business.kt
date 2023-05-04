package kamil.degree.cardetailingapp.model

data class Business(
    var name: String?= null,
    var services: List<Service> = emptyList(),
    var description: String? = null
)
