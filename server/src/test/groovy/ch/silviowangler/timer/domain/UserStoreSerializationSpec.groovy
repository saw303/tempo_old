package ch.silviowangler.timer.domain

import ch.silviowangler.timer.gson.GsonProvider
import spock.lang.Specification

import java.time.OffsetDateTime
import java.time.ZoneOffset

/**
 * @author Silvio Wangler
 */
class UserStoreSerializationSpec extends Specification {


    void "Read user store"() {

        given:
        def json = '''{
  "userDetails": {
    "name": "Bar",
    "firstname": "Foo"
  },
  "records": [
    {
      "id": "1",
      "from": "2017-03-13T08:00Z",
      "to": "2017-03-13T12:15Z"
    }
  ]
}
'''

        when:
        UserStore userStore = GsonProvider.gson().fromJson(json, UserStore)

        then:
        userStore.userDetails.name == 'Bar'
        userStore.userDetails.firstname == 'Foo'

        and:
        userStore.records.size() == 1

        and:
        userStore.records[0].id == '1'
        userStore.records[0].from == OffsetDateTime.of(2017, 3, 13, 8, 0, 0, 0, ZoneOffset.UTC)
        userStore.records[0].to == OffsetDateTime.of(2017, 3, 13, 12, 15, 0, 0, ZoneOffset.UTC)
    }
}
