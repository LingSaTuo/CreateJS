const importClass = JavaImporter().importClass;
const importPackage = JavaImporter().importPackage;
function findViewById(id){
    var id = com.dvc.xml.ID.getID(id);
    var view = activity.getView()
    if(view==null)return view;
    return view.findViewById(id);
}
function setContentView(layout){
    var view = com.kingsatuo.xml2view.XView.createView(activity,"res/layout/"+layout+".xml",activity)
    activity.setContentView(view)
}

function setSupportActionBar(id){
    var toolbar = findViewById(id);
    activity.setSupportActionBar(toolbar);
}
function getSupportActionBar(){
    return activity.getSupportActionBar();
}