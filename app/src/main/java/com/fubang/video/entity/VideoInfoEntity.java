package com.fubang.video.entity;

import java.util.List;

/**
 * Created by jacky on 2017/7/25.
 */
public class VideoInfoEntity {


    /**
     * RequestId : 64626002-EBD5-4097-8DC2-6FB2DD5C430F
     * VideoBase : {"CreationTime":"2017-07-20T06:03:50Z","CoverURL":"http://www.oooktv.com/snapshot/10578b97111048ebbb0574417934656900001.jpg","Status":"Normal","MediaType":"video","VideoId":"10578b97111048ebbb05744179346569","Duration":"17.730","Title":"/storage/emulated/0/tencent/QQfile_recv/QQ20170630-100506-HD.mp4"}
     * PlayInfoList : {"PlayInfo":[{"Format":"m3u8","Duration":"17.760","Height":640,"Encrypt":0,"PlayURL":"http://www.oooktv.com/10578b97111048ebbb05744179346569/ec471f7a2c4940c4888db0841cc00c6d-4b6ffae84f2e1d243955ecaedcf11a3e.m3u8","Width":960,"Fps":"25.000","Bitrate":"823.727","Definition":"LD","Size":1828676},{"Format":"mp4","Duration":"17.760","Height":640,"Encrypt":0,"PlayURL":"http://www.oooktv.com/10578b97111048ebbb05744179346569/ec471f7a2c4940c4888db0841cc00c6d-5287d2089db37e62345123a1be272f8b.mp4","Width":960,"Fps":"25.000","Bitrate":"742.283","Definition":"LD","Size":1653115}]}
     */

    private String RequestId;
    private VideoBaseBean VideoBase;
    private PlayInfoListBean PlayInfoList;

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    public VideoBaseBean getVideoBase() {
        return VideoBase;
    }

    public void setVideoBase(VideoBaseBean VideoBase) {
        this.VideoBase = VideoBase;
    }

    public PlayInfoListBean getPlayInfoList() {
        return PlayInfoList;
    }

    public void setPlayInfoList(PlayInfoListBean PlayInfoList) {
        this.PlayInfoList = PlayInfoList;
    }

    public static class VideoBaseBean {
        /**
         * CreationTime : 2017-07-20T06:03:50Z
         * CoverURL : http://www.oooktv.com/snapshot/10578b97111048ebbb0574417934656900001.jpg
         * Status : Normal
         * MediaType : video
         * VideoId : 10578b97111048ebbb05744179346569
         * Duration : 17.730
         * Title : /storage/emulated/0/tencent/QQfile_recv/QQ20170630-100506-HD.mp4
         */

        private String CreationTime;
        private String CoverURL;
        private String Status;
        private String MediaType;
        private String VideoId;
        private String Duration;
        private String Title;

        public String getCreationTime() {
            return CreationTime;
        }

        public void setCreationTime(String CreationTime) {
            this.CreationTime = CreationTime;
        }

        public String getCoverURL() {
            return CoverURL;
        }

        public void setCoverURL(String CoverURL) {
            this.CoverURL = CoverURL;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getMediaType() {
            return MediaType;
        }

        public void setMediaType(String MediaType) {
            this.MediaType = MediaType;
        }

        public String getVideoId() {
            return VideoId;
        }

        public void setVideoId(String VideoId) {
            this.VideoId = VideoId;
        }

        public String getDuration() {
            return Duration;
        }

        public void setDuration(String Duration) {
            this.Duration = Duration;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }
    }

    public static class PlayInfoListBean {
        private List<PlayInfoBean> PlayInfo;

        public List<PlayInfoBean> getPlayInfo() {
            return PlayInfo;
        }

        public void setPlayInfo(List<PlayInfoBean> PlayInfo) {
            this.PlayInfo = PlayInfo;
        }

        public static class PlayInfoBean {
            /**
             * Format : m3u8
             * Duration : 17.760
             * Height : 640
             * Encrypt : 0
             * PlayURL : http://www.oooktv.com/10578b97111048ebbb05744179346569/ec471f7a2c4940c4888db0841cc00c6d-4b6ffae84f2e1d243955ecaedcf11a3e.m3u8
             * Width : 960
             * Fps : 25.000
             * Bitrate : 823.727
             * Definition : LD
             * Size : 1828676
             */

            private String Format;
            private String Duration;
            private int Height;
            private int Encrypt;
            private String PlayURL;
            private int Width;
            private String Fps;
            private String Bitrate;
            private String Definition;
            private int Size;

            public String getFormat() {
                return Format;
            }

            public void setFormat(String Format) {
                this.Format = Format;
            }

            public String getDuration() {
                return Duration;
            }

            public void setDuration(String Duration) {
                this.Duration = Duration;
            }

            public int getHeight() {
                return Height;
            }

            public void setHeight(int Height) {
                this.Height = Height;
            }

            public int getEncrypt() {
                return Encrypt;
            }

            public void setEncrypt(int Encrypt) {
                this.Encrypt = Encrypt;
            }

            public String getPlayURL() {
                return PlayURL;
            }

            public void setPlayURL(String PlayURL) {
                this.PlayURL = PlayURL;
            }

            public int getWidth() {
                return Width;
            }

            public void setWidth(int Width) {
                this.Width = Width;
            }

            public String getFps() {
                return Fps;
            }

            public void setFps(String Fps) {
                this.Fps = Fps;
            }

            public String getBitrate() {
                return Bitrate;
            }

            public void setBitrate(String Bitrate) {
                this.Bitrate = Bitrate;
            }

            public String getDefinition() {
                return Definition;
            }

            public void setDefinition(String Definition) {
                this.Definition = Definition;
            }

            public int getSize() {
                return Size;
            }

            public void setSize(int Size) {
                this.Size = Size;
            }
        }
    }
}
