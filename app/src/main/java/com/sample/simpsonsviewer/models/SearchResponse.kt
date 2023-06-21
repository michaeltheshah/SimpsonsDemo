package com.sample.simpsonsviewer.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class SearchResponse(
    @SerialName("Abstract")
    val `abstract`: String,
    @SerialName("AbstractSource")
    val abstractSource: String,
    @SerialName("AbstractText")
    val abstractText: String,
    @SerialName("AbstractURL")
    val abstractURL: String,
    @SerialName("Answer")
    val answer: String,
    @SerialName("AnswerType")
    val answerType: String,
    @SerialName("Definition")
    val definition: String,
    @SerialName("DefinitionSource")
    val definitionSource: String,
    @SerialName("DefinitionURL")
    val definitionURL: String,
    @SerialName("Entity")
    val entity: String,
    @SerialName("Heading")
    val heading: String,
    @SerialName("Image")
    val image: String,
    @SerialName("ImageHeight")
    val imageHeight: Int,
    @SerialName("ImageIsLogo")
    val imageIsLogo: Int,
    @SerialName("ImageWidth")
    val imageWidth: Int,
    @SerialName("meta")
    val meta: Meta,
    @SerialName("Redirect")
    val redirect: String,
    @SerialName("RelatedTopics")
    val relatedTopics: List<RelatedTopic>,
    @SerialName("Results")
    val results: List<String>,
    @SerialName("Type")
    val type: String
) {
    val imageUrl: String
        get() = "https://duckduckgo.com/$image"
}

@Serializable
data class Content(
    @SerialName("data_type")
    val dataType: String,
    @SerialName("label")
    val label: String,
    @SerialName("value")
    val value: String,
    @SerialName("wiki_order")
    val wikiOrder: Int
)

@Serializable
data class Meta(
    @SerialName("attribution")
    val attribution: String?,
    @SerialName("blockgroup")
    val blockgroup: String?,
    @SerialName("created_date")
    val createdDate: String?,
    @SerialName("data_type")
    val dataType: String?,
    @SerialName("description")
    val description: String,
    @SerialName("designer")
    val designer: String?,
    @SerialName("dev_date")
    val devDate: String?,
    @SerialName("dev_milestone")
    val devMilestone: String,
    @SerialName("developer")
    val developer: List<Developer>,
    @SerialName("example_query")
    val exampleQuery: String,
    @SerialName("id")
    val id: String,
    @SerialName("is_stackexchange")
    val isStackexchange: String?,
    @SerialName("js_callback_name")
    val jsCallbackName: String,
    @SerialName("label")
    val label: String?,
    @SerialName("live_date")
    val liveDate: String?,
    @SerialName("maintainer")
    val maintainer: Maintainer,
    @SerialName("name")
    val name: String,
    @SerialName("perl_module")
    val perlModule: String,
    @SerialName("producer")
    val producer: String?,
    @SerialName("production_state")
    val productionState: String,
    @SerialName("repo")
    val repo: String,
    @SerialName("signal_from")
    val signalFrom: String,
    @SerialName("src_domain")
    val srcDomain: String,
    @SerialName("src_id")
    val srcId: Int,
    @SerialName("src_name")
    val srcName: String,
    @SerialName("src_options")
    val srcOptions: SrcOptions,
    @SerialName("src_url")
    val srcUrl: String?,
    @SerialName("status")
    val status: String,
    @SerialName("tab")
    val tab: String,
    @SerialName("topic")
    val topic: List<String>,
    @SerialName("unsafe")
    val unsafe: Int,
    @SerialName("value")
    val value: String?
)

@Parcelize
@Serializable
data class RelatedTopic(
    @SerialName("FirstURL")
    val firstURL: String?,
    @SerialName("Icon")
    val icon: Icon?,
    @SerialName("Result")
    val result: String?,
    @SerialName("Text")
    val text: String?
) : Parcelable

@Serializable
data class Developer(
    @SerialName("name")
    val name: String,
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String
)

@Serializable
data class Maintainer(
    @SerialName("github")
    val github: String
)

@Serializable
data class SrcOptions(
    @SerialName("directory")
    val directory: String,
    @SerialName("is_fanon")
    val isFanon: Int,
    @SerialName("is_mediawiki")
    val isMediawiki: Int,
    @SerialName("is_wikipedia")
    val isWikipedia: Int,
    @SerialName("language")
    val language: String,
    @SerialName("min_abstract_length")
    val minAbstractLength: String,
    @SerialName("skip_abstract")
    val skipAbstract: Int,
    @SerialName("skip_abstract_paren")
    val skipAbstractParen: Int,
    @SerialName("skip_end")
    val skipEnd: String,
    @SerialName("skip_icon")
    val skipIcon: Int,
    @SerialName("skip_image_name")
    val skipImageName: Int,
    @SerialName("skip_qr")
    val skipQr: String,
    @SerialName("source_skip")
    val sourceSkip: String,
    @SerialName("src_info")
    val srcInfo: String
)

@Parcelize
@Serializable
data class Icon(
    @SerialName("Height")
    val height: String,
    @SerialName("URL")
    val url: String,
    @SerialName("Width")
    val width: String
) : Parcelable