package org.videolan.vlc.viewmodels.tv

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.videolan.medialibrary.media.MediaLibraryItem
import org.videolan.vlc.R
import org.videolan.vlc.providers.medialibrary.*
import org.videolan.vlc.util.CATEGORY_ALBUMS
import org.videolan.vlc.util.CATEGORY_ARTISTS
import org.videolan.vlc.util.CATEGORY_GENRES
import org.videolan.vlc.util.CATEGORY_VIDEOS
import org.videolan.vlc.viewmodels.MedialibraryViewModel


@ExperimentalCoroutinesApi
class MediaBrowserViewModel(context: Context, category: Long) : MedialibraryViewModel(context) {

    val nbColumns = context.resources.getInteger(R.integer.tv_songs_col_count)
    var currentItem: MediaLibraryItem? = null

    val provider = when(category) {
        CATEGORY_ALBUMS -> AlbumsProvider(null, context, this)
        CATEGORY_ARTISTS -> ArtistsProvider(context, this, true)
        CATEGORY_GENRES -> GenresProvider(context, this)
        CATEGORY_VIDEOS -> VideosProvider(null, context, this)
        else -> TracksProvider(null, context, this)
    }
    override val providers = arrayOf(provider)

    class Factory(private val context: Context, private val category: Long): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MediaBrowserViewModel(context.applicationContext, category) as T
        }
    }
}