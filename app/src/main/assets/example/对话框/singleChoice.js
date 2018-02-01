if (!("libs_inthis" in this)){
throw "请使用CreateJS加载"
}

var Mfill = android.view.ViewGroup.LayoutParams.FILL_PARENT;//充满布局
var Mwrap = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
var MlayoutParams = new android.widget.LinearLayout.LayoutParams(Mfill,Mwrap,1);//布局属性。


var sele = singleChoice("标题",["item1","item2","item3","item4"],2)

libs_inthis.sendMessage("提示","你选择的是第  "+ (sele+1)+"  个")
function singleChoice(title,items,sele){
var dismiss = false
var select = 0;
select = sele==undefined?0:sele
if(sele>items.length)select = items.length-1
var root =new android.widget.RadioGroup(libs_inthis)

libs_inthis.runOnUiThread(function(){
for(var s in items){
 var i = new android.widget.RadioButton(libs_inthis)
 i.setLayoutParams(MlayoutParams);
 if(s==select)
 i.setChecked(true)
 i.setText(items[s])
 i.setId(s)
 root.addView(i)
}
   var ad = new android.support.v7.app.AlertDialog.Builder(libs_inthis)
                   .setTitle(title)
                   .setView(root)
                   .setPositiveButton("确定",null).show()
                  ad.setOnDismissListener(function(){
                  dismiss=true
                  })
                  root.setOnCheckedChangeListener(function(rg,id){
                  select = id
                  })
})
while(!dismiss){
java.lang.Thread.sleep(10)
}
return select;
}