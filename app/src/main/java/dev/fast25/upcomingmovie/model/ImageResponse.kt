package dev.fast25.upcomingmovie.model

import com.google.gson.annotations.SerializedName

data class ImageResponse(

	@field:SerializedName("backdrops")
	val backdrops: ArrayList<BackdropsItem>? = null,

	@field:SerializedName("posters")
	val posters: ArrayList<PostersItem>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("logos")
	val logos: List<LogosItem?>? = null
)

data class PostersItem(

	@field:SerializedName("aspect_ratio")
	val aspectRatio: Any? = null,

	@field:SerializedName("file_path")
	val filePath: String? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Any? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("iso_639_1")
	val iso6391: String? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class BackdropsItem(

	@field:SerializedName("aspect_ratio")
	val aspectRatio: Any? = null,

	@field:SerializedName("file_path")
	val filePath: String? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Any? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("iso_639_1")
	val iso6391: Any? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class LogosItem(

	@field:SerializedName("aspect_ratio")
	val aspectRatio: Any? = null,

	@field:SerializedName("file_path")
	val filePath: String? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Any? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("iso_639_1")
	val iso6391: String? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)
