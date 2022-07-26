package io.blacketron

import io.blacketron.model.ApiResponse
import io.blacketron.repository.HeroRepository
import io.blacketron.repository.NEXT_PAGE_KEY
import io.blacketron.repository.PREVIOUS_PAGE_KEY
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    private val heroRepository: HeroRepository by inject(HeroRepository::class.java)
    @Test
    fun `access root endpoint, assert correct information`() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status()
                )
                assertEquals(
                    expected = "Welcome to Boruto API",
                    actual = response.content
                )
            }
        }
    }

    @Test
    fun `access all heroes endpoint, query all pages, assert correct information`(){
        withTestApplication(Application::module) {
            val pages = 1..5
            val heroes = listOf(
                heroRepository.page1,
                heroRepository.page2,
                heroRepository.page3,
                heroRepository.page4,
                heroRepository.page5,

            )
            fun calculatePage(page: Int): Map<String, Int?>{
                var prevPage: Int? = page
                var nextPage: Int? = page

                if(page == 1){
                    prevPage = null
                }
                if (page == 5){
                    nextPage = null
                }
                if(page in 2..5){
                    prevPage = prevPage?.minus(1)
                }
                if (page in 1 .. 4){
                    nextPage = nextPage?.plus(1)
                }
                return mapOf(PREVIOUS_PAGE_KEY to prevPage, NEXT_PAGE_KEY to nextPage)
            }

            pages.forEach { page ->
                handleRequest(HttpMethod.Get, "/boruto/heroes?page=$page").apply {
                    val expected = ApiResponse(
                        success = true,
                        message = "OK",
                        prevPage = calculatePage(page)["prevPage"] ,
                        nextPage = calculatePage(page)["nextPage"],
                        heroes = heroes[page - 1]
                    )
                    val actual = Json.decodeFromString<ApiResponse>(response.content.toString())

                    assertEquals(
                        expected = HttpStatusCode.OK,
                        actual = response.status()
                    )
                    assertEquals(
                        expected = expected,
                        actual = actual
                    )
                }
            }
        }
    }

    @Test
    fun `access all heroes endpoint, query non existing page, assert error`(){
        withTestApplication(Application::module) {

            handleRequest(HttpMethod.Get, "/boruto/heroes?page=6").apply {
                val expected = ApiResponse(
                    success = false,
                    message = "Page not found"
                )
                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())

                assertEquals(
                    expected = HttpStatusCode.NotFound,
                    actual = response.status()
                )
                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }
    }

    @Test
    fun `access all heroes endpoint, query invalid page, assert error`(){
        withTestApplication(Application::module) {

            handleRequest(HttpMethod.Get, "/boruto/heroes?page=asdfafm").apply {
                val expected = ApiResponse(
                    success = false,
                    message = "Invalid page number"
                )
                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())

                assertEquals(
                    expected = HttpStatusCode.BadRequest,
                    actual = response.status()
                )
                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }
    }
}