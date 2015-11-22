package net.darold.ora2pg;


import org.junit.Test;

public class MainTest {

    @Test
    public void testShowVer() {
	Main.main(new String[] { "-v" });
	Main.main(new String[] { "--version" });
    }

    @Test
    public void testHelp() {
	Main.main(new String[] { "-h" });
	Main.main(new String[] { "--help" });
    }

    @Test
    public void testInitProject() {
	Main.main(new String[] { "--init_project", "test-project" });
    }

    @Test
    public void testTempDir2() {
	Main.main(new String[] { "-T", "test-project" });
    }

    @Test
    public void testTempDir4() {
	Main.main(new String[] { "--temp_dir", "test-project" });
    }
}
