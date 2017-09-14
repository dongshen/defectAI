package sdong.defectAI.utils;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Assert;
import org.junit.Test;

import com.esotericsoftware.minlog.Log;

import sdong.defectAI.exception.GitException;

public class GitUtilsTest {

	@Test
	public void testInitialRepository() {
		try {
			GitUtils.initialRepository("D:/temp/gittest");
			assertEquals(true, new File("D:/temp/gittest", GitUtils.GIT).exists());
		} catch (GitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateNewRepository() {
		try {
			Repository repository = GitUtils.createNewRepository("D:/temp/gittest");
			assertEquals("D:\\temp\\gittest", repository.getDirectory().getPath());
		} catch (GitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetRepository() {
		try {
			Repository repository = GitUtils.getRepository("D:/temp/gittest");
			assertEquals("D:\\temp\\gittest\\.git", repository.getDirectory().getPath());
		} catch (GitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAddAllChange() {
		try {
			Repository repository = GitUtils.getRepository("D:/temp/gittest");
			GitUtils.addAllChange(repository);
			GitUtils.gitStatus(repository);
			assertEquals("D:\\temp\\gittest\\.git", repository.getDirectory().getPath());
		} catch (GitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGitCommite() {
		try {
			RevCommit rev = GitUtils.gitCommit("D:/temp/gittest", "commit message");
			Assert.assertNotNull(rev.name());
		} catch (GitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetChangedFiles() {
		try {
			List<String> changes = GitUtils.getChangedFiles("D:/temp/gittest");
			Log.debug(changes.toString());
			//assertEquals("D:\\temp\\gittest\\.git", repository.getDirectory().getPath());
		} catch (GitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
