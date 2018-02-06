var name = ""//用于保存最后一次的不同的包名

//获取App类实例
var app = new com.lingsatuo.openapi.App(libs_inthis)
libs_inthis.startAS()//启用无障碍

//判断是否为21版本或更高，因为21版本中，以下旧式方法被移除
if(new com.lingsatuo.openapi.App(libs_inthis).getVersionCode()<21){

    //这是旧式方法，如果你发现应用的左上角显示Build  21或更高，这种方法将会报错
	var obj = com.lingsatuo.service.CreateJSService.getInstance()
	var service = com.lingsatuo.service.CreateJSService()
	 service.addNodeInfoListener(function(info){
    	 if(name!=info.getSimpleSource().getPackageName()){
       	   name=info.getSimpleSource().getPackageName()
	  	   var appname = app.getAppName(name)
      	   libs_inthis.sendMessage("包名/名字",name+"	/	"+appname)
         }
 })
}else{//如果是新版本

	//获取实例无障碍服务实例
	var ser = com.lingsatuo.service.CreateJSAccessibilityService

    //为防止重复监听，提前移除所有标签为NAME的监听
    ser.removeAllAccEventListener("NAME");

    //添加标签为NAME的监听，监听具有boolean返回值
	ser.addAccEventListener("NAME",function(event){
        //如果event是null，则直接返回false
        if(event==null)return false;

        //如果包名是null，则直接返回false
        var pkg = event.getPackageName()
        if(pkg==null)return false;

        //如果记录的上次名字等于本次的名字，就直接跳过打印返回false
        if(name==pkg)return false;

        //利用包名获取应用名称
        var appname = app.getAppName(name)

        //打印包名并返回true;
      	libs_inthis.sendMessage("包名/名字",pkg+"	/	"+appname)

        //把本次的包名赋值给上次
        name = pkg;
        return true;
        });

}