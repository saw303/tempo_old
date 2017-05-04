package ch.silviowangler.timer.domain


import groovy.transform.Canonical
import groovy.transform.CompileStatic

/**
 * @author Silvio Wangler
 */
@Canonical
@CompileStatic
class UserStore {

    UserDetails userDetails
    List<Record> records
}
