package service.music.video;

import entity.Comment;
import entity.MusicVideo;
import org.springframework.stereotype.Service;

import java.util.List;

        /**
        * 对MV的操作的Service
        *
        * @author 蒋靓峣 5.11创建
        * */
@Service
public class VideoService {
            /**
             * @param videoName 按照指定规则查找指定MV
             *                  封装信息搜索的关键字
             * @return List<Video>  返回查找到的MV
             */
            public List<MusicVideo> selectListMusicVideoByVideoName(String videoName){
                return null;
            }
            /**
             *
             * @param singerName 按照指定规则查找指定歌曲
             *                封装信息：歌手名字
             * @return List<MusicVideo>  返回查找到的MV
             */
            public List<MusicVideo> selectListMusicVideoBySingerName(String singerName){
                return null;
            }
            /**
             * @param userId 查看用户收藏的MV
             *                封装信息：用户id
             * @return List<Music>  返回查找到的MV
             */
            public List<MusicVideo> selectListMusicVideoByUserId(String userId){
                return null;
            }
            /**
             * 查看播放量最高的前n首歌曲（看前端的页面）
             *          封装信息：无
             * @return List<MusicVideo>  返回查找到的歌曲
             */
            public List<MusicVideo> selectPlayMusicVideoByPlayCount(){
                return null;
            }
            /**
             * @param musicVideoId 修改指定musicId的歌曲
             *          封装信息：musicId
             * @return int  返回执行结果：
             *                              1为成功，0为失败，-1为系统异常
             */
            public int updateMusicVideoByMusicVideoId(String musicVideoId){
                return 0;
            }
            /**
             * @param musicVideoId 下载指定musicId的歌曲
             *          封装信息：musicId
             * @return int  返回执行结果：
             *                             1为成功，0为失败，-1为系统异常
             */
            public int downLoadMusicVideoByMusicVideoId(String musicVideoId){
                return 0;
            }
            /**
             * @param musicVideo 管理员上传歌曲
             *          封装信息：musicId
             * @return int  返回执行结果：
             *                             1为成功，0为失败，-1为系统异常
             */
            public int addMusic(MusicVideo musicVideo){
                return 0;
            }
            /**
             * @param address 上传歌曲
             *          封装信息：musicId
             * @return int  返回执行结果：
             *                             1为成功，0为失败，-1为系统异常
             */
            public int upLoadMusicVideo(String address){
                return 0;
            }
            /**
             * @param musicVideoId 查看歌曲评论(consumer和admin）
             *                封装信息：musicId
             * @return <Comment> 返回对应歌曲的所有评论
             *
             */
            public List<Comment> selectCommentByMusicVideoId(String musicVideoId){
                return null;
            }
            /**
             * @param commentId 删除指定评论(admin）
             *                封装信息：commentId
             * @return int      返回执行结果：0、1、-1
             */
            public int deleteCommentByCommentId(String commentId) {
                return 0;
            }
}
