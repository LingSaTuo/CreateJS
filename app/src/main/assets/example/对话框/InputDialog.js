if (!("libs_inthis" in this)){
throw "请使用CreateJS加载"
}


//必须在主线程
libs_inthis.runOnUiThread(function(){
     var view = new android.widget.EditText(libs_inthis)
     var ad = new android.support.v7.app.AlertDialog.Builder(libs_inthis)
                  .setTitle("这是标题")
                  .setView(view)
                  .setPositiveButton("获取",function(v){
                     libs_inthis.sendMessage("提示","内容为  "+view.getText().toString())
                  })
                  .setNegativeButton("按钮2",null)
                		.setNeutralButton("按钮3",null)
                  .show()
})

