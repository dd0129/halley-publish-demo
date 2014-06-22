package com.dianping.jgit;

import com.dianping.common.Const;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

import java.io.File;

/**
 * Created by hongdi.tang on 14-6-19.
 */
public class JGitUtils {

    public static boolean pull(String remoteGitFile,String projectName){
        try{
            String localGitFile = Const.MASTER_GIT_DIR +File.separator + projectName +File.separator+ ".git";
            Repository repository = new FileRepository(localGitFile);
            File gitFile = new File(localGitFile);
            if(!gitFile.exists()){
                Git.cloneRepository().setURI(remoteGitFile)
                        .setDirectory(new File(Const.MASTER_GIT_DIR + File.separator + projectName)).call();
            }else{
                Git git = new Git(repository);
                PullResult pullResult = git.pull().call();
                System.out.println(pullResult);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
