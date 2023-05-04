package kamil.degree.cardetailingapp.model

data class Business(
    val name: String?= null,
    val services: List<Service> = emptyList(),
    val description: String? = null
)
