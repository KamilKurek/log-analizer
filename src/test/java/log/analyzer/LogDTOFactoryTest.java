package log.analyzer;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import log.analyzer.dto.AppServerLogDTO;
import log.analyzer.dto.EventState;
import log.analyzer.dto.SimpleLogDTO;
import log.analyzer.parser.AppServerLogParser;
import log.analyzer.parser.SimpleLogParser;

public class LogDTOFactoryTest {

    private LogDTOFactory factory = new LogDTOFactory(Arrays.asList(new SimpleLogParser(), new AppServerLogParser()));

    @Test
    public void shouldReturnValidSimpleLogDTOWhenProvidedSimpleLogJson() throws Exception {
        // given
        String validSimpleLogJson = "{\"id\":\"scsmbstgrc\", \"state\":\"FINISHED\", \"timestamp\":1491377495218}";
        // when
        SimpleLogDTO result = factory.parseLog(validSimpleLogJson);
        // then
        Assert.assertNotNull(result);
        Assert.assertEquals("scsmbstgrc", result.getId());
        Assert.assertEquals(EventState.FINISHED, result.getEventState());
        Assert.assertEquals(1491377495218l, result.getTimestamp().longValue());
    }

    @Test
    public void shouldReturnValidAppServerLogDTOWhenProvidedAppServerLogJson() throws Exception {
        // given
        String validSimpleLogJson = "{\"id\":\"scsmbstgra\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495217}";
        // when
        SimpleLogDTO result = factory.parseLog(validSimpleLogJson);
        // then
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof AppServerLogDTO);
        AppServerLogDTO appServerLog = (AppServerLogDTO) result;
        Assert.assertEquals("scsmbstgra", appServerLog.getId());
        Assert.assertEquals(EventState.FINISHED, appServerLog.getEventState());
        Assert.assertEquals(1491377495217l, appServerLog.getTimestamp().longValue());
        Assert.assertEquals("12345", appServerLog.getHost());
    }

    @Test
    public void shouldThrowErrorWhenProvidedUnsupportedLogTypeJson() {
        // given
        String validSimpleLogJson = "{\"id\":\"scsmbstgra\", \"state\":\"FINISHED\", \"type\":\"NOT_SUPPORTED_LOG\", \"timestamp\":1491377495217}";
        // when
        try {
            factory.parseLog(validSimpleLogJson);
            Assert.fail("No parser should be found");
        } catch (Exception e) {
            Assert.assertEquals(
                    "Parser not found for json {\"id\":\"scsmbstgra\", \"state\":\"FINISHED\", \"type\":\"NOT_SUPPORTED_LOG\", \"timestamp\":1491377495217}",
                    e.getMessage());
        }
    }

    @Test
    public void shouldThrowErrorWhenIdNotProvidedForSimpleLogJson() {
        // given
        String validSimpleLogJson = "{\"state\":\"FINISHED\", \"timestamp\":1491377495218}";
        // when
        try {
            factory.parseLog(validSimpleLogJson);
            Assert.fail("Simple log parser should throw exception");
        } catch (Exception e) {
            Assert.assertEquals("Json does not contain field id", e.getMessage());
        }
    }
}
