import com.assignment.catexplorer.data.remote.CatsService
import com.assignment.catexplorer.data.remote.models.CatBreed
import okhttp3.Headers.Companion.toHeaders
import retrofit2.Response

class FakeCatsService(
    private val isPageLimitCrossed: Boolean
) : CatsService {
    override suspend fun getCatBreeds(
        page: Int,
        limit: Int,
        order: String
    ): Response<List<CatBreed>> {

        val nextPage = if (isPageLimitCrossed) {
            "11"
        } else {
            "1"
        }

        val mockHeaders: Map<String, String> = mapOf(
            Pair("pagination-page", nextPage),
            Pair("pagination-count", "100")
        )

        return Response.success(
            listOf(fakeCatData(), fakeCatData()),
            mockHeaders.toHeaders()
        )
    }
}


private fun fakeCatData(): CatBreed {
    return CatBreed(
        weight = null,
        id = "id",
        name = null,
        cfaUrl = null,
        vetstreetUrl = null,
        vcahospitalsUrl = null,
        temperament = null,
        origin = null,
        countryCodes = null,
        countryCode = null,
        description = null,
        lifeSpan = null,
        indoor = null,
        lap = null,
        altNames = null,
        adaptability = null,
        affectionLevel = null,
        childFriendly = null,
        dogFriendly = null,
        energyLevel = null,
        grooming = null,
        healthIssues = null,
        intelligence = null,
        sheddingLevel = null,
        socialNeeds = null,
        strangerFriendly = null,
        vocalisation = null,
        experimental = null,
        hairless = null,
        natural = null,
        rare = null,
        rex = null,
        suppressedTail = null,
        shortLegs = null,
        wikipediaUrl = null,
        hypoallergenic = null,
        referenceImageId = null,
        image = null
    )
}