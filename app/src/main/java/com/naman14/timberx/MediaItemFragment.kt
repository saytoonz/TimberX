package com.naman14.timberx

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.naman14.timberx.ui.albums.AlbumsFragment
import com.naman14.timberx.ui.artist.ArtistFragment
import com.naman14.timberx.ui.folders.FolderFragment
import com.naman14.timberx.ui.playlist.PlaylistFragment
import com.naman14.timberx.ui.songs.SongsFragment
import com.naman14.timberx.util.InjectorUtils
import com.naman14.timberx.util.MediaID

open class MediaItemFragment : NowPlayingFragment() {

    lateinit var mediaType: String
    lateinit var mediaItemFragmentViewModel: MediaItemFragmentViewModel

    var mediaId: String? = null

    companion object {
        fun newInstance(mediaId: MediaID): MediaItemFragment {

            val args = Bundle().apply {
                putString(TimberMusicService.MEDIA_TYPE_ARG, mediaId.type)
                putString(TimberMusicService.MEDIA_ID_ARG, mediaId.mediaId)
            }
            when(mediaId.type?.toInt()) {
                TimberMusicService.TYPE_ALL_SONGS -> return SongsFragment().apply {
                    arguments = args
                }
                TimberMusicService.TYPE_ALL_ALBUMS -> return AlbumsFragment().apply {
                    arguments = args
                }
                TimberMusicService.TYPE_ALL_PLAYLISTS -> return PlaylistFragment().apply {
                    arguments = args
                }
                TimberMusicService.TYPE_ALL_ARTISTS -> return ArtistFragment().apply {
                    arguments = args
                }
                TimberMusicService.TYPE_ALL_FOLDERS -> return FolderFragment().apply {
                    arguments = args
                }
                else -> return SongsFragment().apply {
                    arguments = args
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Always true, but lets lint know that as well.
        val context = activity ?: return
        mediaType = arguments?.getString(TimberMusicService.MEDIA_TYPE_ARG) ?: return
        mediaId = arguments?.getString(TimberMusicService.MEDIA_ID_ARG)

        mediaItemFragmentViewModel = ViewModelProviders
                .of(this, InjectorUtils.provideMediaItemFragmentViewModel(context, MediaID(mediaType, mediaId)))
                .get(MediaItemFragmentViewModel::class.java)
    }
}
