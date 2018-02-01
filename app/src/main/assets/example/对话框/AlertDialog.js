if (!("libs_inthis" in this)){
throw "请使用CreateJS加载"
}


//必须在主线程
libs_inthis.runOnUiThread(function(){
     var ad = new android.support.v7.app.AlertDialog.Builder(libs_inthis)
                  .setTitle("这是标题")
                  .setMessage("这是信息")
                  .setPositiveButton("按钮1",function(view){
                     libs_inthis.sendMessage("提示","你点击了按钮1")
                  })
                  .setNegativeButton("按钮2",null)
                		.setNeutralButton("按钮3",null)
                  .show()
})

