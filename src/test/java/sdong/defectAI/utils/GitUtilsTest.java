package sdong.defectAI.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.junit.Test;

import sdong.defectAI.exception.GitException;

public class GitUtilsTest {

	@Test
	public void testCreateNewRepository() {
		try {
			Repository repository = GitUtils.createNewRepository("D:/temp/gittest");
			assertEquals("D:\\temp\\gittest", repository.getDirectory().getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetRepository() {
		try {
			Repository repository = GitUtils.getRepository("D:/temp/gittest");
			assertEquals("D:\\temp\\gittest\\.git", repository.getDirectory().getPath());
		} catch (IOException e) {
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
		} catch (IOException | GitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGitCommite() {
		try {
			Repository repository = GitUtils.getRepository("D:/temp/gittest");
			GitUtils.gitCommit(repository, "commit message");
			GitUtils.gitStatus(repository);
			assertEquals("D:\\temp\\gittest\\.git", repository.getDirectory().getPath());
		} catch (IOException | GitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
