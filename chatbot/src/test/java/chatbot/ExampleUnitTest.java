package chatbot;

import org.junit.Test;

import java.io.IOException;

import chatbot.services.ParsingHtmlService;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testGetHoliday() throws IOException {
        assertEquals("Праздников нет", ParsingHtmlService.getHoliday("18 марта 2020"));
        assertEquals("День моряка-подводника; ", ParsingHtmlService.getHoliday("19 марта 2020"));
        assertEquals("Международный день без мяса; Международный день счастья; День французского языка; Международный день астрологии; ", ParsingHtmlService.getHoliday("20 марта 2020"));
    }

    @Test
    public void TestGetDates() {
        assertEquals(new String[]{"21 February 2020", "20 December 2020"}, AI.getDates("праздник 21.02.2020, 20.12.2020,"));
        assertEquals(new String[]{"1 January 2020"}, AI.getDates("праздник 01.01.2020"));
        assertEquals(new String[]{"1 January 2020"}, AI.getDates("праздник 1.01.2020"));
        assertNull(AI.getDates("праздник 4.1.202"));
    }
}