package com.xlg.android.room;

import com.socks.library.KLog;

public class RoomPoolMain {
    private MyPoolRoom room;

    public MyPoolRoom getRoom() {
        return room;
    }

    public void setRoom(MyPoolRoom room) {
        this.room = room;
    }

    public void Start( int userId, String userPwd, String ip, int port) {
        room = new MyPoolRoom();

//		room.getChannel().setRoomID(10000);
//		room.getChannel().setUserID(18);
//		room.getChannel().setUserPwd("723105");
//		room.getChannel().Connect("121.43.63.101", 10927);
        room.getChannel().setUserID(userId);
        room.getChannel().setUserPwd(userPwd);
        room.getChannel().Connect(ip, port);
        KLog.e(userId+" "+userPwd+" "+ip+port);
        try {
            for (int i = 0; true; i++) {
                // 暂停10秒
                Thread.sleep(1000 * 10);
                if (room.isOK()) {
                    // 发起心跳
                    room.getChannel().SendKeepAliveReq();
                } else {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
