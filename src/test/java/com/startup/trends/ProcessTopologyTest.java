package com.startup.trends;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ProcessTopologyTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ProcessTopologyTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ProcessTopologyTest.class );
    }

    /**
     * Test process topology
     */
    public void testProcessTopology()
    {
        assertTrue( true );
    }
}
