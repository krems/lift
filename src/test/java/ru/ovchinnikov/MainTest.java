package ru.ovchinnikov;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MainTest {
    private boolean success = false;
    private Main main = new Main() {
        @Override
        protected CommandListener buildCommandListener(final int floorsNum, final double floorHeight,
                                                       final double speed, final int doorsOpenedTime) {
            return new CommandListener(floorsNum, floorHeight, speed, doorsOpenedTime, 1) {
                @Override
                public void start() {
                    success = true;
                }
            };
        }
    };

    @Before
    public void setUp() {
        success = false;
    }

    @Test
    public void testParseArgs_shouldSuccess_whenAllValidShortArgsProvided() {
        String[] args = {
                "-f", "6",
                "-fh", "2.7",
                "-v", "3.1",
                "-ot", "7"
        };
        main.parseArgs(args);
        assertTrue(success);
    }

    @Test
    public void testParseArgs_shouldSuccess_whenAllValidLongArgsProvided() {
        String[] args = {
                "--floors", "6",
                "--floor-height", "2.7",
                "--velocity", "3.1",
                "--opened-time", "7"
        };
        main.parseArgs(args);
        assertTrue(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenNoArgsProvided() {
        String[] args = {};
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenNoFloorArgsProvided() {
        String[] args = {
                "-fh", "2.7",
                "-v", "3.1",
                "-ot", "7"
        };
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenNoHeightArgsProvided() {
        String[] args = {
                "-f", "6",
                "-v", "3.1",
                "-ot", "7"
        };
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenNoVelocityArgsProvided() {
        String[] args = {
                "-f", "6",
                "-fh", "2.7",
                "-ot", "7"
        };
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenNoOpenedDoorsTimeoutArgsProvided() {
        String[] args = {
                "-f", "6",
                "-fh", "2.7",
                "-v", "3.1"
        };
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenFloorArgIsNotInteger() {
        String[] args = {
                "-f", "6.3",
                "-fh", "2.7",
                "-v", "3.1",
                "-ot", "7"
        };
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenDoorsTimeoutArgIsNotInteger() {
        String[] args = {
                "-f", "6",
                "-fh", "2.7",
                "-v", "3.1",
                "-ot", "7.22"
        };
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenFloorHeightArgIsNotFloat() {
        String[] args = {
                "-f", "6",
                "-fh", "ABCD",
                "-v", "3.1",
                "-ot", "7"
        };
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenVelocityArgIsNotFloat() {
        String[] args = {
                "-f", "6.3",
                "-fh", "2.7",
                "-v", "EFGH",
                "-ot", "7"
        };
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenFloorArgIsNotInValidRange_LessThanMin() {
        String[] args = {
                "-f", String.valueOf(Main.MIN_NUMBER_OF_FLOORS - 1),
                "-fh", "2.7",
                "-v", "3.1",
                "-ot", "7"
        };
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenFloorArgIsNotInValidRange_MoreThanMax() {
        String[] args = {
                "-f", String.valueOf(Main.MAX_NUMBER_OF_FLOORS + 1),
                "-fh", "2.7",
                "-v", "3.1",
                "-ot", "7"
        };
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenNoArgForFloorArgsProvided() {
        String[] args = {
                "-f",
                "-fh", "2.7",
                "-v", "3.1",
                "-ot", "7"
        };
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenNoArgForHeightArgsProvided() {
        String[] args = {
                "-f", "6",
                "-fh",
                "-v", "3.1",
                "-ot", "7"
        };
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenNoArgForVelocityArgsProvided() {
        String[] args = {
                "-f", "6",
                "-fh", "2.7",
                "-v",
                "-ot", "7"
        };
        main.parseArgs(args);
        assertFalse(success);
    }

    @Test
    public void testParseArgs_shouldFail_whenNoArgForDoorsTimeoutArgsProvided() {
        String[] args = {
                "-f", "6",
                "-fh", "2.7",
                "-v", "3.1",
                "-ot"
        };
        main.parseArgs(args);
        assertFalse(success);
    }
}
