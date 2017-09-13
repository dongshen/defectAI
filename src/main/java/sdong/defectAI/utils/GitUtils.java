package sdong.defectAI.utils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import sdong.defectAI.exception.GitException;

public class GitUtils {
	private static final Logger LOG = Logger.getLogger(GitUtils.class);

	public static Repository createNewRepository(String projectPath) throws IOException {
		// prepare a new folder
		File localPath = new File(projectPath);
		if (localPath.exists()) {
			throw new IOException("Path for:" + projectPath + " already exist.");
		}

		// create the directory
		Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"));
		repository.create();

		return repository;
	}

	public static Repository getRepository(String projectPath) throws IOException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		try (Repository repository = builder.setGitDir(new File(projectPath, ".git")).readEnvironment().findGitDir()
				.build()) {
			LOG.debug("Get repository: " + repository.getDirectory());
			return repository;
		}
		// return repository;
	}

	public static Status addAllChange(Repository repository) throws GitException {

		Status status = null;
		try {
			Git git = new Git(repository);

			git.add().addFilepattern(".").call();

			status = git.status().call();

			git.close();
		} catch (NoFilepatternException e) {
			LOG.error(e);
			throw new GitException(e);
		} catch (GitAPIException e) {
			LOG.error(e);
			throw new GitException(e);
		}
		return status;

	}
	
	public static void showStatus(Status status) throws GitException {
		LOG.debug("Added: " + status.getAdded());
		LOG.debug("Changed: " + status.getChanged());
		LOG.debug("Conflicting: " + status.getConflicting());
		LOG.debug("ConflictingStageState: " + status.getConflictingStageState());
		LOG.debug("IgnoredNotInIndex: " + status.getIgnoredNotInIndex());
		LOG.debug("Missing: " + status.getMissing());
		LOG.debug("Modified: " + status.getModified());
		LOG.debug("Removed: " + status.getRemoved());
		LOG.debug("Untracked: " + status.getUntracked());
		LOG.debug("UntrackedFolders: " + status.getUntrackedFolders());
	}

	public static Status gitStatus(Repository repository) throws GitException {
		Status status = null;
		try {
			Git git = new Git(repository);
			status = git.status().call();
			showStatus(status);
			git.close();
		} catch (NoFilepatternException e) {
			LOG.error(e);
			throw new GitException(e);
		} catch (GitAPIException e) {
			LOG.error(e);
			throw new GitException(e);
		}
		
		return status;
	}

	public static RevCommit gitCommit(Repository repository, String message) throws GitException {
		RevCommit revCommit = null;
		try {
			Git git = new Git(repository);

			git.status().call();
			revCommit = git.commit().setMessage(message).call();
			LOG.debug("Commit id:"+revCommit.getName());

			 git.status().call();

			git.close();
		} catch (GitAPIException e) {
			LOG.error(e);
			throw new GitException(e);
		}
		return revCommit;
	}
}
