package com.dianping;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.BaseRepositoryBuilder;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by adima on 14-6-13.
 */
public class JGitTest {
    @Test
    public void publish()  {
        try{
            String localPath = "e:/gittest";
            String remotePath = "https://github.com/dd0129/dp-mercury.git";
            String fileName = "/mercury-web/src/main/java/com/dianping/data/warehouse/masterdata/core/dao/impl/BaseDAOImpl.java";
            Repository repository = new FileRepository(localPath + "/.git");
            File gitFile = new File(localPath + "/.git");
            if(!gitFile.exists()){
                Git.cloneRepository().setURI(remotePath).setDirectory(new File(localPath)).call();
            }
            Git git = new Git(repository);
            PullResult pullResult = git.pull().call();
            File file = new File(localPath + fileName);


            System.out.println(file);
        }catch(Exception e){
            e.printStackTrace();;
        }finally{

        }
    }

    @Test
    public void rollback(){
        try{
            String localPath = "e:/gittest";
            String remotePath = "https://github.com/dd0129/dp-mercury.git";
            String fileName = "/test.txt";
            Repository repository = new FileRepository(localPath + "/.git");
            File gitFile = new File(localPath + "/.git");
            if(!gitFile.exists()){
                Git.cloneRepository().setURI(remotePath).setDirectory(new File(localPath)).call();
            }
            Git git = new Git(repository);
            PullResult pullResult = git.pull().call();

            Iterator<RevCommit> logs = git.log().call().iterator();
            while(logs.hasNext()){
                try{
                    RevCommit commit = logs.next();
//                System.out.println(commit.getCommitTime());
//                System.out.println(commit.getFullMessage());
//                System.out.println(commit.getAuthorIdent());
                    System.out.println(commit.getName());
                    Random r = new Random();
                    int num = r.nextInt(200);
                    git.branchCreate().setName("tmp"+num).setStartPoint(commit).call();
                    git.checkout().setName("tmp"+num).call();
                    File file = new File(localPath + fileName);
                    String s = FileUtils.readFileToString(file);
                    System.out.println(s);
                    //git.branchDelete().setBranchNames("tmp"+num).call();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }


        }catch(Exception e){
            e.printStackTrace();;
        }finally{

        }
    }

    @Test
    public void checkoutTest(){
        try{
            String localPath = "e:/gittest";
            String remotePath = "https://github.com/dd0129/dp-mercury.git";
            String fileName = "/test.txt";
            Repository repository = new FileRepository(localPath + "/.git");
            File gitFile = new File(localPath + "/.git");
            if(!gitFile.exists()){
                Git.cloneRepository().setURI(remotePath).setDirectory(new File(localPath)).call();
            }
            Git git = new Git(repository);
            //PullResult pullResult = git.pull().call();
            git.checkout().setName("tmp190").call();
            File file = new File(localPath + fileName);
            String s = FileUtils.readFileToString(file);
            System.out.println(s);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
