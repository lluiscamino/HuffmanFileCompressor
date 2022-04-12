package logic.model.transformation;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransformationTest {
    @Test
    void fileSizeRatio() {
        Transformation transformation = new Transformation(null, null, 4, 2, Duration.ZERO, null, null);
        assertEquals(2, transformation.fileSizeRatio());
    }

    @Test
    void humanReadableDuration() {
        Duration[] durations = {Duration.ZERO, Duration.ofSeconds(20), Duration.ofSeconds(90), Duration.ofMinutes(
                90), Duration.ofHours(7).plusMinutes(34)};
        String[] expectedHumanReadableRepresentations = {"0s", "20s", "1m 30s", "1h 30m", "7h 34m"};
        assertEquals(durations.length, expectedHumanReadableRepresentations.length);
        for (int i = 0; i < durations.length; i++) {
            Duration duration = durations[i];
            String expectedHumanReadableRepresentation = expectedHumanReadableRepresentations[i];
            Transformation transformation = new Transformation(null, null, 0, 0, duration, null, null);
            assertEquals(expectedHumanReadableRepresentation, transformation.humanReadableDuration());
        }
    }
}