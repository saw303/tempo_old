package ch.silviowangler.timer.domain

import groovy.transform.Canonical
import groovy.transform.CompileStatic

import java.time.OffsetDateTime

/**
 * @author Silvio Wangler
 */
@Canonical
@CompileStatic
class Record {

    String id
    OffsetDateTime from
    OffsetDateTime to
}
