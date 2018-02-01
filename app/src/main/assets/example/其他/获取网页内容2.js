if (!("libs_inthis" in this)){
throw "请使用CreateJS加载"
}

libs_siutils.getString(libs_inthis,
                                    "https://gitee.com/projectxero/ca/raw/master/%E5%91%BD%E4%BB%A4%E5%8A%A9%E6%89%8B.js",
                                    function(values){
                                             if(values[1]=="-1"){
                                                 libs_inthis.sendMessage("错误","获取过程中发生了一个或多个错误  "+values[0])
                                             }else{
                                                 libs_inthis.sendMessage("提示",values[0],"#000000")
                                              }
                                    }
                                    ,0)