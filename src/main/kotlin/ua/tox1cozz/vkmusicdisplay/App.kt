package ua.tox1cozz.vkmusicdisplay

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

const val CURRENT_MUSIC_ELEMENT_SELECTOR = "a.current_audio"
var lastSong = "Что-то пошло не так..."

fun main(args: Array<String>) {
    val port = args[0].toInt()
    embeddedServer(Netty, port) {
        routing {
            get("/music/{userId}") {
                val id = call.parameters["userId"]!!
                val pageUrl = "https://vk.com/${id}"
                val currentMusic = try {
                    fetchCurrentPlaying(pageUrl)
                } catch (e: Throwable) {
                    lastSong
                }
                call.respondText(currentMusic)
            }
        }
    }.start(wait = true)
}

private suspend fun fetchCurrentPlaying(pageUrl: String): String = withContext(Dispatchers.IO) {
    val notFoundMessage = "Page $pageUrl not found"
    val document: Document = try {
        Jsoup.connect(pageUrl).get()
    } catch (e: HttpStatusException) {
        if (e.statusCode == 404) {
            return@withContext notFoundMessage
        } else {
            throw e
        }
    }
    val currentAudio: Element? = document.selectFirst(CURRENT_MUSIC_ELEMENT_SELECTOR)
    if (currentAudio != null) {
        currentAudio.text().trim()
    } else {
        val information: Element =
            document.selectFirst("div.message_page_body") ?: return@withContext ""
        val text = information.text().trim()
        return@withContext when {
            text.contains("deleted", true) && text.contains("created", true) -> notFoundMessage
            text.contains("удалена", true) && text.contains("создана", true) -> notFoundMessage
            text.contains("видалено", true) && text.contains("створено", true) -> notFoundMessage
            else -> ""
        }
    }
}