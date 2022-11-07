// Automatically generated - do not modify!

package web.animations

import web.events.Event
import web.events.EventHandler
import web.events.EventTarget
import kotlin.js.Promise

open external class Animation :
    EventTarget {
    var currentTime: CSSNumberish?
    var effect: AnimationEffect?
    val finished: Promise<Animation>
    var id: String
    var oncancel: EventHandler<AnimationPlaybackEvent>?
    var onfinish: EventHandler<AnimationPlaybackEvent>?
    var onremove: EventHandler<Event>?
    val pending: Boolean
    val playState: AnimationPlayState
    var playbackRate: Double
    val ready: Promise<Animation>
    val replaceState: AnimationReplaceState
    var startTime: CSSNumberish?
    var timeline: AnimationTimeline?
    fun cancel()
    fun commitStyles()
    fun finish()
    fun pause()
    fun persist()
    fun play()
    fun reverse()
    fun updatePlaybackRate(playbackRate: Number)
}
