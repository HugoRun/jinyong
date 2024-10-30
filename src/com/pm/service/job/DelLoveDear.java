package com.pm.service.job;

import com.ben.vo.friend.FriendVO;
import com.ls.web.service.rank.RankService;
import com.web.service.friend.FriendService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class DelLoveDear implements Job {

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        FriendService fs = new FriendService();
        List<FriendVO> list = fs.findLoveDear();
        if (list != null) {
            for (FriendVO fv : list) {
                fs.delLoveDear(fv.getFPk());
                //统计需要
                new RankService().updateAdd(fv.getPPk(), "dear", -1);
            }
        }
    }

}
