// Automatically generated - do not modify!

package web.rtc

import js.core.EpochTimeStamp
import js.core.ReadonlyArray

/** [MDN Reference](https://developer.mozilla.org/docs/Web/API/RTCCertificate) */
sealed external class RTCCertificate {
    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/RTCCertificate/expires) */
    val expires: EpochTimeStamp

    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/RTCCertificate/getFingerprints) */
    fun getFingerprints(): ReadonlyArray<RTCDtlsFingerprint>
}
