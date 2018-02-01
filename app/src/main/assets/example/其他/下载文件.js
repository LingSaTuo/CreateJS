if (!("libs_inthis" in this)){
throw "请使用CreateJS加载"
}

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
                                        }
                                 }
                       })
