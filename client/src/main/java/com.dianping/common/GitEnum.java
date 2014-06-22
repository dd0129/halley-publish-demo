package com.dianping.common;

/**
 * Created by hongdi.tang on 14-6-19.
 */
public enum GitEnum {
    data_analysis("git@code.dianpingoa.com:pengfei.shen/data_analysis.git"),
    warehouse("git@code.dianpingoa.com:warehouse.git");

    private String gitPath;

    public static String getGitPath(String projectName) {
        GitEnum[] enums = GitEnum.values();
        for(GitEnum gitEnum : enums){
            if(gitEnum.toString().equals(projectName)){
                return gitEnum.gitPath;
            }
        }
        return null;
    }

    private GitEnum(String gitPath){
        this.gitPath = gitPath;
    }

}
