package sdong.defectAI.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import sdong.defectAI.exception.GitException;

public class GitUtils {
	private static final Logger LOG = Logger.getLogger(GitUtils.class);

	public final static String GIT = ".git";
	public final static String DEFAULT_USER = "amdin";
	public final static String DEFAULT_USER_EMAIL = "shendong70@163.com";
	

	public static void initialRepository(String projectPath) throws GitException {
		try {
			// prepare a new folder
			File rootDir = new File(projectPath);
			// initial
			if (new File(projectPath + File.separator + GIT).exists() == false) {
				Git.init().setDirectory(rootDir).call();
			}

		} catch (IllegalStateException | GitAPIException e) {
			LOG.error(e);
			throw new GitException(e);
		}
	}

	public static Repository createNewRepository(String projectPath) throws GitException {
		try {
			// prepare a new folder
			File localPath = new File(projectPath);
			if (localPath.exists()) {
				throw new IOException("Path for:" + projectPath + " already exist.");
			}

			// create the directory
			Repository repository = FileRepositoryBuilder.create(new File(localPath, GIT));
			repository.create();

			return repository;
		} catch (IOException e) {
			LOG.error(e);
			throw new GitException(e);
		}
	}

	public static Repository getRepository(String projectPath) throws GitException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		try (Repository repository = builder.setGitDir(new File(projectPath, GIT)).readEnvironment().findGitDir()
				.build()) {
			LOG.debug("Get repository: " + repository.getDirectory());
			return repository;
		} catch (IOException e) {
			LOG.error(e);
			throw new GitException(e);
		}
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

	public static RevCommit gitCommit(String projectPath, String remark) throws GitException {
		RevCommit revCommit = null;
		try {
			Git git = Git.open(new File(projectPath));

			git.add().addFilepattern(".").call();
			revCommit = git.commit().setCommitter(DEFAULT_USER, DEFAULT_USER_EMAIL).setMessage(remark).call();
			LOG.debug("Commit id:" + revCommit.getName());

			git.status().call();

			git.close();
		} catch (GitAPIException | IOException e) {
			LOG.error(e);
			throw new GitException(e);
		}
		return revCommit;
	}

	public static String commitToGitRepository(String gitRoot, String remark) throws GitException {
		try {
			File rootDir = new File(gitRoot);

			// initial
			if (new File(gitRoot + File.separator + GIT).exists() == false) {
				Git.init().setDirectory(rootDir).call();
			}

			// 打开git仓库
			Git git = Git.open(rootDir);

			RevCommit revCommit = git.commit().setCommitter(DEFAULT_USER, DEFAULT_USER_EMAIL).setMessage(remark).call();

			git.close();

			return revCommit.getName();
		} catch (IllegalStateException | GitAPIException | IOException e) {
			LOG.error(e);
			throw new GitException(e);
		}
	}

	public static List<String> getChangedFiles(String gitRoot) throws GitException {
		List<String> updateFiles = new ArrayList<String>();
		try {
			File rootDir = new File(gitRoot);

			// 打开git仓库
			Git git = Git.open(rootDir);

			// 判断工作区与暂存区的文件内容是否有变更
			List<DiffEntry> diffEntries = git.diff().setShowNameAndStatusOnly(true).call();
			if (diffEntries == null || diffEntries.size() == 0) {
				throw new GitException("There is no change.");
			}
			// 被修改过的文件

			ChangeType changeType;
			for (DiffEntry entry : diffEntries) {
				changeType = entry.getChangeType();
				switch (changeType) {
				case ADD:
				case COPY:
				case RENAME:
				case MODIFY:
					updateFiles.add(entry.getNewPath());
					break;
				case DELETE:
					updateFiles.add(entry.getOldPath());
					break;
				}
			}

		} catch (IOException | GitAPIException e) {
			LOG.error(e);
			throw new GitException(e);
		}
		return updateFiles;
	}
}
