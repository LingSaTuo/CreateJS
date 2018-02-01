if(("libs_inthis" in this)){
  var dialog = new com.lingsatuo.openapi.Dialog()
  var play = dialog.confirmAlert(libs_inthis,"提示 大小10MB","点下确定你就会听到一首歌，但需要联网，你确定吗?")
  if(play)
  a()
  else
  libs_inthis.sendMessage("提示","看来你不想听呢")
}else{
music()
}

function music(){
var player= new android.media.MediaPlayer()
libs_inthis.sendMessage("提示","正在准备播放")
try {
        player.reset();
        player.setDataSource("/sdcard/Download/reol.mp3");
        player.prepare();
        player.start();
        }catch(e){
         libs_inthis.sendMessage("错误",e.toString())
        }
}
function a(){
if(!new java.io.File("/sdcard/Download/reol.mp3").isFile())
new com.lingsatuo.openapi
            .Download(libs_inthis,
                      "http://createjs-1253269015.coscd.myqcloud.com/reol.mp3",//下载地址
                       "/sdcard/Download/reol.mp3",//保存地址
                       /*
                       * @progress 进度（非百分比）
                       * @max 总大小
                       * @finished 是否完成
                       * @e 错误，无错误时为null
                       */
                         function(progress,max,finished,e){
                               if(e!=null){
                                   libs_inthis.sendMessage("错误",e.toString(),"#d44336")
                                }else{
                                   libs_inthis.getLogView().clear();
                                   libs_inthis.sendMessage("正在下载",progress+"  /  "+max)
                                    if(finished){
                                       libs_inthis.sendMessage("提示","下载完成")
                                       music();
                                        }
                                 }
                       })
          else music();
    }