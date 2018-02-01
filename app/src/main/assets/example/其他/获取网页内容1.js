if (!("libs_inthis" in this)){
throw "请使用CreateJS加载"
}

libs_siutils.getString(libs_inthis,
                                    "https://gitee.com/projectxero/ca/raw/master/%E5%91%BD%E4%BB%A4%E5%8A%A9%E6%89%8B.js",
                                    new com.lingsatuo.utils.OnLoadingUriString(){
                                        onLoadingSuccess(strString){
                                          // libs_utils.saveFile("/sdcard/助手.js",strString)  //保存到本地
                                    libs_inthis.sendMessage("提示",strString,"#000000")
                                        }
                                    }
                                    /*
                                        onLoadingFaild(strString){
                                          libs_inthis.sendMessage("失败",strString,"#00ff00")
                                        }
                                    }


                                    */


                                    )
