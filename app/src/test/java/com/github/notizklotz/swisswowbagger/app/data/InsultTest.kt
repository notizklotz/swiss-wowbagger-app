package com.github.notizklotz.swisswowbagger.app.data

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class InsultTest {

    @Test
    fun `getAudioUrl - no names`() {
        val insult = Insult(1234, "Huere siech")

        assertThat(insult.getAudioUrl(emptyList()))
            .isEqualTo("https://swiss-wowbagger-ultgi7by3q-oa.a.run.app/1234?format=wav&v=undefined&names=")
    }

    @Test
    fun `getAudioUrl - one name`() {
        val insult = Insult(1234, "Huere siech")

        assertThat(insult.getAudioUrl(listOf("myname")))
            .isEqualTo("https://swiss-wowbagger-ultgi7by3q-oa.a.run.app/1234?format=wav&v=undefined&names=myname")
    }


    @Test
    fun `getAudioUrl - multiple names`() {
        val insult = Insult(1234, "Huere siech")

        assertThat(insult.getAudioUrl(listOf("myname", "othername")))
            .isEqualTo("https://swiss-wowbagger-ultgi7by3q-oa.a.run.app/1234?format=wav&v=undefined&names=myname othername")
    }
}