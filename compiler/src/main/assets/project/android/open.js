Autotip.on();//打开自动提示
Autotip.clear();//清除自定义的提示
Autotip.add("Autotip","add","add(tag,tip)")
Autotip.add("Autotip","on","");
Autotip.add("Autotip",new Autotip.Tip("off",""));

/*
Autotip.get(tag,index)//获取Tip 返回值Tip
Autotip.getSize(tag)//获取类为tag的方法数量
Autotip.add(tag,tip)//为类tag添加一个tip
Autotip.add(tag,value)//为函数tag添加一个value,如 Autotip.add("A","function A(){\n\n}")
Autotip.add(tag,name,value)//为类tag添加一个名为name的方法，点击补全时自动填入value
Autotip.getTips(tag)//获取类为tag的所有tip，返回值为List<Tip>
*/
/*
Tip :
    Autotip.Tip{
        public String name;
        public String value;
    }
*/