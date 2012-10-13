package org.silk.checklist;



import java.util.List;

import org.silk.checklist.bo.AuditorBo;
import org.silk.checklist.db.DbHelper;
import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.ProductInfo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;


public class App extends Application {

    private DbHelper dbHelper;
    private AuditorBo auditorBo;
    private List<Auditor> auditorList;
    
    public boolean isNeedUpdate(){
    	return dbHelper.isNeedUpdate();
    }
    
    public List<Auditor> getAuditorList(boolean reload){
    	if(auditorList == null || reload)
    		reloadAuditor();
    	return this.auditorList;
    }
    
    private void reloadAuditor(){
    	auditorList = auditorBo.list();
    }

    @Override
    public void onCreate() {
        super.onCreate(); 
        dbHelper = new DbHelper(this);
        auditorBo = new AuditorBo(getDatabase());
    }
    public SQLiteDatabase getDatabase(){
    	return dbHelper.getWritableDatabase();
    }
    
    public DbHelper getDbHelper() {
		return dbHelper;
	}

	@Override
    public void onTerminate() {
        super.onTerminate();
        dbHelper.close();
    }   
//	public ProductInfo searchHalalProductInfo(String upc){
//    	ProductInfo productFound = new ProductInfo();
//    	productFound.setProduct("สบู่อนาดา บริษัทฮาลคิวจำกัด");
//    	productFound.setHalalId("790690051055");
//    	productFound.setHalalBeginDate("ตุลาคม 2555");
//    	productFound.setHalalExpDate("ตุลาคม 2558");
//    	productFound.setOtherInfo("ศูนย์ผู้บริโภค 085-047-5480, www.halkew.com");
//    	return productFound;
//    }
}

